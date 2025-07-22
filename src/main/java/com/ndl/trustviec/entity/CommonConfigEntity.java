package com.ndl.trustviec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CommonConfig")
@Table(name = "common_config")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonConfigEntity {
    @Id
    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value")
    private String value;
}
