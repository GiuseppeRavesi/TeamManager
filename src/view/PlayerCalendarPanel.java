/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author enzov
 */




public class PlayerCalendarPanel extends javax.swing.JPanel {

    /**
     * Creates new form PlayerCalendarPanel
     */
    private TeamManagerGUI parentFrame;
    private java.util.List<EventoProva1> listaEventi;

    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer rightRenderer;

    private int indexRow;

    public PlayerCalendarPanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        tableModel = (DefaultTableModel) jTable1.getModel();
        rightRenderer = new DefaultTableCellRenderer();

        listaEventi = new ArrayList<>();
        setUpEventiProva();

        initialiteCalendar();
        tableClickListener();

    }

    private void setUpEventiProva() {
        listaEventi.add(new AllenamentoProva1(1, LocalDate.of(2025, 7, 1), LocalTime.of(17, 0), 90, "Campo A", false, "Tecnico", "Passaggi e movimenti"));
        listaEventi.add(new AmichevoleProva1(2, LocalDate.of(2025, 7, 2), LocalTime.of(18, 30), 120, "Stadio B", false, "Squadra Blu"));
        listaEventi.add(new AllenamentoProva1(3, LocalDate.of(2025, 7, 3), LocalTime.of(16, 0), 60, "Campo A", false, "Tattico", "Prove di schema"));
        listaEventi.add(new AmichevoleProva1(4, LocalDate.of(2025, 7, 4), LocalTime.of(20, 0), 110, "Stadio C", false, "Squadra Rossa"));
        listaEventi.add(new AllenamentoProva1(5, LocalDate.of(2025, 7, 5), LocalTime.of(15, 30), 75, "Campo B", false, "Fisico", "Circuito resistenza"));

        listaEventi.add(new AmichevoleProva1(6, LocalDate.of(2025, 7, 6), LocalTime.of(17, 45), 90, "Stadio D", false, "Squadra Verde"));
        listaEventi.add(new AllenamentoProva1(7, LocalDate.of(2025, 7, 7), LocalTime.of(16, 15), 90, "Campo C", false, "Misto", "Tattico + Fisico"));
        listaEventi.add(new AmichevoleProva1(8, LocalDate.of(2025, 7, 8), LocalTime.of(19, 0), 100, "Stadio E", false, "Squadra Gialla"));
        listaEventi.add(new AllenamentoProva1(9, LocalDate.of(2025, 7, 9), LocalTime.of(18, 0), 80, "Campo D", false, "Tecnico", "Controllo e tiro"));
        listaEventi.add(new AllenamentoProva1(10, LocalDate.of(2025, 7, 10), LocalTime.of(17, 30), 60, "Campo A", false, "Fisico", "Corsa e scatti"));
    }

    private void putAtRightElement() {

        for (int x = 0; x < jTable1.getColumnCount(); x++) {

            jTable1.getColumnModel().getColumn(x).setCellRenderer(rightRenderer);

        }
    }

    private void initialiteCalendar() {
        indexRow = -1;
        putAtRightElement();
        tableModel.setRowCount(0);
        jTable1.setSelectionModel(new ForcedListSelectionModel());
        jTable1.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 13));
        showDetailsBtn.setVisible(false);
        disponibilitaButton.setVisible(false);

        for (EventoProva1 e : listaEventi) {
            String conferma = "";
            if (e.isDisponibilita() == false) {
                conferma = "Non fornita";
            } else {
                conferma = "Fornita";
            }

            Object[] row = {e.getId(), e.getData(), e.getOrario(), e.getDurata(), e.getLuogo(), conferma};
            tableModel.addRow(row);
        }

    }

    private void tableClickListener() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                indexRow = row;
                System.out.println("Riga attuale: " + indexRow);
                showDetailsBtn.setVisible(true);
                disponibilitaButton.setVisible(true);
                setUpDetailsDialog();
                setUpPresenzaDialog();
            }
        });
    }

    private void setUpDetailsDialog() {
        detailsDialog.setLocationRelativeTo(null);
        detailsDialog.setTitle("Dettagli evento");
        detailsDialog.setResizable(false);
        detailsDialog.setModal(true);

        if (indexRow >= 0) {
            dateText.setText(listaEventi.get(indexRow).getData().toString());
            timeText.setText(listaEventi.get(indexRow).getOrario().toString());
            durationText.setText(String.valueOf(listaEventi.get(indexRow).getDurata()) + " " + "min");
            placeText.setText(listaEventi.get(indexRow).getLuogo());
            String conferma;
            if (listaEventi.get(indexRow).isDisponibilita() == false) {
                conferma = "Non fornita";
            } else {
                conferma = "Fornita";
            }
            dispText.setText(conferma);

            if (listaEventi.get(indexRow) instanceof AllenamentoProva1) {
                jLabel3.setText("Allenamento");
                typeOrTeamLabel.setText("tipologia:");
                AllenamentoProva1 ap = (AllenamentoProva1) listaEventi.get(indexRow);
                noteText.setText(ap.getNote());
                hybridText.setText(ap.getTipologia());
                noteText.setVisible(true);
                noteLabel.setVisible(true);
            } else if (listaEventi.get(indexRow) instanceof AmichevoleProva1) {
                jLabel3.setText("Amichevole");
                AmichevoleProva1 ap = (AmichevoleProva1) listaEventi.get(indexRow);
                typeOrTeamLabel.setText("Avversari:");
                hybridText.setText(ap.getSquadraAvversaria());
                noteText.setVisible(false);
                noteLabel.setVisible(false);

            }

        }
    }

    private void setUpPresenzaDialog() {

        if (listaEventi.get(indexRow) instanceof AllenamentoProva1) {
            jLabel4.setText("Fornisci adesione allenamento" + " " + listaEventi.get(indexRow).getId());
        } else if (listaEventi.get(indexRow) instanceof AmichevoleProva1) {
            jLabel4.setText("Fornisci adesione amichevole" + " " + listaEventi.get(indexRow).getId());
        }

        adesioneDialog.setTitle("Fornisci presenza");
        adesioneDialog.setLocationRelativeTo(null);
        adesioneDialog.setResizable(false);
        adesioneDialog.setModal(true);
        motivazioneLabel.setVisible(false);
        motivazioneArea.setVisible(false);
        presente.setSelected(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detailsDialog = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        durationLabel = new javax.swing.JLabel();
        typeOrTeamLabel = new javax.swing.JLabel();
        dateText = new javax.swing.JLabel();
        durationText = new javax.swing.JLabel();
        timeText = new javax.swing.JLabel();
        hybridText = new javax.swing.JLabel();
        placeLabel = new javax.swing.JLabel();
        dispText = new javax.swing.JLabel();
        noteLabel = new javax.swing.JLabel();
        noteText = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        placeLabel1 = new javax.swing.JLabel();
        placeText = new javax.swing.JLabel();
        adesioneDialog = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        presente = new javax.swing.JCheckBox();
        assente = new javax.swing.JCheckBox();
        motivazioneLabel = new javax.swing.JLabel();
        motivazioneArea = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        presenzaGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        disponibilitaButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        showDetailsBtn = new javax.swing.JButton();

        detailsDialog.setMinimumSize(new java.awt.Dimension(400, 400));
        detailsDialog.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Dettagli");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Evento");

        jPanel1.setBackground(new java.awt.Color(252, 248, 253));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dateLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dateLabel.setText("data:");

        timeLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        timeLabel.setText("orario:");

        durationLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        durationLabel.setText("durata:");

        typeOrTeamLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        typeOrTeamLabel.setText("Tipologia/Squadra:");

        dateText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dateText.setText("jLabel8");

        durationText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        durationText.setText("jLabel8");

        timeText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        timeText.setText("jLabel8");

        hybridText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        hybridText.setText("jLabel8");

        placeLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeLabel.setText("luogo:");

        dispText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dispText.setText("jLabel8");

        noteLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        noteLabel.setText("note:");

        noteText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        noteText.setText("jLabel8");

        jButton5.setBackground(new java.awt.Color(250, 250, 250));
        jButton5.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton5.setText("Back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        placeLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeLabel1.setText("disponibiltà:");

        placeText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeText.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(placeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(durationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(280, 280, 280))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(placeLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(typeOrTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(noteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noteText, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(dateText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                .addComponent(timeText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(durationText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(placeText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(hybridText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dispText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(timeText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durationLabel)
                    .addComponent(durationText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeLabel)
                    .addComponent(placeText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeLabel1)
                    .addComponent(dispText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeOrTeamLabel)
                    .addComponent(hybridText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noteLabel)
                    .addComponent(noteText))
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout detailsDialogLayout = new javax.swing.GroupLayout(detailsDialog.getContentPane());
        detailsDialog.getContentPane().setLayout(detailsDialogLayout);
        detailsDialogLayout.setHorizontalGroup(
            detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsDialogLayout.createSequentialGroup()
                .addGroup(detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsDialogLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailsDialogLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        detailsDialogLayout.setVerticalGroup(
            detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        adesioneDialog.setMinimumSize(new java.awt.Dimension(400, 400));
        adesioneDialog.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Fornisci Presenza");

        jPanel2.setBackground(new java.awt.Color(243, 252, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setMaximumSize(new java.awt.Dimension(356, 266));
        jPanel2.setMinimumSize(new java.awt.Dimension(356, 266));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel5.setText("Seleziona:");

        presente.setBackground(new java.awt.Color(243, 252, 253));
        presenzaGroup.add(presente);
        presente.setText("Presente");
        presente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presenteActionPerformed(evt);
            }
        });

        assente.setBackground(new java.awt.Color(243, 252, 253));
        presenzaGroup.add(assente);
        assente.setText("Assente");
        assente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assenteActionPerformed(evt);
            }
        });

        motivazioneLabel.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        motivazioneLabel.setText("Motivazione:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        motivazioneArea.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 204, 51));
        jButton1.setText("Invio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(248, 245, 245));
        jButton2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton2.setText("Back");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(motivazioneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(presente, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(assente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(motivazioneArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(presente)
                    .addComponent(assente))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(motivazioneArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(motivazioneLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout adesioneDialogLayout = new javax.swing.GroupLayout(adesioneDialog.getContentPane());
        adesioneDialog.getContentPane().setLayout(adesioneDialogLayout);
        adesioneDialogLayout.setHorizontalGroup(
            adesioneDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adesioneDialogLayout.createSequentialGroup()
                .addGroup(adesioneDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adesioneDialogLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(adesioneDialogLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        adesioneDialogLayout.setVerticalGroup(
            adesioneDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adesioneDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Data", "Orario", "Durata", "Luogo", "Adesione fornita"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(28);
        jScrollPane1.setViewportView(jTable1);

        disponibilitaButton.setBackground(new java.awt.Color(204, 255, 204));
        disponibilitaButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        disponibilitaButton.setForeground(new java.awt.Color(0, 204, 102));
        disponibilitaButton.setText("+Disponibilità");
        disponibilitaButton.setAlignmentY(0.0F);
        disponibilitaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disponibilitaButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel1.setText("Calendario Eventi");

        jButton4.setBackground(new java.awt.Color(233, 233, 248));
        jButton4.setText("Back");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        showDetailsBtn.setBackground(new java.awt.Color(233, 233, 248));
        showDetailsBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        showDetailsBtn.setText("+ dettagli");
        showDetailsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDetailsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(showDetailsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179)
                        .addComponent(disponibilitaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(showDetailsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(disponibilitaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void disponibilitaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disponibilitaButtonActionPerformed
        // TODO add your handling code here:
        if (listaEventi.get(indexRow).isDisponibilita()) {
             JOptionPane.showMessageDialog(
                    null,
                    "Disponibilità già fornita!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        } else {
            adesioneDialog.setVisible(true);
        }
    }//GEN-LAST:event_disponibilitaButtonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //initialiteCalendar();
        //parentFrame.cardLayout.show(parentFrame.getjPanel1(), "COACHPANEL");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void showDetailsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDetailsBtnActionPerformed
        // TODO add your handling code here:
        detailsDialog.setVisible(true);
    }//GEN-LAST:event_showDetailsBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        detailsDialog.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void presenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presenteActionPerformed
        // TODO add your handling code here:
        motivazioneLabel.setVisible(false);
        motivazioneArea.setVisible(false);
    }//GEN-LAST:event_presenteActionPerformed

    private void assenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assenteActionPerformed
        // TODO add your handling code here:
        motivazioneLabel.setVisible(true);
        motivazioneArea.setVisible(true);
    }//GEN-LAST:event_assenteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        listaEventi.get(indexRow).setDisponibilita(true);
        adesioneDialog.setVisible(false);
        initialiteCalendar();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog adesioneDialog;
    private javax.swing.JCheckBox assente;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateText;
    private javax.swing.JDialog detailsDialog;
    private javax.swing.JLabel dispText;
    private javax.swing.JButton disponibilitaButton;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JLabel durationText;
    private javax.swing.JLabel hybridText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JScrollPane motivazioneArea;
    private javax.swing.JLabel motivazioneLabel;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JLabel noteText;
    private javax.swing.JLabel placeLabel;
    private javax.swing.JLabel placeLabel1;
    private javax.swing.JLabel placeText;
    private javax.swing.JCheckBox presente;
    private javax.swing.ButtonGroup presenzaGroup;
    private javax.swing.JButton showDetailsBtn;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel timeText;
    private javax.swing.JLabel typeOrTeamLabel;
    // End of variables declaration//GEN-END:variables
}
