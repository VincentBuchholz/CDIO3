package CDIO3;

import CDIO3.Die;
import CDIO3.Fields;
import gui_main.GUI;
import gui_fields.GUI_Player;
import gui_codebehind.GUI_BoardController;
import gui_fields.GUI_Field;
import java.awt.*;
import gui_fields.GUI_Street;


public class Main {
    public static void main(String[] args) {

        Fields field = new Fields();
        GUI gui = new GUI(field.fields);


        GUI_BoardController controller = new GUI_BoardController(gui.getFields(), Color.blue);
        int amountOfPlayers = controller.getUserInteger("Enter amount of players between 2 and 4", 2, 4);

        if (amountOfPlayers == 2) {


            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 20);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(20);

            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 20);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(20);


            String roll;
            Die die = new Die();


            int previousFieldP1 = 0;
            int newFieldP1;
            int previousFieldP2 = 0;
            int newFieldP2;
            boolean bankruptP1 = false;
            boolean bankruptP2 = false;

            do {

                roll = controller.getUserButtonPressed(player1.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                newFieldP1 = die.getDie() + previousFieldP1;
                if (newFieldP1 >= 24) {
                    accountPlayerOne.deposit(2);
                    newFieldP1 = newFieldP1 - 24;
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);

                if (field.getIsOwnable(newFieldP1) == true && field.getIsOwned(newFieldP1) == false) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    field.setIsOwned(newFieldP1, true);
                    field.setOwnedBy(newFieldP1, 1);
                }
                if (field.getIsOwned(newFieldP1) == true && field.getOwnedBy(newFieldP1) == 2) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    accountPlayerTwo.deposit(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    player2.setBalance(accountPlayerTwo.getBalance());
                }
                if (accountPlayerOne.getBalance() <= 0) {
                    bankruptP1 = true;
                    break;
                }

                previousFieldP1 = newFieldP1;

                roll = controller.getUserButtonPressed(player2.getName()+ " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }


                newFieldP2 = die.getDie() + previousFieldP2;
                if (newFieldP2 >= 24) {
                    newFieldP2 = newFieldP2 - 24;
                    accountPlayerTwo.deposit(2);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                if (field.getIsOwnable(newFieldP2) == true && field.getIsOwned(newFieldP2) == false) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    field.setIsOwned(newFieldP2, true);
                    field.setOwnedBy(newFieldP2, 2);
                }
                if (field.getIsOwned(newFieldP2) == true && field.getOwnedBy(newFieldP2) == 1) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    accountPlayerOne.deposit(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    player1.setBalance(accountPlayerOne.getBalance());
                }
                if (accountPlayerTwo.getBalance() <= 0) {
                    bankruptP2 = true;
                    break;
                }

                previousFieldP2 = newFieldP2;

            } while (bankruptP1 == false && bankruptP2 == false);

            if (accountPlayerOne.getBalance() > accountPlayerTwo.getBalance()) {
                controller.showMessage(player1.getName()+ " you win!");
            }
            if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance()) {
                controller.showMessage( player2.getName()+ " you win!");
            }

        }
    }

}
