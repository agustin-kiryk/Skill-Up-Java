package com.alkemy.wallet.service;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.dto.UserRequestDto;
import java.util.List;

public interface IUserService {

  List<UserDto> listAllUsers();

  void update(UserDto user, Long id);

  UserDto findById(Long id);

  List<AccountBasicDto> getAccountsBalance(Long id);

  void delete(Long id);

  UserDto update(Long id, UserRequestDto updatedDto);
}