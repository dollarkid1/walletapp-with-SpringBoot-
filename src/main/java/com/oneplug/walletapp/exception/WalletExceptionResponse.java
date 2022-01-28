package com.oneplug.walletapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class WalletExceptionResponse {
    private String id;
}
