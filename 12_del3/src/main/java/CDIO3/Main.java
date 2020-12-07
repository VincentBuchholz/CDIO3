package CDIO3;
//import af pakker
import CDIO3.Die;
import CDIO3.Fields;
import gui_main.GUI;
import gui_fields.GUI_Player;
import gui_codebehind.GUI_BoardController;
import gui_fields.GUI_Field;
import java.awt.*;
import gui_fields.GUI_Street;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //Opretter et nyt opbekt af klassen Fields som bliver sat = metoden Fields()
        Fields field = new Fields();

        GUI gui = new GUI(field.fields);
        Random rand = new Random();

        //Bruges til at styre gui'en
        GUI_BoardController controller = new GUI_BoardController(gui.getFields(), Color.lightGray);
        int amountOfPlayers = controller.getUserInteger("Angiv antal spillere, mellem 2 og 4", 2, 4);
        //spillet kører herfra til 2 spillere. if-then statement
        if (amountOfPlayers == 2) {

            //opretter ny spiller (player 1) GUI_Player laver et objekt player1 som bliver instantieret til en GUI_Player(controller.getUserString())
            GUI_Player player1 = new GUI_Player(controller.getUserString("Spiller 1 (den yngste) indtast dit navn"), 20);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            //Opretter et nyt opbekt af klassen Account som bliver sat = metoden Account().
            //Så kan vi lave en start balance til spilleren på 20M
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(20);

            //opretter spiller 2
            GUI_Player player2 = new GUI_Player(controller.getUserString("Spiller 2 indtast dit navn"), 20);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            //Opretter et nyt opbekt af klassen Account som bliver sat = metoden Account().
            //Så kan vi lave en start balance til spilleren på 20M
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(20);


            String roll;
            Die die = new Die();

            String takeCard;
            int cardNumber;


            int previousFieldP1 = 0;
            int newFieldP1;
            int previousFieldP2 = 0;
            int newFieldP2;
            boolean bankruptP1 = false;
            boolean bankruptP2 = false;

            //spillet "starter her"
            //starter med at lave do loopet for at roll terningerne
            do {

                roll = controller.getUserButtonPressed(player1.getName() + " tryk for at kaste", "roll");
            //roll og der bruges vores roll metode i Die klassen
                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }
                //rykker til et nyt felt fra prev felt. det nye felt kommer an på værdien fra die.getDie()
                newFieldP1 = die.getDie() + previousFieldP1;
                //når vi passere start får vi 2M
                if (newFieldP1 >= 23) {
                    accountPlayerOne.deposit(2);
                    //starter forfra i arrayet. første felt igen
                    newFieldP1 = newFieldP1 - 23;
                }
                //hvis du lander på gå i fængsel = gå i fængsel
                if (newFieldP1 == 18) {
                    newFieldP1 = 6;
                    controller.showMessage(player1.getName() + " gå i fængsel!");
                    //koster 1 M at komme ud
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);

                //Chance kort
                //chance kort på felt 3, 9, 15, 21
                if (newFieldP1 == 3 || newFieldP1 == 9 || newFieldP1 == 15 || newFieldP1 == 21) {
                    //beder spilleren om at tage et chance kort
                    takeCard = controller.getUserButtonPressed(player1.getName() + " tag et chance kort","Tag kort");
                    //objekt cardNumber, random chance kort som du kan få. vi har 5 kort. derfor 4+1
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {

                        //viser alle de forskellige chancekort scenarier. switch statement giver en case mellem 1-5
                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 0;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerOne.withdraw(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 23;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerOne.deposit(1);
                                accountPlayerTwo.withdraw(1);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                        }
                    }
                }

                //ser om spiller 1 er landet på et felt der kan ejes og ikke er ejet allerede.
                //hvis if statement er true køber den feltet
                if (field.getIsOwnable(newFieldP1) == true && field.getIsOwned(newFieldP1) == false) {
                    accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                    player1.setBalance(accountPlayerOne.getBalance());
                    field.setIsOwned(newFieldP1, true);
                    field.setOwnedBy(newFieldP1, 1);
                    field.setFieldsSubText(newFieldP1, "Ejer: " + "\n" + player1.getName());
                }
                //ser om feltet er ejet og om det er ejet af spiller 2
                if (field.getIsOwned(newFieldP1) == true && field.getOwnedBy(newFieldP1) == 2) {
                    //Hvis spiller to ejer hele det par man er landet på, betaler spiller et dobbelt.
                    //checker om spilleren ejer et felt ved siden af det ejede felt
                    if (field.getOwnedBy(newFieldP1 + 1) == field.getOwnedBy(newFieldP1) || field.getOwnedBy(newFieldP1 - 1) == field.getOwnedBy(newFieldP1)) {
                        accountPlayerOne.withdraw(field.getPrice(newFieldP1) * 2);
                        accountPlayerTwo.deposit(field.getPrice(newFieldP1) * 2);
                        player1.setBalance(accountPlayerOne.getBalance());
                        player2.setBalance(accountPlayerTwo.getBalance());
                    //ellers betaler du prisen som der står på feltet
                    } else {
                        accountPlayerOne.withdraw(field.getPrice(newFieldP1));
                        accountPlayerTwo.deposit(field.getPrice(newFieldP1));
                        player1.setBalance(accountPlayerOne.getBalance());
                        player2.setBalance(accountPlayerTwo.getBalance());
                    }
                }
                //hvis spiller balance er lig eller mindre end 0 breaker do loopet
                //bankrupt bliver ændret til true som var et objekt at typen boolean
                //Hvis spilleren har en på balance på 0 stopper loopet og der bliver talt op hvilken spiller der har den største balance.
                //Spilleren med den største balance vinder spillet. Står cirka på linje 275
                if (accountPlayerOne.getBalance() <= 0) {
                    bankruptP1 = true;
                    break;
                }

                //sætter prevfield = newField så du kan komme gennem alle felterne og blive på felter i stedet for at starte fra felt 0 hver gang
                previousFieldP1 = newFieldP1;

                roll = controller.getUserButtonPressed(player2.getName() + " tryk for at kaste med terningen", "roll");
                //roll og der bruges vores roll metode i Die klassen
                if (roll.contentEquals("roll")) {
                    die.roll();
                    controller.setDie(die.getDie());
                }

                // rykker til et nyt feltt fra prev felt. det nye felt kommer an på værdien fra die.getDie()
                newFieldP2 = die.getDie() + previousFieldP2;
                //når vi passere start får vi 2M og starter forfra i arrayet, første felt igen.
                if (newFieldP2 >= 23) {
                    newFieldP2 = newFieldP2 - 23;
                    accountPlayerTwo.deposit(2);
                }
                // hvis du lander på gå i fængsel = gå i fængsel
                if (newFieldP2 == 18) {
                    newFieldP2 = 6;
                    controller.showMessage(player2.getName() + " gå i fængsel!");
                    //koster 1M for at komme ud
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                //Chance kort
                //chance kort på felt 3, 9, 15, 21
                if (newFieldP2 == 3 || newFieldP2 == 9 || newFieldP2 == 15 || newFieldP2 == 21) {
                    //beder spilleren om at tage et chance kort
                    takeCard = controller.getUserButtonPressed(player2.getName() + " tag et chance kort","Tag kort");
                    //objekt cardNumber, random chance kort som du kan få. vi har 5 kort. derfor 4+1
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {

                        //viser alle de forskellige chancekort scenarier. switch statement giver en case mellem 1-5
                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 0;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerTwo.withdraw(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 23;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerTwo.deposit(1);
                                accountPlayerOne.withdraw(1);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                        }
                    }
                }

                //ser om spiller 2 er landet på et felt der kan ejes og ikke er ejet allerede.
                //hvis if statement er true køber den feltet
                if (field.getIsOwnable(newFieldP2) == true && field.getIsOwned(newFieldP2) == false) {
                    accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                    player2.setBalance(accountPlayerTwo.getBalance());
                    field.setIsOwned(newFieldP2, true);
                    field.setOwnedBy(newFieldP2, 2);
                    field.setFieldsSubText(newFieldP2, "Ejer: " + "\n" + player2.getName());
                }
                //Hvis spiller to ejer hele det par man er landet på, betaler spiller et dobbelt.
                //checker om spilleren ejer et felt ved siden af det ejede felt
                if (field.getOwnedBy(newFieldP2 + 1) == field.getOwnedBy(newFieldP2) || field.getOwnedBy(newFieldP2 - 1) == field.getOwnedBy(newFieldP2)) {

                    if (field.getOwnedBy(newFieldP2 + 1) == 1 || field.getOwnedBy(newFieldP2 - 1) == 1) {
                        accountPlayerTwo.withdraw(field.getPrice(newFieldP2) * 2);
                        accountPlayerOne.deposit(field.getPrice(newFieldP2) * 2);
                        player2.setBalance(accountPlayerTwo.getBalance());
                        player1.setBalance(accountPlayerOne.getBalance());
                        //ellers betaler du prisen som der står på feltet
                    } else {
                        accountPlayerTwo.withdraw(field.getPrice(newFieldP2));
                        accountPlayerOne.deposit(field.getPrice(newFieldP2));
                        player2.setBalance(accountPlayerTwo.getBalance());
                        player1.setBalance(accountPlayerOne.getBalance());
                    }
                }
                //hvis spiller balance er lig eller mindre end 0 breaker do loopet
                //bankrupt bliver ændret til true som var et objekt at typen boolean
                //Hvis spilleren har en på balance på 0 stopper loopet og der bliver talt op hvilken spiller der har den største balance.
                //Spilleren med den største balance vinder spillet. Står cirka på linje 275
                if (accountPlayerTwo.getBalance() <= 0) {
                    bankruptP2 = true;
                    break;
                }

                previousFieldP2 = newFieldP2;
            //do loopet virker når begge bankrupt = false
            } while (bankruptP1 == false && bankruptP2 == false);


            if (accountPlayerOne.getBalance() > accountPlayerTwo.getBalance()) {
                controller.showMessage(player1.getName() + " du vinder!");
            }
            if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance()) {
                controller.showMessage(player2.getName() + " du vinder!");
            }
        }
        //spillet stopper her for 2 spillere. if-then statement for amount of player == 2

        //det samme sker for 3 & 4 spiller. Koden er duplikeret og tilpasset til at fungere med flere spillere



        // three players
        if (amountOfPlayers == 3 ) {

            //Creates player one
            GUI_Player player1 = new GUI_Player(controller.getUserString("Spiller 1 (den yngste) indtast dit navn"), 18);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(18);

            //Creates player two
            GUI_Player player2 = new GUI_Player(controller.getUserString("Spiller 2 indtast dit navn"), 18);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(18);

            //Creates player three
            GUI_Player player3 = new GUI_Player(controller.getUserString("Spiller 3 indtast dit navn"), 18);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);
            Account accountPlayerThree = new Account();
            accountPlayerThree.setBalance(18);


            String roll;
            Die die = new Die();

            String takeCard;
            int cardNumber;

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

                roll = controller.getUserButtonPressed(player1.getName() + " tryk for at kaste", "roll");

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
                    controller.showMessage(player1.getName() + " gå i fængsel!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);

                //Chance kort
                if (newFieldP1 == 3 || newFieldP1 == 9 || newFieldP1 == 15 || newFieldP1 == 21) {
                    takeCard = controller.getUserButtonPressed(player1.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 0;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betalt M2");
                                accountPlayerOne.withdraw(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 23;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerOne.deposit(2);
                                accountPlayerTwo.withdraw(1);
                                accountPlayerThree.withdraw(1);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                        }
                    }
                }


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

                roll = controller.getUserButtonPressed(player2.getName() + " Tryk for at kaste med terningen", "roll");

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
                    controller.showMessage(player2.getName() + " gå i fængsel!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                //Chance kort
                if (newFieldP2 == 3 || newFieldP2 == 9 || newFieldP2 == 15 || newFieldP2 == 21) {
                    takeCard = controller.getUserButtonPressed(player2.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 0;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerTwo.withdraw(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 23;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerTwo.deposit(2);
                                accountPlayerOne.withdraw(1);
                                accountPlayerThree.withdraw(1);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                        }
                    }
                }

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
                    controller.showMessage(player3.getName() + " gå i fængsel!");
                    accountPlayerThree.withdraw(1);
                }

                //moves player Three
                gui.getFields()[previousFieldP3].setCar(player3, false);
                gui.getFields()[newFieldP3].setCar(player3, true);

                //Chance kort
                if (newFieldP3 == 3 || newFieldP3 == 9 || newFieldP3 == 15 || newFieldP3 == 21) {
                    takeCard = controller.getUserButtonPressed(player3.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP3 = newFieldP3;
                                newFieldP3 = 0;
                                gui.getFields()[previousFieldP3].setCar(player3, false);
                                gui.getFields()[newFieldP3].setCar(player3, true);
                                accountPlayerThree.deposit(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerThree.withdraw(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP3 = newFieldP3;
                                newFieldP3 = 23;
                                gui.getFields()[previousFieldP3].setCar(player3, false);
                                gui.getFields()[newFieldP3].setCar(player3, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerThree.deposit(2);
                                accountPlayerOne.withdraw(1);
                                accountPlayerTwo.withdraw(1);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerThree.deposit(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                        }
                    }
                }


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
                    controller.showMessage(player1.getName() + " du vinder!");
                }
                if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance()) {
                    controller.showMessage(player2.getName() + " du vinder!");
                }
            }
                if (bankruptP2) {
                    if (accountPlayerThree.getBalance() > accountPlayerTwo.getBalance()) {
                        controller.showMessage(player3.getName() + " du vinder!");
                    }
                    if (accountPlayerTwo.getBalance() > accountPlayerThree.getBalance()) {
                        controller.showMessage(player2.getName() + " du vinder!");
                    }
            }
                if (bankruptP1) {
                    if (accountPlayerThree.getBalance() > accountPlayerOne.getBalance()) {
                        controller.showMessage(player3.getName() + " du vinder!");
                    }
                    if (accountPlayerOne.getBalance() > accountPlayerThree.getBalance()) {
                        controller.showMessage(player1.getName() + " du vinder!");
                    }
                }
             }

        // four players
        if (amountOfPlayers == 4 ) {

            //Creates player one
            GUI_Player player1 = new GUI_Player(controller.getUserString("Spiller 1 (den yngste) indtast dit navn"), 16);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);
            Account accountPlayerOne = new Account();
            accountPlayerOne.setBalance(16);

            //Creates player two
            GUI_Player player2 = new GUI_Player(controller.getUserString("Spiller 2 indtast dit navn"), 16);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
            Account accountPlayerTwo = new Account();
            accountPlayerTwo.setBalance(16);

            //Creates player three
            GUI_Player player3 = new GUI_Player(controller.getUserString("Spiller 3 indtast dit navn"), 16);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);
            Account accountPlayerThree = new Account();
            accountPlayerThree.setBalance(16);

            //Creates player four
            GUI_Player player4 = new GUI_Player(controller.getUserString("Spiller 4 indtast dit navn"), 16);
            controller.addPlayer(player4);
            gui.getFields()[0].setCar(player4, true);
            Account accountPlayerFour = new Account();
            accountPlayerFour.setBalance(16);


            String roll;
            Die die = new Die();

            String takeCard;
            int cardNumber;

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

                roll = controller.getUserButtonPressed(player1.getName() + " tryk for at kaste med terningerne", "roll");

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
                    controller.showMessage(player1.getName() + " gå i fængsel!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player one
                gui.getFields()[previousFieldP1].setCar(player1, false);
                gui.getFields()[newFieldP1].setCar(player1, true);

                //Chance kort
                if (newFieldP1 == 3 || newFieldP1 == 9 || newFieldP1 == 15 || newFieldP1 == 21) {
                    takeCard = controller.getUserButtonPressed(player1.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 0;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betalt M2");
                                accountPlayerOne.withdraw(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP1 = newFieldP1;
                                newFieldP1 = 23;
                                gui.getFields()[previousFieldP1].setCar(player1, false);
                                gui.getFields()[newFieldP1].setCar(player1, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerOne.deposit(3);
                                accountPlayerTwo.withdraw(1);
                                accountPlayerThree.withdraw(1);
                                accountPlayerFour.withdraw(1);
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerOne.deposit(2);
                                player1.setBalance(accountPlayerOne.getBalance());
                                break;
                        }
                    }
                }

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

                roll = controller.getUserButtonPressed(player2.getName() + " tryk for at kaste med terningen", "roll");

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
                    controller.showMessage(player2.getName() + " gå i fængsel!");
                    accountPlayerOne.withdraw(1);
                }

                //moves player two
                gui.getFields()[previousFieldP2].setCar(player2, false);
                gui.getFields()[newFieldP2].setCar(player2, true);

                if (newFieldP2 == 3 || newFieldP2 == 9 || newFieldP2 == 15 || newFieldP2 == 21) {
                    takeCard = controller.getUserButtonPressed(player2.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 0;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerTwo.withdraw(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP2 = newFieldP2;
                                newFieldP2 = 23;
                                gui.getFields()[previousFieldP2].setCar(player2, false);
                                gui.getFields()[newFieldP2].setCar(player2, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerTwo.deposit(3);
                                accountPlayerOne.withdraw(1);
                                accountPlayerThree.withdraw(1);
                                accountPlayerFour.withdraw(1);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerTwo.deposit(2);
                                player2.setBalance(accountPlayerTwo.getBalance());
                                break;
                        }
                    }
                }

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
                    controller.showMessage(player3.getName() + " gå i fængsel!");
                    accountPlayerThree.withdraw(1);
                }

                //moves player Three
                gui.getFields()[previousFieldP3].setCar(player3, false);
                gui.getFields()[newFieldP3].setCar(player3, true);

                //Chance kort
                if (newFieldP3 == 3 || newFieldP3 == 9 || newFieldP3 == 15 || newFieldP3 == 21) {
                    takeCard = controller.getUserButtonPressed(player3.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP3 = newFieldP3;
                                newFieldP3 = 0;
                                gui.getFields()[previousFieldP3].setCar(player3, false);
                                gui.getFields()[newFieldP3].setCar(player3, true);
                                accountPlayerThree.deposit(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerThree.withdraw(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP3 = newFieldP3;
                                newFieldP3 = 23;
                                gui.getFields()[previousFieldP3].setCar(player3, false);
                                gui.getFields()[newFieldP3].setCar(player3, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerThree.deposit(3);
                                accountPlayerOne.withdraw(1);
                                accountPlayerTwo.withdraw(1);
                                accountPlayerFour.withdraw(1);
                                player3.setBalance(accountPlayerThree.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerThree.deposit(2);
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                        }
                    }
                }

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

                roll = controller.getUserButtonPressed(player4.getName() + " tryk for at kaste med terningen", "roll");

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
                    controller.showMessage(player4.getName() + " gå i fængsel!");
                    accountPlayerFour.withdraw(1);
                }

                //moves player four
                gui.getFields()[previousFieldP4].setCar(player4, false);
                gui.getFields()[newFieldP4].setCar(player4, true);

                //Chance kort
                if (newFieldP4 == 3 || newFieldP4 == 9 || newFieldP4 == 15 || newFieldP4 == 21) {
                    takeCard = controller.getUserButtonPressed(player4.getName() + " tag et chance kort","Tag kort");
                    cardNumber = rand.nextInt(4)+1;
                    if (takeCard.contentEquals("Tag kort")) {


                        switch (cardNumber) {
                            case 1:
                                controller.displayChanceCard("Gå til start og modtag M2");
                                previousFieldP4 = newFieldP4;
                                newFieldP4 = 0;
                                gui.getFields()[previousFieldP4].setCar(player4, false);
                                gui.getFields()[newFieldP4].setCar(player4, true);
                                accountPlayerFour.deposit(2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                            case 2:
                                controller.displayChanceCard("Du har spist for meget slik, betal M2");
                                accountPlayerFour.withdraw(2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                            case 3:
                                controller.displayChanceCard("Ryk frem til strandprommenaden");
                                previousFieldP4 = newFieldP4;
                                newFieldP4 = 23;
                                gui.getFields()[previousFieldP4].setCar(player4, false);
                                gui.getFields()[newFieldP4].setCar(player4, true);
                                break;
                            case 4:
                                controller.displayChanceCard("Det er din fødselsdag alle giver dig M1");
                                accountPlayerFour.deposit(3);
                                accountPlayerOne.withdraw(1);
                                accountPlayerTwo.withdraw(1);
                                accountPlayerThree.withdraw(1);
                                player4.setBalance(accountPlayerFour.getBalance());
                                player1.setBalance(accountPlayerOne.getBalance());
                                player2.setBalance(accountPlayerTwo.getBalance());
                                player3.setBalance(accountPlayerThree.getBalance());
                                break;
                            case 5:
                                controller.displayChanceCard("Du har lavet alle dine lektier, modtag M2 fra banken");
                                accountPlayerFour.deposit(2);
                                player4.setBalance(accountPlayerFour.getBalance());
                                break;
                        }
                    }
                }


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
                controller.showMessage(player1.getName() + " du vinder!");
            }

            //Player two wins
            if (accountPlayerTwo.getBalance() > accountPlayerOne.getBalance() && accountPlayerTwo.getBalance() > accountPlayerThree.getBalance() && accountPlayerTwo.getBalance() > accountPlayerFour.getBalance()) {
                controller.showMessage(player2.getName() + " du vinder!");
            }

            //player three wins
            if (accountPlayerThree.getBalance() > accountPlayerOne.getBalance() && accountPlayerThree.getBalance() > accountPlayerTwo.getBalance() && accountPlayerThree.getBalance() > accountPlayerFour.getBalance()) {
                controller.showMessage(player3.getName() + " du vinder!");
            }

            //player four wins
            if (accountPlayerFour.getBalance() > accountPlayerOne.getBalance() && accountPlayerFour.getBalance() > accountPlayerTwo.getBalance() && accountPlayerFour.getBalance() > accountPlayerThree.getBalance()) {
            controller.showMessage(player4.getName() + " du vinder!");
            }

        }
    }
}