package com.alkemy.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FixedTermDepositDto {

    private Long id;
    private Double amount;
    private Long userId;
    private Long accountId;
    private Double interest;
    private Date creationDate;
    private Date closingDate;
}
