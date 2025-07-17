package model;

import controller.Session;
import exception.SovrapposizioneEventoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.enums.Ruolo;
import static model.enums.Ruolo.*;
import model.enums.Status;
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

    /**
     * Test of confrontaGiocatore method, of class Calendario.
     */
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

    @Test

    public void testAggiungiStatisticaAllenamento() {

        Map<String, String> campiSpecifici2 = new HashMap<>();
        campiSpecifici2.put("velocitàMax", "20.5");
        campiSpecifici2.put("velocitàMedia", "18.5");
        campiSpecifici2.put("valutazioneForzaFisica", "15");
        campiSpecifici2.put("valutazioneForzaTiro", "25");
        campiSpecifici2.put("frequenzaCardiacaMedia", "22");

        u1 = new Utente("ringhiog@mail.com", "1234", "Giocatore,", gr1.getGiocatore().getId());
        sessione.login(u1);

        int idGiocatore = sessione.getUtenteLoggato().getId();

        c.getEventi().clear();
        // pianifico un allenamento
        try {

            c.pianificaAllenamento(LocalDate.now(), LocalTime.NOON, 120, "Catania", "fisico", "");

        } catch (SovrapposizioneEventoException e) {

            System.out.println(e.getMessage());

        }

        // provo ad aggiungere ad un evento inesistente
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            c.aggiungiStatisticaAllenamento(idGiocatore, 999, campiSpecifici2);

        });

        assertEquals("Evento non trovato", ex1.getMessage());

        // provo ad aggiungere una disponibilità inesistente
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            c.aggiungiStatisticaAllenamento(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici2);

        });
        assertEquals("Disponibilità non trovata", ex2.getMessage());

        // dopo aver testato che disponibilità è inesistente ne aggiungo una
        c.aggiungiDisponibilità(c.getEventi().get(0), true, null);

        //aggiungo una statistica allenamento
        try {

            c.aggiungiStatisticaAllenamento(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici2);

            // verifico che la statistica sia statà aggiunta correttamente
            assertNotNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());

        } catch (IllegalArgumentException e) {

            fail("test fallito");

        }

    }

    @Test

    public void testAggiungiStatisticaAmichevole() {

        Map<String, String> campiSpecifici3 = new HashMap<>();
        campiSpecifici3.put("minutiGiocati", "60");
        campiSpecifici3.put("goal", "1");
        campiSpecifici3.put("autogoal", "0");
        campiSpecifici3.put("cartelliniGialli", "2");
        campiSpecifici3.put("cartelliniRossi", "1");
        campiSpecifici3.put("distanzaTotalePercorsa", "15");
        campiSpecifici3.put("falliCommessi", "8");
        campiSpecifici3.put("assist", "0");
        campiSpecifici3.put("parate", "0");
        campiSpecifici3.put("intercettiRiusciti", "1");
        campiSpecifici3.put("passaggiChiave", "1");
        campiSpecifici3.put("tiriTotali", "8");

        u1 = new Utente("ringhiog@mail.com", "1234", "Giocatore,", gr1.getGiocatore().getId());
        sessione.login(u1);

        int idGiocatore = sessione.getUtenteLoggato().getId();

        c.getEventi().clear();

        // pianifico un amichevole
        try {

            c.pianificaAmichevole(LocalDate.now(), LocalTime.NOON, 120, "Catania", "milan");

        } catch (SovrapposizioneEventoException e) {

            System.out.println(e.getMessage());

        }
        // provo ad aggiungere ad un evento inesistente
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            c.aggiungiStatisticaAmichevole(idGiocatore, 999, campiSpecifici3);

        });

        assertEquals("Evento non trovato con id: " + 999, ex1.getMessage());

        // provo ad aggiungere una disponibilità inesistente
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            c.aggiungiStatisticaAmichevole(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici3);

        });
        assertEquals("Disponibilità non trovata per giocatore: " + idGiocatore, ex2.getMessage());

        // dopo aver testato che disponibilità è inesistente ne aggiungo una
        c.aggiungiDisponibilità(c.getEventi().get(0), true, null);

        try {

            c.aggiungiStatisticaAmichevole(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici3);

            // verifico che la statistica sia statà aggiunta correttamente
            assertNotNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());

        } catch (IllegalArgumentException e) {

            fail("test fallito");

        }

    }

    @Test
    public void testRimuoviStatistica() {

        Map<String, String> campiSpecifici3 = new HashMap<>();
        campiSpecifici3.put("minutiGiocati", "60");
        campiSpecifici3.put("goal", "1");
        campiSpecifici3.put("autogoal", "0");
        campiSpecifici3.put("cartelliniGialli", "2");
        campiSpecifici3.put("cartelliniRossi", "1");
        campiSpecifici3.put("distanzaTotalePercorsa", "15");
        campiSpecifici3.put("falliCommessi", "8");
        campiSpecifici3.put("assist", "0");
        campiSpecifici3.put("parate", "0");
        campiSpecifici3.put("intercettiRiusciti", "1");
        campiSpecifici3.put("passaggiChiave", "1");
        campiSpecifici3.put("tiriTotali", "8");

        u1 = new Utente("ringhiog@mail.com", "1234", "Giocatore,", gr1.getGiocatore().getId());
        sessione.login(u1);

        int idGiocatore = sessione.getUtenteLoggato().getId();

        c.getEventi().clear();

        //pianifico un amichevole
        try {

            c.pianificaAmichevole(LocalDate.now(), LocalTime.NOON, 120, "Catania", "milan");

        } catch (SovrapposizioneEventoException e) {

            System.out.println(e.getMessage());

        }

        // provo ad aggiungere ad un evento inesistente
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            c.rimuoviStatistica(idGiocatore, 999);

        });
        assertEquals("Evento non trovato", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            c.rimuoviStatistica(idGiocatore, c.getEventi().get(0).getId());

        });

        // provo ad aggiungere una disponibilità inesistente
        assertEquals("Disponibilità non trovata", ex2.getMessage());

        // dopo aver testato che disponibilità è inesistente ne aggiungo una
        c.aggiungiDisponibilità(c.getEventi().get(0), true, null);

        try {

            c.aggiungiStatisticaAmichevole(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici3);

            // verifico che la statistica sia statà aggiunta correttamente
            assertNotNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());

        } catch (IllegalArgumentException e) {

            fail("test fallito");

        }

        assertNotNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());

        // verifico che la statistica sia stata rimossa correttamente
        c.rimuoviStatistica(idGiocatore, c.getEventi().get(0).getId());
        assertNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());
    }

    @Test
    public void testVisualizzaStorico() {

        Map<String, String> campiSpecifici3 = new HashMap<>();
        campiSpecifici3.put("minutiGiocati", "60");
        campiSpecifici3.put("goal", "1");
        campiSpecifici3.put("autogoal", "0");
        campiSpecifici3.put("cartelliniGialli", "2");
        campiSpecifici3.put("cartelliniRossi", "1");
        campiSpecifici3.put("distanzaTotalePercorsa", "15");
        campiSpecifici3.put("falliCommessi", "8");
        campiSpecifici3.put("assist", "0");
        campiSpecifici3.put("parate", "0");
        campiSpecifici3.put("intercettiRiusciti", "1");
        campiSpecifici3.put("passaggiChiave", "1");
        campiSpecifici3.put("tiriTotali", "8");

        u1 = new Utente("ringhiog@mail.com", "1234", "Giocatore,", gr1.getGiocatore().getId());
        sessione.login(u1);

        int idGiocatore = sessione.getUtenteLoggato().getId();
        c.getEventi().clear();

        // pianifico un amichevole
        try {

            c.pianificaAmichevole(LocalDate.now(), LocalTime.NOON, 120, "Catania", "milan");

        } catch (SovrapposizioneEventoException e) {

            System.out.println(e.getMessage());

        }
        // provo ad aggiungere una disponibilità inesistente
        c.aggiungiDisponibilità(c.getEventi().get(0), true, null);

        try {

            c.aggiungiStatisticaAmichevole(idGiocatore, c.getEventi().get(0).getId(), campiSpecifici3);

            // verifico che la statistica sia statà aggiunta correttamente
            assertNotNull(c.getEventi().get(0).getDisponibilità().get(0).getStatistica());

        } catch (IllegalArgumentException e) {

            fail("test fallito");

        }

        // visualizzo la statistica
        Statistica s = c.visualizzaStoricoGiocatore(idGiocatore, c.getEventi().get(0).getId());

        // verifico che la visualizazzione sia avvenuta correttamnete
        assertNotNull(s);
        // rimuovo una statistica

        c.rimuoviStatistica(idGiocatore, c.getEventi().get(0).getId());

        // verifico che la visualizzaione sia Null
        s = c.visualizzaStoricoGiocatore(idGiocatore, c.getEventi().get(0).getId());
        assertNull(s);
    }

    @Test
    public void testSuggerisciSessioneMirata() {
        // Configurazione
        int idGiocatore = gr1.getGiocatore().getId();

        // Crea 2 allenamenti con statistiche diverse
        Allenamento allenamento1 = new Allenamento(
                LocalDate.now(),
                LocalTime.of(10, 0),
                90,
                "Campo A",
                "Tecnico",
                "Session di tiro e passaggio"
        );

        Allenamento allenamento2 = new Allenamento(
                LocalDate.now().plusDays(1),
                LocalTime.of(11, 0),
                90,
                "Campo B",
                "Fisico",
                "Lavoro aerobico e potenza"
        );
        StatisticaAllenamento stat1 = new StatisticaAllenamento(
                idGiocatore, allenamento1.getId(),
                25, // velocitàMax (bassa)
                160, // frequenzaCardiaca (alta)
                5, // forzaTiro (bassa)
                5, // forzaFisica (bassa)
                18 // velocitàMedia (bassa)
        );

        StatisticaAllenamento stat2 = new StatisticaAllenamento(
                idGiocatore, allenamento2.getId(),
                35, // velocitàMax (alta)
                140, // frequenzaCardiaca (normale)
                7, // forzaTiro (alta)
                7, // forzaFisica (alta)
                22 // velocitàMedia (alta)
        );

        // Aggiungi disponibilità
        Disponibilità disp1 = new Disponibilità(idGiocatore, allenamento1.getId(), true, null, stat1);
        Disponibilità disp2 = new Disponibilità(idGiocatore, allenamento2.getId(), true, null, stat2);

        allenamento1.aggiungiDisponibilità(disp1);
        allenamento2.aggiungiDisponibilità(disp2);

        c.getEventi().add(allenamento1);
        c.getEventi().add(allenamento2);

        // Esecuzione
        Map<String, Boolean> suggerimenti = c.suggerisciSessioneMirata(idGiocatore);

        // Verifiche
        assertNotNull(suggerimenti);
        assertEquals(5, suggerimenti.size());

        assertFalse(suggerimenti.get("allenamento sullo scatto"));
        assertFalse(suggerimenti.get("allenamento sulla resistenza"));
        assertFalse(suggerimenti.get("allenamento sul tiro"));
        assertFalse(suggerimenti.get("allenamento sui pesi"));
        assertFalse(suggerimenti.get("allenamento aerobico"));

        // Test caso senza dati
        assertThrows(IllegalArgumentException.class, () -> {
            c.suggerisciSessioneMirata(999); // ID inesistente
        });
    }

    @Test
    public void testSuggerisciFormazione() {

        List<GiocatoreInRosa> rosa = new ArrayList<>();

        rosa.add(new GiocatoreInRosa(new Giocatore("Luca", "Bianchi", LocalDate.of(1999, 5, 10), "Italia", "luca.bianchi@email.com"), Ruolo.PORTIERE, Status.DISPONIBILE, 1, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Marco", "Rossi", LocalDate.of(2000, 3, 22), "Italia", "marco.rossi@email.com"), Ruolo.PORTIERE, Status.INFORTUNATO, 12, LocalDate.of(2024, 8, 1)));

        rosa.add(new GiocatoreInRosa(new Giocatore("Andrea", "Verdi", LocalDate.of(1998, 8, 14), "Italia", "andrea.verdi@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 2, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Stefano", "Neri", LocalDate.of(2001, 1, 10), "Italia", "stefano.neri@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 3, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Matteo", "Moretti", LocalDate.of(1997, 12, 5), "Italia", "matteo.moretti@email.com"), Ruolo.DIFENSORE, Status.SOSPESO, 4, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Lorenzo", "Ferrari", LocalDate.of(1996, 6, 30), "Italia", "lorenzo.ferrari@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 5, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Paolo", "Gentile", LocalDate.of(1998, 2, 1), "Italia", "paolo.gentile@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 6, LocalDate.of(2024, 8, 1)));

        rosa.add(new GiocatoreInRosa(new Giocatore("Giorgio", "Rinaldi", LocalDate.of(1995, 11, 11), "Italia", "giorgio.rinaldi@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 7, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Francesco", "Esposito", LocalDate.of(1999, 4, 9), "Italia", "francesco.esposito@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 8, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Davide", "Caputo", LocalDate.of(2002, 10, 3), "Italia", "davide.caputo@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 9, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Simone", "De Luca", LocalDate.of(1997, 9, 18), "Italia", "simone.deluca@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 10, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Alessandro", "Fiore", LocalDate.of(2000, 1, 25), "Italia", "alessandro.fiore@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 11, LocalDate.of(2024, 8, 1)));

        rosa.add(new GiocatoreInRosa(new Giocatore("Federico", "Giuliani", LocalDate.of(2001, 3, 19), "Italia", "federico.giuliani@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 14, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Nicola", "Sartori", LocalDate.of(1996, 7, 27), "Italia", "nicola.sartori@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 15, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Edoardo", "Marino", LocalDate.of(1998, 5, 2), "Italia", "edoardo.marino@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 16, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Luca", "Serra", LocalDate.of(1995, 10, 13), "Italia", "luca.serra@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 17, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Tommaso", "Bianchi", LocalDate.of(1999, 11, 20), "Italia", "tommaso.bianchi@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 18, LocalDate.of(2024, 8, 1)));

        rosa.add(new GiocatoreInRosa(new Giocatore("Gabriele", "Martini", LocalDate.of(2003, 6, 12), "Italia", "gabriele.martini@email.com"), Ruolo.CENTROCAMPISTA, Status.DISPONIBILE, 19, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Riccardo", "Pellegrini", LocalDate.of(1996, 2, 6), "Italia", "riccardo.pellegrini@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 20, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Christian", "Conti", LocalDate.of(1997, 8, 15), "Italia", "christian.conti@email.com"), Ruolo.DIFENSORE, Status.DISPONIBILE, 21, LocalDate.of(2024, 8, 1)));
        rosa.add(new GiocatoreInRosa(new Giocatore("Antonio", "Greco", LocalDate.of(2000, 12, 1), "Italia", "antonio.greco@email.com"), Ruolo.ATTACCANTE, Status.DISPONIBILE, 22, LocalDate.of(2024, 8, 1)));

// Allenamenti
        Allenamento a1 = new Allenamento(LocalDate.of(2024, 9, 2), LocalTime.of(17, 0), 90, "Campo A", "Tattico", "Prove di pressing alto");
        Allenamento a2 = new Allenamento(LocalDate.of(2024, 9, 5), LocalTime.of(18, 0), 90, "Campo A", "Tecnico", "Finalizzazioni e calci piazzati");
        Allenamento a3 = new Allenamento(LocalDate.of(2024, 9, 8), LocalTime.of(17, 0), 90, "Campo B", "Atletico", "Lavoro su resistenza e velocità");
        Allenamento a4 = new Allenamento(LocalDate.of(2024, 9, 12), LocalTime.of(18, 0), 90, "Campo B", "Tattico", "Costruzione dal basso");
        Allenamento a5 = new Allenamento(LocalDate.of(2024, 9, 15), LocalTime.of(17, 30), 90, "Campo A", "Tecnico", "Controllo palla e passaggi rapidi");

// Amichevoli
        Amichevole am1 = new Amichevole(LocalDate.of(2024, 9, 3), LocalTime.of(19, 0), 90, "Stadio Comunale", "Real Rocca");
        Amichevole am2 = new Amichevole(LocalDate.of(2024, 9, 9), LocalTime.of(20, 0), 90, "Stadio B", "Virtus Leonzio");
        Amichevole am3 = new Amichevole(LocalDate.of(2024, 9, 16), LocalTime.of(18, 30), 90, "Stadio A", "Polisportiva Etna");

// Inserimento nel calendario
        c.getEventi().add(a1);
        c.getEventi().add(a2);
        c.getEventi().add(a3);
        c.getEventi().add(a4);
        c.getEventi().add(a5);

        c.getEventi().add(am1);
        c.getEventi().add(am2);
        c.getEventi().add(am3);

// Lista delle disponibilità da aggiungere agli eventi
        for (Evento evento : c.getEventi()) {
            if (evento instanceof Amichevole) {
                for (GiocatoreInRosa g : rosa) {
                    int idGiocatore = g.getGiocatore().getId();
                    int idEvento = evento.getId();

                    // Crea una statistica fittizia
                    StatisticaAmichevole stat = new StatisticaAmichevole(
                            idGiocatore,
                            idEvento,
                            60 + (int) (Math.random() * 31), // minuti giocati tra 60 e 90
                            (int) (Math.random() * 2), // goal
                            0,
                            (int) (Math.random() * 2), // gialli
                            0,
                            8 + (float) (Math.random() * 4), // distanza 8–12 km
                            (int) (Math.random() * 4),
                            (int) (Math.random() * 3),
                            (g.getRuolo() == Ruolo.PORTIERE) ? (int) (Math.random() * 5) : 0,
                            (g.getRuolo() == Ruolo.DIFENSORE) ? (int) (Math.random() * 6) : 0,
                            (g.getRuolo() == Ruolo.CENTROCAMPISTA) ? (int) (Math.random() * 5) : 0,
                            (int) (Math.random() * 6)
                    );

                    // Crea disponibilità con presenza = true e assegna la statistica
                    Disponibilità disp = new Disponibilità(idGiocatore, idEvento, true, null);
                    disp.setStatistica(stat);  // ipotizzando che ci sia un setter per la statistica

                    // Aggiungiamo la disponibilità all’evento
                    evento.getDisponibilità().add(disp);

                }
            }
        }

        System.out.println(c.suggerisciFormazione("4-3-3", rosa));

        List<GiocatoreInRosa> formazione = new ArrayList<>();
        formazione = c.suggerisciFormazione("4-3-3", rosa);
        assertEquals(11, formazione.size());

    }

    @Test
    public void testConteggiaEventiFuturi() {
        // Svuoto gli eventi esistenti per un test pulito
        c.getEventi().clear();

        // Data corrente
        LocalDate oggi = LocalDate.now();

        // Creo eventi passati e futuri
        c.getEventi().add(new Allenamento(oggi.minusDays(1), LocalTime.NOON, 90, "Campo A", "fisico", "test"));
        c.getEventi().add(new Amichevole(oggi.minusDays(1), LocalTime.NOON, 90, "Campo B", "Team A"));

        c.getEventi().add(new Allenamento(oggi.plusDays(1), LocalTime.NOON, 90, "Campo A", "tattico", "test futuro"));
        c.getEventi().add(new Allenamento(oggi.plusDays(2), LocalTime.NOON, 90, "Campo B", "tecnico", "test futuro 2"));
        c.getEventi().add(new Amichevole(oggi.plusDays(3), LocalTime.NOON, 90, "Campo C", "Team B"));

        // Esecuzione metodo
        Map<String, Integer> risultato = c.conteggiaEventiFuturi();

        assertEquals(2, risultato.get("allenamentiFuturi").intValue());
        assertEquals(1, risultato.get("amichevoliFuture").intValue());
    }

    @Test
    public void testCalcolaMediaPresenzeAssenzeMeseCorrente() {
        // Svuoto eventi
        c.getEventi().clear();

        // Data di oggi
        LocalDate oggi = LocalDate.now();
        int idGiocatore = gr1.getGiocatore().getId();

        // Creo evento nel mese corrente
        Allenamento eventoCorrente = new Allenamento(oggi, LocalTime.NOON, 90, "Campo X", "fisico", "sessione test");
        Disponibilità pres = new Disponibilità(idGiocatore, eventoCorrente.getId(), true, null);
        Disponibilità ass = new Disponibilità(idGiocatore + 1, eventoCorrente.getId(), false, null);
        eventoCorrente.getDisponibilità().add(pres);
        eventoCorrente.getDisponibilità().add(ass);

        // Aggiungo evento
        c.getEventi().add(eventoCorrente);

        // Esecuzione metodo
        Map<String, Double> risultato = c.calcolaMediaPresenzeAssenzeMeseCorrente();

        // Verifica (1 presenza e 1 assenza => 50% ciascuna)
        assertEquals(50.0, risultato.get("presenze"), 0.01);
        assertEquals(50.0, risultato.get("assenze"), 0.01);
    }
}
