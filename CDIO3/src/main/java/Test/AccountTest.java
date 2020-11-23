package Test;

import CDIO3.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void deposit() {
        for (int i = 0; i < 1000; i++) {
            Account accountTest = new Account();
            accountTest.getBalance();
            System.out.println("Starting Balance" + " " + accountTest);
            accountTest.deposit(1500);
            System.out.println("Balance after Deposit" + " " + accountTest);
            System.out.println();

            int expectedBalance = 1500;

            assertEquals(expectedBalance, accountTest.getBalance());

        }
    }
}