package com.alkemy.wallet.dto;

import com.alkemy.wallet.enumeration.RoleName;
import java.util.List;
import javax.validation.Valid;
import lombok.Data;

@Data
public class UserDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private RoleName role;

  private List<@Valid AccountBasicDto> accounts;



}
