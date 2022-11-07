package com.alkemy.wallet.auth.service;


import com.alkemy.wallet.auth.dto.AuthenticationRequest;
import com.alkemy.wallet.auth.dto.AuthenticationResponse;
import com.alkemy.wallet.auth.dto.UserAuthDto;
import com.alkemy.wallet.auth.filter.JwtRequestFilter;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.exception.RepeatedUsername;
import com.alkemy.wallet.repository.IUserRepository;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

  @Autowired
  private IUserRepository IUserRepository;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtRequestFilter jwtRequestFilter;





  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = IUserRepository.findByUsername(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("username or password not found");
    }
    return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
  }

  public void save(@Valid UserAuthDto userDto) throws RepeatedUsername {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (IUserRepository.findByUsername(userDto.getEmail()) != null) {
      throw new RepeatedUsername("Username repetido");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    // userEntity.setRoleId();
    userEntity = this.IUserRepository.save(userEntity);

  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
    UserDetails userDetails;
    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
      );
      userDetails = (UserDetails) auth.getPrincipal();
    } catch (BadCredentialsException e) {
      throw new Exception("Username or password invalid", e);
    }
    final String jwt = jwtUtils.generateToken(userDetails);
    return new AuthenticationResponse(jwt);
  }


}