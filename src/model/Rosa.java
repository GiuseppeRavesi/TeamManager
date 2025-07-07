
package model;

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

    public boolean aggiungiGiocatore(Giocatore g, Ruolo ruolo, Status status) {

        if (giocatoriRosa.isEmpty()) {
            giocatoriRosa.add(new GiocatoreInRosa(g, ruolo, status, LocalDate.now()));
            return true;
        }

        if (giocatoriRosa.size() <= 22) {
            for (GiocatoreInRosa gr : giocatoriRosa) {
                if (!gr.getGiocatore().equals(g)) {
                    giocatoriRosa.add(new GiocatoreInRosa(g, ruolo, status, LocalDate.now()));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rimuoviGiocatore(GiocatoreInRosa g) {
        return giocatoriRosa.remove(g);
    }

    public boolean modificaGiocatore(Giocatore giocatoreBase, Ruolo nuovoRuolo, Status nuovoStatus) {
        for (GiocatoreInRosa g : giocatoriRosa) {
            if (g.getGiocatore().equals(giocatoreBase)) {
                g.setRuolo(nuovoRuolo);
                g.setStatus(nuovoStatus);
                return true;
            }
        }
        return false;
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

    public void stampaRosa() {
        for (GiocatoreInRosa g : giocatoriRosa) {
            System.out.println(g);
        }
    }
}

