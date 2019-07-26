package com.hellokoding.auth.api;

import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import com.hellokoding.auth.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Role binding id " + roleBinding.getId());
        List<RoleBindingEntitlement> roleBindingEntitlement = roleBinding.getEntitlements();
        System.out.println("Role binding entitlements size " + roleBindingEntitlement.size());
        for (RoleBindingEntitlement rbe : roleBindingEntitlement) {
          System.out.println("Role binding type " + rbe.getResourceType().name());
          System.out.println("Resource name " + resourceName);
          System.out.println("Condition 1 " + (rbe.getResourceType().name() == resourceName.toUpperCase()));
          System.out.println("Condition 2 " + (rbe.getResourceType().name().equals(resourceName.toUpperCase())));
          if (rbe.getResourceType().name().equals(resourceName.toUpperCase())) {
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
        List<String> operations = role.getPrivilegesByResourceType(ResourceType.valueOf(resourceName.toUpperCase()));
        set.addAll(operations);
      }

      return String.join(",", set);
    }
  }

  @GetMapping(value = "/{resourceName}/user/{userId}", produces = "text/plain")
  public String listResources(@PathVariable String resourceName, @PathVariable int userId) {
    return listAllResources(userId, resourceName);
  }

  private String listAllResources(long userId, String resourceName) {
    Map<Long, Set<String>> resourceMap = new HashMap<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    User user = optionalUser.isPresent() ? optionalUser.get() : null;
    if (user == null) {
      return null;
    } else {
      List<RoleBinding> roleBindingList = user.getRoleBindings();
      List<Role> rolesFound = new ArrayList<>();
//      System.out.println("Role bindings " + roleBindingList.size());
      for (RoleBinding roleBinding : roleBindingList) {
//        System.out.println("Role binding id " + roleBinding.getId());
        List<RoleBindingEntitlement> roleBindingEntitlement = roleBinding.getEntitlements();
//        System.out.println("Role binding entitlements size " + roleBindingEntitlement.size());
        for (RoleBindingEntitlement rbe : roleBindingEntitlement) {
//          System.out.println("Role binding type " + rbe.getResourceType().name());
          if (rbe.getResourceType().name().equals(resourceName.toUpperCase())) {
//            System.out.println("Role binding entitlement " + rbe.getInstanceIds().toString());
            // found it
            Optional<Role> roleOptional = roleRepository.findById(roleBinding.getRoleId());
            Role role = roleOptional.isPresent() ? roleOptional.get() : null;
            List<String> operations = role.getPrivilegesByResourceType(ResourceType.valueOf(resourceName.toUpperCase()));

            for (Long id : rbe.getInstanceIds()) {
              if (resourceMap.containsKey(id)) {
                resourceMap.get(id).addAll(operations);
              } else {
                resourceMap.put(id, new HashSet<>(operations));
              }
            }
          }
        }
      }
      return resourceMap.toString();
    }
  }

  // TODO: role assignment & rolebinding, create role, get roles for user, get all instances for user
}
