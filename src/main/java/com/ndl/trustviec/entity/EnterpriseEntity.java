package com.ndl.trustviec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "Enterprise")
@Table(name = "enterprise")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "enterprise_name")
    private String enterpriseName;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "address")
    private String address;

    @Column(name = "website")
    private String website;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "summary")
    private String summary;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_person_email")
    private String contactPersonEmail;

    @Column(name = "contact_person_phone")
    private String contactPersonPhone;
}
