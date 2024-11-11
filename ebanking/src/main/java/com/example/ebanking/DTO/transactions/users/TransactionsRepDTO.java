package com.example.ebanking.DTO.transactions.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransactionsRepDTO {

    private String amount;
    private String destination_account_id;
    private String source_account_id;
    private String transaction_type;
    private String transaction_status;
    private String transaction_date;

}
