package com.alkemy.wallet.controller;


import com.alkemy.wallet.auth.dto.AuthenticationResponse;
import com.alkemy.wallet.auth.dto.UserAuthDto;
import com.alkemy.wallet.dto.RoleDto;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.service.IRoleService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private IRoleRepository iRoleRepository;
  @Autowired
  private IRoleService iRoleService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> registerRole(@Valid @RequestBody RoleDto role) {
    this.iRoleService.save(role);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
