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

@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id=?")
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

  @Column(name = "CREATION_DATE")
  @CreationTimestamp
  private Date createDateTime;

  @Column(name = "UPDATE_DATE")
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

  private List<AccountEntity> accounts = new ArrayList<>();

  public void addAccount(AccountEntity account) {
    accounts.add(account);

  }

  public void deleteAccount(AccountEntity account) {
    accounts.remove(account);

  }

  private boolean deleted = Boolean.FALSE;

}

