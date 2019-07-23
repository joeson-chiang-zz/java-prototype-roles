package com.hellokoding.auth.model;

import com.hellokoding.auth.util.ResourceType;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "privilege")
public class Privilege {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private ResourceType resourceType;

  private ArrayList<String> operations;

  @ManyToMany(mappedBy = "privileges")
  private Set<Role> roles;


  public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

}
