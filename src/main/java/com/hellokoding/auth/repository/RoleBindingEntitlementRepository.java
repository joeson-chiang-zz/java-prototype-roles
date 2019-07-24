package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.RoleBindingEntitlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleBindingEntitlementRepository extends JpaRepository<RoleBindingEntitlement, Long> {
}
