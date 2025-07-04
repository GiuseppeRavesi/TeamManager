
package controller;

import java.util.ArrayList;
import java.util.List;
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

}
