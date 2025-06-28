package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Evento {

    private static int contatore = 0;

    private int id;
    private LocalDate data;
    private LocalTime orario;
    private int durata;
    private String luogo;
    private List<Disponibilità> disponibilità;

    public Evento(LocalDate data, LocalTime orario, int durata, String luogo) {
        this.id = ++contatore;
        this.data = data;
        this.orario = orario;
        this.durata = durata;
        this.luogo = luogo;
        this.disponibilità = new ArrayList<>();
    }

    public Evento(int id, LocalDate data, LocalTime orario, int durata, String luogo) {
        this.id = id;
        this.data = data;
        this.orario = orario;
        this.durata = durata;
        this.luogo = luogo;
        this.disponibilità = new ArrayList<>();
        
        if(id>contatore)
            contatore = id;
    }

    public void aggiungiDisponibilità(Disponibilità d) {
        disponibilità.add(d);
    }

    public List<Disponibilità> getDisponibilità() {
        return disponibilità;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public String toString() {
        return "Data: " + data + ", Orario: " + orario + ", Durata: " + durata + ", Luogo: " + luogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
