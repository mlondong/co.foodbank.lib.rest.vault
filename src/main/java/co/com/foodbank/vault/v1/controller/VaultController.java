package co.com.foodbank.vault.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.service.VaultService;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.v1.controller
 *         2/06/2021
 */
@Controller
public class VaultController {

    @Autowired
    private VaultService service;



    /**
     * Methd to create an Vault.
     * 
     * @param dto
     * @return {@code IVault}
     */
    public IVault createVault(VaultDTO dto) {
        return service.create(dto);
    }

}
