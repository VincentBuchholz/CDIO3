import CDIO3.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest2 {

    @Test
    void withdraw() {
        for (int i = 0; i < 1000; i++) {
            Account accountTest2 = new Account();
            accountTest2.deposit(1500);
            System.out.println("Starting Balance" + " " + accountTest2);
            accountTest2.withdraw(750);
            System.out.println("Balance after Withdraw" + " " + accountTest2);
            System.out.println();

            int expectedBalance = 750;

            assertEquals(expectedBalance, accountTest2.getBalance());

        }
    }
}