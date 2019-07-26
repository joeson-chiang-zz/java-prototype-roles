package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Role findByName(String name) {
    System.out.println("value is " + name);
    Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name=:name")
            .setParameter("name", name);
    List<Object> result = query.getResultList();
    System.out.println("the result is " + result.size());
    return result.size() == 1 ? (Role) result.get(0) : null;
  }
}
