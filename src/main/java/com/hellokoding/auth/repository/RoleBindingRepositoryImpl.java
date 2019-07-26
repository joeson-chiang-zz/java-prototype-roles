package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Role;
import com.hellokoding.auth.model.RoleBinding;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleBindingRepositoryImpl implements RoleBindingRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public RoleBinding findByRoleAndUser(long roleId, long userId) {
    Query query = entityManager.createQuery("SELECT rb FROM RoleBinding rb WHERE rb.role.id=:role" +
            " AND rb.user.id=:user")
            .setParameter("role", roleId)
            .setParameter("user", userId);
    List<Object> result = query.getResultList();
    System.out.println("the result is " + result.size());
    return result.size() == 1 ? (RoleBinding) result.get(0) : null;
  }
}
