/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author enzov
 */
public class GiocatoreProva {
    
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataNascita;
    private int numMaglia;
    private String status;
    private String ruolo;

    public GiocatoreProva(int id,String nome, String cognome, String email, LocalDate dataNascita, int numMaglia, String status, String ruolo) {
        this.id=id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataNascita = dataNascita;
        this.numMaglia = numMaglia;
        this.status = status;
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
    
    //getter e setter
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public int getNumMaglia() {
        return numMaglia;
    }

    public String getStatus() {
        return status;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setNumMaglia(int numMaglia) {
        this.numMaglia = numMaglia;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GiocatoreProva other = (GiocatoreProva) obj;
        if (this.numMaglia != other.numMaglia) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return Objects.equals(this.cognome, other.cognome);
    }

   

    @Override
    public String toString() {
        return String.format("%-12s %-12s %-4s %-20s %-10s", nome, cognome, "#" + numMaglia, ruolo, status);
    }
    
}
