package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;
import java.util.List;

public interface ITransactionService {


  public List<TransactionDto> getByAccountAndType(Long accountId, String type );



}
