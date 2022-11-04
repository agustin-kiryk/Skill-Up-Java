package com.alkemy.wallet.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ApiErrorDTO {
  private HttpStatus status;
  private String message;
  private List<String> errors;


}
