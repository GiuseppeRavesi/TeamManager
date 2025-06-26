
package model;

import java.util.ArrayList;
import java.util.List;

public class Rosa {

    private List<GiocatoreInRosa> giocatoriRosa;

    public Rosa() {
        this.giocatoriRosa = new ArrayList<>();
    }

    public boolean aggiungiGiocatore(GiocatoreInRosa g) {
        if (!giocatoriRosa.contains(g)) {
            giocatoriRosa.add(g);
            return true;
        }
        return false;
    }

    public boolean rimuoviGiocatore(GiocatoreInRosa g) {
        return giocatoriRosa.remove(g);
    }

    public boolean modificaGiocatore(Giocatore giocatoreBase, String nuovoRuolo, String nuovoStatus) {
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

