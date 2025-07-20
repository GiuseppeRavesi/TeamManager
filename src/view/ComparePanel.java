/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.GiocatoreInRosa;

/**
 *
 * @author enzov
 */
public class ComparePanel extends javax.swing.JPanel {

    /**
     * Creates new form ComparePanel
     */
    private TeamManagerGUI parentFrame;
    private java.util.List<GiocatoreInRosa> rosa;
    private java.util.List<Integer> playerIndexes;
    private DefaultListModel<String> model;

    private java.util.List<JLabel> playerLabel1;
    private java.util.List<JLabel> playerLabel2;

    public ComparePanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        rosa = parentFrame.getTM().visualizzaRosa();
        model = new DefaultListModel<String>();

        playerIndexes = new ArrayList<>();

        initializeList();
        setListPlayerLabel();
    }

    private void initializeList() {
        model.clear();
        for (GiocatoreInRosa p : rosa) {
            model.addElement(p.toString());
        }
        jList1.setModel(model);

        // Imposta font monospaziato per allineare i campi
        jList1.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));

        jList1.setSelectionModel(new MySelectionModel(jList1, 2));
    }
    
    public void initCard(){
        initializeList();
    }
    
    public void logout(){
        parentFrame.getSession().logout();
    }

    private void setUpCompareDialog() {
        comparePlayerDialog.setLocationRelativeTo(null);
        comparePlayerDialog.setTitle("Confronta Giocatori");
        comparePlayerDialog.setResizable(false);
        comparePlayerDialog.setModal(true);

        GiocatoreInRosa g1 = rosa.get(playerIndexes.get(0));
        GiocatoreInRosa g2 = rosa.get(playerIndexes.get(1));

        jLabel3.setText(g1.getGiocatore().getCognome() + " " + "#" + g1.getNumMaglia());
        jLabel4.setText(g2.getGiocatore().getCognome() + " " + "#" + g2.getNumMaglia());

        Map<String, Map<String, Number>> confronto = parentFrame.getTM().confrontaGiocatori(g1, g2);
        Map<String, Number> agg1 = confronto.get(g1.getGiocatore().getNome() + " " + g1.getGiocatore().getCognome() + " " + g1.getNumMaglia());
        Map<String, Number> agg2 = confronto.get(g2.getGiocatore().getNome() + " " + g2.getGiocatore().getCognome() + " " + g2.getNumMaglia());

        //parametri da confrontare
        java.util.List<Number> list1 = new ArrayList<Number>();
        java.util.List<Number> list2 = new ArrayList<Number>();

        setLists(list1, list2, agg1, agg2);

        for (int i = 0; i < list1.size(); i++) {

            Double val1 = (Double) list1.get(i).doubleValue();
            Double val2 = (Double) list2.get(i).doubleValue();

            if (i== 3 || i == 4 || i == 5 || i == 6) {
                if (val1 > val2) {
                    playerLabel1.get(i).setForeground(Color.RED);
                    playerLabel2.get(i).setForeground(Color.GREEN);

                    playerLabel1.get(i).setText("↓" + list1.get(i).toString());
                    playerLabel2.get(i).setText("↑" + list2.get(i).toString());
                } else {
                    playerLabel1.get(i).setForeground(Color.GREEN);
                    playerLabel2.get(i).setForeground(Color.RED);

                    playerLabel1.get(i).setText("↑" + list1.get(i).toString());
                    playerLabel2.get(i).setText("↓" + list2.get(i).toString());
                }

            } else {

                if (val1 > val2) {
                    playerLabel1.get(i).setForeground(Color.GREEN);
                    playerLabel2.get(i).setForeground(Color.RED);

                    playerLabel1.get(i).setText("↑" + list1.get(i).toString());
                    playerLabel2.get(i).setText("↓" + list2.get(i).toString());
                } else {
                    playerLabel1.get(i).setForeground(Color.RED);
                    playerLabel2.get(i).setForeground(Color.GREEN);

                    playerLabel1.get(i).setText("↓" + list1.get(i).toString());
                    playerLabel2.get(i).setText("↑" + list2.get(i).toString());
                }

            }

            if (Objects.equals(val1, val2)) {
                playerLabel1.get(i).setText(list1.get(i).toString());
                playerLabel2.get(i).setText(list2.get(i).toString());
                playerLabel1.get(i).setForeground(Color.BLACK);
                playerLabel2.get(i).setForeground(Color.BLACK);
            }

        }

        comparePlayerDialog.setVisible(true);

    }

    private void setLists(java.util.List<Number> list1, java.util.List<Number> list2, Map<String, Number> agg1,
            Map<String, Number> agg2) {

        list1.add(agg1.get("goal"));
        list1.add(agg1.get("assist"));
        list1.add(agg1.get("minutiGiocati"));
        list1.add(agg1.get("autogoal"));
        list1.add(agg1.get("cartelliniGialli"));
        list1.add(agg1.get("cartelliniRossi"));
        list1.add(agg1.get("falliCommessi"));
        list1.add(agg1.get("intercettiRiusciti"));
        list1.add(agg1.get("passaggiChiave"));
        list1.add(agg1.get("tiriTotali"));
        list1.add(agg1.get("parate"));
        list1.add(agg1.get("distanzaTotalePercorsa"));

        list2.add(agg2.get("goal"));
        list2.add(agg2.get("assist"));
        list2.add(agg2.get("minutiGiocati"));
        list2.add(agg2.get("autogoal"));
        list2.add(agg2.get("cartelliniGialli"));
        list2.add(agg2.get("cartelliniRossi"));
        list2.add(agg2.get("falliCommessi"));
        list2.add(agg2.get("intercettiRiusciti"));
        list2.add(agg2.get("passaggiChiave"));
        list2.add(agg2.get("tiriTotali"));
        list2.add(agg2.get("parate"));
        list2.add(agg2.get("distanzaTotalePercorsa"));

    }

    private void setListPlayerLabel() {

        playerLabel1 = new ArrayList<>();
        playerLabel2 = new ArrayList<>();

        //fillo lista con le label player1
        playerLabel1.add(goal1);
        playerLabel1.add(assist1);
        playerLabel1.add(min1);
        playerLabel1.add(autogoal1);
        playerLabel1.add(gialli1);
        playerLabel1.add(rossi1);
        playerLabel1.add(falli1);
        playerLabel1.add(intercetti1);
        playerLabel1.add(passaggi1);
        playerLabel1.add(tiri1);
        playerLabel1.add(parate1);
        playerLabel1.add(dist1);

        //fillo lista con le label player2
        playerLabel2.add(goal2);
        playerLabel2.add(assist2);
        playerLabel2.add(min2);
        playerLabel2.add(autogoal2);
        playerLabel2.add(gialli2);
        playerLabel2.add(rossi2);
        playerLabel2.add(falli2);
        playerLabel2.add(intercetti2);
        playerLabel2.add(passaggi2);
        playerLabel2.add(tiri2);
        playerLabel2.add(parate2);
        playerLabel2.add(dist2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comparePlayerDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        min2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        min1 = new javax.swing.JLabel();
        goal2 = new javax.swing.JLabel();
        gialli2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        goal1 = new javax.swing.JLabel();
        autogoal2 = new javax.swing.JLabel();
        autogoal1 = new javax.swing.JLabel();
        gialli1 = new javax.swing.JLabel();
        rossi2 = new javax.swing.JLabel();
        rossi1 = new javax.swing.JLabel();
        dist2 = new javax.swing.JLabel();
        falli1 = new javax.swing.JLabel();
        falli2 = new javax.swing.JLabel();
        assist1 = new javax.swing.JLabel();
        assist2 = new javax.swing.JLabel();
        parate1 = new javax.swing.JLabel();
        dist1 = new javax.swing.JLabel();
        intercetti1 = new javax.swing.JLabel();
        parate2 = new javax.swing.JLabel();
        passaggi1 = new javax.swing.JLabel();
        intercetti2 = new javax.swing.JLabel();
        passaggi2 = new javax.swing.JLabel();
        tiri2 = new javax.swing.JLabel();
        tiri1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        comparePlayerDialog.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("minuti giocati:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("goal segnati:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("auto-goal:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("cartellini Gialli:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("cartellini Rossi:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("distanza percorsa(m):");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("falli commessi:");

        min2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        min2.setText("assist:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("parate:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("intercetti riusciti:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("passaggi chiave:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("tiri totali:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Player #1");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Player #2");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("assist:");

        min1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        min1.setText("assist:");

        goal2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        goal2.setText("assist:");

        gialli2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gialli2.setText("assist:");

        jButton1.setBackground(new java.awt.Color(239, 248, 249));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        goal1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        goal1.setText("assist:");

        autogoal2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        autogoal2.setText("assist:");

        autogoal1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        autogoal1.setText("assist:");

        gialli1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gialli1.setText("assist:");

        rossi2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rossi2.setText("assist:");

        rossi1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rossi1.setText("assist:");

        dist2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dist2.setText("assist:");

        falli1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        falli1.setText("assist:");

        falli2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        falli2.setText("assist:");

        assist1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        assist1.setText("assist:");

        assist2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        assist2.setText("assist:");

        parate1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        parate1.setText("assist:");

        dist1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dist1.setText("assist:");

        intercetti1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        intercetti1.setText("assist:");

        parate2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        parate2.setText("assist:");

        passaggi1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passaggi1.setText("assist:");

        intercetti2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        intercetti2.setText("assist:");

        passaggi2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passaggi2.setText("assist:");

        tiri2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tiri2.setText("assist:");

        tiri1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tiri1.setText("assist:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(autogoal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(goal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addComponent(min1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(assist1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rossi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dist1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tiri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gialli1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(intercetti1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passaggi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(falli1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(parate1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(assist2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dist2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(falli2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(parate2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intercetti2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passaggi2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tiri2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(goal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                        .addComponent(min2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(autogoal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(gialli2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rossi2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel25)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(assist1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(min1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(autogoal1)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(goal2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(assist2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(min2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(autogoal2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(goal1)
                        .addGap(90, 90, 90)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(gialli1)
                    .addComponent(gialli2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(rossi1)
                    .addComponent(rossi2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(falli1)
                    .addComponent(falli2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(intercetti2)
                        .addGap(7, 7, 7)
                        .addComponent(passaggi2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tiri2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parate2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(intercetti1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(passaggi1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(tiri1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(parate1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(dist1)
                    .addComponent(dist2))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout comparePlayerDialogLayout = new javax.swing.GroupLayout(comparePlayerDialog.getContentPane());
        comparePlayerDialog.getContentPane().setLayout(comparePlayerDialogLayout);
        comparePlayerDialogLayout.setHorizontalGroup(
            comparePlayerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comparePlayerDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        comparePlayerDialogLayout.setVerticalGroup(
            comparePlayerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comparePlayerDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(800, 600));

        addButton.setBackground(new java.awt.Color(204, 255, 204));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addButton.setForeground(new java.awt.Color(51, 255, 51));
        addButton.setText("OK");
        addButton.setAlignmentY(0.0F);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Nome Item 2 Data", " ", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setMaximumSize(new java.awt.Dimension(90, 90));
        jScrollPane1.setViewportView(jList1);

        backButton.setBackground(new java.awt.Color(232, 253, 253));
        backButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Seleziona 2 giocatori in rosa da confrontare");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        playerIndexes.clear();

        for (int i : jList1.getSelectedIndices()) {
            playerIndexes.add(i);
        }

        if (playerIndexes.size() == 2) {
            setUpCompareDialog();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona 2 giocatori!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        initializeList();
        parentFrame.cardLayout.show(parentFrame.getjPanel1(), "COACHPANEL");
    }//GEN-LAST:event_backButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        comparePlayerDialog.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel assist1;
    private javax.swing.JLabel assist2;
    private javax.swing.JLabel autogoal1;
    private javax.swing.JLabel autogoal2;
    private javax.swing.JButton backButton;
    private javax.swing.JDialog comparePlayerDialog;
    private javax.swing.JLabel dist1;
    private javax.swing.JLabel dist2;
    private javax.swing.JLabel falli1;
    private javax.swing.JLabel falli2;
    private javax.swing.JLabel gialli1;
    private javax.swing.JLabel gialli2;
    private javax.swing.JLabel goal1;
    private javax.swing.JLabel goal2;
    private javax.swing.JLabel intercetti1;
    private javax.swing.JLabel intercetti2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel min1;
    private javax.swing.JLabel min2;
    private javax.swing.JLabel parate1;
    private javax.swing.JLabel parate2;
    private javax.swing.JLabel passaggi1;
    private javax.swing.JLabel passaggi2;
    private javax.swing.JLabel rossi1;
    private javax.swing.JLabel rossi2;
    private javax.swing.JLabel tiri1;
    private javax.swing.JLabel tiri2;
    // End of variables declaration//GEN-END:variables
}
