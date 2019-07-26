package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.RoleBinding;

public interface RoleBindingRepositoryCustom {
  RoleBinding findByRoleAndUser(long roleId, long userId);
}
