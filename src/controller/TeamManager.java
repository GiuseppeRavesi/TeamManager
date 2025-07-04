
package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Calendario;
import model.Evento;
import model.Giocatore;
import model.GiocatoreInRosa;
import model.Rosa;

/**
 *
 * @author Giuseppe Ravesi
 */
public class TeamManager {
    private static TeamManager instance;
    private Rosa r = new Rosa();
    private Calendario c = new Calendario();
    private List<Giocatore> listaGiocatori = new ArrayList<>();
    
    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }
    
    // UC1: Gestisci Rosa
    public boolean aggiungiGiocatoreRosa(Giocatore giocatoreSelezionato, String ruolo, String status) {
        return r.aggiungiGiocatore(giocatoreSelezionato, ruolo, status);
    }

    public void rimuoviGiocatoreDaRosa(GiocatoreInRosa g) {
        r.rimuoviGiocatore(g);
    }

    public boolean modificaGiocatore(Giocatore giocatoreBase, String nuovoRuolo, String nuovoStatus) {
        return r.modificaGiocatore(giocatoreBase, nuovoRuolo, nuovoStatus);
    }

    public List<GiocatoreInRosa> cercaGiocatoriRosa(String filtro) {
        return r.cercaGiocatori(filtro);
    }
    
    public List<GiocatoreInRosa> visualizzaRosa(){
        return r.getGiocatori();
    }
    
    //UC2: Gestisci Evento
    
    public List<Evento> visualizzaCalendario(){
        return c.getEventi();
    }
    
    public void pianificaAmichevole(LocalDate data, LocalTime orario, int durata,
            String luogo, String squadraAvversaria) {
        c.pianificaAmichevole(data, orario, durata, luogo, squadraAvversaria);
    }

    public void pianificaAllenamento(LocalDate data, LocalTime orario, int durata,
            String luogo, String tipologia, String note) {
        c.pianificaAllenamento(data, orario, durata, luogo, tipologia, note);
    }

    public void aggiornaEvento(Evento eventoSelezionato, LocalDate nuovaData,
            LocalTime nuovoOrario, int nuovaDurata,
            String nuovoLuogo, Map<String, String> campiSpecifici) {
        c.aggiornaEvento(eventoSelezionato, nuovaData, nuovoOrario,
                nuovaDurata, nuovoLuogo, campiSpecifici);
    }

    public void rimuoviEvento(Evento eventoSelezionato) {
        c.rimuoviEvento(eventoSelezionato);
    }
    
    //UC3: Gestisci Disponibilità
    
    public void aggiungiDisponibilita(Evento eventoSelezionato, boolean presenza, String motivazione) {
        c.aggiungiDisponibilità(eventoSelezionato, presenza, motivazione);
    }
    
    
    
}
