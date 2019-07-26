package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom{
}
