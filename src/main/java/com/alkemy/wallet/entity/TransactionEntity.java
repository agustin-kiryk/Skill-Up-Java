package com.alkemy.wallet.entity;

import com.alkemy.wallet.enumeration.TypeTransaction;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "TRANSACTIONS")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  @Column(name = "AMOUNT", nullable = false)
  private Double amount;
  @Column(name = "TYPE_TRANSACTION", nullable = false)
  private TypeTransaction type;
  @Column(name = "DESCRIPTION")
  private String description;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "ACCOUNT_ID")
  private AccountEntity accountId;

  @CreationTimestamp
  @Column(name = "TRANSACTION_DATE")
  private Date transactionDate;

}
