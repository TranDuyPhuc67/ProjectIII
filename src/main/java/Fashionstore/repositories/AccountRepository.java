package Fashionstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Fashionstore.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	Optional<Account> findByUsername(String username);
	Optional<Account> findByEmail(String email);
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}