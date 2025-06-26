package model;

import java.time.LocalDate;
import java.util.Objects;


public class Giocatore {

    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String nazionalita;
    private String email;

    public Giocatore(String nome, String cognome, LocalDate dataNascita, String nazionalita, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
        this.email = email;
    }

    // Getters e Setters
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
        return nome.equalsIgnoreCase(g.nome) &&
           cognome.equalsIgnoreCase(g.cognome) &&
           dataNascita.equals(g.dataNascita) &&
           email.equalsIgnoreCase(g.email);
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
