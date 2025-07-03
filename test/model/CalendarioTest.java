/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Giuseppe Ravesi
 */
public class CalendarioTest {
    
    public CalendarioTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of pianificaAllenamento method, of class Calendario.
     */
    @Test
    public void testPianificaAllenamento() {
        System.out.println("pianificaAllenamento");
        LocalDate data = null;
        LocalTime orario = null;
        int durata = 0;
        String luogo = "";
        String tipologia = "";
        String note = "";
        Calendario instance = new Calendario();
        instance.pianificaAllenamento(data, orario, durata, luogo, tipologia, note);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pianificaAmichevole method, of class Calendario.
     */
    @Test
    public void testPianificaAmichevole() {
        System.out.println("pianificaAmichevole");
        LocalDate data = null;
        LocalTime orario = null;
        int durata = 0;
        String luogo = "";
        String squadraAvversaria = "";
        Calendario instance = new Calendario();
        instance.pianificaAmichevole(data, orario, durata, luogo, squadraAvversaria);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiornaEvento method, of class Calendario.
     */
    @Test
    public void testAggiornaEvento() {
        System.out.println("aggiornaEvento");
        Evento eventoSelezionato = null;
        LocalDate nuovaData = null;
        LocalTime nuovoOrario = null;
        int nuovaDurata = 0;
        String nuovoLuogo = "";
        Map<String, String> campiSpecifici = null;
        Calendario instance = new Calendario();
        instance.aggiornaEvento(eventoSelezionato, nuovaData, nuovoOrario, nuovaDurata, nuovoLuogo, campiSpecifici);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rimuoviEvento method, of class Calendario.
     */
    @Test
    public void testRimuoviEvento() {
        System.out.println("rimuoviEvento");
        Evento eventoSelezionato = null;
        Calendario instance = new Calendario();
        instance.rimuoviEvento(eventoSelezionato);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventi method, of class Calendario.
     */
    @Test
    public void testGetEventi() {
        System.out.println("getEventi");
        Calendario instance = new Calendario();
        List<Evento> expResult = null;
        List<Evento> result = instance.getEventi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiungiDisponibilità method, of class Calendario.
     */
    @Test
    public void testAggiungiDisponibilità() {
        System.out.println("aggiungiDisponibilit\u00e0");
        Evento eventoSelezionato = null;
        boolean presenza = false;
        String motivazione = "";
        Calendario instance = new Calendario();
        instance.aggiungiDisponibilità(eventoSelezionato, presenza, motivazione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
