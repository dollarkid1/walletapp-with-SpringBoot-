package com.oneplug.walletapp.controller;

import com.oneplug.walletapp.entity.RecipientWallet;
import com.oneplug.walletapp.entity.Wallet;
import com.oneplug.walletapp.services.ValidationErrorService;
import com.oneplug.walletapp.services.WalletServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletServices walletServices;

    @Autowired
    private ValidationErrorService validationService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(walletServices.getAll(),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(walletServices.getById(id),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Wallet wallet, BindingResult result){

        ResponseEntity errors = validationService.validate(result);
        if (errors != null) return errors;
        Wallet walletSaved = walletServices.update(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Wallet wallet, BindingResult result){

        ResponseEntity errors = validationService.validate(result);
        if (errors != null) return errors;
        wallet.setId(id);
        Wallet walletSaved = walletServices.update(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        walletServices.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PatchMapping("/deposit")
    public ResponseEntity<?> deposit( Long id, BigDecimal amount, @Valid @RequestBody Wallet wallet){
        walletServices.deposit(id,amount);
        Wallet walletSaved = walletServices.update(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.OK);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<?> withdraw(Long id, BigDecimal amount,String pin, @Valid @RequestBody Wallet wallet){
        walletServices.withdraw(id,amount,pin);
        Wallet walletSaved = walletServices.update(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.ACCEPTED);


    }

    @PatchMapping("/transfer")
    public ResponseEntity<?> transfer( Long id, BigDecimal amount, String pin, String recipientAccountNumber, @Valid @RequestBody Wallet wallet){
        walletServices.transfer(recipientAccountNumber,pin,id,amount);
        Wallet walletSaved = walletServices.update(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.ACCEPTED);
    }

}
