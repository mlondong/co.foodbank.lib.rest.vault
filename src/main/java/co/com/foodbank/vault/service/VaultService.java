package co.com.foodbank.vault.service;

import java.util.ArrayList;
import java.util.Collection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.foodbank.address.dto.Address;
import co.com.foodbank.contribution.dto.IContribution;
import co.com.foodbank.country.dto.Country;
import co.com.foodbank.vault.dto.IVault;
import co.com.foodbank.vault.dto.VaultDTO;
import co.com.foodbank.vault.repository.VaultRepository;
import co.com.foodbank.vault.v1.model.Vault;

@Service
public class VaultService {

    @Autowired
    private VaultRepository repository;

    @Autowired
    private ModelMapper modelMapper;



    /**
     * Method to create an Vault.
     * 
     * @param dto
     * @return {@code IValut}
     */
    public IVault create(VaultDTO dto) {
        return repository.save(this.setVault(dto));
    }



    /**
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

}
