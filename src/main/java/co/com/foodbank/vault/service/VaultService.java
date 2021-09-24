package co.com.foodbank.vault.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import co.com.foodbank.address.dto.Address;
import co.com.foodbank.contribution.dto.DetailContributionDTO;
import co.com.foodbank.contribution.dto.GeneralContributionDTO;
import co.com.foodbank.contribution.dto.interfaces.IContribution;
import co.com.foodbank.contribution.dto.response.DetailContributionData;
import co.com.foodbank.contribution.dto.response.GeneralContributionData;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceIllegalArgumentException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceNotAvailableException;
import co.com.foodbank.contribution.sdk.model.ResponseContributionData;
import co.com.foodbank.contribution.sdk.service.SDKContributionService;
import co.com.foodbank.contribution.state.ContributionData;
import co.com.foodbank.contribution.state.Pending;
import co.com.foodbank.country.dto.Country;
import co.com.foodbank.user.sdk.exception.SDKUserServiceException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceIllegalArgumentException;
import co.com.foodbank.user.sdk.exception.SDKUserServiceNotAvailableException;
import co.com.foodbank.user.sdk.service.SDKUserService;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.dto.interfaces.IVault;
import co.com.foodbank.vault.exception.VaultErrorException;
import co.com.foodbank.vault.exception.VaultNotFoundException;
import co.com.foodbank.vault.repository.VaultRepository;
import co.com.foodbank.vault.util.ParametersVault;
import co.com.foodbank.vault.v1.model.Vault;

@Service
@Transactional
public class VaultService {

    @Autowired
    private VaultRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    @Qualifier("sdkContribution")
    private SDKContributionService sdkContribution;

    @Autowired
    @Qualifier("sdkUser")
    private SDKUserService sdkUser;



    /**
     * Method to create an Vault.
     * 
     * @param dto
     * @param idProvider
     * @return {@code IValut}
     * @throws VaultNotFoundException
     */
    public IVault create(@Valid VaultDTO dto) throws VaultNotFoundException {
        return repository.save(this.setVault(dto));
    }



    /**
     * Method to build Vault.
     * 
     * @param dto
     * @return {@code Vault}
     */
    private Vault setVault(VaultDTO dto) {

        Address address = modelMapper.map(dto.getAddress(), Address.class);
        Country country =
                modelMapper.map(dto.getAddress().getCountry(), Country.class);
        address.setCountry(country);

        Vault vault = modelMapper.map(dto, Vault.class);
        vault.setAddress(address);
        vault.setContribution(this.emptyContributions());
        return vault;
    }



    private Collection<IContribution> emptyContributions() {
        return new ArrayList<IContribution>();
    }



    /**
     * Method to find a Vault.
     * 
     * @param _id
     * @return {@code Vault}
     * @throws VaultNotFoundException
     */
    public Vault findById(String _id) throws VaultNotFoundException {
        return repository.findById(_id)
                .orElseThrow(() -> new VaultNotFoundException(
                        _id + ParametersVault.MESSAGE));
    }



    /**
     * Method to update a Vault.
     * 
     * @param _id
     * @return {@code IVault}
     * @throws VaultNotFoundException
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    public Vault update(VaultDTO dto, String _id)
            throws VaultNotFoundException, JsonMappingException,
            JsonProcessingException, SDKUserServiceNotAvailableException,
            SDKUserServiceException, SDKUserServiceIllegalArgumentException {

        /* UPDATE VAULT IN VAULT */
        Vault vaultData = findById(_id);
        vaultData.setAddress(modelMapper.map(dto.address, Address.class));
        vaultData.setContact(dto.getContact());
        vaultData.setPhones(dto.getPhones());
        vaultData.setId(_id);
        Vault result = repository.save(vaultData);

        /* UPDATE VAULT IN USER */
        sdkUser.updateVaultInProvider(modelMapper.map(result, VaultDTO.class),
                _id);

