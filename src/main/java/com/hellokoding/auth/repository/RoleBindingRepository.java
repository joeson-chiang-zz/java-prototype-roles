package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.RoleBinding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleBindingRepository extends JpaRepository<RoleBinding, Long>, RoleBindingRepositoryCustom {
}
