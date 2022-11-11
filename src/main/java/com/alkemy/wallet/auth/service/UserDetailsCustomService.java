package com.alkemy.wallet.auth.service;


import com.alkemy.wallet.auth.dto.ResponseUserDto;
import com.alkemy.wallet.auth.dto.UserAuthDto;
import com.alkemy.wallet.dto.CurrencyDto;
import com.alkemy.wallet.entity.RoleEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.RoleName;
import com.alkemy.wallet.mapper.UserMap;
import com.alkemy.wallet.mapper.exception.RepeatedUsername;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IAccountService;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

  @Autowired
  private IUserRepository IUserRepository;
  @Autowired
  private IRoleRepository iRoleRepository;
  @Autowired
  private UserMap userMap;
  @Autowired
  private IAccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = IUserRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("username or password not found");
    }
    return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
  }

  public ResponseUserDto save(@Valid ResponseUserDto userDto) throws RepeatedUsername {
    if (IUserRepository.findByEmail(userDto.getEmail()) != null) {
      throw new RepeatedUsername("repeated user");
    }
    UserEntity entity = userMap.userAuthDto2Entity(userDto);

    RoleEntity role = iRoleRepository.findByName(RoleName.ROLE_USER);
    entity.setRole(role);

    UserEntity entitySaved = this.IUserRepository.save(entity);

    this.accountService.addAccount(entitySaved.getEmail(), new CurrencyDto(Currency.USD));
    this.accountService.addAccount(entitySaved.getEmail(), new CurrencyDto(Currency.ARS));
    ResponseUserDto responseUserDto = userMap.userAuthEntity2Dto(entitySaved);


    return responseUserDto;


  }

  public void saveAdmin(@Valid UserAuthDto userDto) throws RepeatedUsername {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (IUserRepository.findByEmail(userDto.getEmail()) != null) {
      throw new RepeatedUsername("Username repetido");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    userEntity.setRole(iRoleRepository.findByName(RoleName.ROLE_ADMIN));
    userEntity = this.IUserRepository.save(userEntity);
  }


}