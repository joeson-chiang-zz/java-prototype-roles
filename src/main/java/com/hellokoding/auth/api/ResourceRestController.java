package com.hellokoding.auth.api;

import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import com.hellokoding.auth.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("resources")
public class ResourceRestController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @GetMapping(value = "/{resourceName}/{id}/user/{userId}", produces = "text/plain")
  public String getResource(@PathVariable String resourceName, @PathVariable int id, @PathVariable int userId) {
    System.out.println("Got get request");
    return getResourceById(id, userId, resourceName);
  }

  private String getResourceById(long id, long userId, String resourceName) {
    Optional<User> optionalUser = userRepository.findById(userId);
    User user = optionalUser.isPresent() ? optionalUser.get() : null;
    if (user == null) {
      return null;
    } else {
      System.out.println("Should come in here");
      List<RoleBinding> roleBindingList = user.getRoleBindings();
      List<Role> rolesFound = new ArrayList<>();
      System.out.println("Role bindings " + roleBindingList.size());
      for (RoleBinding roleBinding : roleBindingList) {
        List<RoleBindingEntitlement> roleBindingEntitlement = roleBinding.getEntitlements();
        System.out.println("Role binding entitlements size " + roleBindingEntitlement.size());
        for (RoleBindingEntitlement rbe : roleBindingEntitlement) {
          if (rbe.getResourceType().name() == resourceName) {
            System.out.println("Role binding type " + rbe.getResourceType().name());
            System.out.println("Role binding entitlement " + rbe.getInstanceIds().toString());
            if (rbe.getInstanceIds().contains(id)) {
              // found it
              Optional<Role> roleOptional = roleRepository.findById(roleBinding.getRoleId());
              Role role = roleOptional.isPresent() ? roleOptional.get() : null;
              System.out.println("Role is  " + role);
              if (role != null) {
                System.out.println("Adding role " + role.getId());
                rolesFound.add(role);
              }
            }
          }
        }
      }
      Set<String> set = new HashSet<>();
      for (Role role : rolesFound) {
        List<String> operations = role.getPrivilegesByResourceType(ResourceType.valueOf(resourceName));
        set.addAll(operations);
      }

      return String.join(",", set);
    }
  }
}