package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OAuthDelegateApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthDelegateApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}