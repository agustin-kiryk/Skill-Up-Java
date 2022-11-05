package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;

  public interface ITransactionService {

    public TransactionDto save(TransactionDto transaction);

  }

