package model;

/**
 *
 * @author Giuseppe Ravesi
 */
public class StatisticaAmichevole extends Statistica {

    private int minutiGiocati;
    private int goal;
    private int autogoal;
    private int cartelliniGialli;
    private int cartelliniRossi;
    private float distanzaTotalePercorsa;
    private int falliCommessi;
    private int assist;
    private int parate;
    private int intercettiRiusciti;
    private int passaggiChiave;
    private int tiriTotali;

    public StatisticaAmichevole(int idGiocatore, int idEvento, int minutiGiocati, int goal,
            int autogoal, int cartelliniGialli, int cartelliniRossi, float distanzaTotalePercorsa,
            int falliCommessi, int assist, int parate, int intercettiRiusciti, int passaggiChiave, int tiriTotali) {
        super(idGiocatore, idEvento);
        this.minutiGiocati = minutiGiocati;
        this.goal = goal;
        this.autogoal = autogoal;
        this.cartelliniGialli = cartelliniGialli;
        this.cartelliniRossi = cartelliniRossi;
        this.distanzaTotalePercorsa = distanzaTotalePercorsa;
        this.falliCommessi = falliCommessi;
        this.assist = assist;
        this.parate = parate;
        this.intercettiRiusciti = intercettiRiusciti;
        this.passaggiChiave = passaggiChiave;
        this.tiriTotali = tiriTotali;
    }

    public StatisticaAmichevole(int id, int idGiocatore, int idEvento, int minutiGiocati, int goal, int autogoal,
            int cartelliniGialli, int cartelliniRossi, float distanzaTotalePercorsa, int falliCommessi, int assist,
            int parate, int intercettiRiusciti, int passaggiChiave, int tiriTotali) {
        super(id, idGiocatore, idEvento);
        this.minutiGiocati = minutiGiocati;
        this.goal = goal;
        this.autogoal = autogoal;
        this.cartelliniGialli = cartelliniGialli;
        this.cartelliniRossi = cartelliniRossi;
        this.distanzaTotalePercorsa = distanzaTotalePercorsa;
        this.falliCommessi = falliCommessi;
        this.assist = assist;
        this.parate = parate;
        this.passaggiChiave=passaggiChiave;
        this.intercettiRiusciti = intercettiRiusciti;
        this.tiriTotali = tiriTotali;
    }

    public int getParate() {
        return parate;
    }

    public void setParate(int parate) {
        this.parate = parate;
    }

    public int getIntercettiRiusciti() {
        return intercettiRiusciti;
    }

    public void setIntercettiRiusciti(int intercettiRiusciti) {
        this.intercettiRiusciti = intercettiRiusciti;
    }

    public int getPassaggiChiave() {
        return passaggiChiave;
    }

    public void setPassaggiChiave(int passaggiChiave) {
        this.passaggiChiave = passaggiChiave;
    }

    public int getTiriTotali() {
        return tiriTotali;
    }

    public void setTiriTotali(int tiriTotali) {
        this.tiriTotali = tiriTotali;
    }

    public int getMinutiGiocati() {
        return minutiGiocati;
    }

    public void setMinutiGiocati(int minutiGiocati) {
        this.minutiGiocati = minutiGiocati;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAutogoal() {
        return autogoal;
    }

    public void setAutogoal(int autogoal) {
        this.autogoal = autogoal;
    }

    public int getCartelliniGialli() {
        return cartelliniGialli;
    }

    public void setCartelliniGialli(int cartelliniGialli) {
        this.cartelliniGialli = cartelliniGialli;
    }

    public int getCartelliniRossi() {
        return cartelliniRossi;
    }

    public void setCartelliniRossi(int cartelliniRossi) {
        this.cartelliniRossi = cartelliniRossi;
    }

    public float getDistanzaTotalePercorsa() {
        return distanzaTotalePercorsa;
    }

    public void setDistanzaTotalePercorsa(float distanzaTotalePercorsa) {
        this.distanzaTotalePercorsa = distanzaTotalePercorsa;
    }

    public int getFalliCommessi() {
        return falliCommessi;
    }

    public void setFalliCommessi(int falliCommessi) {
        this.falliCommessi = falliCommessi;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    @Override
    public String toString() {
        return super.toString() + "StatisticaAmichevole {"
                + ", minutiGiocati=" + minutiGiocati
                + ", goal=" + goal
                + ", autogoal=" + autogoal
                + ", cartelliniGialli=" + cartelliniGialli
                + ", cartelliniRossi=" + cartelliniRossi
                + ", distanzaTotalePercorsa=" + distanzaTotalePercorsa
                + ", falliCommessi=" + falliCommessi
                + ", assist=" + assist
                + '}';
    }

}
