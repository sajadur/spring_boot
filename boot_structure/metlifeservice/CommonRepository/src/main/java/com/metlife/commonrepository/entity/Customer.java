package com.metlife.commonrepository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "customer_basic_info")
public class Customer {
    @Id
    @Column(name="id")
    @SequenceGenerator(name = "CustomerIdGenerator", sequenceName = "customer_basic_info_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerIdGenerator")
    private Long id;

    @Column(name = "full_name")
    @NotNull
    @Size(max = 100)
    private String customerFullName;

    @Column(name = "mother_name")
    @Size(max = 100)
    private String motherName;

    @Column(name = "father_name")
    @Size(max = 100)
    private String fatherName;

    @Column(name = "dob")
    @NotNull
    private Date dateOfBirth;


    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "is_removed")
    private Character isRemoved;
}
