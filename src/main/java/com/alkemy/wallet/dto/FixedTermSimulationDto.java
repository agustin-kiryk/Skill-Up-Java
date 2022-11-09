package com.alkemy.wallet.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedTermSimulationDto {

  private Date creationDate;
  private Date closingDate;
  private double amount;
  private double interest;
  private double totalAmount;
}