        return result;
    }



    /**
     * Method to add Contributions in Vault.
     * 
     * @param _id
     * @param dto
     * @return {@code IVault}
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     */
    public IVault addDetailContributionInVault(String _id,
            DetailContributionDTO dto)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException,
            SDKUserServiceNotAvailableException, SDKUserServiceException,
            SDKUserServiceIllegalArgumentException {

        /** FIND A VAULT IF EXIST */
        Vault resultVault = this.findById(_id);

        /** CREATE A CONTRIBUTION WITH SDK CONTRIBUTION */
        ResponseContributionData responseContribution =
                sdkContribution.create(dto);


        /** ADD CONTRIBUTION IN VAULT */
        resultVault.addContribution(modelMapper.map(responseContribution,
                DetailContributionData.class));

        /** SAVE CONTRIBUTION IN VAULT */
        Vault responseVault = repository.save(resultVault);


        /** SAVE CONTRIBUTION IN USER VAULT */
        sdkUser.updateContribution(
                modelMapper.map(responseContribution, ContributionData.class),
                _id, responseContribution.getId());


        return responseVault;


    }



    /**
     * Method to create an contribution in vault
     * 
     * @param _id id del vault
     * @param dto
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws SDKContributionServiceNotAvailableException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     */
    public IVault addGeneralContributionInVault(String idVault,
            GeneralContributionDTO dto)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException,
            SDKUserServiceNotAvailableException, SDKUserServiceException,
            SDKUserServiceIllegalArgumentException {

        /** FIND A VAULT IF EXIST */
        Vault vault = this.findById(idVault);

        /** CREATE A CONTRIBUTION WITH SDK CONTRIBUTION */
        ResponseContributionData responseContribution =
                sdkContribution.create(dto);

        /** ADD CONTRIBUTION IN VAULT */
        vault.addContribution(modelMapper.map(responseContribution,
                GeneralContributionData.class));

        /** SAVE CONTRIBUTION IN VAULT */
        Vault responseVault = repository.save(vault);

        /** SAVE CONTRIBUTION IN USER VAULT */
        sdkUser.updateContribution(
                modelMapper.map(responseContribution, ContributionData.class),
                idVault, responseContribution.getId());

        return responseVault;
    }



    /**
     * Method to find all vaults
     * 
     * @return {@code IVaul}
     */
    public Collection<IVault> findAll() throws VaultNotFoundException {
        return repository.findAll().stream()
                .map(d -> modelMapper.map(d, IVault.class))
                .collect(Collectors.toList());
    }


    /**
     * Method to find vault by contact
     * 
     * @param contact
     * @return {@code Collection<IVault> }
     */
    public Collection<IVault> findByContact(String contact)
            throws VaultNotFoundException {
        return repository.findByContact(contact).stream()
                .map(d -> modelMapper.map(d, IVault.class))
                .collect(Collectors.toList());
    }


    /**
     * Method to find vault by district.
     * 
     * @param district
     * @return {@code Collection<IVault>}
     */
    public Collection<IVault> findByDistrict(String district)
            throws VaultNotFoundException {

        Collection<Vault> response = repository.findByDistrict(district);



        return response.stream().map(d -> modelMapper.map(d, IVault.class))
                .collect(Collectors.toList());
    }


    /**
     * Method t update a Detail Contribution in the current Vault
     * 
     * @param dto
     * @param idVault
     * @param idContribution
     * @return {@code IVault}
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     * @throws VaultErrorException
     */
    public IVault updateDC(DetailContributionDTO dto, String idVault,
            String idContribution) throws JsonMappingException,
            JsonProcessingException, SDKUserServiceNotAvailableException,
            SDKUserServiceException, SDKUserServiceIllegalArgumentException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException,
            VaultErrorException {

        /** FIND A VAULT IF EXIST */
        Vault vault = this.findById(idVault);


        /** CHECK IF CONTRIBUTION IS PENDING STATE */
        IContribution found = getContributionInVault(vault, idContribution);
        this.checkStatePending(found);


        /** UPDATE A CONTRIBUTION WITH SDK CONTRIBUTION */
        ResponseContributionData responseContribution =
                sdkContribution.update(dto, idContribution);


        /** FIND CONTRIBUTION IN VAULT AND REMOVE IT THEN ADD */
        vault.getContribution().remove(found);

        vault.getContribution().add(modelMapper.map(responseContribution,
                DetailContributionData.class));


        /** SAVE CONTRIBUTION IN VAULT */
        Vault responseVault = repository.save(vault);

        /** UPDATE CONTRIBUTION IN USER VAULT */
        sdkUser.updateContribution(
                modelMapper.map(responseContribution, ContributionData.class),
                idVault, responseContribution.getId());
        return responseVault;

    }


    /**
     * Method to check the estate in contribution state pending
     * 
     * @param found
     * @throws VaultErrorException
     */
    private void checkStatePending(IContribution found)
            throws VaultErrorException {
        if (!(found.getState() instanceof Pending)) {
            throw new VaultErrorException(
                    found.getId() + ParametersVault.MESSAGE_ERR_CONTRIB);
        }

    }



    private IContribution getContributionInVault(Vault vault,
            String idContribution) {

        Optional<IContribution> found = vault.getContribution().stream()
                .filter(d -> d.getId().equals(idContribution)).findFirst();

        if (found.isEmpty()) {
            throw new VaultNotFoundException(
                    idContribution + ParametersVault.MESSAGE_ERR);
        }

        return found.get();
    }



    /**
     * Method to update a General Contribution.
     * 
     * @param dto
     * @param idVault
     * @param idContribution
     * @return {@code IVault}
     * @throws SDKUserServiceIllegalArgumentException
     * @throws SDKUserServiceException
     * @throws SDKUserServiceNotAvailableException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws VaultErrorException
     * @throws SDKContributionServiceIllegalArgumentException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceNotAvailableException
     */
    public IVault updateGC(GeneralContributionDTO dto, String idVault,
            String idContribution) throws JsonMappingException,
            JsonProcessingException, SDKUserServiceNotAvailableException,
            SDKUserServiceException, SDKUserServiceIllegalArgumentException,
            VaultErrorException, SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {

        /** FIND A VAULT IF EXIST */
        Vault vault = this.findById(idVault);


        /** CHECK IF CONTRIBUTION IS PENDING STATE */
        IContribution found = getContributionInVault(vault, idContribution);
        this.checkStatePending(found);


        /** UPDATE A CONTRIBUTION WITH SDK CONTRIBUTION */
        ResponseContributionData responseContribution =
                sdkContribution.update(dto, idContribution);


        /** FIND CONTRIBUTION IN VAULT AND REMOVE IT THEN ADD */
        vault.getContribution().remove(found);

        vault.getContribution().add(modelMapper.map(responseContribution,
                GeneralContributionData.class));


        /** SAVE CONTRIBUTION IN VAULT */
        Vault responseVault = repository.save(vault);

        /** UPDATE CONTRIBUTION IN USER VAULT */
        sdkUser.updateContribution(
                modelMapper.map(responseContribution, ContributionData.class),
                idVault, responseContribution.getId());
        return responseVault;
    }



}
