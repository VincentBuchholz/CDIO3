package Test;

import CDIO3.Die;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @org.junit.jupiter.api.Test
    void roll() {
        for (int i = 0; i < 1000; i++)
        {
        Die DieTest = new Die();
        DieTest.roll();
        System.out.print(DieTest.getDie() + " ");
        assertTrue(1 <= DieTest.getDie() && DieTest.getDie() <= 6);
    }
}
}