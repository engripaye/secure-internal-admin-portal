package org.engripaye.secureinternaladminportal.repository;

import org.engripaye.secureinternaladminportal.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
}
