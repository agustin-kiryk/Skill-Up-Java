package com.alkemy.wallet.service.impl;


import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.UserMap;
import com.alkemy.wallet.repository.UserRepository;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMap userMap;

  @Override
  public List<UserDto> listAllUsers() {

    return userMap.userEntityList2DtoList(userRepository.findAll());
  }

  @Override
  public UserDto findById(Long userId)
  {

    Optional<UserEntity> entity=userRepository.findById(userId);
    UserDto dto=null;
    if (entity.isPresent())
      dto=userMap.userEntity2Dto(entity.get());

    return dto;
  }


  @Override
  public void update(UserDto user, Long id) {

  }

  @Override
  public boolean deleteById(Long id) throws Exception {
    try {
      if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return true;
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
