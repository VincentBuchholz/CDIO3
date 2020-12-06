package CDIO3;
// java.util.Random er en standard API Klasse
import java.util.Random;
//klassen Die (Terning)
    public class Die {
        //Nyt random objekt instantieres ved brug af "new"
        Random rand = new Random();
        // opretter en variable af typen int
        private int die;

        //roll metode
        public void roll() {
            //kalder på die variablen og sætter den = objektet rand.nextInt som kan lave et tal mellem 1 - 6
            this.die = rand.nextInt(6)+1;

        }
        //metoden getDie() bruges til at få en værdi af terningen
        public int getDie(){
            return die;
        }
        //Printer værdien af die i stedet for hvor die er gemt i "memory" toString bruges til dette
        public String toString() {
            return String.valueOf(die);
        }
        // ikke relevant
        public static void main(String[] args) {
            // write your code here
        }
    }

