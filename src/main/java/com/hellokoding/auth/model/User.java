package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "user")
    private List<RoleBinding> roleBindings;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User() {

  }

  public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
      this.passwordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
      return passwordConfirm;
    }

    public void setPassword(String password) {
    this.password = password;
  }

    public List<RoleBinding> getRoleBindings() {
    return roleBindings;
  }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoleForRoleBindingForUser() {
      System.out.println("Role Bindings size " + roleBindings.size());
      return roleBindings.stream().map(rb -> rb.getRole()).collect(Collectors.toList());
    }

}
