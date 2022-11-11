package com.alkemy.wallet.service;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.dto.UserRequestDto;
import com.alkemy.wallet.entity.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {



  List<UserDto> listAllUsers();

  void update(UserDto user, Long id);

  UserDto findById(Long id);

  List<AccountBasicDto> getAccountsBalance(Long id);

  void delete(Long id);

  UserDto update(Long id, UserRequestDto updatedDto);


  Page<UserEntity> findALl(Pageable pageable);
}