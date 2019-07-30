package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.RoleBinding;
import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.util.ResourceType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class RoleBindingEntitlementRepositoryCustomImpl implements RoleBindingEntitlementRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public RoleBindingEntitlement findRbeByRbIdAndType(long roleBindingId, ResourceType resourceType) {
    Query query = entityManager.createQuery("SELECT rbe FROM RoleBindingEntitlement rbe WHERE rbe.roleBinding.id=:role" +
            " AND rbe.resourceType=:resource")
            .setParameter("role", roleBindingId)
            .setParameter("resource", resourceType);
    List<Object> result = query.getResultList();
    System.out.println("the result is " + result.size());
    return result.size() == 1 ? (RoleBindingEntitlement) result.get(0) : null;
  }


}
