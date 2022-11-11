package com.alkemy.wallet.auth.controller;


import com.alkemy.wallet.auth.dto.AuthenticationRequest;
import com.alkemy.wallet.auth.dto.AuthenticationResponse;
import com.alkemy.wallet.auth.dto.ResponseUserDto;
import com.alkemy.wallet.auth.dto.UserAuthDto;
import com.alkemy.wallet.auth.service.JwtUtils;
import com.alkemy.wallet.auth.service.UserDetailsCustomService;
import com.alkemy.wallet.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

  private UserDetailsCustomService userDetailsServices;
  private AuthenticationManager authenticationManager;
  private JwtUtils jwtTokenUtils;

  @Autowired
  public UserAuthController(UserDetailsCustomService userDetailsServices,
      AuthenticationManager authenticationManager, JwtUtils jwtTokenUtils,
      IUserService iUserService) {
    this.userDetailsServices = userDetailsServices;
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtils = jwtTokenUtils;
  }
  @ApiOperation(value = "register", notes = "creates and returns an user ")
  @ApiResponse(code = 201, message = "Successfully created")
  @PostMapping("/register")
  public ResponseEntity<ResponseUserDto> signUp(@Valid @RequestBody ResponseUserDto user) {
    ResponseUserDto userRegister = this.userDetailsServices.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
  }

  @ApiOperation(value = "register admin", notes = "creates and returns an admin user ")
  @ApiResponse(code = 201, message = "Successfully created")
  @PostMapping("/registerAdmin")
  public ResponseEntity<AuthenticationResponse> signUpAdmin(@Valid @RequestBody UserAuthDto user) {
    this.userDetailsServices.saveAdmin(user);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  @ApiOperation(value = "login", notes = "a login that returns a token")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized -bad email or password")
  })
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> signIn(
      @RequestBody AuthenticationRequest authenticationRequest) {

    UserDetails userDetails;

    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
            authenticationRequest.getPassword())
    );
    userDetails = (UserDetails) auth.getPrincipal();

    final String jwt = jwtTokenUtils.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }


}