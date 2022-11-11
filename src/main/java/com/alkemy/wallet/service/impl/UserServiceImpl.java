package com.alkemy.wallet.service.impl;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.dto.UserRequestDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.AccountMap;
import com.alkemy.wallet.mapper.UserMap;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IUserService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private IUserRepository IUserRepository;

  @Autowired
  private AccountServiceImpl accountService;

  @Autowired
  private UserMap userMap;

  @Autowired
  private AccountMap accountMap;


  @Override
  public UserDto findById(Long id) {

    UserEntity entity = IUserRepository.findById(id).orElseThrow(
        () -> new ParamNotFound("User ID Invalid"));
    UserDto dto = userMap.userEntity2Dto(entity);
    return dto;


  }

  @Override
  public List<AccountBasicDto> getAccountsBalance() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = IUserRepository.findByEmail(email);
    List<AccountEntity> accounts = user.getAccounts();

    for (int i = 0; i < accounts.size(); i++) {

      AccountEntity account;
      account = accounts.get(i);
      account.setBalance(accountService.calculateBalance(account.getAccountId()));

    }
    List<AccountBasicDto> accountsDto = accountMap.accountEntityList2BasicDto(accounts);
    return accountsDto;
  }
  @Override
  public List<UserDto> listAllUsers() {

    return userMap.userEntityList2DtoList(IUserRepository.findAll());
  }

  @Override
  public void update(UserDto user, Long id) {

  }

  @Override
  public void delete(Long id) {

    UserEntity entity = this.IUserRepository.findById(id).orElseThrow(
        ()->new ParamNotFound("invalid Id"));
    this.IUserRepository.deleteById(id);

}

  @Override
  public UserDto update(Long id, UserRequestDto updatedDto) {
    UserEntity userEntity= IUserRepository.findById(id).orElseThrow(
        ()-> new ParamNotFound("User ID Invalid"));
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = this.IUserRepository.findByEmail(email);
    if(!userEntity.equals(user))
      throw new ParamNotFound("User logged doesn't match with user being updated");
    userEntity.setFirstName(updatedDto.getFirstName());
    userEntity.setLastName(updatedDto.getLastName());
    userEntity.setPassword(updatedDto.getPassword());
    userEntity.setUpdateDateTime(new Date());
    UserEntity entitySaved= IUserRepository.save(userEntity);
    UserDto result=userMap.userEntity2Dto(entitySaved);
    return result;
  }


}


