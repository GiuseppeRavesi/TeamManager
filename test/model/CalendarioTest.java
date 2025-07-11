package model;

import controller.Session;
import exception.SovrapposizioneEventoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import static model.enums.Ruolo.CENTROCAMPISTA;
import static model.enums.Status.DISPONIBILE;
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
        g1 = new Giocatore("Ringhio", "Gattuso",10, LocalDate.of(1978, 1, 9), "Italia", "ringhiog@mail.com");
        g2 = new Giocatore("Gigi", "Buffon",1, LocalDate.of(1978, 1, 28), "Italia", "gigibuff@mail.com");

        //giocatori in rosa di prova
        gr1 = new GiocatoreInRosa(g1, CENTROCAMPISTA, DISPONIBILE, LocalDate.of(2025, 7, 4));
        gr2 = new GiocatoreInRosa(g2, CENTROCAMPISTA, DISPONIBILE, LocalDate.of(2025, 7, 4));

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
        try {
            // Pianifico un primo allenamento valido
            c.pianificaAllenamento(
                    LocalDate.of(2025, 7, 4),
                    LocalTime.of(15, 30, 45),
                    45,
                    "Catania",
                    "Sessione Mirata",
                    "Upper Body"
            );
        } catch (SovrapposizioneEventoException e) {
            fail("Non dovrebbe lanciare eccezione qui!");
        }

        // Verifico che sia stato aggiunto
        assertEquals(1, c.getEventi().size());

        // Provo a pianificare uno sovrapposto -> Deve lanciare l’eccezione
        assertThrows(SovrapposizioneEventoException.class, () -> {
            c.pianificaAllenamento(
                    LocalDate.of(2025, 7, 4),
                    LocalTime.of(15, 30, 45),
                    45,
                    "Catania",
                    "Sessione Mirata",
                    "Upper Body"
            );
        });

        // Verifico che la lista eventi non sia nulla
        assertNotNull(c.getEventi());
    }

    /**
     * Test of pianificaAmichevole method, of class Calendario.
     */
    @Test
    public void testPianificaAmichevole() {
        try {
            // Pianifico due amichevoli distinte
            c.pianificaAmichevole(
                    LocalDate.of(2025, 7, 4),
                    LocalTime.of(15, 30, 45),
                    90,
                    "Manhattan",
                    "Inter"
            );
            c.pianificaAmichevole(
                    LocalDate.of(2025, 8, 6),
                    LocalTime.of(20, 10, 45),
                    100,
                    "Barcelona",
                    "Juventus"
            );
        } catch (SovrapposizioneEventoException e) {
            fail("Non dovrebbe lanciare eccezione qui!");
        }

        assertEquals(2, c.getEventi().size());

        // Tenta di creare un duplicato: deve lanciare eccezione
        assertThrows(SovrapposizioneEventoException.class, () -> {
            c.pianificaAmichevole(
                    LocalDate.of(2025, 7, 4),
                    LocalTime.of(15, 30, 45),
                    90,
                    "Manhattan",
                    "Inter"
            );
        });

        assertNotNull(c.getEventi());
    }

    /**
     * Test of aggiornaEvento method, of class Calendario.
     */
   @Test
public void testAggiornaEvento() throws SovrapposizioneEventoException {

    // Creo due eventi non sovrapposti
    c.pianificaAllenamento(
        LocalDate.of(2025, 7, 4),
        LocalTime.of(15, 0),
        90,
        "Stadio A",
        "Tecnico",
        "Note"
    );

    c.pianificaAmichevole(
        LocalDate.of(2025, 7, 4),
        LocalTime.of(18, 0),
        60,
        "Stadio A",
        "Inter"
    );

    Evento amichevole = c.getEventi().get(1);

    // Caso 1: deve lanciare eccezione perché sovrapposto
    assertThrows(SovrapposizioneEventoException.class, () -> {
        c.aggiornaEvento(
            amichevole,
            LocalDate.of(2025, 7, 4),
            LocalTime.of(15, 30), // si sovrappone all'allenamento
            60,
            "Stadio A",
            campiSpecifici
        );
    });

    // Caso 2: deve andare a buon fine perché non c'è conflitto
    c.aggiornaEvento(
        amichevole,
        LocalDate.of(2025, 7, 4),
        LocalTime.of(19, 30), // non si sovrappone
        60,
        "Stadio A",
        campiSpecifici
    );

    // Verifico che l'evento sia stato aggiornato correttamente
    assertEquals(LocalTime.of(19, 30), amichevole.getOrario());
}


    /**
     * Test of rimuoviEvento method, of class Calendario.
     */
    @Test
    public void testRimuoviEvento() throws SovrapposizioneEventoException {

        //pianificazione amichevoli
        c.pianificaAmichevole(LocalDate.of(2025, 7, 4), LocalTime.of(15, 30, 45), 90, "Manhattan", "Inter");
        c.pianificaAmichevole(LocalDate.of(2025, 8, 6), LocalTime.of(20, 10, 45), 100, "Barcelona", "Juventus");

        //verifico se sono stati aggiunti due amichevoli inizialmente
        assertEquals(2, c.getEventi().size());

        //elimino l'evento di posizione 1
        c.rimuoviEvento(c.getEventi().get(1));
        assertEquals(1, c.getEventi().size());

        //elimino l'evento di posizione 0
        c.rimuoviEvento(c.getEventi().get(0));
        assertEquals(0, c.getEventi().size());
    }

    /**
     * Test of getEventi method, of class Calendario.
     */
    //non mi serve
    @Test
    public void testGetEventi() throws SovrapposizioneEventoException {
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
