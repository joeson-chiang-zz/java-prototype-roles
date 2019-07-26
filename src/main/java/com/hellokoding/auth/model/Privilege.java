package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "privilege")
public class Privilege {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private ResourceType resourceType;

  @ElementCollection
  private List<String> operations;

  @ManyToMany(mappedBy = "privileges")
  private List<Role> roles = new ArrayList<>();

  public Privilege(ResourceType resourceType, List<String> operations) {
    this.resourceType = resourceType;
    this.operations = operations;
  }

  public Privilege() {

  }

  public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public void setResourceType(ResourceType resourceType){
      this.resourceType = resourceType;
    };

    public ResourceType getResourceType() {
      return resourceType;
    };

  public void setOperations(List<String> operations){
    this.operations = operations;
  };

  public List<String> getOperations() {
    return operations;
  };

  public List<Role> getRoles() {
    return roles;
  }
}
