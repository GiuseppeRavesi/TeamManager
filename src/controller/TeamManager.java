package controller;

import exception.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Calendario;
import model.Disponibilità;
import model.Evento;
import model.Giocatore;
import model.GiocatoreInRosa;
import model.Rosa;
import model.Statistica;
import model.Utente;
import model.enums.Ruolo;
import model.enums.Status;

/**
 *
 * @author Giuseppe Ravesi
 */
public class TeamManager {

    private static TeamManager instance;
    private final Rosa r = new Rosa();
    private final Calendario c = new Calendario();
    private List<Giocatore> listaGiocatori = new ArrayList<>();
    private List<Utente> listaUtenti = new ArrayList<>();

    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }
    
    public void inizializzaDatiDaPersistence(PersistenceHandler handler) {
    this.listaGiocatori = handler.getListaGiocatori();
    this.r.setGiocatoriRosa(handler.getRosa());
    this.c.setListaEventi(handler.getListaEventi());
    this.listaUtenti = handler.getListaUtenti();
}

    // UC1: Gestisci Rosa
    public void aggiungiGiocatoreRosa(Giocatore giocatoreSelezionato, Ruolo ruolo, Status status, int numMaglia)
            throws RosaCompletaException, NumeroMagliaDuplicatoException, GiocatoreDuplicatoException {
        r.aggiungiGiocatore(giocatoreSelezionato, ruolo, status, numMaglia);
    }

    public boolean rimuoviGiocatoreDaRosa(GiocatoreInRosa g) {
        return r.rimuoviGiocatore(g);
    }

    public void modificaGiocatore(Giocatore giocatoreBase, Ruolo nuovoRuolo, Status nuovoStatus, int numMaglia)
            throws NumeroMagliaDuplicatoException {
        r.modificaGiocatore(giocatoreBase, nuovoRuolo, nuovoStatus, numMaglia);
    }

    public List<GiocatoreInRosa> cercaGiocatoriRosa(String filtro) {
        return r.cercaGiocatori(filtro);
    }

    public List<GiocatoreInRosa> visualizzaRosa() {
        return r.getGiocatori();
    }

    //UC2: Gestisci Evento
    public List<Evento> visualizzaCalendario() {
        return c.getEventi();
    }

    public void pianificaAmichevole(LocalDate data, LocalTime orario, int durata,
            String luogo, String squadraAvversaria) throws SovrapposizioneEventoException {
        c.pianificaAmichevole(data, orario, durata, luogo, squadraAvversaria);
    }

    public void pianificaAllenamento(LocalDate data, LocalTime orario, int durata,
            String luogo, String tipologia, String note) throws SovrapposizioneEventoException {
        c.pianificaAllenamento(data, orario, durata, luogo, tipologia, note);
    }

    public void aggiornaEvento(Evento eventoSelezionato, LocalDate nuovaData,
            LocalTime nuovoOrario, int nuovaDurata,
            String nuovoLuogo, Map<String, String> campiSpecifici) throws SovrapposizioneEventoException {
        c.aggiornaEvento(eventoSelezionato, nuovaData, nuovoOrario,
                nuovaDurata, nuovoLuogo, campiSpecifici);
    }

    public void rimuoviEvento(Evento eventoSelezionato) {
        c.rimuoviEvento(eventoSelezionato);
    }

    //UC3: Gestisci Disponibilità
    public void comunicaDisponibilita(Evento eventoSelezionato, boolean presenza, String motivazione) throws DisponibilitaEventoPassatoException{
        System.out.println("sono comunica disponilita");
        c.aggiungiDisponibilità(eventoSelezionato, presenza, motivazione);
    }

    public List<Disponibilità> visualizzaDisponibilitàGiocatore(int idGiocatoreRosa) {
        System.out.println("Sono visualizza disp di TM");
        return c.visualizzaDisponibilitàGiocatore(idGiocatoreRosa);
    }

    //UC4 - Analizza Andamento Rosa
    
    public Map<String, Integer> conteggiaEventiFuturi(){
        return c.conteggiaEventiFuturi();
    }
    
    public Map<String, Double> calcolaMediaPresenzeAssenzeMeseCorrente(){
        System.out.println("Sono tm calcola presenze e assenze");
        return c.calcolaMediaPresenzeAssenzeMeseCorrente();
    }
    
    public Map<String, Integer> contaGiocatoriPerStatus() {
    Map<String, Integer> conteggio = new HashMap<>();
    conteggio.put("DISPONIBILE", 0);
    conteggio.put("INFORTUNATO", 0);
    conteggio.put("SOSPESO", 0);

    for (GiocatoreInRosa g : r.getGiocatori()) {
        String stato = g.getStatus().name();
        conteggio.put(stato, conteggio.get(stato) + 1);
    }

    return conteggio;
}
    public Map<String, Double> calcolaMediaStatisticaRosa(){
        return c.calcolaMediaStatisticaRosa(r.getGiocatori());
    }
    
    //UC6 - Gestisci Progressi Giocatore
    public void aggiungiStatisticaAllenamento(int idGiocatore, int idEvento,
            Map<String, String> campiSpecifici) throws IllegalArgumentException {
        
        System.out.println("Statistica allenamento TM");
        c.aggiungiStatisticaAllenamento(idGiocatore, idEvento, campiSpecifici);
    }

    public void aggiungiStatisticaAmichevole(int idGiocatore, int idEvento,
            Map<String, String> campiSpecifici) throws IllegalArgumentException {
        System.out.println("Statistica allenamento TM v2");
        c.aggiungiStatisticaAmichevole(idGiocatore, idEvento, campiSpecifici);
    }

    public void rimuoviStatistica(int idGiocatore, int idEvento) {
        c.rimuoviStatistica(idGiocatore, idEvento);
    }

    public Statistica visualizzaStoricoGiocatore(int idGiocatore, int idEvento) {
        return c.visualizzaStoricoGiocatore(idGiocatore, idEvento);
        //gestire lato GUI il caso in cui la statistica == null, magari stampare un avvertimento
    }

    //UC7: Gestisci Giocatore CRUD
    public boolean creaGiocatore(String nome, String cognome, LocalDate dataNascita,
            String nazionalità, String email) {
        for (Giocatore g : listaGiocatori) {
            if (g.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        Giocatore nuovoGiocatore = new Giocatore(nome, cognome, dataNascita,
                nazionalità, email);
        listaGiocatori.add(nuovoGiocatore);
        return true;
    }

    public boolean eliminaGiocatore(Giocatore giocatore) {
        return listaGiocatori.remove(giocatore);
    }

    public List<Giocatore> cercaGiocatori(String filtro) {
        List<Giocatore> risultati = new ArrayList<>();
        String filtroLower = filtro.toLowerCase();

        for (Giocatore g : listaGiocatori) {
            String nome = g.getNome().toLowerCase();
            String cognome = g.getCognome().toLowerCase();

            if (nome.contains(filtroLower) || cognome.contains(filtroLower)) {
                risultati.add(g);
            }
        }
        return risultati;
    }

    public List<Giocatore> getListaGiocatori() {
        return listaGiocatori;
    }
    
    //UC8: Suggerisci Formazione
    public List<GiocatoreInRosa> suggerisciFormazione (String modulo) throws IllegalArgumentException{
        System.out.println("Sono suggerisciFormazione");
        return c.suggerisciFormazione(modulo, r.getGiocatori());
    }
    
    //UC9: Suggerisci Sessione Mirata

    public Map<String, Boolean> suggerisciSessioneMirata(int idGiocatore) throws IllegalArgumentException{
        return c.suggerisciSessioneMirata(idGiocatore);
    }

    //UC10 - Confronta Giocatore
    public Map<String, Map<String, Number>> confrontaGiocatori(GiocatoreInRosa g1, GiocatoreInRosa g2) {
        return c.confrontaGiocatori(g1, g2);
    }

    protected Rosa getR() {
        return r;
    }

    protected Calendario getC() {
        return c;
    }

    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }
    
    
}
