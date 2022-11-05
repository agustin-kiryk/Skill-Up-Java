package com.alkemy.wallet.dto;


import lombok.Data;

@Data
public class AccountBasicDto {

  private Long accountId;
  private currency currency;

  private double balance;
}


}
