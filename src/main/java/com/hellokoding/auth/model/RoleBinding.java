package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.List;
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

  public List<RoleBindingEntitlements> getEntitlements() {
    return entitlements;
  }

  public void setEntitlements(List<RoleBindingEntitlements> entitlements) {
    this.entitlements = entitlements;
  }
}
