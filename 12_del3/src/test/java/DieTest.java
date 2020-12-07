//importere Die klassen fra pakken CDIO3
import CDIO3.Die;
//importere junit test pakke så vi kan lave en junit test
import org.junit.jupiter.api.Test;
// Importere en pakke så vi kan bruge assert metoder.
import static org.junit.jupiter.api.Assertions.*;
//klassen DieTest
class DieTest {
//Selve testen
    //void tomt rum med roll() metoden
   @Test
    void roll() {
       //opretter objekt one, two... med værdierne 0.
       // Bruges til at se hvor mange gange den udskriver hver værdi mellem 1 - 6
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        // conditional statement for-then(loop), bruges til at køre testen 1000 gange (rulle terning 1000 gange)
        for (int i = 0; i < 1000; i++)
        {
        Die DieTest = new Die();
        DieTest.roll();
        System.out.print(DieTest.getDie() + " ");
        //Junit metoden til at sikre at terningen får et udfald mellem 1 - 6
        assertTrue(1 <= DieTest.getDie() && DieTest.getDie() <= 6);

        //disse if statements sørger for at det bliver lagt værdien 1 til vores objekter af typen int som vi instantiede
            //linje 15-20, hver gange vores terningen rammer en af værdierne så vi kan se hvor mange udfald der kommer af
            //hver værdi
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