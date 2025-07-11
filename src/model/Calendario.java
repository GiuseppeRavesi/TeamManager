package model;

import controller.Session;
import exception.SovrapposizioneEventoException;
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

    public void pianificaAllenamento(LocalDate data, LocalTime orario,
            int durata, String luogo, String tipologia, String note) throws SovrapposizioneEventoException{
        verificaSovrapposizione(data, orario, durata, luogo); 
         
        Allenamento allenamento = new Allenamento(data, orario, durata, luogo, tipologia, note);
        listaEventi.add(allenamento);
    }

    public boolean pianificaAmichevole(LocalDate data, LocalTime orario,
            int durata, String luogo, String squadraAvversaria) throws SovrapposizioneEventoException{
        verificaSovrapposizione(data, orario, durata, luogo);
        Amichevole amichevole = new Amichevole(data, orario, durata, luogo, squadraAvversaria);
        listaEventi.add(amichevole);
        return true;
    }

    public void aggiornaEvento(Evento eventoSelezionato, LocalDate nuovaData, LocalTime nuovoOrario,
            int nuovaDurata, String nuovoLuogo, Map<String, String> campiSpecifici) throws SovrapposizioneEventoException {

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
                    throw new SovrapposizioneEventoException("Sovrapposizione: esiste già un evento"); 
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
    
    private void verificaSovrapposizione(LocalDate data, LocalTime orarioInizio, 
            int durata, String luogo) throws SovrapposizioneEventoException {
    if (listaEventi.isEmpty()) {
        return; // Nessun conflitto
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
            throw new SovrapposizioneEventoException(
                "Sovrapposizione: esiste già un evento a " + luogo 
                        + " il " + data + " dalle " + inizioEsistente + " alle " + fineEsistente);
        }
    }
}

}
