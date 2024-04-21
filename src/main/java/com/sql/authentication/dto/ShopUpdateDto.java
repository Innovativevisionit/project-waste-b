package com.sql.authentication.dto;

import com.sql.authentication.Enum.StatusEnum;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopUpdateDto {
    @NotNull(message = "Id is required")
    private int id;
    @NotNull(message = "Status is required")
    private String status;
    private String reason;
//    @AssertTrue
//    private boolean isReason() {
//        if (status.equalsIgnoreCase(StatusEnum.reject.getValue())) {
//            return reason != null && !reason.isEmpty(); // If either date is null, it's considered valid
//        }
//        return true;
//    }
}
