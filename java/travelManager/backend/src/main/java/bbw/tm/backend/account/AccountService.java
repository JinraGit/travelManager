package bbw.tm.backend.account;

import bbw.tm.configuration.JWTConfiguration;
import bbw.tm.enums.Roles;
import bbw.tm.backend.events.AccountCreatedEvent;
import bbw.tm.role.Role;
import bbw.tm.role.RoleRepository;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final JWTConfiguration jwtConfiguration;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public String generateToken(Account account) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getId() + "")
                .claim("username", account.getUsername())
                .claim("roles", account.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .expirationTime(new Date(new Date().getTime() + jwtConfiguration.getExpiration()))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        try {
            JWSSigner signer = new MACSigner(jwtConfiguration.getSecret().getBytes(StandardCharsets.UTF_8));
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByEmailOrUsername(String email, String username) {
        return accountRepository.existsByUsernameOrEmail(email, username);
    }


    /**
     * Erstellung eines neuen Accounts mit Standardrolle "User".
     */
    public Account create(Account account) {
        //Passwort verschlüsseln
        account.setPassword(passwordEncoder.encode(account.
                getPassword()));

        account.setEnabled(false);

        // Standartrolle zuweisen, falls keine Rolle definiert ist
        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(Roles.USER.name())
                    .orElseThrow(() -> new RuntimeException("Default role User not found"));
            account.setRoles(Collections.singleton(userRole)); // Setzen der Rolle auf User
        }
        Account savedAccount = accountRepository.save(account);

        boolean isUser = savedAccount.getRoles().stream()
                .anyMatch(role -> Roles.USER.name().equalsIgnoreCase(role.getName()));

        if (isUser) {
            eventPublisher.publishEvent(new AccountCreatedEvent(savedAccount));
        }

        return savedAccount;
    }

    /**
     * Erstellung eines neuen Accounts und Erstellen einer Rolle
     */
    public Account createWithRole(Account account, String roleName) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        account.setRoles(Collections.singleton(role));
        return accountRepository.save(account);
    }

    /**
     * Die Rolle eines Accounts anpassen
     */


    @Transactional
    public Account updateRole(Integer accountId, String roleName) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        // Niemals Set.of(...) verwenden, da dieses nicht modifizierbar ist!
        account.setRoles(new HashSet<>(Set.of(role)));

        return accountRepository.save(account);
    }
}
