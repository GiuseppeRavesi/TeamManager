
package controller;

import exception.GiocatoreDuplicatoException;
import exception.RosaCompletaException;
import exception.SovrapposizioneEventoException;
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
    private final List<Giocatore> listaGiocatori = new ArrayList<>();
    
    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }
    
    // UC1: Gestisci Rosa
    public void aggiungiGiocatoreRosa(Giocatore giocatoreSelezionato, Ruolo ruolo, Status status) 
            throws GiocatoreDuplicatoException, RosaCompletaException  {
        r.aggiungiGiocatore(giocatoreSelezionato, ruolo, status);
    }

    public void rimuoviGiocatoreDaRosa(GiocatoreInRosa g) {
        r.rimuoviGiocatore(g);
    }

    public boolean modificaGiocatore(Giocatore giocatoreBase, Ruolo nuovoRuolo, Status nuovoStatus) {
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
    
    public void comunicaDisponibilita(Evento eventoSelezionato, boolean presenza, String motivazione) {
        c.aggiungiDisponibilità(eventoSelezionato, presenza, motivazione);
    }
    
    //UC7: Gestisci Giocatore CRUD
    public boolean creaGiocatore(String nome, String cognome, int numMaglia,LocalDate dataNascita, 
            String nazionalità, String email) {
        for (Giocatore g : listaGiocatori) {
            if (g.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        Giocatore nuovoGiocatore = new Giocatore(nome, cognome, numMaglia,
                dataNascita, nazionalità, email);
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
    
}
