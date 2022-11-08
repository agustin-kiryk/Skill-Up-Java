package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.entity.AccountEntity;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMap {

  @Autowired FixedTermDepositMap fixedTermDepositMap;

  public List<AccountDto> accountEntityList2DtoList(List<AccountEntity> accounts) {
    List<AccountDto> accountDtos = new ArrayList<>();
    AccountDto accountDto;
    for (AccountEntity account: accounts) {
      accountDto = new AccountDto();
      accountDto.setId(account.getAccountId());
      accountDto.setCurrency(account.getCurrency());
      accountDto.setTransactionLimit(account.getTransactionLimit());
      accountDto.setBalance(account.getBalance());
      accountDto.setUpdateDate(account.getUpdateDate());
      accountDto.setCreationDate(account.getCreationDate());
      accountDtos.add(accountDto);

    }
    return accountDtos;
  }
  public AccountBasicDto accountEntity2BasicDto(AccountEntity entity) {

    AccountBasicDto account = new AccountBasicDto();

    account.setAccountId(entity.getAccountId());
    account.setCurrency(entity.getCurrency());
    account.setBalance(0);
    account.setFixedTermDeposits(
        fixedTermDepositMap.entityList2BasicDtoList(entity.getFixedTermDeposits()));

    return account;

  }

  public List<AccountBasicDto> accountEntityList2BasicDto(List<AccountEntity> entities) {

    List<AccountBasicDto> accounts = new ArrayList<>();

    for (AccountEntity entity : entities) {

      accounts.add(accountEntity2BasicDto(entity));

    }

    return accounts;


  }

  public List<AccountEntity> accountDtoList2EntityList(List<AccountBasicDto> accounts) {
    return null;
  }

  public AccountDto accountEntity2DTO(AccountEntity entitySaved) {
    AccountDto dto = new AccountDto();
    dto.setId(entitySaved.getAccountId());
    dto.setCurrency(entitySaved.getCurrency());
    dto.setTransactionLimit(entitySaved.getTransactionLimit());
    dto.setBalance(entitySaved.getBalance());
    dto.setUpdateDate(entitySaved.getUpdateDate());

    return dto;
  }

  public AccountEntity accountDto2Entity(AccountDto dto){
    AccountEntity entity = new AccountEntity();

    entity.setAccountId(dto.getId());
    entity.setCurrency(dto.getCurrency());
    entity.setTransactionLimit(dto.getTransactionLimit());
    entity.setBalance(dto.getBalance());
    entity.setCreationDate(dto.getCreationDate());
    entity.setUpdateDate(dto.getUpdateDate());

    return entity;

  }
}
