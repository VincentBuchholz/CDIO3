package CDIO3;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
import gui_main.GUI;

import java.awt.*;

public class Fields{
     boolean[] isOwnable = new boolean[24]; {
        isOwnable[0] = false;
        isOwnable[1] = true;
        isOwnable[2] = true;
        isOwnable[3] = false;
        isOwnable[4] = true;
        isOwnable[5] = true;
        isOwnable[6] = false;
        isOwnable[7] = true;
        isOwnable[8] = true;
        isOwnable[9] = false;
        isOwnable[10] = true;
        isOwnable[11] = true;
        isOwnable[12] = false;
        isOwnable[13] = true;
        isOwnable[14] = true;
        isOwnable[15] = false;
        isOwnable[16] = true;
        isOwnable[17] = true;
        isOwnable[18] = false;
        isOwnable[19] = true;
        isOwnable[20] = true;
        isOwnable[21] = false;
        isOwnable[22] = true;
        isOwnable[23] = true;
    }





    public boolean getIsOwnable(int fieldNumber) {
        return isOwnable[fieldNumber];

    }


    boolean[] isOwned = new boolean[24]; {
        isOwned[0] = false;
        isOwned[1] = false;
        isOwned[2] = false;
        isOwned[3] = false;
        isOwned[4] = false;
        isOwned[5] = false;
        isOwned[6] = false;
        isOwned[7] = false;
        isOwned[8] = false;
        isOwned[9] = false;
        isOwned[10] = false;
        isOwned[11] = false;
        isOwned[12] = false;
        isOwned[13] = false;
        isOwned[14] = false;
        isOwned[15] = false;
        isOwned[16] = false;
        isOwned[17] = false;
        isOwned[18] = false;
        isOwned[19] = false;
        isOwned[20] = false;
        isOwned[21] = false;
        isOwned[22] = false;
        isOwned[23] = false;
    }

    public void setIsOwned(int fieldNumber, boolean purchased) {
        int number = fieldNumber;
        boolean owned = purchased;

        isOwned[fieldNumber] = owned;

    }

    public boolean getIsOwned(int fieldNumber) {
        return isOwned[fieldNumber];
    }

    int[] price = new int[24];

    {
        price[1] = 1;
        price[2] = 1;
        price[4] = 1;
        price[5] = 1;
        price[7] = 2;
        price[8] = 2;
        price[10] = 2;
        price[11] = 2;
        price[13] = 3;
        price[14] = 3;
        price[16] = 3;
        price[17] = 3;
        price[19] = 4;
        price[20] = 4;
        price[22] = 5;
        price[23] = 5;

    }
    public int getPrice(int fieldNumber) {
        return price[fieldNumber];
    }

    int[] rent = new int[24]; {
        rent[1] = 1;
        rent[2] = 1;
        rent[4] = 1;
        rent[5] = 1;
        rent[7] = 2;
        rent[8] = 2;
        rent[10] = 2;
        rent[11] = 2;
        rent[13] = 3;
        rent[14] = 3;
        rent[16] = 3;
        rent[17] = 3;
        rent[19] = 4;
        rent[20] = 4;
        rent[22] = 5;
        rent[23] = 5;
    }

    public int getRent(int fieldNumber) {
        return rent[fieldNumber];
    }

    int[] ownedBy = new int[24]; {
        ownedBy[1] = 0;
        ownedBy[2] = 0;
        ownedBy[4] = 0;
        ownedBy[5] = 0;
        ownedBy[7] = 0;
        ownedBy[8] = 0;
        ownedBy[10] = 0;
        ownedBy[11] = 0;
        ownedBy[13] = 0;
        ownedBy[14] = 0;
        ownedBy[16] = 0;
        ownedBy[17] = 0;
        ownedBy[19] = 0;
        ownedBy[20] = 0;
        ownedBy[22] = 0;
        ownedBy[23] = 0;
    }

    public void setOwnedBy(int fieldNumber, int playerNumber) {
        ownedBy[fieldNumber] = playerNumber;

    }

    public int getOwnedBy(int fieldNumber) {
        return ownedBy[fieldNumber];
    }

