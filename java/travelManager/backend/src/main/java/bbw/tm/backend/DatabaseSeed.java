package bbw.tm.backend;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.account.AccountRepository;
import bbw.tm.backend.account.AccountService;
import bbw.tm.backend.enums.Roles;
import bbw.tm.backend.role.Role;
import bbw.tm.backend.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("!test")
public class DatabaseSeed implements CommandLineRunner {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseSeed(AccountService accountService,
                        AccountRepository accountRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional
    @Override
    public void run(String... args) {
        // Rollen erstellen
        createRoleIfNotExists(Roles.ADMIN.name());
        createRoleIfNotExists(Roles.USER.name());

        // Rollen finden
        Role adminRole = fetchRole(Roles.ADMIN.name());
        Role userRole = fetchRole(Roles.USER.name());

        // Admin und Coaches erstellen
        createAccountIfNotExists("admin@bbw.ch", "Admin", "12345678", adminRole);
        createAccountIfNotExists("user@bbw.ch", "Coach", "12345678", userRole);
        createAccountIfNotExists("luigi.cavuoti@bbw.ch", "Luigi", "12345678", userRole);
        createAccountIfNotExists("lars.hostettler@bbw.ch", "Lars", "12345678", userRole);
        createAccountIfNotExists("rasim.arzic@bbw.ch", "Rasim", "12345678", userRole);


    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }


    private Role fetchRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rolle " + roleName + " nicht gefunden"));
    }

    private Account createAccountIfNotExists(String email, String username, String password, Role role) {
        return accountRepository.findByEmail(email).orElseGet(() -> {
            Account account = new Account();
            account.setEmail(email);
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password)); // Passwort nur bei Account-Erstellung verschlüsseln
            account.setRoles(Set.of(role));
            return accountRepository.save(account);
        });
    }
}
