package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.RoleDto;
import com.alkemy.wallet.entity.RoleEntity;
import com.alkemy.wallet.mapper.RoleMap;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private RoleMap roleMap;
  @Autowired
  private IRoleRepository roleRepository;


  @Override
  public void save(RoleDto role) {
    RoleEntity entity = roleMap.roleDto2Entity(role);
    roleRepository.save(entity);
  }
}
