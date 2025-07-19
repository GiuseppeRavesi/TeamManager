/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Allenamento;
import model.Amichevole;
import model.Disponibilità;
import model.Evento;
import model.GiocatoreInRosa;
import model.StatisticaAllenamento;
import model.StatisticaAmichevole;

/**
 *
 * @author enzov
 */
public class ProgressPanel extends javax.swing.JPanel {

    /**
     * Creates new form ProgressPanel
     */
    private TeamManagerGUI parentFrame;

    private java.util.List<GiocatoreInRosa> rosa;
    private DefaultListModel<String> model;

    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer rightRenderer;

    private java.util.List<Disponibilità> listaDisponibilita;
    private java.util.List<Evento> eventi;

    private int indexRow;

    private int indexListElement;

    public ProgressPanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        rosa = parentFrame.getTM().visualizzaRosa();

        model = new DefaultListModel<String>();

        tableModel = (DefaultTableModel) jTable1.getModel();
        rightRenderer = new DefaultTableCellRenderer();

        eventi = parentFrame.getTM().visualizzaCalendario();

        initializeList();
        setMouseListenerJList1();

    }

    private void initializeList() {
        indexListElement = -1;
        model.clear();
        for (GiocatoreInRosa p : rosa) {
            model.addElement(p.toString());
        }
        jList1.setModel(model);

        // Imposta font monospaziato per allineare i campi
        jList1.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));

        String numGiocatoriRosa = rosa.size() + " / " + 22;
        numGiocatoriLabel.setText(numGiocatoriRosa);

        if (rosa.size() < 22) {
            jLabel1.setText("Rosa Attuale - Incompleta");
            numGiocatoriLabel.setForeground(Color.red);

        } else {
            jLabel1.setText("Rosa Attuale");
            numGiocatoriLabel.setForeground(Color.GREEN);
        }

    }

    public void initCard() {
        initializeList();
    }

    public void logout() {
        parentFrame.getSession().logout();
    }

    private void setMouseListenerJList1() {
        jList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) {
                    JList target = (JList) me.getSource();
                    int index = target.locationToIndex(me.getPoint());
                    if (index >= 0) {
                        Object item = target.getModel().getElementAt(index);
                    } else {
                        index = -1;
                    }
                    indexListElement = index;
                }

            }
        });

    }

    private void setUpDispDialog() {

        if (indexListElement >= 0) {
            initialiteCalendar();
            dispDialog.setLocationRelativeTo(null);
            dispDialog.setTitle("Lista disponibilità");
            dispDialog.setResizable(false);
            dispDialog.setModal(true);

            dispDialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un giocatore dalla lista!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }

    }

    private void setUpStatAllenamento(Evento ev) {
        statAllenamento.setLocationRelativeTo(null);
        statAllenamento.setTitle("Statistiche allenamento");
        statAllenamento.setResizable(false);
        statAllenamento.setModal(true);

        int id = ev.getId();
        idAllenamento.setText(String.valueOf(id));

        statAllenamento.setVisible(true);
    }

    private void setUpStatAmichevole(Evento ev) {
        statAmichevole.setLocationRelativeTo(null);
        statAmichevole.setTitle("Statistiche amichevole");
        statAmichevole.setResizable(false);
        statAmichevole.setModal(true);

        int id = ev.getId();
        idAmichevole.setText(String.valueOf(id));

        statAmichevole.setVisible(true);
    }

    private void putAtRightElement() {

        for (int x = 0; x < jTable1.getColumnCount(); x++) {

            jTable1.getColumnModel().getColumn(x).setCellRenderer(rightRenderer);

        }
    }

    private void initialiteCalendar() {
        jButton11.setVisible(false);
        tableClickListener();
        indexRow = -1;
        putAtRightElement();
        tableModel.setRowCount(0);
        jTable1.setSelectionModel(new ForcedListSelectionModel());
        jTable1.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));

        //listaDisponibilita = parentFrame.getTM().visualizzaDisponibilitàGiocatore(rosa.get(indexListElement).getGiocatore().getId());
        listaDisponibilita = parentFrame.getTM().visualizzaDisponibilitàGiocatore(rosa.get(indexListElement).getGiocatore().getId());
        String tipoEvento = "";
        String statistica = "";

        for (Disponibilità d : listaDisponibilita) {

            for (Evento e : eventi) {

                if (d.getIdEvento() == e.getId()) {
                    if (e instanceof Allenamento) {
                        tipoEvento = "Allenamento";
                        break;
                    } else if (e instanceof Amichevole) {
                        tipoEvento = "Amichevole";
                        break;
                    }
                }
            }

            if (d.getStatistica() == null) {
                statistica = "Non fornita";

            } else {
                statistica = "Fornita";

            }

            Object[] row = {d.getIdEvento(), d.getIdGiocatore(), tipoEvento, statistica};
            tableModel.addRow(row);
        }

    }

    private void tableClickListener() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                indexRow = row;
                System.out.println("Riga attuale: " + indexRow);
                setUpDetailsDialog();
            }
        });
    }

    private void setUpDetailsDialog() {
        if (listaDisponibilita.get(indexRow).getStatistica() == null) {
            jButton11.setVisible(false);
        } else {
            jButton11.setVisible(true);
            detailsAmichevole.setLocationRelativeTo(null);
            detailsAmichevole.setResizable(false);
            detailsAmichevole.setModal(true);
            
            detailsAllenamento.setLocationRelativeTo(null);
            detailsAllenamento.setResizable(false);
            detailsAllenamento.setModal(true);

            if (listaDisponibilita.get(indexRow).getStatistica() instanceof StatisticaAmichevole) {
                
                detailsAmichevole.setTitle("Statistica amichevole");
              

                StatisticaAmichevole sa = (StatisticaAmichevole) parentFrame.getTM().
                        visualizzaStoricoGiocatore(rosa.get(indexListElement).getGiocatore().getId(), listaDisponibilita.get(indexRow).getIdEvento());

                setAmichevoleDetailsLabel(sa);

            } else if (listaDisponibilita.get(indexRow).getStatistica() instanceof StatisticaAllenamento) {
                detailsAllenamento.setTitle("Statistica amichevole");
                
                StatisticaAllenamento sa = (StatisticaAllenamento) parentFrame.getTM().
                        visualizzaStoricoGiocatore(rosa.get(indexListElement).getGiocatore().getId(), listaDisponibilita.get(indexRow).getIdEvento());
                
                setAllenamentoDetailsLabel(sa);
            }

        }
    }

    private void setAmichevoleDetailsLabel(StatisticaAmichevole sa) {
        jLabel40.setText(String.valueOf(sa.getMinutiGiocati()));
        jLabel39.setText(String.valueOf(sa.getGoal()));
        jLabel42.setText(String.valueOf(sa.getAutogoal()));
        jLabel43.setText(String.valueOf(sa.getCartelliniGialli()));
        jLabel41.setText(String.valueOf(sa.getCartelliniRossi()));
        jLabel45.setText(String.valueOf(sa.getDistanzaTotalePercorsa()));
        jLabel44.setText(String.valueOf(sa.getFalliCommessi()));
        jLabel46.setText(String.valueOf(sa.getAssist()));
        jLabel47.setText(String.valueOf(sa.getParate()));
        jLabel48.setText(String.valueOf(sa.getIntercettiRiusciti()));
        jLabel50.setText(String.valueOf(sa.getPassaggiChiave()));
        jLabel49.setText(String.valueOf(sa.getTiriTotali()));
    }
    
    private void setAllenamentoDetailsLabel(StatisticaAllenamento sa){
        jLabel58.setText(String.valueOf(sa.getVelocitàMax()));
        jLabel60.setText(String.valueOf(sa.getVelocitàMedia()));
        jLabel59.setText(String.valueOf(sa.getValutazioneForzaFisica()));
        jLabel62.setText(String.valueOf(sa.getValutazioneForzaTiro()));
        jLabel61.setText(String.valueOf(sa.getFrequenzaCardiacaMedia()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dispDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        statAllenamento = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        idAllenamento = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        statAmichevole = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        idAmichevole = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        detailsAmichevole = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        detailsAllenamento = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        numGiocatoriLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        dispDialog.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel1.setBackground(new java.awt.Color(248, 251, 252));
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 405));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Evento", "ID Giocatore", "Tipo Evento", "Statistica"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(28);
        jScrollPane2.setViewportView(jTable1);

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 204, 102));
        jButton2.setText("+ Stats");
        jButton2.setAlignmentY(0.0F);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(204, 0, 0));
        jButton3.setText("X Stats");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Disponibilita");

        jButton5.setBackground(new java.awt.Color(233, 233, 248));
        jButton5.setText("Back");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(249, 245, 245));
        jButton11.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jButton11.setText("Info Stats");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dispDialogLayout = new javax.swing.GroupLayout(dispDialog.getContentPane());
        dispDialog.getContentPane().setLayout(dispDialogLayout);
        dispDialogLayout.setHorizontalGroup(
            dispDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dispDialogLayout.setVerticalGroup(
            dispDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dispDialogLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        statAllenamento.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("velocità Max (kmh):");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("velocità Media(kmh):");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("forza Fisica:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("forza Tiro:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("frequenza Cardiaca(bpm):");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Inserisci Statistica Allenamento id:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  " }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText(".");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00  ", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99    " }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  " }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00  ", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99    " }));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  " }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  " }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  ", "100  ", "101  ", "102  ", "103  ", "104  ", "105  ", "106  ", "107  ", "108  ", "109  ", "110  ", "111  ", "112  ", "113  ", "114  ", "115  ", "116  ", "117  ", "118  ", "119  ", "120  ", "121  ", "122  ", "123  ", "124  ", "125  ", "126  ", "127  ", "128  ", "129  ", "130  ", "131  ", "132  ", "133  ", "134  ", "135  ", "136  ", "137  ", "138  ", "139  ", "140  ", "141  ", "142  ", "143  ", "144  ", "145  ", "146  ", "147  ", "148  ", "149  ", "150  ", "151  ", "152  ", "153  ", "154  ", "155  ", "156  ", "157  ", "158  ", "159  ", "160  ", "161  ", "162  ", "163  ", "164  ", "165  ", "166  ", "167  ", "168  ", "169  ", "170  ", "171  ", "172  ", "173  ", "174  ", "175  ", "176  ", "177  ", "178  ", "179  ", "180  ", "181  ", "182  ", "183  ", "184  ", "185  ", "186  ", "187  ", "188  ", "189  ", "190  ", "191  ", "192  ", "193  ", "194  ", "195  ", "196  ", "197  ", "198  ", "199  " }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText(".");

        idAllenamento.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        idAllenamento.setText("NaN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jComboBox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idAllenamento)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(idAllenamento))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jButton4.setBackground(new java.awt.Color(252, 246, 246));
        jButton4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(239, 246, 248));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 204, 204));
        jButton6.setText("OK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statAllenamentoLayout = new javax.swing.GroupLayout(statAllenamento.getContentPane());
        statAllenamento.getContentPane().setLayout(statAllenamentoLayout);
        statAllenamentoLayout.setHorizontalGroup(
            statAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statAllenamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(statAllenamentoLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        statAllenamentoLayout.setVerticalGroup(
            statAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statAllenamentoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        statAmichevole.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("minuti giocati:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("goal segnati:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("auto-goal:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("cartellini Rossi:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("distanza percorsa(m):");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Inserisci Statistica Amichevole id:");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  " }));

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50 " }));

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  " }));

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  " }));

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "100  ", "200  ", "300  ", "400  ", "500  ", "600  ", "700  ", "800  ", "900  ", "1000  ", "1100  ", "1200  ", "1300  ", "1400  ", "1500  ", "1600  ", "1700  ", "1800  ", "1900  ", "2000  ", "2100  ", "2200  ", "2300  ", "2400  ", "2500  ", "2600  ", "2700  ", "2800  ", "2900  ", "3000  ", "3100  ", "3200  ", "3300  ", "3400  ", "3500  ", "3600  ", "3700  ", "3800  ", "3900  ", "4000  ", "4100  ", "4200  ", "4300  ", "4400  ", "4500  ", "4600  ", "4700  ", "4800  ", "4900  ", "5000  ", "5100  ", "5200  ", "5300  ", "5400  ", "5500  ", "5600  ", "5700  ", "5800  ", "5900  ", "6000  ", "6100  ", "6200  ", "6300  ", "6400  ", "6500  ", "6600  ", "6700  ", "6800  ", "6900  ", "7000  ", "7100  ", "7200  ", "7300  ", "7400  ", "7500  ", "7600  ", "7700  ", "7800  ", "7900  ", "8000  ", "8100  ", "8200  ", "8300  ", "8400  ", "8500  ", "8600  ", "8700  ", "8800  ", "8900  ", "9000  ", "9100  ", "9200  ", "9300  ", "9400  ", "9500  ", "9600  ", "9700  ", "9800  ", "9900  ", "10000  ", "10100  ", "10200  ", "10300  ", "10400  ", "10500  ", "10600  ", "10700  ", "10800  ", "10900  ", "11000  ", "11100  ", "11200  ", "11300  ", "11400  ", "11500  ", "11600  ", "11700  ", "11800  ", "11900  ", "12000  ", "12100  ", "12200  ", "12300  ", "12400  ", "12500  ", "12600  ", "12700  ", "12800  ", "12900  ", "13000  ", "13100  ", "13200  ", "13300  ", "13400  ", "13500  ", "13600  ", "13700  ", "13800  ", "13900  ", "14000  ", "14100  ", "14200  ", "14300  ", "14400  ", "14500  ", "14600  ", "14700  ", "14800  ", "14900  ", "15000  ", "15100  ", "15200  ", "15300  ", "15400  ", "15500  ", "15600  ", "15700  ", "15800  ", "15900  ", "16000  ", "16100  ", "16200  ", "16300  ", "16400  ", "16500  ", "16600  ", "16700  ", "16800  ", "16900  ", "17000  ", "17100  ", "17200  ", "17300  ", "17400  ", "17500  ", "17600  ", "17700  ", "17800  ", "17900  ", "18000  ", "18100  ", "18200  ", "18300  ", "18400  ", "18500  ", "18600  ", "18700  ", "18800  ", "18900  ", "19000  ", "19100  ", "19200  ", "19300  ", "19400  ", "19500  ", "19600  ", "19700  ", "19800  ", "19900  ", "20000  ", "20100  ", "20200  ", "20300  ", "20400  ", "20500  ", "20600  ", "20700  ", "20800  ", "20900  ", "21000  ", "21100  ", "21200  ", "21300  ", "21400  ", "21500  ", "21600  ", "21700  ", "21800  ", "21900  ", "22000  ", "22100  ", "22200  ", "22300  ", "22400  ", "22500  ", "22600  ", "22700  ", "22800  ", "22900  ", "23000  ", "23100  ", "23200  ", "23300  ", "23400  ", "23500  ", "23600  ", "23700  ", "23800  ", "23900  ", "24000  ", "24100  ", "24200  ", "24300  ", "24400  ", "24500  ", "24600  ", "24700  ", "24800  ", "24900  ", "25000  ", "25100  ", "25200  ", "25300  ", "25400  ", "25500  ", "25600  ", "25700  ", "25800  ", "25900  ", "26000  ", "26100  ", "26200  ", "26300  ", "26400  ", "26500  ", "26600  ", "26700  ", "26800  ", "26900  ", "27000  ", "27100  ", "27200  ", "27300  ", "27400  ", "27500  ", "27600  ", "27700  ", "27800  ", "27900  ", "28000  ", "28100  ", "28200  ", "28300  ", "28400  ", "28500  ", "28600  ", "28700  ", "28800  ", "28900  ", "29000  ", "29100  ", "29200  ", "29300  ", "29400  ", "29500  ", "29600  ", "29700  ", "29800  ", "29900  ", "30000  " }));

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00  ", "01  ", "02  ", "03  ", "04  ", "05  ", "06  ", "07  ", "08  ", "09  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50 " }));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText(".");

        idAmichevole.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        idAmichevole.setText("NaN");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("falli commessi:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("assist:");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50 " }));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("cartellini Gialli:");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("parate:");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  " }));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("passaggi chiave:");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("intercetti riusciti:");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("tiri totali:");

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0  ", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idAmichevole))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jLabel18)
                                .addGap(0, 0, 0)
                                .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(idAmichevole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(23, 23, 23))
        );

        jButton7.setBackground(new java.awt.Color(252, 246, 246));
        jButton7.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton7.setText("Back");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(239, 246, 248));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 204, 204));
        jButton8.setText("OK");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statAmichevoleLayout = new javax.swing.GroupLayout(statAmichevole.getContentPane());
        statAmichevole.getContentPane().setLayout(statAmichevoleLayout);
        statAmichevoleLayout.setHorizontalGroup(
            statAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statAmichevoleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        statAmichevoleLayout.setVerticalGroup(
            statAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statAmichevoleLayout.createSequentialGroup()
                .addGroup(statAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statAmichevoleLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jButton7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statAmichevoleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(statAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        detailsAmichevole.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("minuti giocati:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("goal segnati:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("auto-goal:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("cartellini Rossi:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("distanza percorsa(m):");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("Statistica Amichevole");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setText("falli commessi:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText("assist:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("cartellini Gialli:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("parate:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("passaggi chiave:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("intercetti riusciti:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("tiri totali:");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("minuti giocati:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("minuti giocati:");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("minuti giocati:");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setText("minuti giocati:");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setText("minuti giocati:");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel44.setText("minuti giocati:");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel45.setText("minuti giocati:");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel46.setText("minuti giocati:");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setText("minuti giocati:");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel48.setText("minuti giocati:");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel49.setText("minuti giocati:");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel50.setText("minuti giocati:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel39))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel45))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel46)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel47))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel48))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel50))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel49))
                .addGap(25, 25, 25))
        );

        jButton9.setBackground(new java.awt.Color(252, 246, 246));
        jButton9.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton9.setText("Back");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailsAmichevoleLayout = new javax.swing.GroupLayout(detailsAmichevole.getContentPane());
        detailsAmichevole.getContentPane().setLayout(detailsAmichevoleLayout);
        detailsAmichevoleLayout.setHorizontalGroup(
            detailsAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsAmichevoleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        detailsAmichevoleLayout.setVerticalGroup(
            detailsAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsAmichevoleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsAmichevoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        detailsAllenamento.setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setText("velocità Max (kmh):");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel51.setText("velocità Media (kmh):");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel52.setText("forza Fisica (0 a 10):");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel53.setText("forza Tiro (0 a 10):");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel54.setText("frequenza Cardiaca (bpm):");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setText("Statistica Allenamento");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("velocità Max (kmh):");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("velocità Max (kmh):");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setText("velocità Max (kmh):");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setText("velocità Max (kmh):");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel62.setText("velocità Max (kmh):");

        jButton12.setBackground(new java.awt.Color(252, 246, 246));
        jButton12.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton12.setText("Back");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel55))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel58))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout detailsAllenamentoLayout = new javax.swing.GroupLayout(detailsAllenamento.getContentPane());
        detailsAllenamento.getContentPane().setLayout(detailsAllenamentoLayout);
        detailsAllenamentoLayout.setHorizontalGroup(
            detailsAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsAllenamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        detailsAllenamentoLayout.setVerticalGroup(
            detailsAllenamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsAllenamentoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(800, 600));

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

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Nome Item 2 Data", " ", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setMaximumSize(new java.awt.Dimension(90, 90));
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 15)); // NOI18N
        jButton1.setText("GO\n");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backButton)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        initializeList();
        parentFrame.cardLayout.show(parentFrame.getjPanel1(), "COACHPANEL");
    }//GEN-LAST:event_backButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        setUpDispDialog();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (indexRow >= 0) {

            int id = listaDisponibilita.get(indexRow).getIdEvento();
            Evento e = null;

            for (Evento ev : eventi) {
                if (ev.getId() == id) {
                    e = ev;
                    break;
                }
            }

            if (e instanceof Allenamento) {
                setUpStatAllenamento(e);
            } else if (e instanceof Amichevole) {
                setUpStatAmichevole(e);
            }

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona una disponibilità!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String[] options = {"  Si  ", "  No  "};

        if (indexRow >= 0) {

            if (listaDisponibilita.get(indexRow).getStatistica() != null) {

                int id = listaDisponibilita.get(indexRow).getIdEvento();
                Evento e = null;

                for (Evento ev : eventi) {
                    if (ev.getId() == id) {
                        e = ev;
                        break;
                    }
                }

                int result = JOptionPane.showOptionDialog(
                        dispDialog,
                        "Vuoi eliminare la statistica di Evento id: " + e.getId() + "?",
                        "Esci",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE, // no icona
                        null,
                        options, //bottoni customizzati
                        options[0] //default btn
                );

                if (result == JOptionPane.YES_OPTION) {
                    //aggiungere logica persistenza (salvataggio)
                    try {
                        parentFrame.getTM().rimuoviStatistica(rosa.get(indexListElement).getGiocatore().getId(), e.getId());
                        dispDialog.setVisible(false);
                        initializeList();
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(
                                null,
                                exception.getMessage(),
                                "Errore",
                                JOptionPane.PLAIN_MESSAGE
                        );
                    }
                }

            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Nessuna statistica da eliminare!",
                        "Errore",
                        JOptionPane.PLAIN_MESSAGE
                );
            }

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona evento con statistica da eliminare!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dispDialog.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            int val1 = parseInt(jComboBox1.getSelectedItem().toString().replaceAll("\\s+", ""));
            float velMax = Float.parseFloat(val1 + "." + jComboBox2.getSelectedItem().toString().replaceAll("\\s+", ""));

            int val3 = parseInt(jComboBox3.getSelectedItem().toString().replaceAll("\\s+", ""));
            float velMedia = Float.parseFloat(val3 + "." + jComboBox4.getSelectedItem().toString().replaceAll("\\s+", ""));

            int forzaFisica = parseInt(jComboBox5.getSelectedItem().toString().replaceAll("\\s+", ""));
            int forzaTiro = parseInt(jComboBox6.getSelectedItem().toString().replaceAll("\\s+", ""));
            int freq = parseInt(jComboBox7.getSelectedItem().toString().replaceAll("\\s+", ""));

            Map<String, String> campiSpecifici = new HashMap<>();
            campiSpecifici.put("velocitàMax", String.valueOf(velMax));
            campiSpecifici.put("velocitàMedia", String.valueOf(velMedia));
            campiSpecifici.put("valutazioneForzaFisica", String.valueOf(forzaFisica));
            campiSpecifici.put("valutazioneForzaTiro", String.valueOf(forzaTiro));
            campiSpecifici.put("frequenzaCardiacaMedia", String.valueOf(freq));

            int idPlayer = rosa.get(indexListElement).getGiocatore().getId();
            int idEvento = parseInt(idAllenamento.getText());

            parentFrame.getTM().aggiungiStatisticaAllenamento(idPlayer, idEvento, campiSpecifici);

            dispDialog.setVisible(false);
            statAllenamento.setVisible(false);
            initializeList();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         statAllenamento.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        if (listaDisponibilita.get(indexRow).getStatistica() instanceof StatisticaAmichevole) {
            detailsAmichevole.setVisible(true);
        } else if (listaDisponibilita.get(indexRow).getStatistica() instanceof StatisticaAllenamento) {
            detailsAllenamento.setVisible(true);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        detailsAllenamento.setVisible(false);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        try {
            int minGiocati = parseInt(jComboBox8.getSelectedItem().toString().replaceAll("\\s+", ""));
            int goal = parseInt(jComboBox9.getSelectedItem().toString().replaceAll("\\s+", ""));
            int autoGoal = parseInt(jComboBox10.getSelectedItem().toString().replaceAll("\\s+", ""));
            int gialli = parseInt(jComboBox11.getSelectedItem().toString().replaceAll("\\s+", ""));
            int rossi = parseInt(jComboBox17.getSelectedItem().toString().replaceAll("\\s+", ""));

            int d1 = parseInt(jComboBox12.getSelectedItem().toString().replaceAll("\\s+", ""));
            float dist = Float.parseFloat(d1 + "." + jComboBox13.getSelectedItem().toString().replaceAll("\\s+", ""));

            int falli = parseInt(jComboBox14.getSelectedItem().toString().replaceAll("\\s+", ""));
            int assist = parseInt(jComboBox15.getSelectedItem().toString().replaceAll("\\s+", ""));
            int parate = parseInt(jComboBox18.getSelectedItem().toString().replaceAll("\\s+", ""));
            int intercetti = parseInt(jComboBox16.getSelectedItem().toString().replaceAll("\\s+", ""));
            int passaggi = parseInt(jComboBox19.getSelectedItem().toString().replaceAll("\\s+", ""));
            int tiri = parseInt(jComboBox20.getSelectedItem().toString().replaceAll("\\s+", ""));

            Map<String, String> campiSpecifici = new HashMap<>();

            campiSpecifici.put("minutiGiocati", String.valueOf(minGiocati));
            campiSpecifici.put("goal", String.valueOf(goal));
            campiSpecifici.put("autogoal", String.valueOf(autoGoal));
            campiSpecifici.put("cartelliniGialli", String.valueOf(gialli));
            campiSpecifici.put("cartelliniRossi", String.valueOf(rossi));
            campiSpecifici.put("distanzaTotalePercorsa", String.valueOf(dist));
            campiSpecifici.put("falliCommessi", String.valueOf(falli));
            campiSpecifici.put("assist", String.valueOf(assist));
            campiSpecifici.put("parate", String.valueOf(parate));
            campiSpecifici.put("intercettiRiusciti", String.valueOf(intercetti));
            campiSpecifici.put("passaggiChiave", String.valueOf(passaggi));
            campiSpecifici.put("tiriTotali", String.valueOf(gialli));

            int idPlayer = rosa.get(indexListElement).getGiocatore().getId();
            int idEvento = parseInt(idAmichevole.getText());

            parentFrame.getTM().aggiungiStatisticaAmichevole(idPlayer, idEvento, campiSpecifici);

            dispDialog.setVisible(false);
            statAmichevole.setVisible(false);
            initializeList();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                null,
                e,
                "Errore",
                JOptionPane.PLAIN_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        statAmichevole.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        detailsAmichevole.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JDialog detailsAllenamento;
    private javax.swing.JDialog detailsAmichevole;
    private javax.swing.JDialog dispDialog;
    private javax.swing.JLabel idAllenamento;
    private javax.swing.JLabel idAmichevole;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel numGiocatoriLabel;
    private javax.swing.JDialog statAllenamento;
    private javax.swing.JDialog statAmichevole;
    // End of variables declaration//GEN-END:variables
}
