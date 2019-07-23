package com.hellokoding.auth;


import com.hellokoding.auth.model.Privilege;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.PrivilegeRepository;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
;
import org.springframework.stereotype.Component;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class InitialDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

//  @Autowired
//  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup)
      return;
    Privilege readPrivilege
      = createPrivilegeIfNotFound("READ_PRIVILEGE");
    Privilege writePrivilege
      = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

    Set<Privilege> adminPrivileges = new HashSet<Privilege>(Arrays.asList(readPrivilege, writePrivilege));
    createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    createRoleIfNotFound("ROLE_USER", new HashSet<Privilege>(Arrays.asList(readPrivilege)));

    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    User user = new User();
    user.setUsername("admin");
//    user.setFirstName("Test");
//    user.setLastName("Test");
//    user.setPassword(passwordEncoder.encode("test"));
//    user.setEmail("test@test.com");
    user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
//    user.setEnabled(true);
    userRepository.save(user);
    System.out.println("user admin is created with name " + user.getUsername()  + " and roles " + user.getRoles());

    alreadySetup = true;
  }

  @Transactional
  private Privilege createPrivilegeIfNotFound(String name) {

    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
      privilege = new Privilege();
      privilege.setName(name);
      privilegeRepository.save(privilege);
    }
    return privilege;
  }

  @Transactional
  private Role createRoleIfNotFound(
    String name, Set<Privilege> privileges) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role();
      role.setName(name);
      role.setPrivileges(privileges);
      roleRepository.save(role);
    }
    return role;
  }
}