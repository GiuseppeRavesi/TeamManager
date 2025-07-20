package model;

import controller.Session;
import exception.DisponibilitaEventoPassatoException;
import exception.SovrapposizioneEventoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.enums.Ruolo;

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

    public void setListaEventi(List<Evento> listaEventi) {
        this.listaEventi = listaEventi;
    }
    
    public void aggiungiDisponibilità(Evento eventoSelezionato, boolean presenza, String motivazione) throws DisponibilitaEventoPassatoException {
        
        if(eventoSelezionato.getData().isBefore(LocalDate.now())){
            throw new DisponibilitaEventoPassatoException();
        }
        
        int idGiocatore = Session.getInstance().getUtenteLoggato().getId();
        System.out.println("Aggiungi disp id user: "+idGiocatore);

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

        
        System.out.println("Statistica allenamento aggiunta");
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
                Float.parseFloat(campiSpecifici.get("distanzaTotalePercorsa")),
                Integer.parseInt(campiSpecifici.get("falliCommessi")),
                Integer.parseInt(campiSpecifici.get("assist")),
                Integer.parseInt(campiSpecifici.get("parate")),
                Integer.parseInt(campiSpecifici.get("intercettiRiusciti")),
                Integer.parseInt(campiSpecifici.get("passaggiChiave")),
                Integer.parseInt(campiSpecifici.get("tiriTotali"))
        );

        System.out.println("Inserimento statistiche amichevole riuscito");
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

    public List<Disponibilità> visualizzaDisponibilitàGiocatore(int idGiocatoreRosa) {
        List<Disponibilità> disp;
        disp = new ArrayList<>();
        for (Evento e : listaEventi) {
            for (Disponibilità d : e.getDisponibilità()) {
                if (d.getIdGiocatore() == idGiocatoreRosa && d.isPresenza()) {
                    
                    System.out.println("Sono visualizza disp di calendario");
                    disp.add(d);
                }
            }
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

    public Map<String, Boolean> suggerisciSessioneMirata(int idGiocatore) throws IllegalArgumentException{
        float sommaVelocitaMax = 0;
        float sommaFrequenzaCardiaca = 0;
        float sommaForzaTiro = 0;
        float sommaForzaFisica = 0;
        float sommaVelocitaMedia = 0;

        int count = 0;

        for (Evento evento : listaEventi) {
            if (evento instanceof Allenamento) {
                for (Disponibilità disp : evento.getDisponibilità()) {
                    if (disp.getIdGiocatore() == idGiocatore && disp.getStatistica() instanceof StatisticaAllenamento) {
                        StatisticaAllenamento sa = (StatisticaAllenamento) disp.getStatistica();

                        sommaVelocitaMax += sa.getVelocitàMax();
                        sommaFrequenzaCardiaca += sa.getFrequenzaCardiacaMedia();
                        sommaForzaTiro += sa.getValutazioneForzaTiro();
                        sommaForzaFisica += sa.getValutazioneForzaFisica();
                        sommaVelocitaMedia += sa.getVelocitàMedia();
                        count++;
                    }
                }
            }
        }

        Map<String, Boolean> suggerimenti = new HashMap<>();

        if (count == 0) {
            throw new IllegalArgumentException("Il giocatore non possiede statistiche.\nImpossibile suggerire una sessione mirata");
        }

        float mediaVelocitaMax = sommaVelocitaMax / count;
        float mediaFrequenzaCardiaca = sommaFrequenzaCardiaca / count;
        float mediaForzaTiro = sommaForzaTiro / count;
        float mediaForzaFisica = sommaForzaFisica / count;
        float mediaVelocitaMedia = sommaVelocitaMedia / count;

        suggerimenti.put("allenamento sullo scatto", mediaVelocitaMax < 30);
        suggerimenti.put("allenamento sulla resistenza", mediaFrequenzaCardiaca > 150);
        suggerimenti.put("allenamento sul tiro", mediaForzaTiro < 6);
        suggerimenti.put("allenamento sui pesi", mediaForzaFisica < 6);
        suggerimenti.put("allenamento aerobico", mediaVelocitaMedia < 20);

        return suggerimenti;
    }

    public List<GiocatoreInRosa> suggerisciFormazione(String modulo, List<GiocatoreInRosa> rosa) throws IllegalArgumentException{
        String[] numeri = modulo.split("-");
        int numDif = Integer.parseInt(numeri[0]);
        int numCen = Integer.parseInt(numeri[1]);
        int numAtt = Integer.parseInt(numeri[2]);
        int numPor = 1;
        
        if(rosa.size() < 22){
            throw new IllegalArgumentException("Rosa Incompleta, impossibile suggerire formazione");
        }

        List<GiocatoreInRosa> suggeriti = new ArrayList<>();

        List<GiocatoreInRosa> portieri = rosa.stream()
                .filter(g -> g.getRuolo() == Ruolo.PORTIERE)
                .sorted((g1, g2) -> Integer.compare(
                calcolaAggregati(g2).get("parate").intValue(),
                calcolaAggregati(g1).get("parate").intValue()
        ))
                .limit(numPor)
                .toList();
        suggeriti.addAll(portieri);

        List<GiocatoreInRosa> difensori = rosa.stream()
                .filter(g -> g.getRuolo() == Ruolo.DIFENSORE)
                .sorted((g1, g2) -> Integer.compare(
                calcolaAggregati(g2).get("intercettiRiusciti").intValue(),
                calcolaAggregati(g1).get("intercettiRiusciti").intValue()
        ))
                .limit(numDif)
                .toList();
        suggeriti.addAll(difensori);

        List<GiocatoreInRosa> centrocampisti = rosa.stream()
                .filter(g -> g.getRuolo() == Ruolo.CENTROCAMPISTA)
                .sorted((g1, g2) -> Integer.compare(
                calcolaAggregati(g2).get("passaggiChiave").intValue(),
                calcolaAggregati(g1).get("passaggiChiave").intValue()
        ))
                .limit(numCen)
                .toList();
        suggeriti.addAll(centrocampisti);

        List<GiocatoreInRosa> attaccanti = rosa.stream()
                .filter(g -> g.getRuolo() == Ruolo.ATTACCANTE)
                .sorted((g1, g2) -> Integer.compare(
                calcolaAggregati(g2).get("goal").intValue(),
                calcolaAggregati(g1).get("goal").intValue()
        ))
                .limit(numAtt)
                .toList();
        suggeriti.addAll(attaccanti);

        return suggeriti;
    }

    public Map<String, Integer> conteggiaEventiFuturi() {
        int allenamentiFuturi = 0;
        int amichevoliFuture = 0;

        LocalDate oggi = LocalDate.now();

        for (Evento e : listaEventi) {
            if (e.getData().isAfter(oggi)) {
                if (e instanceof Allenamento) {
                    allenamentiFuturi++;
                } else if (e instanceof Amichevole) {
                    amichevoliFuture++;
                }
            }
        }

        Map<String, Integer> conteggio = new HashMap<>();
        conteggio.put("allenamentiFuturi", allenamentiFuturi);
        conteggio.put("amichevoliFuture", amichevoliFuture);

        return conteggio;
    }

    public Map<String, Double> calcolaMediaPresenzeAssenzeMeseCorrente() {
        LocalDate oggi = LocalDate.now();
        int meseCorrente = oggi.getMonthValue();
        int annoCorrente = oggi.getYear();

        int totalePresenze = 0;
        int totaleAssenze = 0;

        for (Evento e : listaEventi) {
            if (e.getData().getMonthValue() == meseCorrente && e.getData().getYear() == annoCorrente) {
                for (Disponibilità d : e.getDisponibilità()) {
                    if (d.isPresenza()) {
                        totalePresenze++;
                    } else {
                        totaleAssenze++;
                    }
                }
            }
        }

        int totaleDisponibilità = totalePresenze + totaleAssenze;

        double percentualePresenze = 0.0;
        double percentualeAssenze = 0.0;

        if (totaleDisponibilità > 0) {
            percentualePresenze = ((double) totalePresenze / totaleDisponibilità) * 100;
            percentualeAssenze = ((double) totaleAssenze / totaleDisponibilità) * 100;
        }

        Map<String, Double> risultato = new HashMap<>();
        risultato.put("presenze", percentualePresenze);
        risultato.put("assenze", percentualeAssenze);

        return risultato;
    }

    public Map<String, Double> calcolaMediaStatisticaRosa(List<GiocatoreInRosa> rosa) {
        double mediaGoal = 0;
        double mediaAssist = 0;
        double mediaGialli = 0;
        double mediaRossi = 0;
        double mediaFalli = 0;

        int giocatoriConsiderati = 0;

        for (GiocatoreInRosa g : rosa) {
            int sommaGoal = 0, sommaAssist = 0, sommaGialli = 0, sommaRossi = 0, sommaFalli = 0;
            int count = 0;

            for (Evento evento : listaEventi) {
                for (Disponibilità d : evento.getDisponibilità()) {
                    if (d.getIdGiocatore() == g.getGiocatore().getId()
                            && d.getStatistica() instanceof StatisticaAmichevole s) {

                        sommaGoal += s.getGoal();
                        sommaAssist += s.getAssist();
                        sommaGialli += s.getCartelliniGialli();
                        sommaRossi += s.getCartelliniRossi();
                        sommaFalli += s.getFalliCommessi();
                        count++;
                    }
                }
            }
            if (count > 0) {
                mediaGoal += (double) sommaGoal / count;
                mediaAssist += (double) sommaAssist / count;
                mediaGialli += (double) sommaGialli / count;
                mediaRossi += (double) sommaRossi / count;
                mediaFalli += (double) sommaFalli / count;
                giocatoriConsiderati++;
            }
        }

        Map<String, Double> medieGlobali = new HashMap<>();

        if (giocatoriConsiderati == 0) {
            // Nessun dato disponibile
            medieGlobali.put("goal", 0.0);
            medieGlobali.put("assist", 0.0);
            medieGlobali.put("cartelliniGialli", 0.0);
            medieGlobali.put("cartelliniRossi", 0.0);
            medieGlobali.put("falliCommessi", 0.0);
            return medieGlobali;
        }

        medieGlobali.put("goal", mediaGoal / giocatoriConsiderati);
        medieGlobali.put("assist", mediaAssist / giocatoriConsiderati);
        medieGlobali.put("cartelliniGialli", mediaGialli / giocatoriConsiderati);
        medieGlobali.put("cartelliniRossi", mediaRossi / giocatoriConsiderati);
        medieGlobali.put("falliCommessi", mediaFalli / giocatoriConsiderati);

        return medieGlobali;
    }

}
