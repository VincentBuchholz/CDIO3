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


        GUI_BoardController controller = new GUI_BoardController(gui.getFields(), Color.lightGray);
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
                if (newFieldP1 >= 23) {
                    accountPlayerOne.deposit(2);
                    newFieldP1 = newFieldP1 - 23;
                }

                if (newFieldP1 == 18) {
                    newFieldP1 = 6;
                    controller.showMessage(player1.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);


                if (field.getIsOwnable(newFieldP1) == true && field.getIsOwned(newFieldP1) == false) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    field.setIsOwned(newFieldP1, true);
                    field.setOwnedBy(newFieldP1, 1);
                    field.setFieldsSubText(newFieldP1, "Ejer: " + "\n" + player1.getName());
                }
                if (field.getIsOwned(newFieldP1) == true && field.getOwnedBy(newFieldP1) == 2) {
                    //Hvis spiller to ejer hele det par man er landet på, betaler spiller et dobbelt.
                    if (field.getOwnedBy(newFieldP1 + 1) == field.getOwnedBy(newFieldP1) || field.getOwnedBy(newFieldP1 - 1) == field.getOwnedBy(newFieldP1)) {
                        accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                        accountPlayerTwo.deposit(field.getPrice(newFieldP1) * 2);
                        player1.setBalance(accountPlayerOne.getBalance());
                        player2.setBalance(accountPlayerTwo.getBalance());
                    } else {
                        accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                        accountPlayerTwo.deposit(field.getPrice(newFieldP1));
                        player1.setBalance(accountPlayerOne.getBalance());
                        player2.setBalance(accountPlayerTwo.getBalance());
                    }
                }
                if (accountPlayerOne.getBalance() <= 0) {
                    bankruptP1 = true;
                    break;
                }

                previousFieldP1 = newFieldP1;

                roll = controller.getUserButtonPressed(player2.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }


                newFieldP2 = die.getDie() + previousFieldP2;
                if (newFieldP2 >= 23) {
                    newFieldP2 = newFieldP2 - 23;
                    accountPlayerTwo.deposit(2);
                }

                if (newFieldP2 == 18) {
                    newFieldP2 = 6;
                    controller.showMessage(player2.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                if (field.getIsOwnable(newFieldP2) == true && field.getIsOwned(newFieldP2) == false) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    field.setIsOwned(newFieldP2, true);
                    field.setOwnedBy(newFieldP2, 2);
                    field.setFieldsSubText(newFieldP2, "Ejer: " + "\n" + player2.getName());
                }
                if (field.getOwnedBy(newFieldP2 + 1) == field.getOwnedBy(newFieldP2) || field.getOwnedBy(newFieldP2 - 1) == field.getOwnedBy(newFieldP2)) {
                    //Hvis spiller et ejer hele det par man er landet på, betaler spiller to dobbelt.
                    if (field.getOwnedBy(newFieldP2 + 1) == 1 || field.getOwnedBy(newFieldP2 - 1) == 1) {
                        accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                        accountPlayerOne.deposit(field.getPrice(newFieldP2) * 2);
                        player2.setBalance(accountPlayerTwo.getBalance());
                        player1.setBalance(accountPlayerOne.getBalance());
                    } else {
                        accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                        accountPlayerOne.deposit(field.getPrice(newFieldP2));
                        player2.setBalance(accountPlayerTwo.getBalance());
                        player1.setBalance(accountPlayerOne.getBalance());
                    }
                }
                if (accountPlayerTwo.getBalance() <= 0) {
                    bankruptP2 = true;
                    break;
                }

                previousFieldP2 = newFieldP2;

            } while (bankruptP1 == false && bankruptP2 == false);


            if (accountPlayerOne.getBalance() > accountPlayerTwo.getBalance()) {
                controller.showMessage(player1.getName() + " you win!");
            }
            if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance()) {
                controller.showMessage(player2.getName() + " you win!");
            }
        }


        // three players
        if (amountOfPlayers == 3 ) {

            //Creates player one
            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 18);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(18);

            //Creates player two
            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 18);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(18);

            //Creates player three
            GUI_Player player3 = new GUI_Player(controller.getUserString("Player three enter your name"), 18);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);
            Account accountPlayerThree = new Account();
            accountPlayerThree.setBalance(18);


            String roll;
            Die die = new Die();


            int previousFieldP1 = 0;
            int newFieldP1;
            int previousFieldP2 = 0;
            int newFieldP2;
            int previousFieldP3 = 0;
            int newFieldP3;
            boolean bankruptP1 = false;
            boolean bankruptP2 = false;
            boolean bankruptP3 = false;


            do {

                roll = controller.getUserButtonPressed(player1.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player loops on board
                newFieldP1 = die.getDie() + previousFieldP1;
                if (newFieldP1 >= 23) {
                    accountPlayerOne.deposit(2);
                    newFieldP1 = newFieldP1 - 23;
                }

                //player  goes to jail
                if (newFieldP1 == 18) {
                    newFieldP1 = 6;
                    controller.showMessage(player1.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);


                if (field.getIsOwnable(newFieldP1) == true && field.getIsOwned(newFieldP1) == false) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    field.setIsOwned(newFieldP1, true);
                    field.setOwnedBy(newFieldP1, 1);
                    field.setFieldsSubText(newFieldP1, "Ejer: " + "\n" + player1.getName());
                }
                if (field.getIsOwned(newFieldP1) == true && field.getOwnedBy(newFieldP1) != 1) {

                    //If other player owns pair, player one pays double
                    if (field.getOwnedBy(newFieldP1 + 1) == field.getOwnedBy(newFieldP1) || field.getOwnedBy(newFieldP1 - 1) == field.getOwnedBy(newFieldP1)) {

                        switch (field.getOwnedBy(newFieldP1)) {
                            case 2:
                            accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                            accountPlayerTwo.deposit(field.getPrice(newFieldP1) * 2);
                            player1.setBalance(accountPlayerOne.getBalance());
                            player2.setBalance(accountPlayerTwo.getBalance());
                            case 3:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                                accountPlayerThree.deposit(field.getPrice(newFieldP1) * 2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                        }

                    } else {
                        switch (field.getOwnedBy(newFieldP1)) {
                            case 2:
                            accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                            accountPlayerTwo.deposit(field.getPrice(newFieldP1));
                            player1.setBalance(accountPlayerOne.getBalance());
                            player2.setBalance(accountPlayerTwo.getBalance());
                            case 3:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                                accountPlayerThree.deposit(field.getPrice(newFieldP1));
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                        }
                    }
                }

                if (accountPlayerOne.getBalance() <= 0) {
                    bankruptP1 = true;
                    break;
                }

                previousFieldP1 = newFieldP1;

                roll = controller.getUserButtonPressed(player2.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player two loops on board
                newFieldP2 = die.getDie() + previousFieldP2;
                if (newFieldP2 >= 23) {
                    newFieldP2 = newFieldP2 - 23;
                    accountPlayerTwo.deposit(2);
                }

                //send player two to jail
                if (newFieldP2 == 18) {
                    newFieldP2 = 6;
                    controller.showMessage(player2.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                if (field.getIsOwnable(newFieldP2) == true && field.getIsOwned(newFieldP2) == false) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    field.setIsOwned(newFieldP2, true);
                    field.setOwnedBy(newFieldP2, 2);
                    field.setFieldsSubText(newFieldP2, "Ejer: " + "\n" + player2.getName());
                }
                if (field.getIsOwned(newFieldP2) == true && field.getOwnedBy(newFieldP2) != 2) {
                    //If other player owns pair, player two pays double

                    if (field.getOwnedBy(newFieldP2 + 1) == field.getOwnedBy(newFieldP2) || field.getOwnedBy(newFieldP2 - 1) == field.getOwnedBy(newFieldP2)) {
                       switch (field.getOwnedBy(newFieldP2)) {
                           case 1:
                           accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                           accountPlayerOne.deposit(field.getPrice(newFieldP2) * 2);
                           player2.setBalance(accountPlayerTwo.getBalance());
                           player1.setBalance(accountPlayerOne.getBalance());
                           case 3:
                               accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                               accountPlayerThree.deposit(field.getPrice(newFieldP2) * 2);
                               player2.setBalance(accountPlayerTwo.getBalance());
                               player3.setBalance(accountPlayerThree.getBalance());
                       }


                    } else {
                        switch(field.getOwnedBy(newFieldP2)) {
                            case 1:
                            accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                            accountPlayerOne.deposit(field.getPrice(newFieldP2));
                            player2.setBalance(accountPlayerTwo.getBalance());
                            player1.setBalance(accountPlayerOne.getBalance());
                            case 3:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                                accountPlayerThree.deposit(field.getPrice(newFieldP2));
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                        }

                    }
                }

                if (accountPlayerTwo.getBalance() <= 0) {
                    bankruptP2 = true;
                    break;
                }

                previousFieldP2 = newFieldP2;

                roll = controller.getUserButtonPressed(player3.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player loops on board
                newFieldP3 = die.getDie() + previousFieldP3;
                if (newFieldP3 >= 23) {
                    accountPlayerThree.deposit(2);
                    newFieldP3 = newFieldP3 - 23;
                }

                //player  goes to jail
                if (newFieldP3 == 18) {
                    newFieldP3 = 6;
                    controller.showMessage(player3.getName() + " go to jail!");
                    accountPlayerThree.withdraw(1);
                }

                //moves player Three
                gui.getFields()[previousFieldP3].setCar(player3, false);
                gui.getFields()[newFieldP3].setCar(player3, true);


                if (field.getIsOwnable(newFieldP3) == true && field.getIsOwned(newFieldP3) == false) {
                    accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                    player3.setBalance(accountPlayerThree.getBalance());
                    field.setIsOwned(newFieldP3, true);
                    field.setOwnedBy(newFieldP3, 3);
                    field.setFieldsSubText(newFieldP3, "Ejer: " + "\n" + player3.getName());
                }
                if (field.getIsOwned(newFieldP2) == true && field.getOwnedBy(newFieldP2) != 3) {

                    //If other player owns pair, player one pays double
                    if (field.getOwnedBy(newFieldP3 + 1) == field.getOwnedBy(newFieldP3) || field.getOwnedBy(newFieldP3 - 1) == field.getOwnedBy(newFieldP3)) {

                        switch (field.getOwnedBy(newFieldP3)) {
                            case 1:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3) * 2);
                                accountPlayerOne.deposit(field.getPrice(newFieldP3) * 2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3) * 2);
                                accountPlayerTwo.deposit(field.getPrice(newFieldP3) * 2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                        }

                    } else {
                        switch (field.getOwnedBy(newFieldP1)) {
                            case 1:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                                accountPlayerOne.deposit(field.getPrice(newFieldP3));
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                                accountPlayerTwo.deposit(field.getPrice(newFieldP3));
                                player3.setBalance(accountPlayerThree.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                        }
                    }
                }

                if (accountPlayerThree.getBalance() <= 0) {
                    bankruptP3 = true;
                    break;
                }

                previousFieldP3 = newFieldP3;


            } while (bankruptP1 == false && bankruptP2 == false && bankruptP3 == false);

            if (bankruptP3) {
                if (accountPlayerOne.getBalance() > accountPlayerTwo.getBalance()) {
                    controller.showMessage(player1.getName() + " you win!");
                }
                if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance()) {
                    controller.showMessage(player2.getName() + " you win!");
                }
            }
                if (bankruptP2) {
                    if (accountPlayerThree.getBalance() > accountPlayerTwo.getBalance()) {
                        controller.showMessage(player3.getName() + " you win!");
                    }
                    if (accountPlayerTwo.getBalance() > accountPlayerThree.getBalance()) {
                        controller.showMessage(player2.getName() + " you win!");
                    }
            }
                if (bankruptP1) {
                    if (accountPlayerThree.getBalance() > accountPlayerOne.getBalance()) {
                        controller.showMessage(player3.getName() + " you win!");
                    }
                    if (accountPlayerOne.getBalance() > accountPlayerThree.getBalance()) {
                        controller.showMessage(player1.getName() + " you win!");
                    }
                }
             }

        // four players
        if (amountOfPlayers == 4 ) {

            //Creates player one
            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 16);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(16);

            //Creates player two
            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 16);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(16);

            //Creates player three
            GUI_Player player3 = new GUI_Player(controller.getUserString("Player three enter your name"), 16);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);
            Account accountPlayerThree = new Account();
            accountPlayerThree.setBalance(16);

            //Creates player four
            GUI_Player player4 = new GUI_Player(controller.getUserString("Player four enter your name"), 16);
            controller.addPlayer(player4);
            gui.getFields()[0].setCar(player4, true);
            Account accountPlayerFour = new Account();
            accountPlayerFour.setBalance(16);


            String roll;
            Die die = new Die();


            int previousFieldP1 = 0;
            int newFieldP1;
            int previousFieldP2 = 0;
            int newFieldP2;
            int previousFieldP3 = 0;
            int newFieldP3;
            int previousFieldP4 = 0;
            int newFieldP4;
            boolean bankruptP1 = false;
            boolean bankruptP2 = false;
            boolean bankruptP3 = false;
            boolean bankruptP4 = false;


            do {

                roll = controller.getUserButtonPressed(player1.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player loops on board
                newFieldP1 = die.getDie() + previousFieldP1;
                if (newFieldP1 >= 23) {
                    accountPlayerOne.deposit(2);
                    newFieldP1 = newFieldP1 - 23;
                }

                //player  goes to jail
                if (newFieldP1 == 18) {
                    newFieldP1 = 6;
                    controller.showMessage(player1.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);


                if (field.getIsOwnable(newFieldP1) == true && field.getIsOwned(newFieldP1) == false) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    field.setIsOwned(newFieldP1, true);
                    field.setOwnedBy(newFieldP1, 1);
                    field.setFieldsSubText(newFieldP1, "Ejer: " + "\n" + player1.getName());
                }
                if (field.getIsOwned(newFieldP1) == true && field.getOwnedBy(newFieldP1) != 1) {

                    //If other player owns pair, player one pays double
                    if (field.getOwnedBy(newFieldP1 + 1) == field.getOwnedBy(newFieldP1) || field.getOwnedBy(newFieldP1 - 1) == field.getOwnedBy(newFieldP1)) {

                        switch (field.getOwnedBy(newFieldP1)) {
                            case 2:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                                accountPlayerTwo.deposit(field.getPrice(newFieldP1) * 2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 3:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                                accountPlayerThree.deposit(field.getPrice(newFieldP1) * 2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                            case 4:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                                accountPlayerFour.deposit(field.getPrice(newFieldP1) * 2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());

                        }

                    } else {
                        switch (field.getOwnedBy(newFieldP1)) {
                            case 2:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                                accountPlayerTwo.deposit(field.getPrice(newFieldP1));
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 3:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                                accountPlayerThree.deposit(field.getPrice(newFieldP1));
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                            case 4:
                                accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                                accountPlayerFour.deposit(field.getPrice(newFieldP1));
                                player1.setBalance(accountPlayerOne.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());

                        }
                    }
                }

                if (accountPlayerOne.getBalance() <= 0) {
                    bankruptP1 = true;
                    break;
                }

                previousFieldP1 = newFieldP1;

                roll = controller.getUserButtonPressed(player2.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player two loops on board
                newFieldP2 = die.getDie() + previousFieldP2;
                if (newFieldP2 >= 23) {
                    newFieldP2 = newFieldP2 - 23;
                    accountPlayerTwo.deposit(2);
                }

                //send player two to jail
                if (newFieldP2 == 18) {
                    newFieldP2 = 6;
                    controller.showMessage(player2.getName() + " go to jail!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                if (field.getIsOwnable(newFieldP2) == true && field.getIsOwned(newFieldP2) == false) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    field.setIsOwned(newFieldP2, true);
                    field.setOwnedBy(newFieldP2, 2);
                    field.setFieldsSubText(newFieldP2, "Ejer: " + "\n" + player2.getName());
                }
                if (field.getIsOwned(newFieldP2) == true && field.getOwnedBy(newFieldP2) != 2) {
                    //If other player owns pair, player two pays double

                    if (field.getOwnedBy(newFieldP2 + 1) == field.getOwnedBy(newFieldP2) || field.getOwnedBy(newFieldP2 - 1) == field.getOwnedBy(newFieldP2)) {
                        switch (field.getOwnedBy(newFieldP2)) {
                            case 1:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                                accountPlayerOne.deposit(field.getPrice(newFieldP2) * 2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 3:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                                accountPlayerThree.deposit(field.getPrice(newFieldP2) * 2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                            case 4:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                                accountPlayerFour.deposit(field.getPrice(newFieldP2) * 2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());

                        }


                    } else {
                        switch (field.getOwnedBy(newFieldP2)) {
                            case 1:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                                accountPlayerOne.deposit(field.getPrice(newFieldP2));
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 3:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                                accountPlayerThree.deposit(field.getPrice(newFieldP2));
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                            case 4:
                                accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                                accountPlayerFour.deposit(field.getPrice(newFieldP2));
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                        }

                    }
                }

                if (accountPlayerTwo.getBalance() <= 0) {
                    bankruptP2 = true;
                    break;
                }

                previousFieldP2 = newFieldP2;

                roll = controller.getUserButtonPressed(player3.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player loops on board
                newFieldP3 = die.getDie() + previousFieldP3;
                if (newFieldP3 >= 23) {
                    accountPlayerThree.deposit(2);
                    newFieldP3 = newFieldP3 - 23;
                }

                //player  goes to jail
                if (newFieldP3 == 18) {
                    newFieldP3 = 6;
                    controller.showMessage(player3.getName() + " go to jail!");
                    accountPlayerThree.withdraw(1);
                }

                //moves player Three
                gui.getFields()[previousFieldP3].setCar(player3, false);
                gui.getFields()[newFieldP3].setCar(player3, true);


                if (field.getIsOwnable(newFieldP3) == true && field.getIsOwned(newFieldP3) == false) {
                    accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                    player3.setBalance(accountPlayerThree.getBalance());
                    field.setIsOwned(newFieldP3, true);
                    field.setOwnedBy(newFieldP3, 3);
                    field.setFieldsSubText(newFieldP3, "Ejer: " + "\n" + player3.getName());
                }
                if (field.getIsOwned(newFieldP2) == true && field.getOwnedBy(newFieldP2) != 3) {

                    //If other player owns pair, player one pays double
                    if (field.getOwnedBy(newFieldP3 + 1) == field.getOwnedBy(newFieldP3) || field.getOwnedBy(newFieldP3 - 1) == field.getOwnedBy(newFieldP3)) {

                        switch (field.getOwnedBy(newFieldP3)) {
                            case 1:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3) * 2);
                                accountPlayerOne.deposit(field.getPrice(newFieldP3) * 2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3) * 2);
                                accountPlayerTwo.deposit(field.getPrice(newFieldP3) * 2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 4:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3) * 2);
                                accountPlayerFour.deposit(field.getPrice(newFieldP3) * 2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                        }

                    } else {
                        switch (field.getOwnedBy(newFieldP3)) {
                            case 1:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                                accountPlayerOne.deposit(field.getPrice(newFieldP3));
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                                accountPlayerTwo.deposit(field.getPrice(newFieldP3));
                                player3.setBalance(accountPlayerThree.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 4:
                                accountPlayerThree.withdraw(field.getPrice(newFieldP3));
                                accountPlayerFour.deposit(field.getPrice(newFieldP3));
                                player3.setBalance(accountPlayerThree.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                        }
                    }
                }

                if (accountPlayerThree.getBalance() <= 0) {
                    bankruptP3 = true;
                    break;
                }

                previousFieldP3 = newFieldP3;

                roll = controller.getUserButtonPressed(player4.getName() + " press to roll", "roll");

                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                //Makes sure player loops on board
                newFieldP4 = die.getDie() + previousFieldP4;
                if (newFieldP4 >= 23) {
                    accountPlayerFour.deposit(2);
                    newFieldP4 = newFieldP4 - 23;
                }

                //player  goes to jail
                if (newFieldP4 == 18) {
                    newFieldP4 = 6;
                    controller.showMessage(player4.getName() + " go to jail!");
                    accountPlayerFour.withdraw(1);
                }

                //moves player Three
                gui.getFields()[previousFieldP4].setCar(player4, false);
                gui.getFields()[newFieldP4].setCar(player4, true);


                if (field.getIsOwnable(newFieldP4) == true && field.getIsOwned(newFieldP4) == false) {
                    accountPlayerFour.withdraw(field.getPrice(newFieldP4));
                    player4.setBalance(accountPlayerFour.getBalance());
                    field.setIsOwned(newFieldP4, true);
                    field.setOwnedBy(newFieldP4, 4);
                    field.setFieldsSubText(newFieldP4, "Ejer: " + "\n" + player4.getName());
                }
                if (field.getIsOwned(newFieldP4) == true && field.getOwnedBy(newFieldP4) != 4) {

                    //If other player owns pair, player one pays double
                    if (field.getOwnedBy(newFieldP4 + 1) == field.getOwnedBy(newFieldP4) || field.getOwnedBy(newFieldP4 - 1) == field.getOwnedBy(newFieldP4)) {

                        switch (field.getOwnedBy(newFieldP4)) {
                            case 1:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4) * 2);
                                accountPlayerOne.deposit(field.getPrice(newFieldP4) * 2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4) * 2);
                                accountPlayerTwo.deposit(field.getPrice(newFieldP4) * 2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 4:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4) * 2);
                                accountPlayerThree.deposit(field.getPrice(newFieldP4) * 2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                        }

                    } else {
                        switch (field.getOwnedBy(newFieldP4)) {
                            case 1:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4));
                                accountPlayerOne.deposit(field.getPrice(newFieldP4));
                                player4.setBalance(accountPlayerFour.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                            case 2:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4));
                                accountPlayerTwo.deposit(field.getPrice(newFieldP4));
                                player4.setBalance(accountPlayerFour.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                            case 3:
                                accountPlayerFour.withdraw(field.getPrice(newFieldP4));
                                accountPlayerThree.deposit(field.getPrice(newFieldP4));
                                player4.setBalance(accountPlayerFour.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                        }
                    }
                }

                if (accountPlayerFour.getBalance() <= 0) {
                    bankruptP4 = true;
                    break;
                }

                previousFieldP4 = newFieldP4;


            } while (bankruptP1 == false && bankruptP2 == false && bankruptP3 == false && bankruptP4 == false);


            //Player one wins
            if (accountPlayerOne.getBalance() > accountPlayerTwo.getBalance() && accountPlayerOne.getBalance() > accountPlayerThree.getBalance() && accountPlayerOne.getBalance() > accountPlayerFour.getBalance()) {
                controller.showMessage(player1.getName() + " you win!");
            }

            //Player two wins
            if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance() && accountPlayerTwo.getBalance() > accountPlayerThree.getBalance() && accountPlayerTwo.getBalance() > accountPlayerFour.getBalance()) {
                controller.showMessage(player2.getName() + " you win!");
            }

            //player three wins
            if (accountPlayerThree.getBalance() > accountPlayerOne.getBalance() && accountPlayerThree.getBalance() > accountPlayerTwo.getBalance() && accountPlayerThree.getBalance() > accountPlayerFour.getBalance()) {
                controller.showMessage(player3.getName() + " you win!");
            }

            //player four wins
            if (accountPlayerFour.getBalance() > accountPlayerOne.getBalance() && accountPlayerFour.getBalance() > accountPlayerTwo.getBalance() && accountPlayerFour.getBalance() > accountPlayerThree.getBalance()) {
            }
            else {
                controller.showMessage("Det blev uafgjort");
            }
        }
    }
}

