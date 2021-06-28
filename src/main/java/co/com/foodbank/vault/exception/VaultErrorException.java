package co.com.foodbank.vault.exception;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.exception 27/06/2021
 */
public class VaultErrorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public VaultErrorException(String err) {
        super(err);
    }
}
