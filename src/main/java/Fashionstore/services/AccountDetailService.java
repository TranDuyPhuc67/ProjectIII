package Fashionstore.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Fashionstore.entities.Account;
import Fashionstore.entities.AccountDetails;
import Fashionstore.entities.ERole;
import Fashionstore.entities.Role;
import Fashionstore.repositories.AccountRepository;

@Service
public class AccountDetailService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = accountRepository.findByUsername(username);
        if (!user.isPresent())
            return null;
        Account acc=user.get();

        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        Set<Role> roles = acc.getRoles();
        for (Role userRole : roles) {
        	ERole rolename=userRole.getRoleName();
        	grantedAuthoritySet.add(new SimpleGrantedAuthority(rolename.name()));
		}
        return new AccountDetails(grantedAuthoritySet, acc.getEmail(), acc.getFullname(), acc.getPassword(), acc.getUsername(), acc.getPicture(),acc.getPhone(),acc.getAddress(),  acc.isEnabled(),true,true,true);
    }
}