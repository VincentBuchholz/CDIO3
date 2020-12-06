package CDIO3;

//klassen account
public class Account {
    // opretter en variable (balance) af typen int
    private int balance;

    //Tilføjer M til balancen
    public int deposit(int amountToDeposit) {
        this.balance = this.balance + amountToDeposit;
        //returnere en værdi af den samlede balance efter deposit
        return balance;
    }
    //Fratrækker M til balancen
    public int withdraw(int amountToWithdraw) {
        this.balance = this.balance - amountToWithdraw;
        //returnere en værdi af den samlede balance efter withdraw
        return this.balance;
    }
    //en metode til at få balance værdien
    public int getBalance() { return balance;
    }
    //setBalance bruges til at give balancen en bestemt værdi
    public int setBalance(int balance) {
        this.balance = balance;
        return this.balance;
    }
    //toString metoden bruges til at kunne udskrive værdien af balancen
    public String toString() {
        return String.valueOf(this.balance);
    }

}


