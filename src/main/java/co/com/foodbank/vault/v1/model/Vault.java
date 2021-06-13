package co.com.foodbank.vault.v1.model;

import java.util.Collection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import co.com.foodbank.address.dto.IAddress;
import co.com.foodbank.contribution.dto.IContribution;
import co.com.foodbank.vault.dto.IVault;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.v1.model 31/05/2021
 */
// @JsonIgnoreProperties(value = {"id"})
@Document(collection = "Vault")
public class Vault implements IVault {

    @Id
    private String id;

    private IAddress address;

    private String phones;

    private String contact;

    private Collection<IContribution> contribution;


    /**
     * Default constructor.
     */
    public Vault() {

    }

    public Vault(String id, IAddress address, String phones, String contact,
            Collection<IContribution> contribution) {
        super();
        this.id = id;
        this.address = address;
        this.phones = phones;
        this.contact = contact;
        this.contribution = contribution;
    }


    @Override
    public String getContact() {
        // TODO Auto-generated method stub
        return contact;
    }


    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return id;
    }


    @Override
    public IAddress getAddress() {
        // TODO Auto-generated method stub
        return address;
    }


    @Override
    public String getPhones() {
        // TODO Auto-generated method stub
        return phones;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setAddress(IAddress address) {
        this.address = address;
    }


    public void setPhones(String phones) {
        this.phones = phones;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }


    public void setContribution(Collection<IContribution> contribution) {
        this.contribution = contribution;
    }

    @Override
    public Collection<IContribution> getContribution() {
        // TODO Auto-generated method stub
        return null;
    }



}
