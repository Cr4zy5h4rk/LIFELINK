package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.Gender;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonorService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.JwkUtils;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl.JwtTokenProvider;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl.MedicalRecordGenerator;

import java.io.IOException;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/delegate")
@CrossOrigin(origins = "*")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

//    // OIDC Provider details
//    private String ISSUER = "http://localhost:8088";



    private final static String REDIRECT_URI = "http://localhost:4200/callback";

    @Value("${esignet.token.url:http://localhost:8088/v1/esignet/oauth/v2/token}")
    private String TOKEN_URL;

    @Value("${esignet.userinfo.url:http://localhost:8088/v1/esignet/oidc/userinfo}")
    private String USERINFO_URL;

    @Value("${PRIVATE_KEY_JWK}")
    private String privateKeyJwk;

    @Value("${CLIENT_ID}")
    private String CLIENT_ID ;

    private final RestTemplate restTemplate;
    private final DonorService donorService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MedicalRecordGenerator medicalRecordGenerator;

    private final ResourceLoader resourceLoader;

    @Autowired
    public AuthController(
            RestTemplate restTemplate,
            DonorService donorService,
            JwtTokenProvider jwtTokenProvider,
            MedicalRecordGenerator medicalRecordGenerator,
            ResourceLoader resourceLoader) {
        this.restTemplate = restTemplate;
        this.donorService = donorService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.resourceLoader = resourceLoader;
        this.medicalRecordGenerator = medicalRecordGenerator;
    }

    @GetMapping("/login-bridge")
    public ResponseEntity<Resource> loginBridge() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/login-bridge.html");

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(){
        return  ResponseEntity.ok().body(Optional.empty());
    }

    @GetMapping("/fetchUserInfo")
    public ResponseEntity<?> fetchUserInfo(@RequestParam String code) {
        try {
            log.debug("Callback with code: {}", code);

            // Convert JWK to PrivateKey
            PrivateKey privateKey = JwkUtils.jwkToPrivateKey(privateKeyJwk);

            // Create client assertion
            String clientAssertion = createClientAssertion(privateKey);

            // Prepare token request
            MultiValueMap<String, String> tokenRequestParams = new LinkedMultiValueMap<>();
            tokenRequestParams.add("grant_type", "authorization_code");
            tokenRequestParams.add("code", code);
            tokenRequestParams.add("client_id", CLIENT_ID);
            tokenRequestParams.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
            tokenRequestParams.add("client_assertion", clientAssertion);
            tokenRequestParams.add("redirect_uri", REDIRECT_URI);

            // Exchange code for tokens
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenRequestParams, tokenHeaders);
            System.out.println("tokenRequest: " + tokenRequest);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    tokenRequest,
                    Map.class
            );
            if (tokenResponse.getStatusCode().is2xxSuccessful()) {
                // Extract access token
                Map<String, Object> tokenBody = tokenResponse.getBody();
                String accessToken = (String) tokenBody.get("access_token");
                log.debug("Access token obtained successfully");

                // Get user info
                HttpHeaders userinfoHeaders = new HttpHeaders();
                userinfoHeaders.add("Authorization", "Bearer " + accessToken);
                HttpEntity<?> userinfoRequest = new HttpEntity<>(userinfoHeaders);

                ResponseEntity<String> userinfoResponse = restTemplate.exchange(
                        USERINFO_URL,
                        HttpMethod.GET,
                        userinfoRequest,
                        String.class
                );

                String userInfoStr = userinfoResponse.getBody();
                log.debug("User info response received: {}", userinfoResponse.getStatusCode());

                // Parse userinfo response (handle JWT or JSON)
                JsonNode userInfo;
                if (userInfoStr.split("\\.").length == 3) {
                    // It's a JWT, decode it
                    String decodedPayload = JwkUtils.decodeJwtPayload(userInfoStr);
                    ObjectMapper mapper = new ObjectMapper();
                    userInfo = mapper.readValue(decodedPayload, JsonNode.class);
                } else {
                    // It's a JSON
                    ObjectMapper mapper = new ObjectMapper();
                    userInfo = mapper.readValue(userInfoStr, JsonNode.class);
                }

                // Map user info to DonorDTO
                DonorDTO donorDTO = mapUserInfoToDonorDTO(userInfo);

                DonorDTO savedDonor = this.donorService.save(donorDTO);

                String jwtToken = this.jwtTokenProvider.generateToken(savedDonor);

                // Créer une réponse avec les informations utilisateur et le token
                Map<String, Object> response = new HashMap<>();
                response.put("user", savedDonor);
                response.put("token", jwtToken);

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorBody = tokenResponse.getBody();
                return ResponseEntity
                        .status(tokenResponse.getStatusCode())
                        .body(errorBody);
            }
        } catch (Exception e) {
            log.error("Error during OIDC authentication: ", e);
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    private String createClientAssertion(PrivateKey privateKey) {
        long now = System.currentTimeMillis() / 1000;

        return Jwts.builder()
                .setIssuer(CLIENT_ID)
                .setSubject(CLIENT_ID)
                .setAudience(TOKEN_URL)
                .setExpiration(new Date((now + 300) * 1000)) // 5 minutes from now
                .setIssuedAt(new Date(now * 1000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public DonorDTO mapUserInfoToDonorDTO(JsonNode userInfo) {
        DonorDTO donorDTO = new DonorDTO();

        // Map basic user information
        donorDTO.setFirstName(userInfo.get("name").asText().split(" ")[0]);
        donorDTO.setEmail(userInfo.get("email").asText());
        donorDTO.setLastName(userInfo.get("name").asText().split(" ")[1]);



        // Convert birthdate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.parse(userInfo.get("birthdate").asText(), formatter);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of("Africa/Dakar"));
        donorDTO.setBirthDate(zonedDateTime);

        // Add additional information
        donorDTO.setId(userInfo.get("phone_number").asText());
        donorDTO.setPhoneNumber(userInfo.get("phone_number").asText());
        donorDTO.setGender(Gender.valueOf(userInfo.get("gender").asText().toUpperCase()));
        Random random = new Random();
        donorDTO.setFidelityPoints(random.nextInt(50, 250));
        donorDTO.setSize(1.75);
        donorDTO.setWeight(70.0);
        if (userInfo.get("phone_number").asText().equals("+221772345678")){
            donorDTO.setPicture("https://img.freepik.com/free-photo/casual-young-african-man-smiling-isolated-white_93675-128895.jpg");
        }
        if (userInfo.get("phone_number").asText().equals("+221773456789")){
            donorDTO.setPicture("https://cdn.standardmedia.co.ke/images/tuesday/nwpg2ujktut5l5f572e6850c16.jpg");
        }
        if (userInfo.get("phone_number").asText().equals("+221771234567")){
            donorDTO.setPicture("https://static.vecteezy.com/system/resources/previews/008/957/223/non_2x/muslim-female-doctor-avatar-wearing-hijab-and-stethoscope-clipart-icon-in-flat-design-vector.jpg");
        }
        donorDTO.setReceiveOtpForBloodRequest(false);

        // Ces informations proviennent normalement du systeme tier de carnet medical
        //Ils ont ete rempli pour les besoins d'illustrations

        BloodType[] bloodTypes = BloodType.values();
        ResusType[] resusTypes = ResusType.values();



        int bloodTypeIndex = random.nextInt(bloodTypes.length);
        int resusTypeIndex = random.nextInt(resusTypes.length);

        donorDTO.setBloodType(bloodTypes[bloodTypeIndex]);
        donorDTO.setResusType(resusTypes[resusTypeIndex]);

        // Générer aléatoirement un carnet médical parmi les 3 modèles
        String medicalRecord = medicalRecordGenerator.generateRandomMedicalRecord();

        // Mettre à jour le groupe sanguin dans le carnet médical pour correspondre au donneur
        String bloodTypeStr = donorDTO.getBloodType().toString();
        String resusTypeStr = donorDTO.getResusType().toString().equals("POSITIVE") ? "+" : "-";
        medicalRecord = medicalRecordGenerator.updateBloodType(medicalRecord, bloodTypeStr, resusTypeStr);

        // Stocker le carnet médical dans le donneur
        donorDTO.setMedicalData(medicalRecord);



        return donorDTO;
    }
}