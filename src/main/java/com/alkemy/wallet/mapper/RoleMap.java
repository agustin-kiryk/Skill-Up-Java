package com.alkemy.wallet.mapper;


import com.alkemy.wallet.dto.RoleDto;
import com.alkemy.wallet.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMap {

  public RoleEntity roleDto2Entity(RoleDto role) {
    RoleEntity entity = new RoleEntity();
    entity.setDescription(role.getDescription());
    entity.setName(role.getName());
    return entity;
  }

  public RoleDto roleEntity2Dto(RoleEntity role) {
    return null;
  }


}
