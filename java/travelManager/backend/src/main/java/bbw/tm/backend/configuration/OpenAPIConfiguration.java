package bbw.tm.backend.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Travelmanager API - Backend",
                version = "0.0.1",
                description = "Example",
                termsOfService = "https://www.bbcag.ch",
                contact = @Contact(
                        name = "Rasim Arzic & Lars Hostettler",
                        email = "rasim.arzic@bbcag.ch, lars.hostettler@bbcag.ch",
                        url = "https://berufsbildungscenter.ch/kontakt"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        security = @SecurityRequirement(name = "Authorization")
)
public class OpenAPIConfiguration {
}
