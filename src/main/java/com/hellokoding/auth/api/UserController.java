package com.hellokoding.auth.api;

import com.hellokoding.auth.api.model.RoleCreate;
import com.hellokoding.auth.api.model.UserCreate;
import com.hellokoding.auth.model.Privilege;
import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/")
  public void createUser(@RequestBody UserCreate user) {
    createUserFromBody(user);
  }

  private void createUserFromBody(UserCreate userCreate) {
    userRepository.save(new User(userCreate.getUsername(), userCreate.getPassword()));
  }
}
