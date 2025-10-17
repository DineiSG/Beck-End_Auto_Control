package com.autoshopping.stock_control.api.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    private String status;
    private String title;
    private String msg;
    private String token;
    private Object dados;

}
