package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMap {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccountMap accountMap;

  @Autowired
  private RoleMap roleMap;

  public UserDto userEntity2Dto(UserEntity entity) {
    UserDto userDto = new UserDto();

    userDto.setId(entity.getIdUser());
    userDto.setFirstName(entity.getFirstName());
    userDto.setLastName(entity.getLastName());
    userDto.setEmail(entity.getEmail());

    userDto.setRole(roleMap.roleEntity2Dto(entity.getRole()));
    userDto.setAccounts(accountMap.accountEntityList2BasicDto(entity.getAccounts()));

    return userDto;
  }

  public UserEntity userDto2Entity(UserDto dto) {

    UserEntity userEntity = new UserEntity();

    userEntity.setIdUser(dto.getId());
    userEntity.setFirstName(dto.getFirstName());
    userEntity.setLastName(dto.getLastName());
    userEntity.setEmail(dto.getEmail());
    userEntity.setAccounts(accountMap.accountDtoList2EntityList(dto.getAccounts()));
    userEntity.setRole(roleMap.roleDto2Entity(dto.getRole()));

    return userEntity;

  }

  public List<UserDto> userEntityList2DtoList(List<UserEntity> entities) {

    List<UserDto> userDtoList = new ArrayList<>();

    for (UserEntity userEntity : entities) {

      userDtoList.add(userEntity2Dto(userEntity));

    }

    return userDtoList;


  }

  public List<UserEntity> userDtoList2EntityList(List<UserDto> dtoList) {

    List<UserEntity> entityList = new ArrayList<>();

    for (UserDto dto : dtoList) {

      entityList.add(userDto2Entity(dto));

    }

    return entityList;

  }
}
