package co.com.foodbank.vault.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import co.com.foodbank.vault.v1.model.Vault;

@Repository
public interface VaultRepository extends MongoRepository<Vault, String> {

}
