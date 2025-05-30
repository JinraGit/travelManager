package bbw.tm.backend.configuration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
    private String[] allowedUrls;
    private String[] authUrls;
    private String[] allowedOrigins;
    @Override
    public String toString() {
        return "AppConfiguration{" +
                "allowedUrls=" + Arrays.toString(allowedUrls) +
                ", authUrls=" + Arrays.toString(authUrls) +
                ", allowedOrigins=" + Arrays.toString(allowedOrigins) +
                '}';
    }
}
