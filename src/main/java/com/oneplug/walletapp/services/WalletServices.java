package com.oneplug.walletapp.services;

import com.oneplug.walletapp.entity.RecipientWallet;
import com.oneplug.walletapp.entity.Wallet;
import com.oneplug.walletapp.exception.WalletException;
import com.oneplug.walletapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class WalletServices {

    @Autowired
    private WalletRepository walletRepository;


    public List<Wallet> getAll() {
        return walletRepository.findAll();
    }

    public Wallet getById(Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new WalletException("Wallet with " + id + " does not exist ");
    }

    public Wallet update(Wallet wallet) {
        if (wallet.getId() == null) {
            walletRepository.save(wallet);
        } else {
            walletRepository.save(wallet);
        }
        return wallet;
    }

    public Wallet create(String name, String pin) {

        Wallet userInfo = new Wallet();
        if (pin.length() != 4) {
            throw new WalletException("Invalid pin provided for wallet");
        }

        try {
            String[] letters = pin.split("");
            for (String letter : letters) {
                int num = Integer.parseInt(letter);
            }
            userInfo.setPin(pin);
        } catch (NumberFormatException e) {
            throw new WalletException("Invalid pin provided for wallet");
        }
        return walletRepository.save(userInfo);
    }

    public boolean delete(Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            walletRepository.delete(wallet.get());
            return true;
        }
        //TODO
        throw new WalletException("Wallet with " + id + " does not exist ");

    }


    public void deposit(Long walletId, BigDecimal amount) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty()) {
            throw new WalletException("Wallet with " + walletId + " does not exist ");
        }
        Wallet userWallet = wallet.get();
        BigDecimal newBalance = userWallet.getCurrentBalance().add(amount);
        userWallet.setCurrentBalance(newBalance);
        walletRepository.save(userWallet);

    }

    public void withdraw(Long walletId, BigDecimal amount, String pin) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            throw new WalletException("Wallet with " + walletId + " does not exist ");
        }

        Wallet userWallet = wallet.get();
        if (!userWallet.getPin().equals(pin)) {
            throw new WalletException("Pin invalid!");
        }

        BigDecimal newBalance = userWallet.getCurrentBalance().subtract(amount);
        userWallet.setCurrentBalance(newBalance);
        walletRepository.save(userWallet);

    }





    public void transfer(String recipientAccountNumber, String pin, Long walletId, BigDecimal amount) {

        RecipientWallet recipientWallet = new RecipientWallet();
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            throw new WalletException("Wallet with " + walletId + " does not exist ");
        }

        Wallet userWallet = wallet.get();
        String myPin = userWallet.getPin();

        if (recipientAccountNumber == null || !recipientAccountNumber.equals(recipientWallet.getAccountNumber())) {
            throw new WalletException("recipient account number is null/doesnt match");
        }

        if (!userWallet.getPin().equals(pin)) {
            throw new WalletException("Pin invalid!");
        } else {
            userWallet.setCurrentBalance(userWallet.getCurrentBalance().subtract(amount));

            recipientWallet.setCurrentBalance(recipientWallet.getCurrentBalance().add(amount));
        }


        BigDecimal newBalance = userWallet.getCurrentBalance().subtract(amount);
        userWallet.setCurrentBalance(newBalance);
        walletRepository.save(userWallet);


    }

}
