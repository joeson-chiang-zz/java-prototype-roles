package com.hellokoding.auth.api.model;

import com.hellokoding.auth.util.ResourceType;

import java.util.List;

public class RoleCreate {
  private String name;
  private ResourceType resourceType;
  private List<String> operations;

  public RoleCreate(String name, String resourceType, List<String> operations) {
    this.name = name;
    this.resourceType = ResourceType.valueOf(resourceType);
    this.operations = operations;
  }

  public String getName() {
    return name;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public List<String> getOperations() {
    return operations;
  }
}
