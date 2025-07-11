/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.time.LocalDate;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enzov
 */
public class TeamManagerTest {

    private static TeamManager tm;

    @BeforeClass
    public static void setUpClass() {
        tm = TeamManager.getInstance();
    }

    @After
    public void tearDown() {
        
        tm.getListaGiocatori().clear();

    }

    @Test
    public void testSingletonInstance() {
        TeamManager tm2 = TeamManager.getInstance();
        assertSame(tm2, tm);
    }

    /**
     * Test of creaGiocatore method, of class TeamManager.
     */
    @Test
    public void testCreaGiocatore() {

        //si procede a verificare se un giocatore viene creato correttamente -> TRUE
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", 10, LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
        //verifico la dimensione della lista giocatori
        assertEquals(1, tm.getListaGiocatori().size());

        //verifico che la lista giocatori non sia null
        assertNotNull(tm.getListaGiocatori());

        //provo ad aggiungere lo stesso giocatore -> FALSE
        assertFalse(tm.creaGiocatore("Ringhio", "Gattuso", 10, LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
    }

    /**
     * Test of eliminaGiocatore method, of class TeamManager.
     */
    @Test
    public void testEliminaGiocatore() {
        
        //inserisco un giocatore di prova
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", 10, LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
        
        //elimino il giocatore inserito
        assertTrue(tm.eliminaGiocatore(tm.getListaGiocatori().get(0)));
        
        //verifico se la lista giocatori è vuota
        assertEquals(0,tm.getListaGiocatori().size());
        
    }

    /**
     * Test of cercaGiocatori method, of class TeamManager.
     */
    @Test
    public void testCercaGiocatori() {
        
        //inserisco un giocatore di prova
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", 10, LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
        
        //provo ad eseguire una ricerca, assicurandomi che inserendo il parametro di ricerca non sia vuota
        assertNotEquals(0,tm.cercaGiocatori("R").size());
        
        //provo a svolgere una ricerca che non restituisce alcun risultato
        assertEquals(0,tm.cercaGiocatori("gbuhub").size());

    }

}
