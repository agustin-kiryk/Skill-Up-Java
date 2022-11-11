package com.alkemy.wallet.mapper;

import com.alkemy.wallet.auth.dto.ResponseUserDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMap {

  @Autowired
  private IUserRepository IUserRepository;

  @Autowired
  private AccountMap accountMap;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  public UserDto userEntity2Dto(UserEntity entity) {
    UserDto userDto = new UserDto();

    userDto.setId(entity.getUserId());
    userDto.setFirstName(entity.getFirstName());
    userDto.setLastName(entity.getLastName());
    userDto.setEmail(entity.getEmail());
    userDto.setRole(entity.getRole().getName());//TODO: HACER METODO
    userDto.setAccounts(accountMap.accountEntityList2BasicDto(entity.getAccounts()));

    return userDto;
  }

  public UserEntity userDto2Entity(UserDto dto) {

    UserEntity userEntity = new UserEntity();

    userEntity.setUserId(dto.getId());
    userEntity.setFirstName(dto.getFirstName());
    userEntity.setLastName(dto.getLastName());
    userEntity.setEmail(dto.getEmail());
    userEntity.setAccounts(
        accountMap.accountDtoList2EntityList(dto.getAccounts()));// TODO: HACER METODO
    userEntity.setRole(roleRepository.findByName(dto.getRole()));

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

  public UserEntity userAuthDto2Entity(ResponseUserDto userDto) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    UserEntity userEntity = new UserEntity();
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userEntity.setUpdateDateTime(userDto.getUpdateDate());
    userEntity.setCreateDateTime(userDto.getCreationDate());

    return userEntity;
  }

  public ResponseUserDto userAuthEntity2Dto(UserEntity entitySaved) {
    ResponseUserDto dto = new ResponseUserDto();
    dto.setId(entitySaved.getUserId());
    dto.setFirstName(entitySaved.getFirstName());
    dto.setLastName(entitySaved.getLastName());
    dto.setEmail(entitySaved.getEmail());
    //dto.setPassword(entitySaved.getPassword());
    dto.setRole(entitySaved.getRole().getName());
    dto.setUpdateDate(entitySaved.getUpdateDateTime());
    dto.setCreationDate(entitySaved.getCreateDateTime());
    dto.setAccounts(entitySaved.getAccounts());//TODO: VER TRAER ID ACCOUNT CON ACOUNTREPO
    return dto;
  }
}
