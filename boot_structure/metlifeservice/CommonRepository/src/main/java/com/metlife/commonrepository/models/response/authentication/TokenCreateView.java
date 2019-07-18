package com.metlife.commonrepository.models.response.authentication;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TokenCreateView implements Serializable {
    private String token;
    private String customerName;
}
