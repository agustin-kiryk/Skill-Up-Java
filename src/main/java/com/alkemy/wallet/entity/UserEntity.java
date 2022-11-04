package com.alkemy.wallet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "USERS")

@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class UserEntity implements Serializable {


  public static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "FIRST_NAME", nullable = false)
  private String firstName;

  @Column(name = "LAST_NAME", nullable = false)
  private String lastName;

  @Column(name = "EMAIL", nullable = false)
  private String email;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @CreationTimestamp
  private Date createDateTime;

  @UpdateTimestamp
  private Date updateDateTime;

  @ManyToOne()
  @JoinColumn(name = "roleId")
  private RoleEntity role;

  @OneToMany(mappedBy = "user",
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.REFRESH,
          CascadeType.PERSIST
      })

  private boolean deleted = Boolean.FALSE;

  private List<AccountEntity> accounts = new ArrayList<>();

  private void addAccount(AccountEntity account) {
    accounts.add(account);

  }

  private void deleteAccount(AccountEntity account) {
    accounts.remove(account);

  }


}
