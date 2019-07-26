package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Role;

public interface RoleRepositoryCustom {
  Role findByName(String name);
}
