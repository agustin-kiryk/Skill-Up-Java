package com.alkemy.wallet.auth.dto;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.RoleEntity;
import com.alkemy.wallet.enumeration.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  @Email(message = "the email must be real email")
  private String email;
  @NotEmpty
  private String password;
  private RoleName role;
  private List<AccountDto> accounts;
  private Date creationDate;
  private Date updateDate;
  private String jwt;
}
