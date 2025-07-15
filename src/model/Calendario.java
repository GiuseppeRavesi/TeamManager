package model;

import controller.Session;
import exception.SovrapposizioneEventoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendario {

    private List<Evento> listaEventi;

    public Calendario() {
        this.listaEventi = new ArrayList<>();
    }

    public void pianificaAllenamento(LocalDate data, LocalTime orario,
            int durata, String luogo, String tipologia, String note) throws SovrapposizioneEventoException {

        verificaSovrapposizione(data, orario, durata, luogo);

        Allenamento allenamento = new Allenamento(data, orario, durata, luogo, tipologia, note);
        listaEventi.add(allenamento);
    }

    public void pianificaAmichevole(LocalDate data, LocalTime orario,
            int durata, String luogo, String squadraAvversaria) throws SovrapposizioneEventoException {
        verificaSovrapposizione(data, orario, durata, luogo);
        Amichevole amichevole = new Amichevole(data, orario, durata, luogo, squadraAvversaria);
        listaEventi.add(amichevole);
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
        return listaEventi;
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
            if (!evento.getData().equals(data)) {
                continue;
            }
            if (!evento.getLuogo().equalsIgnoreCase(luogo)) {
                continue;
            }

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

    public void aggiungiStatisticaAllenamento(int idGiocatore, int idEvento,
            Map<String, String> campiSpecifici) throws IllegalArgumentException {

        Evento eventoTrovato = null;
        for (Evento e : listaEventi) {
            if (e.getId() == idEvento) {
                eventoTrovato = e;
                break;
            }
        }
        if (eventoTrovato == null) {
            throw new IllegalArgumentException("Evento non trovato");
        }

        Disponibilità disponibilitàTrovata = null;
        for (Disponibilità d : eventoTrovato.getDisponibilità()) {
            if (d.getIdGiocatore() == idGiocatore) {
                disponibilitàTrovata = d;
                break;
            }
        }
        if (disponibilitàTrovata == null) {
            throw new IllegalArgumentException("Disponibilità non trovata");
        }

        StatisticaAllenamento sa = new StatisticaAllenamento(
                idGiocatore,
                idEvento,
                Float.parseFloat(campiSpecifici.get("velocitàMax")),
                Float.parseFloat(campiSpecifici.get("velocitàMedia")),
                Integer.parseInt(campiSpecifici.get("valutazioneForzaFisica")),
                Integer.parseInt(campiSpecifici.get("valutazioneForzaTiro")),
                Integer.parseInt(campiSpecifici.get("frequenzaCardiacaMedia"))
        );

        disponibilitàTrovata.setStatistica(sa);
    }

    public void aggiungiStatisticaAmichevole(int idGiocatore, int idEvento,
            Map<String, String> campiSpecifici) throws IllegalArgumentException {

        Evento eventoTrovato = null;
        for (Evento e : listaEventi) {
            if (e.getId() == idEvento) {
                eventoTrovato = e;
                break;
            }
        }
        if (eventoTrovato == null) {
            throw new IllegalArgumentException("Evento non trovato con id: " + idEvento);
        }

        Disponibilità disponibilitàTrovata = null;
        for (Disponibilità d : eventoTrovato.getDisponibilità()) {
            if (d.getIdGiocatore() == idGiocatore) {
                disponibilitàTrovata = d;
                break;
            }
        }
        if (disponibilitàTrovata == null) {
            throw new IllegalArgumentException("Disponibilità non trovata per giocatore: " + idGiocatore);
        }

        StatisticaAmichevole sa = new StatisticaAmichevole(
                idGiocatore,
                idEvento,
                Integer.parseInt(campiSpecifici.get("minutiGiocati")),
                Integer.parseInt(campiSpecifici.get("goal")),
                Integer.parseInt(campiSpecifici.get("autogoal")),
                Integer.parseInt(campiSpecifici.get("cartelliniGialli")),
                Integer.parseInt(campiSpecifici.get("cartelliniRossi")),
                Integer.parseInt(campiSpecifici.get("distanzaTotalePercorsa")),
                Integer.parseInt(campiSpecifici.get("falliCommessi")),
                Integer.parseInt(campiSpecifici.get("assist")),
                Integer.parseInt(campiSpecifici.get("parate")),
                Integer.parseInt(campiSpecifici.get("intercettiRiusciti")),
                Integer.parseInt(campiSpecifici.get("passaggiChiave")),
                Integer.parseInt(campiSpecifici.get("tiriTotali"))
        );

        disponibilitàTrovata.setStatistica(sa);
    }

    public void rimuoviStatistica(int idGiocatore, int idEvento) throws IllegalArgumentException {

        Evento eventoTrovato = null;
        for (Evento e : listaEventi) {
            if (e.getId() == idEvento) {
                eventoTrovato = e;
                break;
            }
        }
        if (eventoTrovato == null) {
            throw new IllegalArgumentException("Evento non trovato");
        }

        Disponibilità disponibilitàTrovata = null;
        for (Disponibilità d : eventoTrovato.getDisponibilità()) {
            if (d.getIdGiocatore() == idGiocatore) {
                disponibilitàTrovata = d;
                break;
            }
        }
        if (disponibilitàTrovata == null) {
            throw new IllegalArgumentException("Disponibilità non trovata");
        }

        disponibilitàTrovata.rimuoviStatistica();
    }

    public Statistica visualizzaStoricoGiocatore(int idGiocatore, int idEvento) {
        for (Evento e : listaEventi) {
            if (e.getId() == idEvento) {
                for (Disponibilità d : e.getDisponibilità()) {
                    if (d.getIdGiocatore() == idGiocatore) {
                        return d.getStatistica(); //Può essere null
                    }
                }
            }
        }
        return null;
    }
    
    public List<Disponibilità> visualizzaDisponibilitàGiocatore(int idGiocatoreRosa){
        List<Disponibilità> disp;
        disp = new ArrayList<>();
        for (Evento e : listaEventi){
            for(Disponibilità d: e.getDisponibilità())
                if(d.getIdGiocatore() == idGiocatoreRosa)
                    disp.add(d);
        }
        
        return disp;
    }

    //UC10 : CONFRONTA GIOCATORE
    public Map<String, Map<String, Number>> confrontaGiocatori(GiocatoreInRosa g1, GiocatoreInRosa g2) {
        Map<String, Number> stats1 = calcolaAggregati(g1);
        Map<String, Number> stats2 = calcolaAggregati(g2);

        Map<String, Map<String, Number>> confronto = new HashMap<>();
        confronto.put(g1.getGiocatore().getNome() + " " + g1.getGiocatore().getCognome() + " " + g1.getNumMaglia(), stats1);
        confronto.put(g2.getGiocatore().getNome() + " " + g2.getGiocatore().getCognome() + " " + g2.getNumMaglia(), stats2);

        return confronto;
    }

    private Map<String, Number> calcolaAggregati(GiocatoreInRosa giocatore) {
        Map<String, Number> aggregati = new HashMap<>();
        int totalGoal = 0;
        int totalAssist = 0;
        int totalMinuti = 0;
        int totalAutogoal = 0;
        int totalCartelliniGialli = 0;
        int totalCartelliniRossi = 0;
        int totalFalliCommessi = 0;
        int totalIntercetti = 0;
        int totalPassaggiChiave = 0;
        int totalTiriTotali = 0;
        int totalParate = 0;
        float totalDistanza = 0;
        
        for (Evento evento : listaEventi) {
            for (Disponibilità disp : evento.getDisponibilità()) {
                if (disp.getIdGiocatore() == giocatore.getGiocatore().getId() && disp.getStatistica() instanceof StatisticaAmichevole) {
                    StatisticaAmichevole s = (StatisticaAmichevole) disp.getStatistica();
                    totalGoal += s.getGoal();
                    totalAssist += s.getAssist();
                    totalMinuti += s.getMinutiGiocati();
                    totalAutogoal += s.getAutogoal();
                    totalCartelliniGialli += s.getCartelliniGialli();
                    totalCartelliniRossi += s.getCartelliniRossi();
                    totalFalliCommessi += s.getFalliCommessi();
                    totalIntercetti += s.getIntercettiRiusciti();
                    totalPassaggiChiave += s.getPassaggiChiave();
                    totalTiriTotali += s.getTiriTotali();
                    totalParate += s.getParate();
                    totalDistanza += s.getDistanzaTotalePercorsa();
                }
            }
        }
        aggregati.put("goal", totalGoal);
        aggregati.put("assist", totalAssist);
        aggregati.put("minutiGiocati", totalMinuti);
        aggregati.put("autogoal", totalAutogoal);
        aggregati.put("cartelliniGialli", totalCartelliniGialli);
        aggregati.put("cartelliniRossi", totalCartelliniRossi);
        aggregati.put("falliCommessi", totalFalliCommessi);
        aggregati.put("intercettiRiusciti", totalIntercetti);
        aggregati.put("passaggiChiave", totalPassaggiChiave);
        aggregati.put("tiriTotali", totalTiriTotali);
        aggregati.put("parate", totalParate);
        aggregati.put("distanzaTotalePercorsa", totalDistanza);

        return aggregati;
    }
}
