package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.model.Privilege;

import com.hellokoding.auth.repository.UserRepository;
import com.hellokoding.auth.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    private PrivilegeRepository privilegeRepository;

//  @Autowired
//  private IUserService service;
//
//  @Autowired
//  private MessageSource messages;

//    public CheckAccess
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        System.out.println("user:" + user.getUsername());
        System.out.println("roles" + user.getRoles());
        for (Role role : user.getRoles()){
          System.out.println("role: " + role.getName());
          System.out.println("Privileges: " + role.getPrivileges());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }


//      User user = userRepository.findByUsername(username);
//
//      if (user == null) {
//        return new org.springframework.security.core.userdetails.User(
//          " ", " ", true, true, true, true,
//          getAuthorities(Arrays.asList(
//            roleRepository.findByName("ROLE_USER"))));
//      }
//
//      return new org.springframework.security.core.userdetails.User(
//        user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
//        true, getAuthorities(user.getRoles()));
//  }

//  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//    return getGrantedAuthorities(getPrivileges(roles));
//  }
//
//  private List<String> getPrivileges(Collection<Role> roles) {
//    List<String> privileges = new ArrayList<String>();
//    List<Privilege> collection = new ArrayList<Privilege>();
//    for (Role role : roles) {
//      collection.addAll(role.getPrivileges());
//    }
//    for (Privilege item : collection) {
//      privileges.add(item.getName());
//    }
//    return privileges;
//  }
//
//  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//    for (String privilege : privileges) {
//      authorities.add(new SimpleGrantedAuthority(privilege));
//    }
//    return authorities;
//  }

}
