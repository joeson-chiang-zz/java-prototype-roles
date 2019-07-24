package com.hellokoding.auth.model;

import javax.persistence.*;

@Entity
@Table(name = "roleprivilege")
public class RolePrivilege {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long roleId;
  private Long privilegeId;
}
