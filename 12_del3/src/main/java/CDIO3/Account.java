package CDIO3;


public class Account {
    private int balance;

    //Tilføjer M til balancen
    public int deposit(int amountToDeposit) {
        this.balance = this.balance + amountToDeposit;
        return balance;
    }
    //Fratrækker M til balancen
    public int withdraw(int amountToWithdraw) {
        this.balance = this.balance - amountToWithdraw;
        return this.balance;
    }

    public int getBalance() { return balance;
    }

    public int setBalance(int balance) {
        this.balance = balance;
        return this.balance;
    }

    public String toString() {
        return String.valueOf(this.balance);
    }

}


