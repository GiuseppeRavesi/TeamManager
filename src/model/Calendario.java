package model;

import controller.Session;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calendario {

    private List<Evento> listaEventi;

    public Calendario() {
        this.listaEventi = new ArrayList<>();
    }

    //NOTA BENE:In iterazione 2, verificare la NON sovrapposizione allenamenti (luogo,data)
    public void pianificaAllenamento(LocalDate data, LocalTime orario,
            int durata, String luogo, String tipologia, String note) {
        Allenamento allenamento = new Allenamento(data, orario, durata, luogo, tipologia, note);
        listaEventi.add(allenamento);
    }

    //NOTA BENE:In iterazione 2, verificare la NON sovrapposizione amichevoli (luogo,data)
    public void pianificaAmichevole(LocalDate data, LocalTime orario,
            int durata, String luogo, String squadraAvversaria) {
        Amichevole amichevole = new Amichevole(data, orario, durata, luogo, squadraAvversaria);
        listaEventi.add(amichevole);
    }

    //NOTA BENE:In iterazione 2, verificare la NON sovrapposizione eventi (luogo,data)
    public void aggiornaEvento(Evento eventoSelezionato, LocalDate nuovaData, LocalTime nuovoOrario,
            int nuovaDurata, String nuovoLuogo, Map<String, String> campiSpecifici) {

        eventoSelezionato.setData(nuovaData);
        eventoSelezionato.setOrario(nuovoOrario);
        eventoSelezionato.setDurata(nuovaDurata);
        eventoSelezionato.setLuogo(nuovoLuogo);

        if (eventoSelezionato instanceof Allenamento) {
            Allenamento a = (Allenamento) eventoSelezionato;
            a.setTipologia(campiSpecifici.getOrDefault("tipologia", a.getTipologia()));
            a.setNote(campiSpecifici.getOrDefault("note", a.getNote()));
        } else if (eventoSelezionato instanceof Amichevole) {
            Amichevole am = (Amichevole) eventoSelezionato;
            am.setSquadraAvversaria(campiSpecifici.getOrDefault("squadraAvversaria", am.getSquadraAvversaria()));
        }
    }

    public void rimuoviEvento(Evento eventoSelezionato) {
        if (!listaEventi.isEmpty()) {
            listaEventi.remove(eventoSelezionato);
        }
    }

    public List<Evento> getEventi() {
        return new ArrayList<>(listaEventi);
    }

    public void aggiungiDisponibilità(Evento eventoSelezionato, boolean presenza, String motivazione) {
        int idGiocatore = Session.getInstance().getUtenteLoggato().getId();

        String motivazioneFinale = presenza ? null : motivazione;

        Disponibilità nuovaDisponibilità = new Disponibilità(idGiocatore, eventoSelezionato.getId(), presenza, motivazioneFinale);

        eventoSelezionato.aggiungiDisponibilità(nuovaDisponibilità);
    }
}
