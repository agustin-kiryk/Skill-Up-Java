package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.service.impl.UserServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserServiceImpl userServiceImpl;


  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getAll()
  {
    List<UserDto> users=userServiceImpl.listAllUsers();
    return ResponseEntity.ok().body(users);
  }


  @GetMapping("/account/balance"){


  }
}
