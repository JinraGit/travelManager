package bbw.tm.backend.account;

import bbw.tm.backend.role.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountConverter implements Converter<Jwt, AccountAuthenticationToken> {
    @Override
    public AccountAuthenticationToken convert(Jwt jwt) {
        int id = Integer.parseInt(jwt.getSubject());
        String username = jwt.getClaimAsString("username");

        // Sicherstellen, dass wir keine Null bekommen
        List<String> roles = Optional.ofNullable(jwt.getClaimAsStringList("roles"))
                .orElse(List.of());

        Set<Role> roleSet = roles.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name);
                    return role;
                })
                .collect(Collectors.toSet());

        // Authorities für Spring Security
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.toUpperCase())
                .toList();

        Account account = new Account();
        account.setId(id);
        account.setUsername(username);
        account.setRoles(roleSet);

        return new AccountAuthenticationToken(jwt, account, authorities);
    }
}
