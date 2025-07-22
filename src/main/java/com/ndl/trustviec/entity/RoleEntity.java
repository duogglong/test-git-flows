package com.ndl.trustviec.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Role")
@Table(name = "role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @Column(name = "role")
    private String role;
}
