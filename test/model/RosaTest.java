/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enzov
 */
public class RosaTest {

    private Giocatore g1;
    private Giocatore g2;

    private GiocatoreInRosa gr1;
    private GiocatoreInRosa gr2;

    private Rosa r1;

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {

        //giocatori di prova
        g1 = new Giocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com");
        g2 = new Giocatore("Roberto", "Buffon", LocalDate.of(1978, 1, 28), "Italia", "gigibuff@mail.com");

        //giocatori in rosa di prova
        gr1 = new GiocatoreInRosa(g1, "Centrocampista", "Disponibile", LocalDate.of(2025, 7, 4));
        gr2 = new GiocatoreInRosa(g2, "Centrocampista", "Disponibile", LocalDate.of(2025, 7, 4));

        //creo una rosa
        r1 = new Rosa();
    }

    @After
    public void tearDown() {
        r1 = null;
    }

    /**
     * Test of aggiungiGiocatore method, of class Rosa.
     */
    @Test
    public void testAggiungiGiocatore() {

        //verifico che inizialmente non vi siano giocatori in rosa
        assertSame(0, r1.getGiocatori().size());

        //aggiungo un nuovo giocatore e verifico che sia stato aggiunto
        r1.aggiungiGiocatore(gr1);
        assertSame(1, r1.getGiocatori().size());

        //provo ad aggiungere uno stesso giocatore -> deve restuire false
        assertFalse(r1.aggiungiGiocatore(gr1));

        //verifico che la lista giocatori non sia null
        assertNotNull(r1.getGiocatori());

    }

    /**
     * Test of rimuoviGiocatore method, of class Rosa.
     */
    @Test
    public void testRimuoviGiocatore() {

        //verifico che inizialmente non vi siano giocatori in rosa
        assertSame(0, r1.getGiocatori().size());

        //aggiungo un nuovo giocatore e verifico che sia stato aggiunto
        r1.aggiungiGiocatore(gr1);
        assertSame(1, r1.getGiocatori().size());

        //rimuovo e verifico che il giocatore viene rimosso correttamente
        r1.rimuoviGiocatore(gr1);
        assertSame(0, r1.getGiocatori().size());

    }

    /**
     * Test of modificaGiocatore method, of class Rosa.
     */
    @Test
    public void testModificaGiocatore() {

        //aggiungo un giocatore in rosa
        r1.aggiungiGiocatore(gr1);

        //creo backup gr1
        GiocatoreInRosa gr1_Backup = new GiocatoreInRosa(new Giocatore("Ringhio", "Gattuso",
                LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"), "Centrocampista", "Disponibile", LocalDate.of(2025, 7, 4));

        //provo a modificare un giocatore presente in rosa -> esito TRUE
        assertTrue(r1.modificaGiocatore(gr1.getGiocatore(), "Terzino", "Sospeso"));

        //verifico se i campi effettivamente sono stati modificati
        assertNotSame(gr1_Backup.getRuolo(),gr1.getRuolo());
        assertNotSame(gr1_Backup.getStatus(),gr1.getStatus());
        
        //provo a modificare giocatore NON presente in rosa -> false
        assertFalse(r1.modificaGiocatore(g2, "Ala destra", "Sospeso"));
    }

    /**
     * Test of cercaGiocatori method, of class Rosa.
     */
    @Test
    public void testCercaGiocatori() {
        
        //aggiungo in rosa
        r1.aggiungiGiocatore(gr1);
        r1.aggiungiGiocatore(gr2);
        
        //verifico se la ricerca mi restituisce una lista non vuota
        assertNotNull(r1.cercaGiocatori("R"));
        
        //verifico che la lista sia accurato
        System.out.println("Giocatore filtrati: "+ "\n" +r1.cercaGiocatori("R"));
    }

    /**
     * Test of getGiocatori method, of class Rosa.
     */
    @Test
    public void testGetGiocatori() {
        //aggiungo in rosa
        r1.aggiungiGiocatore(gr1);
        
        //verifico che la get funzioni
        assertNotNull(r1.getGiocatori());

    }

}
