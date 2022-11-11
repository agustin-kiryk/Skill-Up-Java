package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.RoleEntity;
import com.alkemy.wallet.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository <RoleEntity, Long> {

  RoleEntity findByName(RoleName name);
}




