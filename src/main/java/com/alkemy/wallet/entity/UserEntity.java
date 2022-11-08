package com.alkemy.wallet.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USERS")

@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class UserEntity implements UserDetails {


  public static final long serialVersionUID = 1L;
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;

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

  @Column(name= "CREATION_DATE")
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

  private void addAccount(AccountEntity account) {
    accounts.add(account);

  }

  private void deleteAccount(AccountEntity account) {
    accounts.remove(account);

  }
  private boolean deleted = Boolean.FALSE;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
  public UserEntity() {
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }
  private String username;
}


