
package model;

/**
 *
 * @author Giuseppe Ravesi
 */
public class Utente {

    private String email;
    private String password;
    private String ruolo;
    private int id;

    public Utente(String email, String password, String ruolo, int id) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public int getId() {
        return id;
    }
}
