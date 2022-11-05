package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.FixedTermDepositBasicDto;
import com.alkemy.wallet.entity.FixedTermDepositEntity;
import java.util.ArrayList;
import java.util.List;

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

}
