package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
 @Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "role_privilege",
      joinColumns = @JoinColumn(
        name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
        name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "role")
    private List<RoleBinding> roleBindings;

    public Role(String name, Privilege... privileges) {
      this.name = name;
      this.privileges = Stream.of(privileges).collect(Collectors.toList());
      this.privileges.forEach(p -> p.getRoles().add(this));
    }

    public Role() {

    }

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
      this.privileges.forEach(p -> p.getRoles().add(this));
  }

    public List<String> getPrivilegesByResourceType(ResourceType resourceType) {
      for (Privilege privilege : privileges) {
        if (privilege.getResourceType() == resourceType) {
          return privilege.getOperations();
        }
      }
      return null;
    }

    public List<RoleBinding> getRoleBindings() {
      return roleBindings;
    }
}
