package com.oneplug.walletapp.repository;

import com.oneplug.walletapp.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    List<Wallet> findAllByOrderByPriority();
    Wallet findByAccountNumber();
    //ToDo find by id
}


interface Father {
    public String canSing();
}

class Son implements Father {

    @Override
    public String canSing() {
        return "I can sing";
    }
}

abstract class Mother {
    public String cook() {
        return "I love cooking egusi soup";
    }

    public void canClean() {

    }

    public abstract void clean();
}

class Daughter extends Mother {

    @Override
    public void clean() {

    }
}