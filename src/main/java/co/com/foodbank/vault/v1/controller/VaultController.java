package co.com.foodbank.vault.v1.controller;

import java.util.Collection;
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
import co.com.foodbank.user.sdk.exception.SDKUserServiceException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceIllegalArgumentException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceNotAvailableException;
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
     * @param idProvider
     * @return {@code IVault}
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
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
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    public IVault updateVault(@Valid VaultDTO dto,
            @NotBlank @NotNull String _id)
            throws VaultNotFoundException, JsonMappingException,
            JsonProcessingException, SDKUserServiceNotAvailableException,
            SDKUserServiceException, SDKUserServiceIllegalArgumentException {
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



    /**
     * Method to find all vaults
     * 
     * @return {@code IVaul}
     */
    public Collection<IVault> findAll() throws VaultNotFoundException {
        return service.findAll();
    }



    /**
     * Method to find vault by contact
     * 
     * @param contact
     * @return {@code Collection<IVault> }
     */
    public Collection<IVault> findByContact(@NotNull @NotBlank String contact) {
        return service.findByContact(contact);
    }



    /**
     * Method to find vault by district.
     * 
     * @param district
     * @return {@code Collection<IVault>}
     */
    public Collection<IVault> findByDistrict(@NotNull @NotBlank String district)
            throws VaultNotFoundException {
        return service.findByDistrict(district);
    }

}
