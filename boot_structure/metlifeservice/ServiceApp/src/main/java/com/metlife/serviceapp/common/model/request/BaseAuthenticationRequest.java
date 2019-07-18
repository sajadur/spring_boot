package com.metlife.serviceapp.common.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseAuthenticationRequest implements Serializable {
    private String userName;
    private String ipAddress;
}
