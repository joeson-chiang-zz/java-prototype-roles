package com.hellokoding.auth.api.model;

import com.hellokoding.auth.util.ResourceType;

import java.util.List;

public class RoleCreate {
  private String name;
  private ResourceType resourceType;
  private List<String> operations;

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
