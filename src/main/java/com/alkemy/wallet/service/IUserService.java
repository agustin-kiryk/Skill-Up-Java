package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.UserDto;
import java.util.List;

public interface IUserService {

  List<UserDto> listAllUsers();

  void update(UserDto user, Long id);

  public boolean deleteById(Long id) throws Exception;

}
