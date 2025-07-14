package model;

import exception.GiocatoreDuplicatoException;
import exception.NumeroMagliaDuplicatoException;
import exception.RosaCompletaException;
import java.time.LocalDate;
import model.enums.Ruolo;
import static model.enums.Ruolo.*;
import model.enums.Status;
import static model.enums.Status.*;
import org.junit.After;
import org.junit.Before;
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

    @Before
    public void setUp() {

        //giocatori di prova
        g1 = new Giocatore("Ringhio", "Gattuso", LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com");
        g2 = new Giocatore("Gigi", "Buffon", LocalDate.of(1978, 1, 28), "Italia", "gigibuff@mail.com");

        //giocatori in rosa di prova
        gr1 = new GiocatoreInRosa(g1, CENTROCAMPISTA, DISPONIBILE, 1, LocalDate.of(2025, 7, 4));
        gr2 = new GiocatoreInRosa(g2, CENTROCAMPISTA, DISPONIBILE, 10, LocalDate.of(2025, 7, 4));

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
    public void testAggiungiGiocatore() throws GiocatoreDuplicatoException, RosaCompletaException, NumeroMagliaDuplicatoException {

        // Verifica che la rosa sia inizialmente vuota
        assertEquals(0, r1.getGiocatori().size());

        // Aggiungi un giocatore: deve andare bene
        r1.aggiungiGiocatore(g1, Ruolo.ATTACCANTE, Status.DISPONIBILE, 2);
        assertEquals(1, r1.getGiocatori().size());

        // Verifica che la lista non sia null
        assertNotNull(r1.getGiocatori());

        // Prova ad aggiungere lo stesso giocatore -> GiocatoreDuplicatoException
        GiocatoreDuplicatoException ex1 = assertThrows(
                GiocatoreDuplicatoException.class,
                () -> r1.aggiungiGiocatore(g1, Ruolo.DIFENSORE, Status.DISPONIBILE, 3)
        );
        assertEquals("Il giocatore è già presente nella rosa.", ex1.getMessage());

        // Aggiungi un altro giocatore con stesso numero di maglia -> NumeroMagliaDuplicatoException
        Giocatore altro = new Giocatore("Francesco", "Totti", LocalDate.of(1976, 9, 27), "Italia", "totti@mail.com");

        NumeroMagliaDuplicatoException ex2 = assertThrows(
                NumeroMagliaDuplicatoException.class,
                () -> r1.aggiungiGiocatore(altro, Ruolo.ATTACCANTE, Status.DISPONIBILE, 2) // 2 è già usato
        );
        assertEquals("Numero maglia già utilizzato", ex2.getMessage());

        // Riempi la rosa fino a 22 giocatori diversi
        for (int i = 0; i < 21; i++) {
            Giocatore g = new Giocatore("Nome" + i, "Cognome" + i, LocalDate.now(), "Italia", "email" + i + "@example.com");
            r1.aggiungiGiocatore(g, Ruolo.DIFENSORE, Status.DISPONIBILE, i + 3); // usa numeri diversi da 2
        }
        assertEquals(22, r1.getGiocatori().size());

        // Provo ad aggiungere ancora -> RosaCompletaException
        RosaCompletaException ex3 = assertThrows(
                RosaCompletaException.class,
                () -> r1.aggiungiGiocatore(
                        new Giocatore("Nuovo", "Giocatore", LocalDate.now(), "Italia", "nuovo@mail.com"),
                        Ruolo.PORTIERE,
                        Status.DISPONIBILE,
                        25
                )
        );
        assertEquals("Rosa piena",ex3.getMessage());
        // aggiunto verifica messaggio RosaCompletaException
    }

    /**
     * Test of rimuoviGiocatore method, of class Rosa.
     */
    @Test
    public void testRimuoviGiocatore() throws GiocatoreDuplicatoException, RosaCompletaException, Exception {

        //verifico che inizialmente non vi siano giocatori in rosa
        assertEquals(0, r1.getGiocatori().size());

        //aggiungo un nuovo giocatore e verifico che sia stato aggiunto
        r1.aggiungiGiocatore(g1, ATTACCANTE, DISPONIBILE, 3);
        assertEquals(1, r1.getGiocatori().size());

        //rimuovo e verifico che il giocatore viene rimosso correttamente
        r1.rimuoviGiocatore(gr1);
        assertEquals(0, r1.getGiocatori().size());

    }

    /**
     * Test of modificaGiocatore method, of class Rosa.
     */
    @Test
    public void testModificaGiocatore() throws GiocatoreDuplicatoException, RosaCompletaException, NumeroMagliaDuplicatoException {

        //aggiungo un giocatore in rosa
        r1.aggiungiGiocatore(g1, ATTACCANTE, SOSPESO, 4);

        //creo backup gr1
        GiocatoreInRosa gr1_Backup = new GiocatoreInRosa(new Giocatore("Ringhio", "Gattuso",
                LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com"),
                CENTROCAMPISTA, DISPONIBILE, 10, LocalDate.of(2025, 7, 4));

        //verifico se i campi effettivamente sono stati modificati
        assertNotEquals(gr1_Backup.getRuolo(), r1.getGiocatori().get(0).getRuolo());
        assertNotEquals(gr1_Backup.getStatus(), r1.getGiocatori().get(0).getStatus());
        assertNotEquals(gr1_Backup.getNumMaglia(), r1.getGiocatori().get(0).getNumMaglia());
        // sistemato getNumMaglia in gr1_backup
    }
        
    /**
     * Test of cercaGiocatori method, of class Rosa.
     */
    @Test
    public void testCercaGiocatori() throws GiocatoreDuplicatoException, RosaCompletaException, NumeroMagliaDuplicatoException {

        //aggiungo in rosa
        r1.aggiungiGiocatore(g1, ATTACCANTE, DISPONIBILE, 5);
        r1.aggiungiGiocatore(g2, DIFENSORE, DISPONIBILE, 6);

        //verifico se la ricerca mi restituisce una lista non vuota
        assertNotEquals(0, r1.cercaGiocatori("R").size());

        //verifico che la ricerca non restituisca nulla con parametri non corrispondenti
        assertEquals(0, r1.cercaGiocatori("ahahbah").size());

    }

    /**
     * Test of getGiocatori method, of class Rosa.
     */
    @Test
    public void testGetGiocatori() throws GiocatoreDuplicatoException, RosaCompletaException, NumeroMagliaDuplicatoException {
        //aggiungo in rosa
        r1.aggiungiGiocatore(g1, ATTACCANTE, DISPONIBILE, 10);

        //verifico che la get funzioni
        assertNotNull(r1.getGiocatori());

    }

}
