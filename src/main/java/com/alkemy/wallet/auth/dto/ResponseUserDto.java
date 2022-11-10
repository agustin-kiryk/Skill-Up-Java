package com.alkemy.wallet.auth.dto;

import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {
  private Long id;
  @NotNull
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private RoleEntity role;
  private List<AccountEntity> accounts;
  private Date creationDate;
  private Date updateDate;
  private String jwt;
}
