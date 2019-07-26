package com.hellokoding.auth.api;

import com.hellokoding.auth.api.model.RoleCreate;
import com.hellokoding.auth.api.model.UserCreate;
import com.hellokoding.auth.model.Privilege;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/")
  public void createUser(@RequestBody UserCreate user) {
    createUserFromBody(user);
  }

  @GetMapping(value="/{userId}/roles")
  public String getRolesForUser(@PathVariable long userId) {
    return getRolesForUserId(userId);
  }

  private String getRolesForUserId(long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    User user = userOptional.isPresent() ? userOptional.get() : null;
    if (user == null) {
      throw new MissingResourceException("User not found {}", user.getId().toString(), "");
    }
    List<Role> roles = user.getRoleForRoleBindingForUser();
    String result = "";
    for (Role role: roles) {
      result += role.getName();
      result += " ";
    }
    return result;
  }

  private void createUserFromBody(UserCreate userCreate) {
    userRepository.save(new User(userCreate.getUsername(), userCreate.getPassword()));
  }
}
