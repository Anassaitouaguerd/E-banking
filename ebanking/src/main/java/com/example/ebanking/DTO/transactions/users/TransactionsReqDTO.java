package com.example.ebanking.DTO.transactions.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsReqDTO {

    @NotBlank(message = "Amount is required")
    private String amount;

    @NotBlank(message = "is_interbank is required")
    private Boolean is_interbank;

    @NotBlank(message = "destination_account_id is required")
    private Long destination_account_id;

}
