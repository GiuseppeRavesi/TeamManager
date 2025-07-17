package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Allenamento;
import model.Evento;
import model.Giocatore;
import model.GiocatoreInRosa;
import model.enums.Ruolo;
import model.enums.Status;
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
    private PersistenceHandler handler;

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
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
        //verifico la dimensione della lista giocatori
        assertEquals(1, tm.getListaGiocatori().size());

        //verifico che la lista giocatori non sia null
        assertNotNull(tm.getListaGiocatori());

        //provo ad aggiungere lo stesso giocatore -> FALSE
        assertFalse(tm.creaGiocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));
    }

    /**
     * Test of eliminaGiocatore method, of class TeamManager.
     */
    @Test
    public void testEliminaGiocatore() {

        //inserisco un giocatore di prova
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));

        //elimino il giocatore inserito
        assertTrue(tm.eliminaGiocatore(tm.getListaGiocatori().get(0)));

        //verifico se la lista giocatori è vuota
        assertEquals(0, tm.getListaGiocatori().size());

    }

    /**
     * Test of cercaGiocatori method, of class TeamManager.
     */
    @Test
    public void testCercaGiocatori() {

        //inserisco un giocatore di prova
        assertTrue(tm.creaGiocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"));

        //provo ad eseguire una ricerca, assicurandomi che inserendo il parametro di ricerca non sia vuota
        assertNotEquals(0, tm.cercaGiocatori("R").size());

        //provo a svolgere una ricerca che non restituisce alcun risultato
        assertEquals(0, tm.cercaGiocatori("gbuhub").size());

    }

    @Test
    public void testInizializzazioneDatiDaPersistence() {
        handler = new PersistenceHandler();

        // Popola il PersistenceHandler con dati fittizi
        Giocatore giocatore = new Giocatore("Test", "Player", LocalDate.now(), "prova", "prova");
        handler.getListaGiocatori().add(giocatore);
        handler.getRosa().add(new GiocatoreInRosa(giocatore, Ruolo.ATTACCANTE, Status.DISPONIBILE, 22, LocalDate.now()));
        Evento evento = new Allenamento(LocalDate.now(), LocalTime.of(10, 0), 90, "Campo Test", "Tecnico", "Note");
        handler.getListaEventi().add(evento);

        // Inizializza TeamManager con i dati dal persistence handler
        tm.inizializzaDatiDaPersistence(handler);

        // Verifica che puntino alla stessa istanza
        assertSame(handler.getListaGiocatori(), tm.getListaGiocatori());
        assertSame(handler.getRosa(), tm.getR().getGiocatori());
        assertSame(handler.getListaEventi(), tm.getC().getEventi());
    }

}
