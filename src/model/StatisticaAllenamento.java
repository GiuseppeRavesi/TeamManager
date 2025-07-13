package model;

/**
 *
 * @author Giuseppe Ravesi
 */
public class StatisticaAllenamento extends Statistica {

    private float velocitàMax;
    private float velocitàMedia;
    private int valutazioneForzaFisica;
    private int valutazioneForzaTiro;
    private int frequenzaCardiacaMedia;

    public StatisticaAllenamento(
            int idGiocatore,
            int idEvento,
            float velocitàMax,
            float velocitàMedia,
            int valutazioneForzaFisica,
            int valutazioneForzaTiro,
            int frequenzaCardiacaMedia
    ) {
        super(idGiocatore, idEvento);
        this.velocitàMax = velocitàMax;
        this.velocitàMedia = velocitàMedia;
        this.valutazioneForzaFisica = valutazioneForzaFisica;
        this.valutazioneForzaTiro = valutazioneForzaTiro;
        this.frequenzaCardiacaMedia = frequenzaCardiacaMedia;
    }

    public StatisticaAllenamento(
            int id,
            int idGiocatore,
            int idEvento,
            float velocitàMax,
            float velocitàMedia,
            int valutazioneForzaFisica,
            int valutazioneForzaTiro,
            int frequenzaCardiacaMedia
    ) {
        super(id, idGiocatore, idEvento);
        this.velocitàMax = velocitàMax;
        this.velocitàMedia = velocitàMedia;
        this.valutazioneForzaFisica = valutazioneForzaFisica;
        this.valutazioneForzaTiro = valutazioneForzaTiro;
        this.frequenzaCardiacaMedia = frequenzaCardiacaMedia;
    }

    public float getVelocitàMax() {
        return velocitàMax;
    }

    public void setVelocitàMax(float velocitàMax) {
        this.velocitàMax = velocitàMax;
    }

    public float getVelocitàMedia() {
        return velocitàMedia;
    }

    public void setVelocitàMedia(float velocitàMedia) {
        this.velocitàMedia = velocitàMedia;
    }

    public int getValutazioneForzaFisica() {
        return valutazioneForzaFisica;
    }

    public void setValutazioneForzaFisica(int valutazioneForzaFisica) {
        this.valutazioneForzaFisica = valutazioneForzaFisica;
    }

    public int getValutazioneForzaTiro() {
        return valutazioneForzaTiro;
    }

    public void setValutazioneForzaTiro(int valutazioneForzaTiro) {
        this.valutazioneForzaTiro = valutazioneForzaTiro;
    }

    public int getFrequenzaCardiacaMedia() {
        return frequenzaCardiacaMedia;
    }

    public void setFrequenzaCardiacaMedia(int frequenzaCardiacaMedia) {
        this.frequenzaCardiacaMedia = frequenzaCardiacaMedia;
    }

    @Override
    public String toString() {
        return super.toString()+ "StatisticaAllenamento{" + "velocit\u00e0Max=" + velocitàMax 
                + ", velocit\u00e0Media=" + velocitàMedia 
                + ", valutazioneForzaFisica=" + valutazioneForzaFisica 
                + ", valutazioneForzaTiro=" + valutazioneForzaTiro 
                + ", frequenzaCardiacaMedia=" + frequenzaCardiacaMedia + '}';
    }

}
