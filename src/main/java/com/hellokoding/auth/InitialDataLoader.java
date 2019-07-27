package com.hellokoding.auth;


import com.hellokoding.auth.model.*;
import com.hellokoding.auth.repository.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
;
import com.hellokoding.auth.util.ResourceType;
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

  @Autowired
  private RoleBindingEntitlementRepository roleBindingEntitlementRepository;

  @Autowired
  private RoleBindingRepository roleBindingRepository;

//  @Autowired
//  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup)
      return;
    Privilege writePrivilege
      = createPrivilegeIfNotFound(ResourceType.FLOW, Arrays.asList("create", "read", "update", "delete"));
    Privilege readPrivilege
      = createPrivilegeIfNotFound(ResourceType.FLOW, Arrays.asList("read"));

    createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(writePrivilege));
    createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    User user = new User();
    user.setUsername("admin");
    user.setRoles(Arrays.asList(adminRole));
    userRepository.save(user);
    System.out.println("user admin is created with name " + user.getUsername()  + " and roles " + user.getRoles());

    RoleBinding roleBinding = createRoleBinding(adminRole, user);
    createRoleBindingEntitlement(ResourceType.FLOW, Arrays.asList((long) 1, (long) 2, (long) 3), roleBinding);

    alreadySetup = true;
  }

  @Transactional
  private Privilege createPrivilegeIfNotFound(ResourceType type, List<String> operations) {
    // TODO: fix this
    Privilege privilege = null;
    if (privilege == null) {
      privilege = new Privilege();
      privilege.setResourceType(type);
      privilege.setOperations(operations);
      privilegeRepository.save(privilege);
    }
    return privilege;
  }

  @Transactional
  private Role createRoleIfNotFound(
    String name, List<Privilege> privileges) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role();
      role.setName(name);
      role.setPrivileges(privileges);
      roleRepository.save(role);
    }
    return role;
  }

  @Transactional
  private RoleBinding createRoleBinding (Role role, User user) {
    RoleBinding roleBinding = new RoleBinding();
    roleBinding.setRole(role);
    roleBinding.setUser(user);
    roleBindingRepository.save(roleBinding);
    return roleBinding;
  }

  @Transactional
  private RoleBindingEntitlement createRoleBindingEntitlement(
    ResourceType type, List<Long> instanceIds, RoleBinding roleBinding) {
    RoleBindingEntitlement entitlement = new RoleBindingEntitlement();
    entitlement.setResourceType(type);
    entitlement.setInstanceIds(new HashSet<>(instanceIds));
    entitlement.setRoleBinding(roleBinding);
    roleBindingEntitlementRepository.save(entitlement);
    return entitlement;
  }
}