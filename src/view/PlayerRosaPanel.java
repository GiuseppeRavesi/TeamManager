/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import modelProva.GiocatoreProva;

/**
 *
 * @author enzov
 */
public class PlayerRosaPanel extends javax.swing.JPanel {

    /**
     * Creates new form PlayerRosaPanel
     */
    private TeamManagerGUI parentFrame;

    private java.util.List<GiocatoreProva> squadra;
    private java.util.List<GiocatoreProva> listaGiocatori;

    private DefaultListModel<String> model;


    public PlayerRosaPanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        squadra = new ArrayList<>();
        listaGiocatori = new ArrayList<>();
        
         model = new DefaultListModel<String>();

        setSquadra();
        setListaGiocatori();

        initializeList();
    }

    private void initializeList() {
        model.clear();
        for (GiocatoreProva p : squadra) {
            model.addElement(p.toString());
        }
        jList1.setModel(model);

        // Imposta font monospaziato per allineare i campi
        jList1.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));

        String numGiocatoriRosa = squadra.size() + " / " + 22;
        numGiocatoriLabel.setText(numGiocatoriRosa);

        if (squadra.size() < 22) {
            jLabel1.setText("Rosa Attuale - Incompleta");
            numGiocatoriLabel.setForeground(Color.red);

        } else {
            jLabel1.setText("Rosa Attuale");
            numGiocatoriLabel.setForeground(Color.GREEN);
        }

    }

    public void logout() {
        initializeList();
    }
    
    private void setSquadra() {
        squadra.add(new GiocatoreProva(1, "Luca", "Rossi", "luca.rossi@email.com", LocalDate.of(1995, 3, 12), 10, "Attivo", "Attaccante"));
        squadra.add(new GiocatoreProva(2, "Marco", "Bianchi", "marco.bianchi@email.com", LocalDate.of(1993, 6, 22), 1, "Attivo", "Portiere"));
        squadra.add(new GiocatoreProva(3, "Alessandro", "Verdi", "alessandro.verdi@email.com", LocalDate.of(1996, 1, 9), 4, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(4, "Giovanni", "Neri", "giovanni.neri@email.com", LocalDate.of(1998, 4, 18), 8, "Attivo", "Centrocampista"));
        squadra.add(new GiocatoreProva(5, "Davide", "Ferrari", "davide.ferrari@email.com", LocalDate.of(1994, 12, 5), 5, "Infortunato", "Difensore"));
        squadra.add(new GiocatoreProva(6, "Matteo", "Esposito", "matteo.esposito@email.com", LocalDate.of(1997, 2, 14), 7, "Attivo", "Centrocampista"));
        squadra.add(new GiocatoreProva(7, "Simone", "Gallo", "simone.gallo@email.com", LocalDate.of(1999, 7, 3), 11, "Attivo", "Attaccante"));
        squadra.add(new GiocatoreProva(8, "Andrea", "Fontana", "andrea.fontana@email.com", LocalDate.of(1992, 9, 26), 6, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(9, "Francesco", "Marino", "francesco.marino@email.com", LocalDate.of(1991, 11, 8), 2, "Squalificato", "Difensore"));
        squadra.add(new GiocatoreProva(10, "Riccardo", "Conti", "riccardo.conti@email.com", LocalDate.of(1995, 5, 19), 3, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(11, "Stefano", "Pellegrini", "stefano.pellegrini@email.com", LocalDate.of(2000, 8, 30), 9, "Attivo", "Attaccante"));
        squadra.add(new GiocatoreProva(12, "Fabio", "De Luca", "fabio.deluca@email.com", LocalDate.of(1996, 10, 21), 13, "Attivo", "Centrocampista"));
        squadra.add(new GiocatoreProva(13, "Giorgio", "Rinaldi", "giorgio.rinaldi@email.com", LocalDate.of(1997, 1, 15), 14, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(14, "Emanuele", "Costa", "emanuele.costa@email.com", LocalDate.of(1993, 3, 3), 15, "Infortunato", "Attaccante"));
        squadra.add(new GiocatoreProva(15, "Federico", "Barbieri", "federico.barbieri@email.com", LocalDate.of(1994, 6, 11), 16, "Attivo", "Portiere"));
        squadra.add(new GiocatoreProva(16, "Daniele", "Sala", "daniele.sala@email.com", LocalDate.of(1992, 8, 17), 17, "Attivo", "Centrocampista"));
        squadra.add(new GiocatoreProva(17, "Lorenzo", "Martini", "lorenzo.martini@email.com", LocalDate.of(1990, 12, 25), 18, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(18, "Nicola", "Bianco", "nicola.bianco@email.com", LocalDate.of(1998, 7, 13), 19, "Squalificato", "Attaccante"));
        squadra.add(new GiocatoreProva(19, "Pietro", "Fabbri", "pietro.fabbri@email.com", LocalDate.of(1996, 5, 20), 20, "Attivo", "Difensore"));
        squadra.add(new GiocatoreProva(20, "Valerio", "Grassi", "valerio.grassi@email.com", LocalDate.of(1995, 9, 4), 21, "Attivo", "Centrocampista"));
        squadra.add(new GiocatoreProva(21, "Massimo", "Gentile", "massimo.gentile@email.com", LocalDate.of(1997, 11, 2), 22, "Attivo", "Attaccante"));
        //squadra.add(new GiocatoreProva(22, "Enrico", "Longo", "enrico.longo@email.com", LocalDate.of(1999, 2, 28), 23, "Attivo", "Difensore"));
    }
    
    private void setListaGiocatori() {
        listaGiocatori.add(new GiocatoreProva(1, "Luca", "Rossi", "luca.rossi@email.com", LocalDate.of(1995, 3, 12), 10, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(2, "Marco", "Bianchi", "marco.bianchi@email.com", LocalDate.of(1993, 6, 22), 1, "Attivo", "Portiere"));
        listaGiocatori.add(new GiocatoreProva(3, "Alessandro", "Verdi", "alessandro.verdi@email.com", LocalDate.of(1996, 1, 9), 4, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(4, "Giovanni", "Neri", "giovanni.neri@email.com", LocalDate.of(1998, 4, 18), 8, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(5, "Davide", "Ferrari", "davide.ferrari@email.com", LocalDate.of(1994, 12, 5), 5, "Infortunato", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(6, "Matteo", "Esposito", "matteo.esposito@email.com", LocalDate.of(1997, 2, 14), 7, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(7, "Simone", "Gallo", "simone.gallo@email.com", LocalDate.of(1999, 7, 3), 11, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(8, "Andrea", "Fontana", "andrea.fontana@email.com", LocalDate.of(1992, 9, 26), 6, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(9, "Francesco", "Marino", "francesco.marino@email.com", LocalDate.of(1991, 11, 8), 2, "Squalificato", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(10, "Riccardo", "Conti", "riccardo.conti@email.com", LocalDate.of(1995, 5, 19), 3, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(11, "Stefano", "Pellegrini", "stefano.pellegrini@email.com", LocalDate.of(2000, 8, 30), 9, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(12, "Fabio", "De Luca", "fabio.deluca@email.com", LocalDate.of(1996, 10, 21), 13, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(13, "Giorgio", "Rinaldi", "giorgio.rinaldi@email.com", LocalDate.of(1997, 1, 15), 14, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(14, "Emanuele", "Costa", "emanuele.costa@email.com", LocalDate.of(1993, 3, 3), 15, "Infortunato", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(15, "Federico", "Barbieri", "federico.barbieri@email.com", LocalDate.of(1994, 6, 11), 16, "Attivo", "Portiere"));
        listaGiocatori.add(new GiocatoreProva(16, "Daniele", "Sala", "daniele.sala@email.com", LocalDate.of(1992, 8, 17), 17, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(17, "Lorenzo", "Martini", "lorenzo.martini@email.com", LocalDate.of(1990, 12, 25), 18, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(18, "Nicola", "Bianco", "nicola.bianco@email.com", LocalDate.of(1998, 7, 13), 19, "Squalificato", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(19, "Pietro", "Fabbri", "pietro.fabbri@email.com", LocalDate.of(1996, 5, 20), 20, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(20, "Valerio", "Grassi", "valerio.grassi@email.com", LocalDate.of(1995, 9, 4), 21, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(21, "Massimo", "Gentile", "massimo.gentile@email.com", LocalDate.of(1997, 11, 2), 22, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(22, "Enrico", "Longo", "enrico.longo@email.com", LocalDate.of(1999, 2, 28), 23, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(23, "Tommaso", "Caruso", "tommaso.caruso@email.com", LocalDate.of(1993, 5, 2), 24, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(24, "Leonardo", "Moretti", "leonardo.moretti@email.com", LocalDate.of(1996, 8, 11), 25, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(25, "Gabriele", "Mancini", "gabriele.mancini@email.com", LocalDate.of(1995, 3, 7), 26, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(26, "Samuel", "Silvestri", "samuel.silvestri@email.com", LocalDate.of(1997, 9, 30), 27, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(27, "Michele", "Testa", "michele.testa@email.com", LocalDate.of(1992, 1, 19), 28, "Infortunato", "Portiere"));
        listaGiocatori.add(new GiocatoreProva(28, "Alberto", "Basile", "alberto.basile@email.com", LocalDate.of(1994, 12, 8), 29, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(29, "Carlo", "Santoro", "carlo.santoro@email.com", LocalDate.of(1993, 6, 14), 30, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(30, "Christian", "Palmieri", "christian.palmieri@email.com", LocalDate.of(1998, 4, 16), 31, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(31, "Vincenzo", "Parisi", "vincenzo.parisi@email.com", LocalDate.of(1999, 11, 5), 32, "Squalificato", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(32, "Salvatore", "Monti", "salvatore.monti@email.com", LocalDate.of(1996, 2, 23), 33, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(33, "Diego", "Martino", "diego.martino@email.com", LocalDate.of(1995, 10, 3), 34, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(34, "Fabian", "De Santis", "fabian.desantis@email.com", LocalDate.of(1991, 5, 28), 35, "Attivo", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(35, "Antonio", "Bellini", "antonio.bellini@email.com", LocalDate.of(1994, 7, 12), 36, "Attivo", "Portiere"));
        listaGiocatori.add(new GiocatoreProva(36, "Giulio", "Ruggieri", "giulio.ruggieri@email.com", LocalDate.of(1997, 12, 1), 37, "Attivo", "Centrocampista"));
        listaGiocatori.add(new GiocatoreProva(37, "Raffaele", "Ferretti", "raffaele.ferretti@email.com", LocalDate.of(1990, 3, 15), 38, "Attivo", "Difensore"));
        listaGiocatori.add(new GiocatoreProva(38, "Sebastiano", "Vitale", "sebastiano.vitale@email.com", LocalDate.of(1998, 6, 9), 39, "Infortunato", "Attaccante"));
        listaGiocatori.add(new GiocatoreProva(39, "Claudio", "Negri", "claudio.negri@email.com", LocalDate.of(1993, 9, 21), 40, "Attivo", "Difensore"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        numGiocatoriLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Nome Item 2 Data", " ", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setMaximumSize(new java.awt.Dimension(90, 90));
        jScrollPane1.setViewportView(jList1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Rosa Attuale");

        numGiocatoriLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        numGiocatoriLabel.setText("NumGiocatori");
        numGiocatoriLabel.setMaximumSize(null);

        backButton.setBackground(new java.awt.Color(232, 253, 253));
        backButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton)
                .addGap(49, 49, 49))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        initializeList();
        parentFrame.cardLayout.show(parentFrame.getjPanel1(), "PLAYERPANEL");
    }//GEN-LAST:event_backButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel numGiocatoriLabel;
    // End of variables declaration//GEN-END:variables
}
