package co.com.foodbank.vault.repository;

import java.util.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import co.com.foodbank.vault.exception.VaultNotFoundException;
import co.com.foodbank.vault.v1.model.Vault;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.vault.repository 1/07/2021
 */
@Repository
public interface VaultRepository extends MongoRepository<Vault, String> {

    @Query("{'contact':{'$regex':'?0','$options':'i'}}")
    Collection<Vault> findByContact(String contact)
            throws VaultNotFoundException;

    @Query("{'address.district':{'$regex':'?0','$options':'i'}}")
    Collection<Vault> findByDistrict(String district)
            throws VaultNotFoundException;
}
