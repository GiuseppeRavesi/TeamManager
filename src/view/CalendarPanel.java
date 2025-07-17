/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Font;
import static java.lang.Integer.parseInt;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import view.TeamManagerGUI;

/**
 *
 * @author enzov
 */
public class CalendarPanel extends javax.swing.JPanel {

    /**
     * Creates new form CalendarPanel
     */
    private TeamManagerGUI parentFrame;
    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer rightRenderer;

    private java.util.List<EventoProva> listaEventi;

    private int indexRow;

    private int idCalendarLatest;

    public CalendarPanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        tableModel = (DefaultTableModel) jTable1.getModel();
        rightRenderer = new DefaultTableCellRenderer();

        listaEventi = new ArrayList<>();
        setUpEventiProva();

        initialiteCalendar();
        tableClickListener();

        idCalendarLatest = 20;

    }

    private void putAtRightElement() {

        for (int x = 0; x < jTable1.getColumnCount(); x++) {

            jTable1.getColumnModel().getColumn(x).setCellRenderer(rightRenderer);

        }
    }

    private void setUpEventiProva() {
        listaEventi.add(new AllenamentoProva(1, LocalDate.of(2025, 7, 1), LocalTime.of(17, 0), 90, "Campo A", "Tecnico", "Passaggi e movimenti"));
        listaEventi.add(new AmichevoleProva(2, LocalDate.of(2025, 7, 2), LocalTime.of(18, 30), 120, "Stadio B", "Squadra Blu"));
        listaEventi.add(new AllenamentoProva(3, LocalDate.of(2025, 7, 3), LocalTime.of(16, 0), 60, "Campo A", "Tattico", "Prove di schema"));
        listaEventi.add(new AmichevoleProva(4, LocalDate.of(2025, 7, 4), LocalTime.of(20, 0), 110, "Stadio C", "Squadra Rossa"));
        listaEventi.add(new AllenamentoProva(5, LocalDate.of(2025, 7, 5), LocalTime.of(15, 30), 75, "Campo B", "Fisico", "Circuito resistenza"));

        listaEventi.add(new AmichevoleProva(6, LocalDate.of(2025, 7, 6), LocalTime.of(17, 45), 90, "Stadio D", "Squadra Verde"));
        listaEventi.add(new AllenamentoProva(7, LocalDate.of(2025, 7, 7), LocalTime.of(16, 15), 90, "Campo C", "Misto", "Tattico + Fisico"));
        listaEventi.add(new AmichevoleProva(8, LocalDate.of(2025, 7, 8), LocalTime.of(19, 0), 100, "Stadio E", "Squadra Gialla"));
        listaEventi.add(new AllenamentoProva(9, LocalDate.of(2025, 7, 9), LocalTime.of(18, 0), 80, "Campo D", "Tecnico", "Controllo e tiro"));
        listaEventi.add(new AllenamentoProva(10, LocalDate.of(2025, 7, 10), LocalTime.of(17, 30), 60, "Campo A", "Fisico", "Corsa e scatti"));

        listaEventi.add(new AmichevoleProva(11, LocalDate.of(2025, 7, 11), LocalTime.of(20, 0), 120, "Stadio F", "Squadra Nera"));
        listaEventi.add(new AllenamentoProva(12, LocalDate.of(2025, 7, 12), LocalTime.of(17, 0), 90, "Campo B", "Tattico", "Partita a tema"));
        listaEventi.add(new AmichevoleProva(13, LocalDate.of(2025, 7, 13), LocalTime.of(18, 15), 110, "Stadio G", "Squadra Bianca"));
        listaEventi.add(new AllenamentoProva(14, LocalDate.of(2025, 7, 14), LocalTime.of(16, 45), 75, "Campo A", "Tecnico", "Palleggi e dribbling"));
        listaEventi.add(new AllenamentoProva(15, LocalDate.of(2025, 7, 15), LocalTime.of(16, 0), 60, "Campo C", "Misto", "Ripasso generale"));

        listaEventi.add(new AmichevoleProva(16, LocalDate.of(2025, 7, 16), LocalTime.of(19, 30), 95, "Stadio H", "Squadra Viola"));
        listaEventi.add(new AllenamentoProva(17, LocalDate.of(2025, 7, 17), LocalTime.of(17, 0), 90, "Campo D", "Tattico", "Moduli difensivi"));
        listaEventi.add(new AmichevoleProva(18, LocalDate.of(2025, 7, 18), LocalTime.of(20, 0), 105, "Stadio I", "Squadra Arancione"));
        listaEventi.add(new AllenamentoProva(19, LocalDate.of(2025, 7, 19), LocalTime.of(16, 30), 70, "Campo B", "Fisico", "Agilità e equilibrio"));
        listaEventi.add(new AmichevoleProva(20, LocalDate.of(2025, 7, 20), LocalTime.of(18, 45), 115, "Stadio L", "Squadra Marrone"));

    }

    private void initialiteCalendar() {
        indexRow = -1;
        putAtRightElement();
        tableModel.setRowCount(0);
        jTable1.setSelectionModel(new ForcedListSelectionModel());
        jTable1.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));
        showDetailsBtn.setVisible(false);

        for (EventoProva e : listaEventi) {
            Object[] row = {e.getId(), e.getData(), e.getOrario(), e.getDurata(), e.getLuogo()};
            tableModel.addRow(row);
        }

    }
    
    public void logout(){
        initialiteCalendar();
    }

    private void tableClickListener() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                indexRow = row;
                System.out.println("Riga attuale: " + indexRow);
                showDetailsBtn.setVisible(true);
                setUpDetailsDialog();
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

            if (listaEventi.get(indexRow) instanceof AllenamentoProva) {
                jLabel3.setText("Allenamento");
                typeOrTeamLabel.setText("tipologia:");
                AllenamentoProva ap = (AllenamentoProva) listaEventi.get(indexRow);
                noteText.setText(ap.getNote());
                hybridText.setText(ap.getTipologia());
                noteText.setVisible(true);
                noteLabel.setVisible(true);
            } else if (listaEventi.get(indexRow) instanceof AmichevoleProva) {
                jLabel3.setText("Amichevole");
                AmichevoleProva ap = (AmichevoleProva) listaEventi.get(indexRow);
                typeOrTeamLabel.setText("Avversari:");
                hybridText.setText(ap.getSquadraAvversaria());
                noteText.setVisible(false);
                noteLabel.setVisible(false);

            }

        }
    }

    private void setUpRemoveDialog() {
        removeEventDialog.setLocationRelativeTo(null);
        removeEventDialog.setTitle("Rimuovi evento");
        removeEventDialog.setResizable(false);
        removeEventDialog.setModal(true);

        if (indexRow >= 0) {
            dateText1.setText(listaEventi.get(indexRow).getData().toString());
            timeText1.setText(listaEventi.get(indexRow).getOrario().toString());
            durationText1.setText(String.valueOf(listaEventi.get(indexRow).getDurata()) + " " + "min");
            placeText1.setText(listaEventi.get(indexRow).getLuogo());

            if (listaEventi.get(indexRow) instanceof AllenamentoProva) {
                jLabel10.setText("Sei sicuro di voler eliminare questo allenamento?");
                typeOrTeamLabel1.setText("tipologia:");
                AllenamentoProva ap = (AllenamentoProva) listaEventi.get(indexRow);
                noteText1.setText(ap.getNote());
                hybridText1.setText(ap.getTipologia());
                noteText1.setVisible(true);
                noteLabel3.setVisible(true);
            } else if (listaEventi.get(indexRow) instanceof AmichevoleProva) {
                jLabel10.setText("Sei sicuro di voler eliminare questo amichevole?");
                AmichevoleProva ap = (AmichevoleProva) listaEventi.get(indexRow);
                typeOrTeamLabel1.setText("Avversari:");
                hybridText1.setText(ap.getSquadraAvversaria());
                noteText1.setVisible(false);
                noteLabel3.setVisible(false);

            }
            
            removeEventDialog.setVisible(true);

        }else{
             JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un evento!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }

    private void setUpCreateEventDialog() {
        createEventDialog.setLocationRelativeTo(null);
        createEventDialog.setTitle("Crea Evento");
        createEventDialog.setResizable(false);
        createEventDialog.setModal(true);
        allenamentoRadio.setSelected(true);

        jDateChooser1.setDate(null);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jTextField1.setText("");
        jTextField2.setText("");
        jTextArea1.setText("");
        hybridLabel.setText("Tipologia:");
        jScrollPane2.setVisible(true);

        createEventDialog.setVisible(true);

    }

    private void setUpModifyEventDialog() {
        if (indexRow >= 0) {

            modifyEventDialog.setLocationRelativeTo(null);
            modifyEventDialog.setResizable(false);
            modifyEventDialog.setModal(true);
            noteLabel2.setVisible(false);
            jScrollPane3.setVisible(false);

            jDateChooser2.setDate(localDateToDate(listaEventi.get(indexRow).getData()));

            for (int i = 1; i <= 24; i++) {
                if (listaEventi.get(indexRow).getOrario().getHour() == i - 1) {
                    jComboBox4.setSelectedIndex(i);
                }
            }

            for (int i = 1; i <= 60; i++) {
                if (listaEventi.get(indexRow).getOrario().getMinute() == i - 1) {
                    jComboBox5.setSelectedIndex(i);
                }
            }

            for (int i = 30; i <= 299; i++) {
                if (listaEventi.get(indexRow).getDurata() == i) {
                    jComboBox6.setSelectedIndex(i - 29);
                }
            }

            jTextField3.setText(listaEventi.get(indexRow).getLuogo());

            if (listaEventi.get(indexRow) instanceof AllenamentoProva) {
                modifyEventDialog.setTitle("Modifica allenamento");
                jTextField4.setText(((AllenamentoProva) listaEventi.get(indexRow)).getTipologia());
                noteLabel2.setVisible(true);
                jScrollPane3.setVisible(true);
                jTextArea2.setText(((AllenamentoProva) listaEventi.get(indexRow)).getNote());
                jLabel11.setText("Modifica allenamento");
                hybridLabel1.setText("Tipologia:");

            } else if (listaEventi.get(indexRow) instanceof AmichevoleProva) {
                modifyEventDialog.setTitle("Modifica amichevole");
                jTextField4.setText(((AmichevoleProva) listaEventi.get(indexRow)).getSquadraAvversaria());
                jLabel11.setText("Modifica amichevole");
                hybridLabel1.setText("Avversari:");
            }

            modifyEventDialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un evento!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }

    }

    private LocalDate dateToLocalDate(Date d) {
        Instant instant = d.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
        placeText = new javax.swing.JLabel();
        noteLabel = new javax.swing.JLabel();
        noteText = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        createEventDialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        amichevoleRadio = new javax.swing.JRadioButton();
        allenamentoRadio = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        dots = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        hybridLabel = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        noteLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        createEventGroup = new javax.swing.ButtonGroup();
        modifyEventDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        dots1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        hybridLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        noteLabel2 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        removeEventDialog = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        dateLabel1 = new javax.swing.JLabel();
        timeLabel1 = new javax.swing.JLabel();
        durationLabel1 = new javax.swing.JLabel();
        typeOrTeamLabel1 = new javax.swing.JLabel();
        dateText1 = new javax.swing.JLabel();
        durationText1 = new javax.swing.JLabel();
        timeText1 = new javax.swing.JLabel();
        hybridText1 = new javax.swing.JLabel();
        placeLabel1 = new javax.swing.JLabel();
        placeText1 = new javax.swing.JLabel();
        noteLabel3 = new javax.swing.JLabel();
        noteText1 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        showDetailsBtn = new javax.swing.JButton();

        detailsDialog.setMinimumSize(new java.awt.Dimension(400, 400));

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

        placeText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeText.setText("jLabel8");

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
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(placeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(typeOrTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(durationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(hybridText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(durationText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(placeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateText, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(noteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(noteText, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateText))
                .addGap(12, 12, 12)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeOrTeamLabel)
                    .addComponent(hybridText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(noteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noteText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        javax.swing.GroupLayout detailsDialogLayout = new javax.swing.GroupLayout(detailsDialog.getContentPane());
        detailsDialog.getContentPane().setLayout(detailsDialogLayout);
        detailsDialogLayout.setHorizontalGroup(
            detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsDialogLayout.createSequentialGroup()
                .addGroup(detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsDialogLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(detailsDialogLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        detailsDialogLayout.setVerticalGroup(
            detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        createEventDialog.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel2.setBackground(new java.awt.Color(234, 243, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("TIPOLOGIA EVENTO");

        amichevoleRadio.setBackground(new java.awt.Color(234, 243, 245));
        createEventGroup.add(amichevoleRadio);
        amichevoleRadio.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        amichevoleRadio.setText("Amichevole");
        amichevoleRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amichevoleRadioActionPerformed(evt);
            }
        });

        allenamentoRadio.setBackground(new java.awt.Color(234, 243, 245));
        createEventGroup.add(allenamentoRadio);
        allenamentoRadio.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        allenamentoRadio.setText("Allenamento");
        allenamentoRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allenamentoRadioActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel5.setText("Crea evento");

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N
        jLabel6.setText("data:");

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel7.setText("orario:");

        jComboBox1.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HH", "00", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MM", "00  ", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        dots.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dots.setText(":");

        jLabel8.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel8.setText("durata:");

        jComboBox3.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "durata (min)", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  ", "100  ", "101  ", "102  ", "103  ", "104  ", "105  ", "106  ", "107  ", "108  ", "109  ", "110  ", "111  ", "112  ", "113  ", "114  ", "115  ", "116  ", "117  ", "118  ", "119  ", "120  ", "121  ", "122  ", "123  ", "124  ", "125  ", "126  ", "127  ", "128  ", "129  ", "130  ", "131  ", "132  ", "133  ", "134  ", "135  ", "136  ", "137  ", "138  ", "139  ", "140  ", "141  ", "142  ", "143  ", "144  ", "145  ", "146  ", "147  ", "148  ", "149  ", "150  ", "151  ", "152  ", "153  ", "154  ", "155  ", "156  ", "157  ", "158  ", "159  ", "160  ", "161  ", "162  ", "163  ", "164  ", "165  ", "166  ", "167  ", "168  ", "169  ", "170  ", "171  ", "172  ", "173  ", "174  ", "175  ", "176  ", "177  ", "178  ", "179  ", "180  ", "181  ", "182  ", "183  ", "184  ", "185  ", "186  ", "187  ", "188  ", "189  ", "190  ", "191  ", "192  ", "193  ", "194  ", "195  ", "196  ", "197  ", "198  ", "199  ", "200  ", "201  ", "202  ", "203  ", "204  ", "205  ", "206  ", "207  ", "208  ", "209  ", "210  ", "211  ", "212  ", "213  ", "214  ", "215  ", "216  ", "217  ", "218  ", "219  ", "220  ", "221  ", "222  ", "223  ", "224  ", "225  ", "226  ", "227  ", "228  ", "229  ", "230  ", "231  ", "232  ", "233  ", "234  ", "235  ", "236  ", "237  ", "238  ", "239  ", "240  ", "241  ", "242  ", "243  ", "244  ", "245  ", "246  ", "247  ", "248  ", "249  ", "250  ", "251  ", "252  ", "253  ", "254  ", "255  ", "256  ", "257  ", "258  ", "259  ", "260  ", "261  ", "262  ", "263  ", "264  ", "265  ", "266  ", "267  ", "268  ", "269  ", "270  ", "271  ", "272  ", "273  ", "274  ", "275  ", "276  ", "277  ", "278  ", "279  ", "280  ", "281  ", "282  ", "283  ", "284  ", "285  ", "286  ", "287  ", "288  ", "289  ", "290  ", "291  ", "292  ", "293  ", "294  ", "295  ", "296  ", "297  ", "298  ", "299" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel9.setText("luogo:");

        jTextField1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N

        hybridLabel.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        hybridLabel.setText("Tipologia:");

        jTextField2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N

        jScrollPane2.setPreferredSize(new java.awt.Dimension(248, 96));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane2.setViewportView(jTextArea1);

        noteLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        noteLabel1.setText("note:");

        jButton6.setBackground(new java.awt.Color(204, 255, 204));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 153, 51));
        jButton6.setText("OK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(243, 246, 246));
        jButton7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jButton7.setText("Back");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hybridLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noteLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dots, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField1)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, 249, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 135, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(allenamentoRadio)
                        .addComponent(amichevoleRadio)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dots, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hybridLabel)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noteLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allenamentoRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amichevoleRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout createEventDialogLayout = new javax.swing.GroupLayout(createEventDialog.getContentPane());
        createEventDialog.getContentPane().setLayout(createEventDialogLayout);
        createEventDialogLayout.setHorizontalGroup(
            createEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        createEventDialogLayout.setVerticalGroup(
            createEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        modifyEventDialog.setMinimumSize(new java.awt.Dimension(500, 500));
        modifyEventDialog.setPreferredSize(new java.awt.Dimension(500, 500));

        jPanel3.setBackground(new java.awt.Color(234, 243, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel11.setText("Modifica");

        jLabel12.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N
        jLabel12.setText("data:");

        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel13.setText("orario:");

        jComboBox4.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HH", "00", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox5.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox5.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MM", "00  ", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        dots1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dots1.setText(":");

        jLabel14.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel14.setText("durata:");

        jComboBox6.setBackground(new java.awt.Color(255, 253, 253));
        jComboBox6.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "durata (min)", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  ", "100  ", "101  ", "102  ", "103  ", "104  ", "105  ", "106  ", "107  ", "108  ", "109  ", "110  ", "111  ", "112  ", "113  ", "114  ", "115  ", "116  ", "117  ", "118  ", "119  ", "120  ", "121  ", "122  ", "123  ", "124  ", "125  ", "126  ", "127  ", "128  ", "129  ", "130  ", "131  ", "132  ", "133  ", "134  ", "135  ", "136  ", "137  ", "138  ", "139  ", "140  ", "141  ", "142  ", "143  ", "144  ", "145  ", "146  ", "147  ", "148  ", "149  ", "150  ", "151  ", "152  ", "153  ", "154  ", "155  ", "156  ", "157  ", "158  ", "159  ", "160  ", "161  ", "162  ", "163  ", "164  ", "165  ", "166  ", "167  ", "168  ", "169  ", "170  ", "171  ", "172  ", "173  ", "174  ", "175  ", "176  ", "177  ", "178  ", "179  ", "180  ", "181  ", "182  ", "183  ", "184  ", "185  ", "186  ", "187  ", "188  ", "189  ", "190  ", "191  ", "192  ", "193  ", "194  ", "195  ", "196  ", "197  ", "198  ", "199  ", "200  ", "201  ", "202  ", "203  ", "204  ", "205  ", "206  ", "207  ", "208  ", "209  ", "210  ", "211  ", "212  ", "213  ", "214  ", "215  ", "216  ", "217  ", "218  ", "219  ", "220  ", "221  ", "222  ", "223  ", "224  ", "225  ", "226  ", "227  ", "228  ", "229  ", "230  ", "231  ", "232  ", "233  ", "234  ", "235  ", "236  ", "237  ", "238  ", "239  ", "240  ", "241  ", "242  ", "243  ", "244  ", "245  ", "246  ", "247  ", "248  ", "249  ", "250  ", "251  ", "252  ", "253  ", "254  ", "255  ", "256  ", "257  ", "258  ", "259  ", "260  ", "261  ", "262  ", "263  ", "264  ", "265  ", "266  ", "267  ", "268  ", "269  ", "270  ", "271  ", "272  ", "273  ", "274  ", "275  ", "276  ", "277  ", "278  ", "279  ", "280  ", "281  ", "282  ", "283  ", "284  ", "285  ", "286  ", "287  ", "288  ", "289  ", "290  ", "291  ", "292  ", "293  ", "294  ", "295  ", "296  ", "297  ", "298  ", "299" }));

        jLabel15.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        jLabel15.setText("luogo:");

        jTextField3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N

        hybridLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        hybridLabel1.setText("Tipologia:");

        jTextField4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N

        jScrollPane3.setPreferredSize(new java.awt.Dimension(248, 96));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 13)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane3.setViewportView(jTextArea2);

        noteLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        noteLabel2.setText("note:");

        jButton8.setBackground(new java.awt.Color(204, 255, 204));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 153, 51));
        jButton8.setText("OK");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(243, 246, 246));
        jButton9.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jButton9.setText("Back");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hybridLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noteLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField4)
                                        .addComponent(jTextField3)
                                        .addComponent(jComboBox6, 0, 249, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dots1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 147, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dots1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hybridLabel1)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noteLabel2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout modifyEventDialogLayout = new javax.swing.GroupLayout(modifyEventDialog.getContentPane());
        modifyEventDialog.getContentPane().setLayout(modifyEventDialogLayout);
        modifyEventDialogLayout.setHorizontalGroup(
            modifyEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modifyEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        modifyEventDialogLayout.setVerticalGroup(
            modifyEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifyEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        removeEventDialog.setMinimumSize(new java.awt.Dimension(400, 400));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Sei sicuro di voler eliminare questo evento?");

        jPanel4.setBackground(new java.awt.Color(252, 248, 253));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dateLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dateLabel1.setText("data:");

        timeLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        timeLabel1.setText("orario:");

        durationLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        durationLabel1.setText("durata:");

        typeOrTeamLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        typeOrTeamLabel1.setText("Tipologia/Squadra:");

        dateText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        dateText1.setText("jLabel8");

        durationText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        durationText1.setText("jLabel8");

        timeText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        timeText1.setText("jLabel8");

        hybridText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        hybridText1.setText("jLabel8");

        placeLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeLabel1.setText("luogo:");

        placeText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        placeText1.setText("jLabel8");

        noteLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        noteLabel3.setText("note:");

        noteText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        noteText1.setText("jLabel8");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(placeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(typeOrTeamLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(durationLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(hybridText1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(durationText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timeText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(placeText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateText1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(noteLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(noteText1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel1)
                    .addComponent(dateText1))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel1)
                    .addComponent(timeText1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durationLabel1)
                    .addComponent(durationText1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeLabel1)
                    .addComponent(placeText1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeOrTeamLabel1)
                    .addComponent(hybridText1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(noteLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noteText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jButton10.setBackground(new java.awt.Color(204, 255, 204));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(0, 204, 51));
        jButton10.setText("Conferma");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(238, 246, 248));
        jButton11.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton11.setText("Back");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout removeEventDialogLayout = new javax.swing.GroupLayout(removeEventDialog.getContentPane());
        removeEventDialog.getContentPane().setLayout(removeEventDialogLayout);
        removeEventDialogLayout.setHorizontalGroup(
            removeEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeEventDialogLayout.createSequentialGroup()
                .addGroup(removeEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(removeEventDialogLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButton11)
                        .addGap(84, 84, 84)
                        .addComponent(jButton10))
                    .addGroup(removeEventDialogLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, removeEventDialogLayout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        removeEventDialogLayout.setVerticalGroup(
            removeEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(removeEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(248, 251, 252));
        setMinimumSize(new java.awt.Dimension(800, 600));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Data", "Orario", "Durata", "Luogo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 204, 102));
        jButton1.setText("+");
        jButton1.setAlignmentY(0.0F);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 0, 0));
        jButton2.setText("X");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jButton3.setText("Modifica");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(showDetailsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addComponent(showDetailsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setUpRemoveDialog();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setUpModifyEventDialog();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setUpCreateEventDialog();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void showDetailsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDetailsBtnActionPerformed
        // TODO add your handling code here:
        detailsDialog.setVisible(true);
    }//GEN-LAST:event_showDetailsBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int flag = 0;

        if (jDateChooser1.getDate() == null || jComboBox1.getSelectedIndex() == 0 || jComboBox2.getSelectedIndex() == 0
                || jComboBox3.getSelectedIndex() == 0 || jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty()) {
            flag = 1;
        }

        if (flag == 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Riempire tutti i campi!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        } else {
            Date data = jDateChooser1.getDate();
            int ora = parseInt(jComboBox1.getSelectedItem().toString().replaceAll("\\s+", ""));
            int min = parseInt(jComboBox2.getSelectedItem().toString().replaceAll("\\s+", ""));
            int durata = parseInt(jComboBox3.getSelectedItem().toString().replaceAll("\\s+", ""));
            String luogo = jTextField1.getText();
            String ibrido = jTextField2.getText();
            String note = jTextArea1.getText();
            idCalendarLatest++;

            System.out.println(jComboBox1.getSelectedItem().toString());

            if (allenamentoRadio.isSelected()) {

                listaEventi.add(new AllenamentoProva(idCalendarLatest, dateToLocalDate(data),
                        LocalTime.of(ora, min), durata, luogo, ibrido, note));

            } else if (amichevoleRadio.isSelected()) {
                listaEventi.add(new AmichevoleProva(idCalendarLatest, dateToLocalDate(data),
                        LocalTime.of(ora, min), durata, luogo, ibrido));
            }

            initialiteCalendar();
            createEventDialog.setVisible(false);
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        createEventDialog.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void amichevoleRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amichevoleRadioActionPerformed
        // TODO add your handling code here:
        hybridLabel.setText("Avversari:");
        noteLabel1.setVisible(false);
        jScrollPane2.setVisible(false);
        jTextArea1.setText("");
    }//GEN-LAST:event_amichevoleRadioActionPerformed

    private void allenamentoRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allenamentoRadioActionPerformed
        // TODO add your handling code here:
        hybridLabel.setText("Tipologia:");
        noteLabel1.setVisible(true);
        jScrollPane2.setVisible(true);

    }//GEN-LAST:event_allenamentoRadioActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int flag = 0;

        if (jDateChooser2.getDate() == null || jComboBox4.getSelectedIndex() == 0 || jComboBox5.getSelectedIndex() == 0
                || jComboBox6.getSelectedIndex() == 0 || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty()) {
            flag++;
        }

        if (flag == 1) {

            JOptionPane.showMessageDialog(
                    null,
                    "Riempire tutti i campi!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );

        } else {
            Date data = jDateChooser2.getDate();
            int ora = parseInt(jComboBox4.getSelectedItem().toString().replaceAll("\\s+", ""));
            int min = parseInt(jComboBox5.getSelectedItem().toString().replaceAll("\\s+", ""));
            int durata = parseInt(jComboBox6.getSelectedItem().toString().replaceAll("\\s+", ""));
            String luogo = jTextField3.getText();
            String ibrido = jTextField4.getText();
            String note = jTextArea2.getText();

            listaEventi.get(indexRow).setData(dateToLocalDate(data));
            listaEventi.get(indexRow).setOrario(LocalTime.of(ora, min));
            listaEventi.get(indexRow).setDurata(durata);
            listaEventi.get(indexRow).setLuogo(luogo);

            if (listaEventi.get(indexRow) instanceof AllenamentoProva) {
                AllenamentoProva ap = (AllenamentoProva) listaEventi.get(indexRow);
                ap.setTipologia(ibrido);
                ap.setNote(note);
            } else if (listaEventi.get(indexRow) instanceof AmichevoleProva) {
                AmichevoleProva ap = (AmichevoleProva) listaEventi.get(indexRow);
                ap.setSquadraAvversaria(ibrido);
            }

            initialiteCalendar();
            modifyEventDialog.setVisible(false);
        }


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        modifyEventDialog.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        listaEventi.remove(listaEventi.get(indexRow));
        initialiteCalendar();
        removeEventDialog.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        initialiteCalendar();
        parentFrame.cardLayout.show(parentFrame.getjPanel1(), "COACHPANEL");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        detailsDialog.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        removeEventDialog.setVisible(false);
    }//GEN-LAST:event_jButton11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton allenamentoRadio;
    private javax.swing.JRadioButton amichevoleRadio;
    private javax.swing.JDialog createEventDialog;
    private javax.swing.ButtonGroup createEventGroup;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JLabel dateText;
    private javax.swing.JLabel dateText1;
    private javax.swing.JDialog detailsDialog;
    private javax.swing.JLabel dots;
    private javax.swing.JLabel dots1;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JLabel durationLabel1;
    private javax.swing.JLabel durationText;
    private javax.swing.JLabel durationText1;
    private javax.swing.JLabel hybridLabel;
    private javax.swing.JLabel hybridLabel1;
    private javax.swing.JLabel hybridText;
    private javax.swing.JLabel hybridText1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JDialog modifyEventDialog;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JLabel noteLabel1;
    private javax.swing.JLabel noteLabel2;
    private javax.swing.JLabel noteLabel3;
    private javax.swing.JLabel noteText;
    private javax.swing.JLabel noteText1;
    private javax.swing.JLabel placeLabel;
    private javax.swing.JLabel placeLabel1;
    private javax.swing.JLabel placeText;
    private javax.swing.JLabel placeText1;
    private javax.swing.JDialog removeEventDialog;
    private javax.swing.JButton showDetailsBtn;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel timeLabel1;
    private javax.swing.JLabel timeText;
    private javax.swing.JLabel timeText1;
    private javax.swing.JLabel typeOrTeamLabel;
    private javax.swing.JLabel typeOrTeamLabel1;
    // End of variables declaration//GEN-END:variables
}
