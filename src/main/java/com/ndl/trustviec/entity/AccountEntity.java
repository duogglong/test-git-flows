package com.ndl.trustviec.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Account")
@Table(name = "account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity extends AbstractAuditingEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "cif", unique = true)
    private String cif;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "parent_id")
    private UUID parentId;

    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", columnDefinition = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "varchar(30)"))
    private Set<RoleEntity> roles = new HashSet<>();
}
