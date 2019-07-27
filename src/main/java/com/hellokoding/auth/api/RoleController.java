package com.hellokoding.auth.api;

import com.hellokoding.auth.api.model.RoleCreate;
import com.hellokoding.auth.model.Privilege;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/")
  public void createRole(@RequestBody RoleCreate roleCreate) {
    createRoleFromBody(roleCreate);
  }

  @GetMapping(value = "/rolebindings/{roleName}", produces = "text/plain")
  public String getRoleBindingsForRole(@PathVariable String roleName) {
    return getRoleBindingsByRoleName(roleName);
  }

  @GetMapping(value = "/{roleName}/users", produces = "text/plain")
  public String getUsersForRole(@PathVariable String roleName) {
    return getUsersByRoleName(roleName);
  }

  @DeleteMapping(value = "/{roleName}")
  public void deleteRole(@PathVariable String roleName) {
    deleteRoleByName(roleName);
  }

  private void deleteRoleByName(String roleName) {
    roleRepository.deleteByName(roleName);
  }

  private String getRoleBindingsByRoleName(String roleName) {
    System.out.println("value extracted: " + roleName);
    Role role = roleRepository.findByName(roleName);
    if (role == null) {
      throw new MissingResourceException("Role not found {}", roleName, "");
    }
    List<RoleBinding> roleBindings = role.getRoleBindings();
    List<Long> roleBindingIds = roleBindings.stream().map(RoleBinding::getId).collect(Collectors.toList());
    String result = "";
    for (Long id: roleBindingIds) {
      result += id.toString();
      result += " ";
    }
    return result;
  }

  private String getUsersByRoleName(String roleName) {
    Role role = roleRepository.findByName(roleName);
    if (role == null) {
      throw new MissingResourceException("Role not found {}", roleName, "");
    }

    Set<Long> userIds = role.getRoleBindings().stream().map(u -> u.getUserId()).collect(Collectors.toSet());
    List<User> users = userRepository.findAllById(userIds);
    String result = "";
    for (User user : users) {
      result += user.getUsername();
      result += " ";
    }
    return result;
  }

  private void createRoleFromBody(RoleCreate roleCreate) {
    roleRepository.save(new Role(roleCreate.getName(), new Privilege(roleCreate.getResourceType(), roleCreate.getOperations())));
  }
}
