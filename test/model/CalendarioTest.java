package model;

import controller.Session;
import exception.SovrapposizioneEventoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import static model.enums.Ruolo.*;
import static model.enums.Status.*;
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
        gr1 = new GiocatoreInRosa(g1, CENTROCAMPISTA, DISPONIBILE, 10, LocalDate.of(2025, 7, 4));
        gr2 = new GiocatoreInRosa(g2, CENTROCAMPISTA, DISPONIBILE, 1, LocalDate.of(2025, 7, 4));

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

    @Test
    public void testConfrontaGiocatoriCompleto() {

        /* Promemoria costruttore StatisticaAmichevole:
        public StatisticaAmichevole(int idGiocatore, int idEvento, int minutiGiocati, int goal,
            int autogoal, int cartelliniGialli, int cartelliniRossi, float distanzaTotalePercorsa,
            int falliCommessi, int assist, int parate, int intercettiRiusciti, int passaggiChiave, int tiriTotali)
        */
        // Creo due eventi
        Amichevole a1 = new Amichevole(LocalDate.now(), LocalTime.NOON, 90, "Stadio Olimpico", "Roma");
        Amichevole a2 = new Amichevole(LocalDate.now().plusDays(1), LocalTime.NOON, 90, "San Siro", "Milan");
        c.getEventi().add(a1);
        c.getEventi().add(a2);

        // Aggiungo statistiche per giocatore1
        Disponibilità d1g1 = new Disponibilità(gr1.getGiocatore().getId(), a1.getId(), true, null);
        d1g1.setStatistica(new StatisticaAmichevole(gr1.getGiocatore().getId(), a1.getId(), 90, 2, 1, 0, 0, 9.5f, 1, 0, 0, 3, 2, 1));
        a1.aggiungiDisponibilità(d1g1);

        Disponibilità d2g1 = new Disponibilità(gr1.getGiocatore().getId(), a2.getId(), true, null);
        d2g1.setStatistica(new StatisticaAmichevole(gr1.getGiocatore().getId(), a2.getId(), 80, 1, 0, 1, 0, 10.5f, 0, 1, 0, 2, 1, 0));
        a2.aggiungiDisponibilità(d2g1);

        // Aggiungi statistiche per giocatore2
        Disponibilità d1g2 = new Disponibilità(gr2.getGiocatore().getId(), a1.getId(), true, null);
        d1g2.setStatistica(new StatisticaAmichevole(gr2.getGiocatore().getId(), a1.getId(), 90, 0, 2, 0, 1, 8.0f, 2, 1, 1, 1, 0, 2));
        a1.aggiungiDisponibilità(d1g2);

        // === Esecuzione ===
        Map<String, Map<String, Number>> risultato = c.confrontaGiocatori(gr1, gr2);

        // === Verifiche ===
        // 1. Verifico struttura base
        assertNotNull(risultato);
        assertEquals(2, risultato.size());
        assertTrue(risultato.containsKey("Ringhio Gattuso"));
        assertTrue(risultato.containsKey("Gigi Buffon"));

        Map<String, Number> statsG1 = risultato.get("Ringhio Gattuso");
        Map<String, Number> statsG2 = risultato.get("Gigi Buffon");

        // 2. Verifico tutti i campi per giocatore1 (somme attese)
        assertEquals(3, statsG1.get("goal").intValue());           // 2 + 1
        assertEquals(1, statsG1.get("assist").intValue());         // 0 + 1
        assertEquals(170, statsG1.get("minutiGiocati").intValue()); // 90 + 80
        assertEquals(1, statsG1.get("autogoal").intValue());       // 1 + 0
        assertEquals(1, statsG1.get("cartelliniGialli").intValue());// 0 + 1
        assertEquals(0, statsG1.get("cartelliniRossi").intValue()); // 0 + 0
        assertEquals(1, statsG1.get("falliCommessi").intValue());  // 1 + 0
        assertEquals(5, statsG1.get("intercettiRiusciti").intValue()); // 3 + 2
        assertEquals(3, statsG1.get("passaggiChiave").intValue()); // 2 + 1
        assertEquals(1, statsG1.get("tiriTotali").intValue());     // 1 + 0
        assertEquals(0, statsG1.get("parate").intValue());         // 0 + 0
        assertEquals(20.0f, statsG1.get("distanzaTotalePercorsa").floatValue(), 0.01f); // 9.5 + 10.

        // 3. Verifico tutti i campi per giocatore2 (solo primo evento)
        assertEquals(0, statsG2.get("goal").intValue());
        assertEquals(1, statsG2.get("assist").intValue());
        assertEquals(90, statsG2.get("minutiGiocati").intValue());
        assertEquals(2, statsG2.get("autogoal").intValue());
        assertEquals(0, statsG2.get("cartelliniGialli").intValue());
        assertEquals(1, statsG2.get("cartelliniRossi").intValue());
        assertEquals(2, statsG2.get("falliCommessi").intValue());
        assertEquals(1, statsG2.get("intercettiRiusciti").intValue());
        assertEquals(0, statsG2.get("passaggiChiave").intValue());
        assertEquals(2, statsG2.get("tiriTotali").intValue());
        assertEquals(1, statsG2.get("parate").intValue());
        assertEquals(8.0f, statsG2.get("distanzaTotalePercorsa").floatValue(), 0.01f);

        // 4. Verifico che i campi non siano null
        assertNotNull(statsG1.get("passaggiChiave"));
        assertNotNull(statsG2.get("intercettiRiusciti"));

        // 5. Verifico tipi dei valori
        assertTrue(statsG1.get("goal") instanceof Integer);
        assertTrue(statsG1.get("distanzaTotalePercorsa") instanceof Float);
    }
}
