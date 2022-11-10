package com.alkemy.wallet.dto;


import com.alkemy.wallet.enumeration.RoleName;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto {

  private Long id;
  private String description;
  @NotNull
  private RoleName name;

}
