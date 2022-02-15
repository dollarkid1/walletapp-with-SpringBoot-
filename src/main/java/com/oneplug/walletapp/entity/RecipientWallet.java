package com.oneplug.walletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecipientWallet {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @NotBlank(message = "Name can't be blank")
        @Size(min = 2, max = 30)
        private String name;

        @Size(min = 2, max = 30)
        private String accountNumber;

        @Size(max = 100)
        private String description;

        @Min(1)
        @Max(3)
        private Integer priority;

        private BigDecimal currentBalance = BigDecimal.ZERO;




    }
