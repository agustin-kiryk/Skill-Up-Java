package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.FixedTermDepositBasicDto;
import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.entity.FixedTermDepositEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FixedTermDepositMap {

  public FixedTermDepositBasicDto entity2BasicDto(FixedTermDepositEntity fixedTermDeposit) {

    FixedTermDepositBasicDto basicDto = new FixedTermDepositBasicDto();

    basicDto.setId(fixedTermDeposit.getId());
    basicDto.setAmount(fixedTermDeposit.getAmount());
    basicDto.setCreationDate(fixedTermDeposit.getCreationDate());
    basicDto.setClosingDate(fixedTermDeposit.getClosingDate());

    return basicDto;
  }

  public List<FixedTermDepositBasicDto> entityList2BasicDtoList(List<FixedTermDepositEntity> entities){

    List<FixedTermDepositBasicDto> basicDtos = new ArrayList<>();

    for(FixedTermDepositEntity entity : entities) {

      basicDtos.add(entity2BasicDto(entity));
    }

    return basicDtos;
  }

  public FixedTermDepositEntity fixedTermDepositDto2Entity(FixedTermDepositDto dto) {

    FixedTermDepositEntity entity = new FixedTermDepositEntity();

    entity.setAmount(dto.getAmount());
    entity.setInterest(dto.getInterest());
    entity.setClosingDate(dto.getClosingDate());
    entity.setCreationDate(dto.getCreationDate());
    entity.setUserId(dto.getUserId());
    entity.setAccountId(dto.getAccountId());
    return entity;
  }

  public FixedTermDepositDto fixedTermDepositEntity2Dto(FixedTermDepositEntity entity) {

    FixedTermDepositDto fixedTermDepositDto = new FixedTermDepositDto();

    fixedTermDepositDto.setId(entity.getId());
    fixedTermDepositDto.setAccountId(entity.getAccountId());
    fixedTermDepositDto.setUserId(entity.getUserId());
    fixedTermDepositDto.setAmount(entity.getAmount());
    fixedTermDepositDto.setClosingDate(entity.getClosingDate());
    fixedTermDepositDto.setCreationDate(entity.getCreationDate());
    fixedTermDepositDto.setInterest(entity.getInterest());
    return fixedTermDepositDto;
  }

}
