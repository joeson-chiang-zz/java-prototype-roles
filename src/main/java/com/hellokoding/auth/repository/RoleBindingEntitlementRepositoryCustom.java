package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.RoleBindingEntitlement;
import com.hellokoding.auth.util.ResourceType;

public interface RoleBindingEntitlementRepositoryCustom {
  RoleBindingEntitlement findRbeByRbIdAndType(long roleBindingId, ResourceType resourceType);
}
