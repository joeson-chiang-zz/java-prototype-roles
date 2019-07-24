package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(
      name = "roleprivilege",
      joinColumns = @JoinColumn(
        name = "roleId", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
        name = "privilegeId", referencedColumnName = "id"))
    private List<Privilege> privileges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
    return privileges;
  }

    public void setPrivileges(List<Privilege> privileges) {
    this.privileges = privileges;
  }

    public List<String> getPrivilegesByResourceType(ResourceType resourceType) {
      for (Privilege privilege : privileges) {
        if (privilege.getResourceType() == resourceType) {
          return privilege.getOperations();
        }
      }
      return null;
    }
}
