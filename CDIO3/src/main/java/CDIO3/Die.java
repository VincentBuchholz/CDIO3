package CDIO3;

import java.util.Random;

    public class Die {
        Random rand = new Random();

        private int die;


        public void roll() {
            this.die = rand.nextInt(6)+1;

        }

        public int getDie(){
            return die;
        }

        public String toString() {
            return String.valueOf(die);
        }

        public static void main(String[] args) {
            // write your code here
        }
    }

