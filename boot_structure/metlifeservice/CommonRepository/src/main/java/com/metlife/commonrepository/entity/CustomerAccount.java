package com.metlife.commonrepository.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.metlife.commonrepository.helper.serializer.SensitiveDataSerializer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "customer_account")
public class CustomerAccount {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "CustomerAccountIdGenerator", sequenceName = "customer_account_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerAccountIdGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "mobile_number")
    @NotNull
    @Size(max = 13)
    private String mobileNumber;

    @Column(name = "status")
    private int customerStatus;

    @Column(name = "status_change_remarks")
    private String statusChangeRemarks;

    @JsonSerialize(using = SensitiveDataSerializer.class)
    @Column(name = "password")
    @Size(max = 100)
    private String loginPassword;

    @Column(name = "customer_block_date")
    private Date customerBlockDate;

    @Column(name = "customer_unblock_date")
    private Date customerUnblockDate;

    @Column(name = "customer_temp_block_date")
    private Date customerTempBlockDate;

    @Column(name = "customer_temp_unblock_date")
    private Date customerTempUnblockDate;

    @Column(name = "bad_li_per_day")
    private Integer badLoginPerDay;

    @Column(name = "bad_li_attempt")
    private Integer badLoginAttempt;

    @Column(name = "is_removed")
    private Character isRemoved;

}
