package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.PageDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.dto.UserRequestDto;
import com.alkemy.wallet.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @ApiOperation(value = "Get users", notes = "Returns a list of all users")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized -you are not an admin"),
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<UserDto>> getAll() {
    List<UserDto> users = userService.listAllUsers();
    return ResponseEntity.ok().body(users);
  }

  @ApiOperation(value = "Get user", notes = "Returns a user as per the id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized -you are not an admin"),
      @ApiResponse(code = 404, message = "Not found - The user was not found")
  })
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> search(
      @PathVariable("id") @ApiParam(name = "id", value = "User id", example = "1") Long id) {
    UserDto dto = iUserService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @ApiOperation(value = "Delete user", notes = "deletes a user as per the id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Successfully deleted"),
      @ApiResponse(code = 401, message = "Unauthorized -you are not logged as the user you want to delete"),
      @ApiResponse(code = 404, message = "Not found - The user was not found")
  })
  @PreAuthorize("hasRole('USER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(
      @PathVariable("id") @ApiParam(name = "id", value = "User id", example = "1") Long id) {
    this.userService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @ApiOperation(value = "Update user", notes = "updates and returns a user as per the id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully updated"),
      @ApiResponse(code = 401, message = "Unauthorized -you are not logged as the user you want to update"),
      @ApiResponse(code = 404, message = "Not found - The user was not found")
  })
  @PreAuthorize("hasRole('USER')")
  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
      @PathVariable @ApiParam(name = "id", value = "User id", example = "1") Long id,
      @RequestParam UserRequestDto updatedDto) {
    UserDto dto = userService.update(id, updatedDto);
    return ResponseEntity.ok().body(dto);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{userspaginated}")
  public ResponseEntity<PageDto<UserDto>> getAllUsers(@PageableDefault(size = 10) Pageable page,
      HttpServletRequest request) {
    PageDto<UserDto> result = iUserService.findAllUsers(page, request);
    return ResponseEntity.ok().body(result);
  }

}

