
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Evento {
    private static int contatore = 0;
    
    protected int id; 
    protected LocalDate data;
    protected LocalTime orario;
    protected LocalTime durata;
    protected String luogo;
    
    public Evento(LocalDate data, LocalTime orario, LocalTime durata, String luogo) {
        this.id = ++contatore;
        this.data = data;
        this.orario = orario;
        this.durata = durata;
        this.luogo = luogo;
    }

    public Evento(int id, LocalDate data, LocalTime orario, LocalTime durata, String luogo) {
        this.id = id;
        this.data = data;
        this.orario = orario;
        this.durata = durata;
        this.luogo = luogo;
    }

    public static void setContatore(int valore) {
        contatore = valore;
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

    public LocalTime getDurata() {
        return durata;
    }

    public void setDurata(LocalTime durata) {
        this.durata = durata;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public abstract String getTipoEvento();

    @Override
    public String toString() {
        return "Data: " + data + ", Orario: " + orario + ", Durata: " + durata + ", Luogo: " + luogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento other = (Evento) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

