package co.com.foodbank.vault.restcontroller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.v1.controller.VaultController;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.restcontroller
 *         31/05/2021
 */
@RestController
@RequestMapping(value = "/vault")
@Validated
public class VaultRestController {


    @Autowired
    public VaultController controller;



    @PostMapping(value = "/createVault",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> createVolunter(
            @RequestBody @Valid VaultDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(controller.createVault(dto));
    }

}
