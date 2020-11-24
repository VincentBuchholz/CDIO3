package Test;

import CDIO3.Die;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

   @Test
    void roll() {
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 6;
        for (int i = 0; i < 1000; i++)
        {
        Die DieTest = new Die();
        DieTest.roll();
        System.out.print(DieTest.getDie() + " ");
        assertTrue(1 <= DieTest.getDie() && DieTest.getDie() <= 6);


        if (DieTest.getDie() == 1){
            one++;
        }
        if (DieTest.getDie() == 2){
                two++;
        }
        if (DieTest.getDie() == 3){
                three++;
        }
        if (DieTest.getDie() == 4){
                four++;
        }
        if (DieTest.getDie() == 5){
                five++;
        }
        if (DieTest.getDie() == 6){
                six++;
        }
    }

        System.out.println("");
        System.out.println("One: " +one);
        System.out.println("Two: " + two);
        System.out.println("Three: " + three);
        System.out.println("Four: " + four);
        System.out.println("Five: " + five);
        System.out.println("Six: " + six);
    }
}