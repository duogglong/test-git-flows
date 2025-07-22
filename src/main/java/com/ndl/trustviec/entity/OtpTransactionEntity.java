package com.ndl.trustviec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;

@Entity(name = "OtpTransaction")
@Table(name = "otp_transaction")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpTransactionEntity extends AbstractAuditingEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "cif")
    private String cif;

    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private String type;

    @Column(name = "otp")
    private String otp;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "request_object", columnDefinition = "TEXT")
    private String requestObject;
}
