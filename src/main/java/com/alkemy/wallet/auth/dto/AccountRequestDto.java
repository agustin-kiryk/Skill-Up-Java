package com.alkemy.wallet.auth.dto;


import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDto {
  private String currency;
  private Double transactionLimit;
  private Double balance;
  private Long userId;
}