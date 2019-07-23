package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rolebinding")
public class RoleBinding {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long roleId;

  private Long userId;
  private Long groupId;
  private Long workspaceId;

  private List<RoleBindingEntitlements> entitlements;

//  @ManyToMany(mappedBy = "roles")
//  private Set<User> users;

//  private Set<Privilege> privileges;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public List<Entitlements> getEntitlements() {
    return entitlements;
  }

  public void setEntitlements(Set<Entitlement> privileges) {
    this.privileges = privileges;
  }
}
