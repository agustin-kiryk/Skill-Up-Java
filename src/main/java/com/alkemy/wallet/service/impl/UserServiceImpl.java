package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.mapper.UserMap;
import com.alkemy.wallet.repository.UserRepository;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
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
