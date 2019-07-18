package com.metlife.commonrepository.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "customer_token")
public class CustomerToken {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerAccount customerAccount;

    @Column(name = "created_on")
    @NotNull
    private Date createdOn;

    @Column(name = "expire_on")
    @NotNull
    private Date expireOn;

    @Column(name = "request_from", length = 10)
    private String requestFrom;

    @Column(name = "request_ip", length = 20)
    private String requestedIP;
}
