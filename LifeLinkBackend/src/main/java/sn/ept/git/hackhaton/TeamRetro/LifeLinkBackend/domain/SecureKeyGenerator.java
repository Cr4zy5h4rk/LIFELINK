package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class SecureKeyGenerator {
    public static String generateSecureKey() {
        // Générer une clé sécurisée pour HS512
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Encoder la clé en Base64 pour pouvoir la stocker facilement
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static void main(String[] args) {
        String secureKey = generateSecureKey();
        System.out.println("Secure JWT Key: " + secureKey);
    }
}