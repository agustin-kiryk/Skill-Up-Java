package com.alkemy.wallet.auth.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto {
  private Long id;
  private Double amount;
  private String type;
  private String description;
  private Long userId;
  private Long accountId;
  private LocalDateTime transactionDate;
}