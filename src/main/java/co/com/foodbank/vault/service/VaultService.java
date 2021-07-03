package co.com.foodbank.vault.service;

import java.util.ArrayList;
import java.util.Collection;
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
import co.com.foodbank.contribution.dto.ContributionData;
import co.com.foodbank.contribution.dto.DetailContributionDTO;
import co.com.foodbank.contribution.dto.DetailContributionData;
import co.com.foodbank.contribution.dto.GeneralContributionDTO;
import co.com.foodbank.contribution.dto.GeneralContributionData;
import co.com.foodbank.contribution.dto.IContribution;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceIllegalArgumentException;
import co.com.foodbank.contribution.sdk.exception.SDKContributionServiceNotAvailableException;
import co.com.foodbank.contribution.sdk.model.ResponseContributionData;
import co.com.foodbank.contribution.sdk.service.SDKContributionService;
import co.com.foodbank.contribution.state.Pending;
import co.com.foodbank.country.dto.Country;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.exception.VaultNotFoundException;
import co.com.foodbank.vault.repository.VaultRepository;
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



    private static final String MESSAGE = " id not found.";


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
                .orElseThrow(() -> new VaultNotFoundException(_id + MESSAGE));
    }



    /**
     * Method to update a Vault.
     * 
     * @param _id
     * @return {@code IVault}
     * @throws VaultNotFoundException
     */
    public Vault update(VaultDTO dto, String _id)
            throws VaultNotFoundException {

        Vault vaultData = findById(_id);
        vaultData.setAddress(modelMapper.map(dto.address, Address.class));
        vaultData.setContact(dto.getContact());
        vaultData.setPhones(dto.getPhones());
        vaultData.setId(_id);
        return repository.save(vaultData);
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
     */
    public IVault addDetailContributionInVault(String _id,
            DetailContributionDTO dto)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {

        // Find Vault
        Vault vauldDb = this.findById(_id);
        ResponseContributionData response = sdkContribution.create(dto);
        DetailContributionData result =
                modelMapper.map(response, DetailContributionData.class);
        vauldDb.addContribution(result);

        return repository.save(vauldDb);


    }



    /**
     * @param _id
     * @param dto
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws SDKContributionServiceNotAvailableException
     * @throws SDKContributionServiceException
     * @throws SDKContributionServiceIllegalArgumentException
     */
    public IVault addGeneralContributionInVault(String _id,
            GeneralContributionDTO dto)
            throws JsonMappingException, JsonProcessingException,
            SDKContributionServiceNotAvailableException,
            SDKContributionServiceException,
            SDKContributionServiceIllegalArgumentException {

        Vault vauldDb = this.findById(_id);
        ResponseContributionData response = sdkContribution.create(dto);
        GeneralContributionData result =
                modelMapper.map(response, GeneralContributionData.class);
        vauldDb.addContribution(result);

        return repository.save(vauldDb);
    }



    /**
     * This method is temporal because the state is lost when try to use
     * modelmapper because the interface is not deserialize, here we need to
     * change dto an others things.
     * 
     * @param contribution
     * @return {@code ContributionData}
     */
    private ContributionData keepStatePending(
            ResponseContributionData contribution) {
        ContributionData common =
                modelMapper.map(contribution, ContributionData.class);
        Pending pendingState = new Pending();
        pendingState.pending(common);
        return common;
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



}
