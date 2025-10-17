package com.autoshopping.stock_control.api.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionCheckResponse {
    private boolean active;
    private Long timestamp;
}
