package com.sql.authentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean status;
    private String message;
    private Object data;

    public ApiResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
