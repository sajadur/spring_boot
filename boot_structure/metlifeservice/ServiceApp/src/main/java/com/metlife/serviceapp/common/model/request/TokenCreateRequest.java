package com.metlife.serviceapp.common.model.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.metlife.commons.helper.serializer.SensitiveDataSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenCreateRequest extends BaseAuthenticationRequest {
    @JsonSerialize(using = SensitiveDataSerializer.class)
    private String password;
}
