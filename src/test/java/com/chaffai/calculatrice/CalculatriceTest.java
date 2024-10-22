package com.chaffai.calculatrice;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class CalculatriceTest {
    private Calculatrice calculatrice = new Calculatrice();
    @Test
    public void testSum() {
        assertEquals(15, calculatrice.sum(10, 5));
    }
}