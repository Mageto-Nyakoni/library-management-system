package org.example;

import org.junit.jupiter.api.Test;

import nyakoni.library.model.Account;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void deposit() {
        Account acc = new Account();
        assertEquals(0.0,acc.getBalance());
    }

    @Test
    void withdraw() {
        Account acc = new Account();
        acc.withdraw(1.0);
        assertEquals(-1.0,acc.getBalance());
    }

    @Test
    void getBalance() {
    }
}