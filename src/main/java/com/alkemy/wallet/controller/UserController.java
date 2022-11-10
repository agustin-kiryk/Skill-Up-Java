package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.dto.UserRequestDto;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserService userService;
  @Autowired
  IUserService iUserService;


  @GetMapping()
  public ResponseEntity<List<UserDto>> getAll()
  {
    List<UserDto> users= userService.listAllUsers();
    return ResponseEntity.ok().body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> search(@PathVariable("id") Long id){
    UserDto dto = iUserService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
    this.userService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestParam UserRequestDto updatedDto)
  {
    UserDto dto=userService.update(id,updatedDto);
    return ResponseEntity.ok().body(dto);
  }




}

