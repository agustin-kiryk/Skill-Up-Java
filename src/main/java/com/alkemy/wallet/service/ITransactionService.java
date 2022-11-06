package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionsDto;
import java.util.List;

public interface ITransactionService {

  List<TransactionsDto> transactionsById(Long userId);

}
