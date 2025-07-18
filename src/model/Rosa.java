package model;

import exception.GiocatoreDuplicatoException;
import exception.NumeroMagliaDuplicatoException;
import exception.RosaCompletaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.enums.Ruolo;
import model.enums.Status;

public class Rosa {

    private List<GiocatoreInRosa> giocatoriRosa;

    public Rosa() {
        this.giocatoriRosa = new ArrayList<>();
    }

    public void aggiungiGiocatore(Giocatore g, Ruolo ruolo, Status status, int numMaglia)
            throws RosaCompletaException, NumeroMagliaDuplicatoException, GiocatoreDuplicatoException {
        if (giocatoriRosa.size() >= 22) {
            throw new RosaCompletaException();
        }

        for (GiocatoreInRosa gr : giocatoriRosa) {
            if (gr.getNumMaglia() == numMaglia) {
                throw new NumeroMagliaDuplicatoException();
            }
        }

        for (GiocatoreInRosa gr : giocatoriRosa) {
            if (gr.getGiocatore().equals(g)) {
                throw new GiocatoreDuplicatoException();
            }
        }
        giocatoriRosa.add(new GiocatoreInRosa(g, ruolo, status, numMaglia, LocalDate.now()));
    }

    public boolean rimuoviGiocatore(GiocatoreInRosa g) {
        return giocatoriRosa.remove(g);
    }

    public void modificaGiocatore(Giocatore giocatoreBase, Ruolo nuovoRuolo, Status nuovoStatus, int nuovoNumMaglia)
            throws NumeroMagliaDuplicatoException {
        
        boolean giocatoreTrovato = false;
        System.out.println("CIAO MODIFICA");
        for (GiocatoreInRosa g : giocatoriRosa) {
            if (g.getGiocatore().equals(giocatoreBase)) {
                giocatoreTrovato = true;
                break;
            }
        }

        if (!giocatoreTrovato) {
            throw new IllegalArgumentException("Giocatore non presente nella rosa!");
        }

        for (GiocatoreInRosa g : giocatoriRosa) {
            if (!g.getGiocatore().equals(giocatoreBase)) {
                if (g.getNumMaglia() == nuovoNumMaglia) {
                    throw new NumeroMagliaDuplicatoException();
                }
            }
        }

        for (GiocatoreInRosa g : giocatoriRosa) {
            if (g.getGiocatore().equals(giocatoreBase)) {
                g.setRuolo(nuovoRuolo);
                g.setStatus(nuovoStatus);
                g.setNumMaglia(nuovoNumMaglia);
                break;
            }
        }
    }

    public List<GiocatoreInRosa> cercaGiocatori(String filtro) {
        List<GiocatoreInRosa> risultati = new ArrayList<>();
        String filtroLower = filtro.toLowerCase();

        for (GiocatoreInRosa g : giocatoriRosa) {
            String nome = g.getGiocatore().getNome().toLowerCase();
            String cognome = g.getGiocatore().getCognome().toLowerCase();

            if (nome.contains(filtroLower) || cognome.contains(filtroLower)) {
                risultati.add(g);
            }
        }
        return risultati;
    }

    public List<GiocatoreInRosa> getGiocatori() {
        return giocatoriRosa;
    }

    public void setGiocatoriRosa(List<GiocatoreInRosa> giocatoriRosa) {
        this.giocatoriRosa = giocatoriRosa;
    }

    public void stampaRosa() {
        for (GiocatoreInRosa g : giocatoriRosa) {
            System.out.println(g);
        }
    }
}
