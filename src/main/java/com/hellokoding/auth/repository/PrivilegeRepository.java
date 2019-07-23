package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{
  Privilege findByName(String name);
  Privilege save(Privilege privilege);
}
