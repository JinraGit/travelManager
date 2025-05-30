package bbw.tm.backend.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {
    public static final String PATH = "/accounts";

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService,
                             AuthenticationManager authenticationManager,
                             AccountRepository accountRepository) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/signup")
    @SecurityRequirements
    @Operation(
            summary = "Erstellt ein neues Benutzerkonto",
            description = "Registriert einen neuen Benutzer basierend auf den angegebenen Daten im Request-Körper. " +
                    "Wenn die E-Mail oder der Benutzername bereits existieren, wird ein Konfliktfehler (HTTP 409) zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Das Benutzerkonto wurde erfolgreich erstellt."),
            @ApiResponse(responseCode = "409", description = "Das Benutzerkonto konnte nicht erstellt werden, da die E-Mail oder der Benutzername bereits existieren."),
            @ApiResponse(responseCode = "400", description = "Die Anfrage war ungültig. Validierungsfehler im Anfragetext.")
    })
    public ResponseEntity<?> signUp(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        // Überprüfen, ob die E-Mail oder der Benutzername bereits existieren
        if (accountService.existsByEmailOrUsername(accountRequestDTO.getEmail(), accountRequestDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-Mail oder Benutzername existiert bereits.");
        }

        // Account aus DTO mappen
        Account account = AccountMapper.fromDTO(accountRequestDTO);

        // Account erstellen (Event wird ausgelöst, wenn APPRENTICE vorhanden ist)
        Account createdAccount = accountService.create(account);        // Response bereitstellen
        AccountResponseDTO accountResponseDTO = AccountMapper.toDTO(createdAccount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Account wurde erfolgreich erstellt. Falls die Rolle APPRENTICE zugewiesen wurde, wird automatisch eine verknüpfte Person erstellt.",
                        "account", accountResponseDTO
                ));
    }


    @PostMapping("/signin")
    @SecurityRequirements
    @Operation(
            summary = "Meldet einen Benutzer an",
            description = "Authentifiziert den Benutzer basierend auf der angegebenen E-Mail-Adresse und dem Passwort. " +
                    "Bei erfolgreicher Authentifizierung wird ein Token zurückgegeben, das für nachfolgende Anfragen verwendet werden kann. " +
                    "Gibt bei ungültigen Anmeldedaten einen Fehler (HTTP 401) zurück."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Der Benutzer wurde erfolgreich angemeldet. Ein Token wird zurückgegeben."),
            @ApiResponse(responseCode = "401", description = "Die Anmeldung ist fehlgeschlagen. Account nicht aktiv oder ungültige E-Mail-Adresse/Passwort."),
            @ApiResponse(responseCode = "400", description = "Die Anfrage war ungültig. Validierungsfehler im Anfragetext.")
    })
    public ResponseEntity<?> signIn(@Valid @RequestBody AccountSignInDTO accountSignInDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountSignInDTO.getEmail(), accountSignInDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = (Account) authentication.getPrincipal();
            String token = accountService.generateToken(account);
            TokenResponseDTO dto = AccountMapper.toDTO(account, token);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            assert e.getReason() != null;
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

    @GetMapping
    @Operation(
            summary = "Gibt alle Benutzerkonten zurück",
            description = "Liefert eine Liste aller Benutzerkonten im System. " +
                    "Diese Funktion ist nützlich für administrative Zwecke und Benutzerübersichten."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste aller Benutzerkonten wurde erfolgreich abgerufen.")
    })
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDTO> accountDTOs = accounts.stream()
                .map(account -> {
                    AccountResponseDTO dto = AccountMapper.toDTO(account);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOs);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Gibt ein Benutzerkonto anhand der ID zurück",
            description = "Liefert die Details eines spezifischen Benutzerkontos basierend auf der angegebenen ID. " +
                    "Wenn kein Benutzerkonto mit der angegebenen ID gefunden wird, wird ein 404-Fehler zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Das Benutzerkonto wurde erfolgreich gefunden und zurückgegeben."),
            @ApiResponse(responseCode = "404", description = "Es wurde kein Benutzerkonto mit der angegebenen ID gefunden.")
    })    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + id));

        AccountResponseDTO accountDTO = AccountMapper.toDTO(account);

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/admin/create")
    @Operation(
            summary = "Erstellt ein Benutzerkonto als Administrator",
            description = "Erstellt ein Benutzerkonto mit der Rolle, die im Request spezifiziert wird. " +
                    "Nur zugänglich für Benutzer mit administrativen Berechtigungen. " +
                    "Wenn die angegebenen Daten ungültig sind oder das Konto bereits existiert, wird ein entsprechender Fehler zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Das Benutzerkonto wurde erfolgreich erstellt."),
            @ApiResponse(responseCode = "409", description = "Das Benutzerkonto konnte nicht erstellt werden, da die E-Mail oder der Benutzername bereits existieren."),
            @ApiResponse(responseCode = "400", description = "Die Anfrage war ungültig. Validierungsfehler im Anfragetext.")
    })
    public ResponseEntity<?> createAccountAsAdmin(@Valid @RequestBody AccountRequestDTO dto) {
        if (accountService.existsByEmailOrUsername(dto.getEmail(), dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Account account = AccountMapper.fromDTO(dto);
        account = accountService.createWithRole(account, dto.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toDTO(account));
    }

    @PutMapping("/{id}/role")
    @Operation(
            summary = "Aktualisiert die Rolle eines Benutzerkontos",
            description = "Ändert die Rolle eines bestehenden Benutzerkontos. " +
                    "Nur administrativ nutzbar. Wenn die angegebene Rolle ungültig ist oder das Benutzerkonto nicht existiert, wird ein entsprechender Fehler zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Die Rolle des Benutzerkontos wurde erfolgreich aktualisiert."),
            @ApiResponse(responseCode = "404", description = "Das Benutzerkonto wurde nicht gefunden."),
            @ApiResponse(responseCode = "400", description = "Ungültige Rolle angegeben.")
    })
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @Valid @RequestBody UpdateRoleDTO body) {
        Account updated = accountService.updateRole(id, body.getRole());
        return ResponseEntity.ok(AccountMapper.toDTO(updated));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Löschen eines Benutzerkontos",
            description = "Löscht ein bestehendes Benutzerkonto basierend auf der angegebenen ID. " +
                    "Wenn kein Benutzerkonto mit der angegebenen ID gefunden wird, wird ein 404-Fehler zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Benutzerkonto erfolgreich gelöscht."),
            @ApiResponse(responseCode = "404", description = "Kein Benutzerkonto mit der angegebenen ID gefunden.")
    })
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account nicht gefunden mit ID: " + id));

        accountRepository.delete(account);
        return ResponseEntity.noContent().build();
    }


}
