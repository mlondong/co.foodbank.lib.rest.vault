package co.com.foodbank.vault.v1.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public IVault createVault(@Valid VaultDTO dto)
            throws VaultNotFoundException {
        return service.create(dto);
    }



    /**
     * Method to find a Vault.
     * 
     * @param _id
     * @return {@code IVault}
     * @throws VaultNotFoundException
     */
    public IVault findById(String _id) throws VaultNotFoundException {
        return service.findById(_id);
    }



    /**
     * Method to update a vault.
     * 
     * @param dto
     * @param _id
     * @return {@code IVault}
     */
    public IVault updateVault(@Valid VaultDTO dto,
            @NotBlank @NotNull String _id) throws VaultNotFoundException {
        return service.update(dto, _id);
    }



    /**
     * Method to add Contributions Detail in Vault.
     * 
     * @param dto
     * @param _id
     * @return {@code IVault}
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    public IVault addDetailContributionInVault(@Valid DetailContributionDTO dto,
            @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {
        return service.addDetailContributionInVault(_id, dto);
    }



    /**
     * Method to add Contributions General in Vault.
     * 
     * @param dto
     * @param _id
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws SDKContributionServiceNotAvailableException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceIllegalArgumentException
     */
    public IVault addGeneralContributionInVault(
            @Valid GeneralContributionDTO dto, @NotBlank @NotNull String _id)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {
        return service.addGeneralContributionInVault(_id, dto);
    }

}
