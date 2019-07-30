package com.hellokoding.auth.api;

import com.hellokoding.auth.api.model.EntitlementCreate;
import com.hellokoding.auth.api.model.RoleCreate;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleBindingEntitlementRepository;
import com.hellokoding.auth.repository.RoleBindingRepository;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Optional;

@RestController
@RequestMapping("/rolebindings")
public class RoleBindingController {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleBindingRepository roleBindingRepository;

  @Autowired
  private RoleBindingEntitlementRepository roleBindingEntitlementRepository;

  @PostMapping(value = "/role/{roleName}/user/{userId}")
  public void createRoleBinding(@PathVariable String roleName, @PathVariable long userId) {
    bindRoleToUser(roleName, userId);
  }

  @PostMapping(value = "/role/{roleName}/user/{userId}/instances")
  public void createRoleBindingWithEntitlement(@PathVariable String roleName, @PathVariable long userId,
                                               @RequestBody EntitlementCreate entitlementCreate) {
    RoleBinding roleBinding = bindRoleToUser(roleName, userId);
    addInstancesToRoleBinding(roleBinding, entitlementCreate);
  }

  @PutMapping(value = "/role/{roleName}/user/{userId}/instances")
  public void addEntitlement(@PathVariable String roleName, @PathVariable long userId,
                                               @RequestBody EntitlementCreate entitlementCreate) {
    Role role = roleRepository.findByName(roleName);
    RoleBinding roleBinding = roleBindingRepository.findByRoleAndUser(role.getId(), userId);
    addEntitlementToRoleBinding(roleBinding, entitlementCreate);
  }

  @DeleteMapping(value = "/role/{roleName}/user/{userId}/instances")
  public void removeEntitlement(@PathVariable String roleName, @PathVariable long userId,
                             @RequestBody EntitlementCreate entitlementCreate) {
    Role role = roleRepository.findByName(roleName);
    RoleBinding roleBinding = roleBindingRepository.findByRoleAndUser(role.getId(), userId);
    removeEntitlementFromRoleBinding(roleBinding, entitlementCreate);
  }

  private void removeEntitlementFromRoleBinding(RoleBinding roleBinding, EntitlementCreate entitlementCreate) {
    RoleBindingEntitlement roleBindingEntitlement = roleBindingEntitlementRepository.findRbeByRbIdAndType(
            roleBinding.getId(), entitlementCreate.getResourceType());
    roleBindingEntitlement.getInstanceIds().removeAll(entitlementCreate.getInstances());
    if (roleBindingEntitlement.getInstanceIds().size() > 0) {
      roleBindingEntitlementRepository.save(roleBindingEntitlement);
    } else {
      roleBindingEntitlementRepository.delete(roleBindingEntitlement);
    }
  }

  private void addEntitlementToRoleBinding(RoleBinding roleBinding, EntitlementCreate entitlementCreate) {
    RoleBindingEntitlement roleBindingEntitlement = roleBindingEntitlementRepository.findRbeByRbIdAndType(
            roleBinding.getId(), entitlementCreate.getResourceType());
    roleBindingEntitlement.getInstanceIds().addAll(entitlementCreate.getInstances());
    roleBindingEntitlementRepository.save(roleBindingEntitlement);
  }

  private void addInstancesToRoleBinding(RoleBinding roleBinding, EntitlementCreate entitlementCreate) {
    roleBindingEntitlementRepository.save(
            new RoleBindingEntitlement(roleBinding, entitlementCreate.getResourceType(), entitlementCreate.getInstances()));
  }

  private RoleBinding bindRoleToUser(String roleName, long userId) {
    Role role = roleRepository.findByName(roleName);
    Optional<User> userOptional = userRepository.findById(userId);
    User user = userOptional.isPresent() ? userOptional.get() : null;
    if (role == null) {
      throw new MissingResourceException("Role is missing {}", roleName, "");
    }
    if (user == null) {
      throw new MissingResourceException("User is missing {}", user.getId().toString(), "");
    }
    RoleBinding roleBinding = roleBindingRepository.findByRoleAndUser(role.getId(), userId);
    if (roleBinding == null) {
      roleBinding = roleBindingRepository.save(new RoleBinding(user, role));
    }
    return roleBinding;
  }
}