    GUI_Field[] fields = {
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),
            new GUI_Street(),


    };



    {
        fields[0].setTitle("Start");
        fields[0].setBackGroundColor(Color.white);
        fields[0].setSubText("Modtag M2");
        fields[1].setTitle("BURGERBAREN");
        fields[1].setSubText("M1");
        fields[1].setBackGroundColor(Color.orange);
        fields[2].setTitle("PIZZERIAET");
        fields[2].setSubText("M1");
        fields[2].setBackGroundColor(Color.orange);
        fields[3].setTitle("CHANCE");
        fields[3].setBackGroundColor(Color.white);
        fields[3].setSubText("");
        fields[4].setTitle("SLIKBUTIKKEN");
        fields[4].setSubText("M1");
        fields[4].setBackGroundColor(Color.cyan);
        fields[5].setTitle("ISKIOSKEN");
        fields[5].setSubText("M1");
        fields[5].setBackGroundColor(Color.cyan);
        fields[6].setTitle("FÆNGSELSBESØG");
        fields[6].setBackGroundColor(Color.white);
        fields[6].setSubText("");
        fields[7].setTitle("MUSEET");
        fields[7].setSubText("M2");
        fields[7].setBackGroundColor(Color.pink);
        fields[8].setTitle("BIBLIOTEKET");
        fields[8].setSubText("M2");
        fields[8].setBackGroundColor(Color.pink);
        fields[9].setTitle("CHANCE");
        fields[9].setBackGroundColor(Color.white);
        fields[9].setSubText("");
        fields[10].setTitle("SKATERPARKEN");
        fields[10].setSubText("M2");
        fields[10].setBackGroundColor(Color.gray);
        fields[11].setTitle("SWIMMINGPOOLEN");
        fields[11].setSubText("M2");
        fields[11].setBackGroundColor(Color.gray);
        fields[12].setBackGroundColor(Color.white);
        fields[12].setTitle("GRATIS");
        fields[12].setSubText("PARKERING");
        fields[13].setTitle("SPILLEHALLEN");
        fields[13].setSubText("M3");
        fields[13].setBackGroundColor(Color.RED);
        fields[14].setTitle("KINOEN");
        fields[14].setSubText("M3");
        fields[14].setBackGroundColor(Color.RED);
        fields[15].setTitle("CHANCE");
        fields[15].setBackGroundColor(Color.white);
        fields[15].setSubText("");
        fields[16].setTitle("LEGETØJSBUTIKKEN");
        fields[16].setSubText("M3");
        fields[16].setBackGroundColor(Color.yellow);
        fields[17].setTitle("DYREBUTIKKEN");
        fields[17].setSubText("M3");
        fields[17].setBackGroundColor(Color.yellow);
        fields[18].setTitle("GÅ I");
        fields[18].setSubText("FÆNGSEL");
        fields[18].setBackGroundColor(Color.white);
        fields[19].setTitle("BOWLINGHALLEN");
        fields[19].setSubText("M4");
        fields[19].setBackGroundColor(Color.GREEN);
        fields[20].setTitle("ZOO");
        fields[20].setSubText("M4");
        fields[20].setBackGroundColor(Color.GREEN);
        fields[21].setTitle("CHANCE");
        fields[21].setBackGroundColor(Color.white);
        fields[21].setSubText("");
        fields[22].setTitle("VANDLANDET");
        fields[22].setSubText("M5");
        fields[22].setBackGroundColor(Color.blue);
        fields[22].setTitle("STRANDPROMENADEN");
        fields[22].setSubText("M5");
        fields[22].setBackGroundColor(Color.blue);
    }

    public void setFieldsSubText (int fieldNumber, String subText){
        fields[fieldNumber].setSubText(subText);
    }



    public static void main(String[] args) {
        Fields fields = new Fields();
        System.out.println(fields.isOwnable);
        System.out.println(fields.getIsOwned(0));
        fields.setIsOwned(0,true);
        System.out.println(fields.getIsOwned(0));
        System.out.println(fields.getPrice(1));
        System.out.println(fields.getOwnedBy(1));
        fields.setOwnedBy(1,1);
        System.out.println(fields.getOwnedBy(1));


    }
}



