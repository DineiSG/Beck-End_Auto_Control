package com.autoshopping.stock_control.api.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserAccessResponse {
    private String status;
    private String message;
    private Object data;
    private Object acessos;
    private Long timestamp;

    public static UserAccessResponse error(String message) {
        return UserAccessResponse.builder()
                .status("error")
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static UserAccessResponse success(Object data) {
        return UserAccessResponse.builder()
                .status("success")
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
