package com.hellokoding.auth.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roleprivileges")
public class RolePrivileges {
  private Long roleId;
  private Long privilegeId;
}
