import gui_main.GUI;
import gui_fields.GUI_Player;
import gui_codebehind.GUI_BoardController;
import gui_fields.GUI_Field;
import java.awt.*;
import gui_fields.GUI_Street;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Start;
import gui_fields.GUI_Empty;
import gui_fields.GUI_Refuge;


public class Main {
    public static void main(String[] args) {

        GUI gui = new GUI();



        GUI_BoardController controller = new GUI_BoardController(gui.getFields(), Color.blue);
       int amountOfPlayers = controller.getUserInteger("Enter amount of players between 2 and 4",2,4);

        if (amountOfPlayers == 2) {
            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 20);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);

            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 20);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);
        }
        if (amountOfPlayers == 3) {
            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 18);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);

            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 18);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);

            GUI_Player player3 = new GUI_Player(controller.getUserString("Player three enter your name"), 18);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);
        }
        if (amountOfPlayers == 4) {
            GUI_Player player1 = new GUI_Player(controller.getUserString("Player one enter your name"), 16);
            controller.addPlayer(player1);
            gui.getFields()[0].setCar(player1, true);

            GUI_Player player2 = new GUI_Player(controller.getUserString("Player two enter your name"), 16);
            controller.addPlayer(player2);
            gui.getFields()[0].setCar(player2, true);

            GUI_Player player3 = new GUI_Player(controller.getUserString("Player three enter your name"), 16);
            controller.addPlayer(player3);
            gui.getFields()[0].setCar(player3, true);

            GUI_Player player4 = new GUI_Player(controller.getUserString("Player four enter your name"), 16);
            controller.addPlayer(player4);
            gui.getFields()[0].setCar(player4, true);
        }




    }
}