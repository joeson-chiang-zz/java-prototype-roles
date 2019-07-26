package com.hellokoding.auth.api.model;

import com.hellokoding.auth.util.ResourceType;

import java.util.List;

public class EntitlementCreate {
  private ResourceType resourceType;
  private List<Long> instances;

  public EntitlementCreate(String resourceType, List<Long> instances) {
    this.resourceType = ResourceType.valueOf(resourceType);
    this.instances = instances;
  }


  public ResourceType getResourceType() {
    return resourceType;
  }

  public List<Long> getInstances() {
    return instances;
  }
}
