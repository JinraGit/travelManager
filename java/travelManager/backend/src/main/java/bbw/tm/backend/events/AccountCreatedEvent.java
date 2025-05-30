package bbw.tm.backend.events;

import bbw.tm.backend.account.Account;
import lombok.Getter;

@Getter
public class AccountCreatedEvent {
    private final Account account;

    public AccountCreatedEvent(Account account) {
        this.account = account;
    }
}