package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
      name = "rolebinding",
      joinColumns = @JoinColumn(
        name = "roleId", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
        name = "userId", referencedColumnName = "id"))
    private List<RoleBinding> roleBindings;


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
}
