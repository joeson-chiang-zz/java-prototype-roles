package com.hellokoding.auth.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rolebinding")
public class RoleBinding {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private Long groupId;
  private Long workspaceId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;


  @OneToMany(mappedBy = "roleBinding", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<RoleBindingEntitlement> entitlements;

  public RoleBinding() {
  }

  public RoleBinding(User user, Role role, Long id) {
    this.user = user;
    this.role = role;
    if (id != null) {
      this.id = id;
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRoleId() {
    return role.getId();
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Long getUserId() {
    return user.getId();
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<RoleBindingEntitlement> getEntitlements() {
    return entitlements;
  }
}
