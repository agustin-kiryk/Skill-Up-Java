package com.alkemy.wallet.auth.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
  private Long id;
  private String currency;
  private Double transactionLimit;
  private Double balance;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
