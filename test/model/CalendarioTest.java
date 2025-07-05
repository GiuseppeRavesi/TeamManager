/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import controller.Session;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
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
public class CalendarioTest {

    private Giocatore g1;
    private Giocatore g2;

    private GiocatoreInRosa gr1;
    private GiocatoreInRosa gr2;

    private Disponibilità d1;
    private Disponibilità d2;
    private Disponibilità d3;
    private Disponibilità d4;

    private Amichevole a1;
    private Amichevole a2;

    private Calendario c;

    private static Session sessione;
    private Utente u1;

    private Map<String, String> campiSpecifici;

    @Before
    public void setUpClass() {
        //giocatori di prova
        g1 = new Giocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com");
        g2 = new Giocatore("Gigi", "Buffon", LocalDate.of(1978, 1, 28), "Italia", "gigibuff@mail.com");

        //giocatori in rosa di prova
        gr1 = new GiocatoreInRosa(g1, "Centrocampista", "Disponibile", LocalDate.of(2025, 7, 4));
        gr2 = new GiocatoreInRosa(g2, "Centrocampista", "Disponibile", LocalDate.of(2025, 7, 4));

        //eventi di prova
        a1 = new Amichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        a2 = new Amichevole(LocalDate.of(2025, 8, 6), LocalTime.of(20, 10, 45), 100, "Barcelona", "Juventus");

        //disponibilità giocatori
        d1 = new Disponibilità(gr1.getGiocatore().getId(), a1.getId(), true, null);
        d2 = new Disponibilità(gr2.getGiocatore().getId(), a1.getId(), true, null);
        d3 = new Disponibilità(gr1.getGiocatore().getId(), a2.getId(), true, null);
        d4 = new Disponibilità(gr2.getGiocatore().getId(), a2.getId(), true, null);

        //creazione Calendario
        c = new Calendario();

        //inizializzazione Mappa
        campiSpecifici = new HashMap<>();
        campiSpecifici.put("squadraAvversaria", "Liverpool");
        campiSpecifici.put("tipologia", "Sessione Corpo Libero");
        campiSpecifici.put("note", "Lower Body");

    }

    @BeforeClass
    public static void initSession() {
        sessione = Session.getInstance();
    }

    @After
    public void clear() {
        c = null;
    }

    @AfterClass
    public static void cleanSession() {
        sessione = null;
    }

    /**
     * Test of pianificaAllenamento method, of class Calendario.
     */
    @Test
    public void testPianificaAllenamento() {
        //pianificazione allenamento
        c.pianificaAllenamento(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 45, "Catania", "Sessione Mirata", "Upper Body");

        //verifico se effettivamente è presente un allenamento
        assertSame(1, c.getEventi().size());

        //verifico sovrapposizione, se due eventi stesso giorno e stesso orario -> false
        assertFalse(c.pianificaAllenamento(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 45, "Catania", "Sessione Mirata", "Upper Body"));

        //verifico se effettivamente gli eventi aggiunti non siano null
        assertNotNull(c.getEventi());

    }

    /**
     * Test of pianificaAmichevole method, of class Calendario.
     */
    @Test
    public void testPianificaAmichevole() {
        //pianificazione amichevoli
        c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        c.pianificaAmichevole(LocalDate.of(2025, 8, 6), LocalTime.of(20, 10, 45), 100, "Barcelona", "Juventus");

        //verifico se sono stati aggiunti due amichevoli inizialmente
        assertSame(2, c.getEventi().size());

        //verifico sovrapposizione, se due eventi stesso giorno e stesso orario -> false
        assertFalse(c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter"));

        //verifico se effettivamente gli eventi aggiunti non siano null
        assertNotNull(c.getEventi());
    }

    /**
     * Test of aggiornaEvento method, of class Calendario.
     */
    @Test
    public void testAggiornaEvento() {

        //creo un evento a1 copia
        Amichevole a1_Old = new Amichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        c.aggiornaEvento(a1, LocalDate.of(2026, 5, 18), LocalTime.of(22, 30, 55), 95, "CaltaCity", campiSpecifici);

        //verifico se effetivamente a1 è stato aggiornato confrontando i campi
        assertNotEquals(a1_Old.getData(), a1.getData());
        assertNotEquals(a1_Old.getOrario(), a1.getOrario());
        assertNotEquals(a1_Old.getDurata(), a1.getDurata());
        assertNotEquals(a1_Old.getLuogo(), a1.getLuogo());
        assertNotEquals(a1_Old.getSquadraAvversaria(), a1.getSquadraAvversaria());
        //------------------------------------------------------------------------

        // Creo un primo evento
        boolean ok1 = c.pianificaAllenamento(LocalDate.of(2025, 7, 4), LocalTime.of(15, 0), 90, "Stadio A", "Tecnico", "Note");
        assertTrue(ok1);

        // Creo un secondo evento
        boolean ok2 = c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(18, 0), 60, "Stadio A", "Inter");
        assertTrue(ok2);

        // Modifico il secondo evento per sovrapporlo al primo
        Evento amichevole = c.getEventi().get(1);
        boolean conflitto = c.aggiornaEvento(amichevole, LocalDate.of(2025, 7, 4),
                LocalTime.of(15, 30), // si sovrappone al primo
                60,
                "Stadio A",
                campiSpecifici);
        assertFalse(conflitto);  // deve fallire per conflitto

        //Rimodifico il secondo evento per evitare i conflitti
        Evento a = c.getEventi().get(1);
        boolean conflitto1 = c.aggiornaEvento(amichevole, LocalDate.of(2025, 7, 4),
                LocalTime.of(19, 30), //Non ci sono conflitti di orario
                60,
                "Stadio A",
                campiSpecifici);
        assertTrue(conflitto1);
    }

    /**
     * Test of rimuoviEvento method, of class Calendario.
     */
    @Test
    public void testRimuoviEvento() {

        //pianificazione amichevoli
        c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        c.pianificaAmichevole(LocalDate.of(2025, 8, 6), LocalTime.of(20, 10, 45), 100, "Barcelona", "Juventus");

        //verifico se sono stati aggiunti due amichevoli inizialmente
        assertSame(2, c.getEventi().size());

        //elimino l'evento di posizione 1
        c.rimuoviEvento(c.getEventi().get(1));
        assertSame(1, c.getEventi().size());

        //elimino l'evento di posizione 0
        c.rimuoviEvento(c.getEventi().get(0));
        assertSame(0, c.getEventi().size());
    }

    /**
     * Test of getEventi method, of class Calendario.
     */
    //non mi serve
    @Test
    public void testGetEventi() {
        //verifico se effettivamente gli eventi aggiunti non siano null
        //pianificazione amichevoli
        c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        c.pianificaAmichevole(LocalDate.of(2025, 8, 6), LocalTime.of(20, 10, 45), 100, "Barcelona", "Juventus");

        System.out.println("Lista eventi Aggiungi " + c.getEventi());
        assertNotNull(c.getEventi());
    }

    /**
     * Test of aggiungiDisponibilità method, of class Calendario.
     */
    @Test
    public void testAggiungiDisponibilità() {
        //essendo necessario una sessione, si prsegue a testare con un utente loggato fittizio
        u1 = new Utente("ringhiog@mail.com", "1234", "Giocatore,", gr1.getGiocatore().getId());
        sessione.login(u1);

        //per un evento presente in calendario, aggiungo una disponibilita
        c.aggiungiDisponibilità(a1, true, null);

        //verifico se effettivamente il giocatore ha fornito correttamente la sua disponibilita per evento a1
        assertNotNull(a1.getDisponibilità());
    }

}
