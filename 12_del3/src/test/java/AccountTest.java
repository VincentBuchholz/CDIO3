//importere Die klassen fra pakken CDIO3
import CDIO3.Account;
//importere junit test klasse så vi kan lave en junit test
import org.junit.jupiter.api.Test;
// Importere en klasse så vi kan bruge assert metoder.
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void deposit() {
        // conditional statement(loop) for-then, bruges til at køre testen 1000 gange (rulle terning 1000 gange)

        for (int i = 0; i < 1000; i++) {
            Account accountTest = new Account();
            System.out.println("Starting Balance" + " " + accountTest);
            accountTest.deposit(1500);
            System.out.println("Balance after Deposit" + " " + accountTest);
            System.out.println();

            int expectedBalance = 1500;
            //assertEquals ser om expectedBalance = værdien af balancen efter deposit 1000 gange
            assertEquals(expectedBalance, accountTest.getBalance());

        }
    }
}