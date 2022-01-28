package com.oneplug.walletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {
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

    @Min(1)@Max(3)
    private Integer priority;

    private Double currentBalance;

    @PrePersist
    public void setBalance(){
        this.currentBalance = new Double(0);
    }
}
