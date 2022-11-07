package com.alkemy.wallet.entity;

import com.alkemy.wallet.enumeration.Currency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
@SQLDelete(sql = "UPDATE accounts SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ACCOUNT_ID", nullable = false)
  private Long accountId;

  @Enumerated(EnumType.STRING)
  @Column(name = "CURRENCY", nullable = false)
  private Currency currency;

  @Column(name = "TRANSACTION_LIMITS", nullable = false)
  private Double transactionLimit;

  @Column(name = "BALANCE", nullable = false)
  private Double balance;

  @Column(name = "UPDATE_DATE")
  private Date updateDate;

  @Column(name = "CREATION_DATE")
  private Date creationDate;

  @Column(name = "SOFT_DELETE")
  private boolean softDelete = Boolean.FALSE;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)

  private UserEntity user;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountEntity")
  private List<FixedTermDepositEntity> fixedTermDeposits=new ArrayList<>();


  @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountEntity")
  private List<TransactionEntity> transactionEntities = new ArrayList<>();

}
