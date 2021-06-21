package co.com.foodbank.vault.restcontroller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.contribution.dto.DetailContributionDTO;
import co.com.foodbank.contribution.dto.GeneralContributionDTO;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceIllegalArgumentException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceNotAvailableException;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.exception.VaultNotFoundException;
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



    /**
     * method to create a Vault.
     * 
     * @param dto
     * @return {@code ResponseEntity<IVault>}
     */
    @PostMapping(value = "/createVault",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> createVAult(
            @RequestBody @Valid VaultDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(controller.createVault(dto));
    }



    /**
     * Method to update a Vault.
     * 
     * @param dto
     * @return {@code ResponseEntity<IVault>}
     */
    @PutMapping(value = "/updateVault/id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> updateVault(
            @PathVariable("id") @NotBlank @NotNull String _id,
            @RequestBody @Valid VaultDTO dto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.updateVault(dto, _id));
    }



    /**
     * method to find Vault.
     * 
     * @param _id
     * @return {@code IVault}
     * @throws VaultNotFoundException
     */
    @GetMapping(value = "/id/{id}")
    public IVault findById(@PathVariable("id") @NotBlank @NotNull String _id)
            throws VaultNotFoundException {
        return controller.findById(_id);
    }



    /**
     * Method to add Contributions in Vault.
     * 
     * @param _id
     * @return {@code ResponseEntity<IVault> }
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @PutMapping(value = "/add-DetailContribution/vault-id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> addContributionDetailInVault(
            @RequestBody @Valid DetailContributionDTO dto,
            @PathVariable("id") @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.addDetailContributionInVault(dto, _id));
    }


    /**
     * Method to add Contributions in Vault.
     * 
     * @param _id
     * @return {@code ResponseEntity<IVault> }
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @PutMapping(value = "/add-GeneralContribution/vault-id/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<IVault> addContributionGeneralInVault(
            @RequestBody @Valid GeneralContributionDTO dto,
            @PathVariable("id") @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(controller.addGeneralContributionInVault(dto, _id));
    }


}
