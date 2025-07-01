package controller;

import model.Utente;

/**
 *
 * @author Giuseppe Ravesi
 */
public class Session {
     private static Session instance;
    private Utente utenteLoggato;

    private Session() { }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(Utente utente) {
        this.utenteLoggato = utente;
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public void logout() {
        utenteLoggato = null;
    }

    public boolean isAuthenticated() {
        return utenteLoggato != null;
    }

    public boolean isAllenatore() {
        return utenteLoggato != null && "ALLENATORE".equals(utenteLoggato.getRuolo());
    }

    public boolean isGiocatore() {
        return utenteLoggato != null && "GIOCATORE".equals(utenteLoggato.getRuolo());
    }
}
