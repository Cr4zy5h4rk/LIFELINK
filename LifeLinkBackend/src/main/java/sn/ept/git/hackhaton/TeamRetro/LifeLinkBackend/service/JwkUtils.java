package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;

/**
 * Utilities pour la conversion de clés JWK
 */
public class JwkUtils {

    /**
     * Convertit une clé JWK en PrivateKey Java
     *
     * @param jwkJson Le JWK au format JSON
     * @return Une instance de PrivateKey
     */
    public static PrivateKey jwkToPrivateKey(String jwkJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jwkNode = mapper.readTree(jwkJson);

        // Extraction des paramètres de la clé RSA du JWK
        String n = jwkNode.get("n").asText();
        String d = jwkNode.get("d").asText();

        // Décodage sécurisé pour les paramètres RSA
        byte[] modulusBytes = base64UrlDecode(n);
        byte[] privateExponentBytes = base64UrlDecode(d);

        // Conversion en BigInteger pour les paramètres RSA
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger privateExponent = new BigInteger(1, privateExponentBytes);

        // Création de la spécification de clé RSA
        RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);

        // Génération de la clé privée
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    /**
     * Décode un JWT et extrait la charge utile (payload)
     *
     * @param jwtToken Le token JWT
     * @return La charge utile du JWT décodée sous forme de chaîne JSON
     */
    public static String decodeJwtPayload(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Format JWT invalide");
        }

        String payload = parts[1];
        byte[] decodedBytes = base64UrlDecode(payload);
        return new String(decodedBytes);
    }

    /**
     * Décode une chaîne Base64URL en tableau d'octets de manière sécurisée
     * Cette méthode gère correctement le format Base64URL sans padding utilisé par JWK/JWT
     *
     * @param base64UrlString La chaîne Base64URL à décoder
     * @return Le tableau d'octets décodé
     */
    private static byte[] base64UrlDecode(String base64UrlString) {
        // Convertir de Base64URL à Base64 standard
        String base64String = base64UrlString
                .replace('-', '+')
                .replace('_', '/');

        // Ajouter le padding si nécessaire
        switch (base64String.length() % 4) {
            case 0:
                // Aucun padding nécessaire
                break;
            case 2:
                base64String += "==";
                break;
            case 3:
                base64String += "=";
                break;
            case 1:
                // Cas non standard - le padding ne peut pas être corrigé
                throw new IllegalArgumentException("Longueur Base64 invalide : " + base64String.length());
        }

        // Décoder la chaîne Base64 standard
        return Base64.getDecoder().decode(base64String);
    }
}