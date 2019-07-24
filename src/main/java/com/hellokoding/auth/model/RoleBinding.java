package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.List;

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

  @OneToMany(targetEntity=RoleBindingEntitlement.class)
  private List<RoleBindingEntitlement> entitlements;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<RoleBindingEntitlement> getEntitlements() {
    return entitlements;
  }
}
