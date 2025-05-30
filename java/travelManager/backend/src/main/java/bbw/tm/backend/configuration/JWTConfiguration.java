package bbw.tm.backend.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfiguration {
    private String secret;
    private String algorithm;
    private int expiration;

    @Override
    public String toString() {
        return "JWTConfiguration{" +
                "secret='" + secret + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
