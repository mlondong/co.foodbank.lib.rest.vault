package co.com.foodbank.vault.exception;

public class VaultNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VaultNotFoundException(String email) {
        super(email);
    }
}
