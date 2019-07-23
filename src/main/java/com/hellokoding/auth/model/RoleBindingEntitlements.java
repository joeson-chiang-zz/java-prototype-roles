package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name = "rolebindingentitlement")
public class RoleBindingEntitlements {
  private Long roleBindingId;
  private ResourceType resourceType;
  private ArrayList<Long> instanceIds;
}
