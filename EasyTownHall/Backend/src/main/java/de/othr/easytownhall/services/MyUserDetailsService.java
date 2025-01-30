package de.othr.easytownhall.services;

import de.othr.easytownhall.models.entities.Privilege;
import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.enums.RoleEnum;
import de.othr.easytownhall.repositories.RoleRepository;
import de.othr.easytownhall.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true,
                    true, true,
                    true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByName(RoleEnum.CITIZEN))));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isActive(), true, true,
                true, getAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(String.valueOf(role.getName())); //fürs erste das nicht alles explodiert
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(String.valueOf(item.getName())); //fürs erste das nicht alles explodiert
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
