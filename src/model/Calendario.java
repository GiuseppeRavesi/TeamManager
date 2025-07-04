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

    public boolean pianificaAllenamento(LocalDate data, LocalTime orario,
            int durata, String luogo, String tipologia, String note) {
        if (verificaSovrapposizione(data, orario, durata, luogo)) {
            return false;
        }
        Allenamento allenamento = new Allenamento(data, orario, durata, luogo, tipologia, note);
        listaEventi.add(allenamento);
        return true;
    }

    public boolean pianificaAmichevole(LocalDate data, LocalTime orario,
            int durata, String luogo, String squadraAvversaria) {
        if (verificaSovrapposizione(data, orario, durata, luogo)) {
            return false;
        }
        Amichevole amichevole = new Amichevole(data, orario, durata, luogo, squadraAvversaria);
        listaEventi.add(amichevole);
        return true;
    }

    public boolean aggiornaEvento(Evento eventoSelezionato, LocalDate nuovaData, LocalTime nuovoOrario,
            int nuovaDurata, String nuovoLuogo, Map<String, String> campiSpecifici) {

        // Controllo conflitti con altri eventi
        for (Evento e : listaEventi) {
            // Salta se sto controllando l'evento stesso
            if (e.equals(eventoSelezionato)) {
                continue;
            }

            if (e.getLuogo().equalsIgnoreCase(nuovoLuogo) && e.getData().equals(nuovaData)) {

                LocalTime inizioEsistente = e.getOrario();
                LocalTime fineEsistente = inizioEsistente.plusMinutes(e.getDurata());

                LocalTime nuovoInizio = nuovoOrario;
                LocalTime nuovoFine = nuovoOrario.plusMinutes(nuovaDurata);

                boolean sovrapposto = !(nuovoFine.isBefore(inizioEsistente) || nuovoInizio.isAfter(fineEsistente));
            
                if (sovrapposto) {
                    System.out.println("Conflitto di orario con un altro evento esistente!");
                    return false; 
                }
            }
        }

        // Se non ci sono conflitti, aggiorno i dati
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

        return true; 
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
    
    private boolean verificaSovrapposizione(LocalDate data, LocalTime orarioInizio, int durata, String luogo) {
    if (listaEventi.isEmpty()) {
        return false; 
    }

    LocalTime inizioNuovo = orarioInizio;
    LocalTime fineNuovo = orarioInizio.plusMinutes(durata);

    for (Evento evento : listaEventi) {
        if (!evento.getData().equals(data)) continue; 
        if (!evento.getLuogo().equalsIgnoreCase(luogo)) continue; 

        LocalTime inizioEsistente = evento.getOrario();
        LocalTime fineEsistente = inizioEsistente.plusMinutes(evento.getDurata());

        boolean sovrapposto = inizioNuovo.isBefore(fineEsistente) && inizioEsistente.isBefore(fineNuovo);
        if (sovrapposto) {
            return true; 
        }
    }

    return false; 
}
}
