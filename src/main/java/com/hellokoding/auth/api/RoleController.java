package com.hellokoding.auth.api;

import com.hellokoding.auth.api.model.RoleCreate;
import com.hellokoding.auth.model.Privilege;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private RoleRepository roleRepository;

  @PostMapping(value = "/")
  public void createRole(@RequestBody RoleCreate roleCreate) {
    createRoleFromBody(roleCreate);
  }

  @GetMapping(value = "/rolebindings/{roleName}", produces = "text/plain")
  public String getRoleBindingsForRole(String roleName) {
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

  private void getRoleBindingsByRoleName(String roleName) {

  }

  private void createRoleFromBody(RoleCreate roleCreate) {
    roleRepository.save(new Role(roleCreate.getName(), new Privilege(roleCreate.getResourceType(), roleCreate.getOperations())));
  }
}
