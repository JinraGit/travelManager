package bbw.tm.backend.account;

import bbw.tm.backend.role.Role;

import java.util.stream.Collectors;

public class AccountMapper {
    public static Account fromDTO(AccountRequestDTO dto) {
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setEmail(dto.getEmail());
        return account;
    }

    public static AccountResponseDTO toDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setEmail(account.getEmail());
        dto.setRoles(account.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return dto;
    }    public static TokenResponseDTO toDTO(Account account, String token) {
        TokenResponseDTO dto = new TokenResponseDTO();
        dto.setAccount(toDTO(account));
        dto.setToken(token);
        return dto;
    }
}
