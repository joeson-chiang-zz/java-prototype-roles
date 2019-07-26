package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rolebindingentitlement")
public class RoleBindingEntitlement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="roleBindingId", referencedColumnName = "id")
  private RoleBinding roleBinding;
  private ResourceType resourceType;
  @ElementCollection
  private List<Long> instanceIds;

  public RoleBindingEntitlement(RoleBinding roleBinding, ResourceType resourceType, List<Long> instanceIds) {
    this.roleBinding = roleBinding;
    this.resourceType = resourceType;
    this.instanceIds = instanceIds;
  }

  public RoleBindingEntitlement() {

  }

  public void setResourceType(ResourceType resourceType){
    this.resourceType = resourceType;
  };

  public ResourceType getResourceType() {
    return resourceType;
  };

  public void setInstanceIds(List<Long> instanceIds){
    this.instanceIds = instanceIds;
  };

  public List<Long> getInstanceIds() {
    return instanceIds;
  };

  public void setRoleBinding(RoleBinding roleBinding) {
    this.roleBinding = roleBinding;
  }

}
