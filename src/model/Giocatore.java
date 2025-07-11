package model;

import java.time.LocalDate;
import java.util.Objects;


public class Giocatore {

    private String nome;
    private String cognome;
    private int numMaglia;
    private LocalDate dataNascita;
    private String nazionalita;
    private String email;
    private static int contatore = 0;
    private int id;

    public Giocatore(String nome, String cognome, int numMaglia,
            LocalDate dataNascita, String nazionalita, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.numMaglia = numMaglia;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
        this.email = email;
        this.id = ++contatore;
    }
    
    public Giocatore(int id, String nome, String cognome, int numMaglia,String ruoloPreferito,
            LocalDate dataNascita, String nazionalita, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.numMaglia = numMaglia;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
        this.email = email;
        this.id = id;
        
        if(id > contatore)
            contatore = id;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Giocatore)) return false;
        Giocatore g = (Giocatore) o;
        return email.equalsIgnoreCase(g.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome.toLowerCase(), cognome.toLowerCase(), dataNascita, email.toLowerCase());
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + nazionalita + ") - " + email;
    }
}
