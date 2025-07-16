/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import modelProva.GiocatoreProva;
import view.TeamManagerGUI;

/**
 *
 * @author enzov
 */
public class RosaPanel extends javax.swing.JPanel {

    private TeamManagerGUI parentFrame;

    private java.util.List<GiocatoreProva> squadra;
    private java.util.List<GiocatoreProva> listaGiocatori;
    private java.util.List<GiocatoreProva> listaFiltrata;

    private DefaultListModel<String> model;
    private DefaultListModel<String> searchModel;

    private int indexListElement;
    private int indexJList2;

    /**
     * Creates new form RosaPanel
     */
    public RosaPanel(TeamManagerGUI parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();

        squadra = new ArrayList<>();
        listaGiocatori = new ArrayList<>();
        listaFiltrata = new ArrayList<>();

        model = new DefaultListModel<String>();
        searchModel = new DefaultListModel<String>();

        setSquadra();
        setListaGiocatori();

        initializeList();
        
        setMouseListenerJList1();
        setMouseListenerJList2();
    }

    private void initializeList() {
        indexListElement = -1;
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
    
    public void logout(){
        initializeList();
    }

    private void initialiteSearchList() {
        indexJList2=-1;
        searchModel.clear();
        listaFiltrata.addAll(listaGiocatori);
        searchPlayerField.setText("");

        int count = 0;
        for (GiocatoreProva p : listaGiocatori) {
            searchModel.addElement(p.toString());
            count++;
        }
        jList2.setModel(searchModel);
        System.out.println("Giocatori totali: " + count);

        // Imposta font monospaziato per allineare i campi
        jList2.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

    }

    private void dynamicSearch() {
        searchModel.clear();
        listaFiltrata.clear();
        setindexListElement(-1);

        // listaFiltrata.addAll(tm.cercaGiocatori( searchPlayerField.getText()) );
        /*
   
        for(Giocatore p: listaFiltrata){
        
            searchModel.addElement(p.toString());
        
        }
        

         */
        for (GiocatoreProva p : listaGiocatori) {
            if (p.getNome().contains(searchPlayerField.getText())) {
                searchModel.addElement(p.toString());
                listaFiltrata.add(p);
            }
        }

        jList2.setModel(searchModel);

    }

    private void setindexListElement(int value) {
        indexListElement = value;
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
                    setindexListElement(index);
                }

            }
        });

    }

    private void setMouseListenerJList2() {
        jList2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) {
                    JList target = (JList) me.getSource();
                    int index = target.locationToIndex(me.getPoint());
                    if (index >= 0) {
                        Object item = target.getModel().getElementAt(index);
                    } else {
                        index = -1;
                    }
                    indexJList2 = index;
                }

            }
        });

    }

    private void setUpModifyDialog() {
        //ModifyDialog
        if (indexListElement >= 0) {
            //set up popup dialog
            dialogModifyPlayer.setLocationRelativeTo(null);
            dialogModifyPlayer.setTitle("Modifica Giocatore");
            dialogModifyPlayer.setResizable(false);
            dialogModifyPlayer.setModal(true);
            jComboBox1.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);
            jComboBox6.setSelectedIndex(0);

            GiocatoreProva giocatoreSel = squadra.get(indexListElement);
            System.out.println(giocatoreSel.getNome());
            playerName.setText(giocatoreSel.getNome());
            playerSurname.setText(giocatoreSel.getCognome());
            numberPlayer.setText(String.valueOf(giocatoreSel.getNumMaglia()));
            playerRole.setText(giocatoreSel.getRuolo());
            playerStatus.setText(giocatoreSel.getStatus());
            
            if(giocatoreSel.getRuolo().equals("Attaccante")){
                jComboBox1.setSelectedIndex(1);
            }else if(giocatoreSel.getRuolo().equals("Centrocampista")){
                jComboBox1.setSelectedIndex(2);
            }else if(giocatoreSel.getRuolo().equals("Difensore")){
                jComboBox1.setSelectedIndex(3);
            }else if(giocatoreSel.getRuolo().equals("Portiere")){
                jComboBox1.setSelectedIndex(4);
            }
            
            if(giocatoreSel.getStatus().equals("Attivo")){
                jComboBox2.setSelectedIndex(1);
            }else if(giocatoreSel.getStatus().equals("Infortunato")){
                jComboBox2.setSelectedIndex(2);
            }else if(giocatoreSel.getStatus().equals("Squalificato")){
                jComboBox2.setSelectedIndex(3);
            }
            
            //fare altri controlli
            for(int i=1;i<=99;i++){
                if(giocatoreSel.getNumMaglia() ==i){
                    jComboBox6.setSelectedIndex(i);
                    break;
                }
            }

            dialogModifyPlayer.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un giocatore dalla lista!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );

        }
    }

    private void setUpRemoveDialog() {
        //ModifyDialog
        if (indexListElement >= 0) {
            //set up popup dialog
            dialogRemovePlayer.setLocationRelativeTo(null);
            dialogRemovePlayer.setTitle("Rimuovi giocatore");
            dialogRemovePlayer.setResizable(false);
            dialogRemovePlayer.setModal(true);

            GiocatoreProva giocatoreSel = squadra.get(indexListElement);
            System.out.println(giocatoreSel.getNome());
            playerName1.setText(giocatoreSel.getNome());
            playerSurname1.setText(giocatoreSel.getCognome());
            numberPlayer1.setText(String.valueOf(giocatoreSel.getNumMaglia()));
            playerRole1.setText(giocatoreSel.getRuolo());
            playerStatus1.setText(giocatoreSel.getStatus());

            dialogRemovePlayer.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un giocatore dalla lista!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );

        }
    }

    private void setUpAddDialog() {
        initialiteSearchList();
        dialogAddPlayer.setLocationRelativeTo(null);
        dialogAddPlayer.setTitle("Aggiungi giocatore");
        dialogAddPlayer.setResizable(false);
        dialogAddPlayer.setModal(true);
        dialogAddPlayer.setVisible(true);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
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

        dialogModifyPlayer = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        playerName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        playerSurname = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        numberPlayer = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        playerRole = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        playerStatus = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelRole = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        labelStatus1 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        dialogAddPlayer = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        searchPlayerField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        dialogRemovePlayer = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        playerName1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        playerSurname1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        numberPlayer1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        playerRole1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        playerStatus1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        modifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        numGiocatoriLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        dialogModifyPlayer.setMinimumSize(new java.awt.Dimension(400, 400));
        dialogModifyPlayer.setPreferredSize(new java.awt.Dimension(400, 400));

        jPanel1.setMinimumSize(new java.awt.Dimension(400, 400));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("nome:");

        playerName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerName.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("cognome: ");

        playerSurname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerSurname.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("numero maglia:");

        numberPlayer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numberPlayer.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("ruolo attuale:");

        playerRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerRole.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("stato attuale:");

        playerStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerStatus.setText("jLabel11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerSurname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(playerName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(playerSurname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(numberPlayer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerRole)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(playerStatus))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelRole.setText("nuovo ruolo:");

        labelStatus.setText("nuovo status:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleziona ruolo", "Attaccante", "Centrocampista", "Difensore", "Portiere" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleziona status", "Attivo", "Infortunato", "Squalificato" }));

        labelStatus1.setText("Nuovo #maglia:");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero maglia", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99  " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(labelRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, 158, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(labelStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jComboBox6, 0, 158, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRole, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jButton6.setBackground(new java.awt.Color(255, 255, 249));
        jButton6.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jButton6.setText("Back");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 153));
        jButton1.setText("Invio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogModifyPlayerLayout = new javax.swing.GroupLayout(dialogModifyPlayer.getContentPane());
        dialogModifyPlayer.getContentPane().setLayout(dialogModifyPlayerLayout);
        dialogModifyPlayerLayout.setHorizontalGroup(
            dialogModifyPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogModifyPlayerLayout.createSequentialGroup()
                .addGroup(dialogModifyPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogModifyPlayerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dialogModifyPlayerLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(dialogModifyPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogModifyPlayerLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogModifyPlayerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        dialogModifyPlayerLayout.setVerticalGroup(
            dialogModifyPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModifyPlayerLayout.createSequentialGroup()
                .addGroup(dialogModifyPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModifyPlayerLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        dialogAddPlayer.setMinimumSize(new java.awt.Dimension(400, 400));

        jPanel4.setBackground(new java.awt.Color(243, 246, 248));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Seleziona un giocatore");

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel3.setText("Cerca:");

        searchPlayerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPlayerFieldActionPerformed(evt);
            }
        });
        searchPlayerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchPlayerFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchPlayerFieldKeyReleased(evt);
            }
        });

        jLabel15.setText("Seleziona status");

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleziona ruolo", "Attaccante", "Centrocampista", "Difensore", "Portiere" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel16.setText("Seleziona ruolo");

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleziona status", "Disponibile", "Sospeso", "Infortunato" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel17.setText("Nuovo #maglia");

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero maglia", "1  ", "2  ", "3  ", "4  ", "5  ", "6  ", "7  ", "8  ", "9  ", "10  ", "11  ", "12  ", "13  ", "14  ", "15  ", "16  ", "17  ", "18  ", "19  ", "20  ", "21  ", "22  ", "23  ", "24  ", "25  ", "26  ", "27  ", "28  ", "29  ", "30  ", "31  ", "32  ", "33  ", "34  ", "35  ", "36  ", "37  ", "38  ", "39  ", "40  ", "41  ", "42  ", "43  ", "44  ", "45  ", "46  ", "47  ", "48  ", "49  ", "50  ", "51  ", "52  ", "53  ", "54  ", "55  ", "56  ", "57  ", "58  ", "59  ", "60  ", "61  ", "62  ", "63  ", "64  ", "65  ", "66  ", "67  ", "68  ", "69  ", "70  ", "71  ", "72  ", "73  ", "74  ", "75  ", "76  ", "77  ", "78  ", "79  ", "80  ", "81  ", "82  ", "83  ", "84  ", "85  ", "86  ", "87  ", "88  ", "89  ", "90  ", "91  ", "92  ", "93  ", "94  ", "95  ", "96  ", "97  ", "98  ", "99 " }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(searchPlayerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 82, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        jButton3.setBackground(new java.awt.Color(223, 249, 253));
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 153));
        jButton2.setText("Conferma");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogAddPlayerLayout = new javax.swing.GroupLayout(dialogAddPlayer.getContentPane());
        dialogAddPlayer.getContentPane().setLayout(dialogAddPlayerLayout);
        dialogAddPlayerLayout.setHorizontalGroup(
            dialogAddPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAddPlayerLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(dialogAddPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialogAddPlayerLayout.setVerticalGroup(
            dialogAddPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAddPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogAddPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        dialogRemovePlayer.setMinimumSize(new java.awt.Dimension(400, 400));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setMinimumSize(new java.awt.Dimension(400, 400));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("nome:");

        playerName1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerName1.setText("jLabel3");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("cognome: ");

        playerSurname1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerSurname1.setText("jLabel5");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("numero maglia:");

        numberPlayer1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        numberPlayer1.setText("jLabel7");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("ruolo attuale:");

        playerRole1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerRole1.setText("jLabel9");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("stato attuale:");

        playerStatus1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        playerStatus1.setText("jLabel11");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberPlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerSurname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerRole1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(playerName1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(playerSurname1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(numberPlayer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerRole1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(playerStatus1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel14.setText("Sei sicuro di voler eliminare questo giocatore?");

        jButton4.setBackground(new java.awt.Color(231, 255, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(204, 255, 204));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(51, 204, 0));
        jButton5.setText("Conferma");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogRemovePlayerLayout = new javax.swing.GroupLayout(dialogRemovePlayer.getContentPane());
        dialogRemovePlayer.getContentPane().setLayout(dialogRemovePlayerLayout);
        dialogRemovePlayerLayout.setHorizontalGroup(
            dialogRemovePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogRemovePlayerLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel14)
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(dialogRemovePlayerLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(dialogRemovePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(dialogRemovePlayerLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dialogRemovePlayerLayout.setVerticalGroup(
            dialogRemovePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogRemovePlayerLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dialogRemovePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(138, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 0));

        addButton.setBackground(new java.awt.Color(204, 255, 204));
        addButton.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        addButton.setForeground(new java.awt.Color(51, 255, 51));
        addButton.setText("+");
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
        jList1.setMaximumSize(new java.awt.Dimension(90, 90));
        jScrollPane1.setViewportView(jList1);

        modifyButton.setBackground(new java.awt.Color(204, 204, 204));
        modifyButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        modifyButton.setText("Modifica");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(255, 129, 129));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 0, 0));
        deleteButton.setText("X");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(232, 253, 253));
        backButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        numGiocatoriLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        numGiocatoriLabel.setText("NumGiocatori");
        numGiocatoriLabel.setMaximumSize(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Rosa Attuale");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numGiocatoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 54, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        initializeList();
        parentFrame.cardLayout.show(parentFrame.getjPanel1(), "COACHPANEL");
    }//GEN-LAST:event_backButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        setUpRemoveDialog();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        // TODO add your handling code here:
        setUpModifyDialog();
        //setup del popup modifica

    }//GEN-LAST:event_modifyButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        
        setUpAddDialog();
    }//GEN-LAST:event_addButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int flag = 0;

        if (jComboBox1.getSelectedIndex() > 0) {
            squadra.get(indexListElement).setRuolo(jComboBox1.getItemAt(jComboBox1.getSelectedIndex()));
            flag++;
        }

        if (jComboBox2.getSelectedIndex() > 0) {
            squadra.get(indexListElement).setStatus(jComboBox2.getItemAt(jComboBox2.getSelectedIndex()));
            flag++;
        }

        if (flag == 2) {
            initializeList();
            dialogModifyPlayer.setVisible(false);
        }

        if (flag < 2) {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un nuovo ruolo e un nuovo status!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dialogAddPlayer.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int flag = 0;
        int maxSize = 0;
        int noSet=1;
        if (squadra.size() == 22) {
            JOptionPane.showMessageDialog(
                    null,
                    "Rosa piena!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
            maxSize++;
        }

        if (indexJList2 >= 0) {
            for (GiocatoreProva p : squadra) {
                if (p.equals(listaFiltrata.get(indexJList2))) {
                    flag++;
                }
            }
            if (flag == 0 && maxSize != 1) {
                int checkBox = 0;
                if (jComboBox3.getSelectedIndex() > 0) {
                    checkBox++;
                }

                if (jComboBox4.getSelectedIndex() > 0) {
                    checkBox++;
                }
                if (checkBox == 2) {
                    squadra.add(listaFiltrata.get(indexJList2));
                    noSet=0;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Seleziona un ruolo e uno status",
                            "Errore",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    noSet=1;
                }
            }

            /*
                try{
                    
                    tm.addGiocatore( listaFiltrata.get(indexListElement))
                }catch(Exception e){
            
            }
            
            
            
             */
        } else if (maxSize != 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Seleziona un giocatore!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        }

        if (flag == 1 && maxSize == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Giocatore già presente in rosa!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE
            );
        } else if (flag == 0 && indexJList2 != -1 && noSet==0) {
            dialogAddPlayer.setVisible(false);
            initializeList();
            
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchPlayerFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchPlayerFieldKeyPressed
        // TODO add your handling code here:
        dynamicSearch();

    }//GEN-LAST:event_searchPlayerFieldKeyPressed

    private void searchPlayerFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPlayerFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchPlayerFieldActionPerformed

    private void searchPlayerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchPlayerFieldKeyReleased
        // TODO add your handling code here:
        dynamicSearch();
    }//GEN-LAST:event_searchPlayerFieldKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dialogRemovePlayer.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        squadra.remove(indexListElement);
        initializeList();
        dialogRemovePlayer.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        dialogModifyPlayer.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JDialog dialogAddPlayer;
    private javax.swing.JDialog dialogModifyPlayer;
    private javax.swing.JDialog dialogRemovePlayer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelRole;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelStatus1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JLabel numGiocatoriLabel;
    private javax.swing.JLabel numberPlayer;
    private javax.swing.JLabel numberPlayer1;
    private javax.swing.JLabel playerName;
    private javax.swing.JLabel playerName1;
    private javax.swing.JLabel playerRole;
    private javax.swing.JLabel playerRole1;
    private javax.swing.JLabel playerStatus;
    private javax.swing.JLabel playerStatus1;
    private javax.swing.JLabel playerSurname;
    private javax.swing.JLabel playerSurname1;
    private javax.swing.JTextField searchPlayerField;
    // End of variables declaration//GEN-END:variables
}
