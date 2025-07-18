/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.PersistenceHandler;
import controller.Session;
import controller.TeamManager;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author enzov
 */
public class TeamManagerGUI extends javax.swing.JFrame {

    /**
     * Creates new form TeamManagerGUI
     */
    CardLayout cardLayout;

    LoginPanel login;
    JPanel container;
    CoachPanel coachPanel;
    RosaPanel rosaPanel;
    CreatePlayerPanel createPanel;
    CalendarPanel calendarPanel;
    PlayerPanel playerPanel;
    PlayerRosaPanel playerRosaPanel;
    PlayerCalendarPanel playerCalendarPanel;

    //classe facade controllore
    TeamManager tm;
    Session session;

    public TeamManagerGUI() {
        super("TeamManager v1.0");
        //init();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        options.setEnabled(false);
        setUpWindowCloseListener();

        initializePersistence();

        tm = TeamManager.getInstance();
        session = Session.getInstance();

        cardLayout = new CardLayout();
        jPanel1.setLayout(cardLayout);

        login = new LoginPanel(this);
        jPanel1.add(login, "LOGIN");

        cardLayout.show(jPanel1, "LOGIN");
        add(jPanel1);

    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    /*
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        setCards();

        cardLayout.show(container, "LOGIN");
        add(container);
        setVisible(true);
        setMenu();

    }
    
    /*

    /*
    private void setCards() {
        login = new LoginPanel(this);
        container.add(login, "LOGIN");

        coachPanel = new CoachPanel(this);
        container.add(coachPanel, "COACHPANEL");
    }
     */
    private void setCardsUIBuilder() {
        cardLayout = new CardLayout();
        jPanel1.setLayout(cardLayout);

        login = new LoginPanel(this);

        jPanel1.add(login, "LOGIN");

        coachPanel = new CoachPanel(this);
        jPanel1.add(coachPanel, "COACHPANEL");

        rosaPanel = new RosaPanel(this);
        jPanel1.add(rosaPanel, "ROSAPANEL");

        createPanel = new CreatePlayerPanel(this);
        jPanel1.add(createPanel, "CREATEPANEL");

        calendarPanel = new CalendarPanel(this);
        jPanel1.add(calendarPanel, "CALENDARPANEL");

        playerPanel = new PlayerPanel(this);
        jPanel1.add(playerPanel, "PLAYERPANEL");

        playerRosaPanel = new PlayerRosaPanel(this);
        jPanel1.add(playerRosaPanel, "PLAYERROSAPANEL");

        playerCalendarPanel = new PlayerCalendarPanel(this);
        jPanel1.add(playerCalendarPanel, "PLAYERCALENDARPANEL");

        cardLayout.show(jPanel1, "LOGIN");
        add(jPanel1);

        //logica di logout parziale
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                options.setEnabled(false);
                cardLayout.show(jPanel1, "LOGIN");

                rosaPanel.logout();
                calendarPanel.logout();
                createPanel.logout();
            }
        });

    }

    public void setCardsUIBuilder2() {

        coachPanel = new CoachPanel(this);
        jPanel1.add(coachPanel, "COACHPANEL");

        rosaPanel = new RosaPanel(this);
        jPanel1.add(rosaPanel, "ROSAPANEL");

        createPanel = new CreatePlayerPanel(this);
        jPanel1.add(createPanel, "CREATEPANEL");

        calendarPanel = new CalendarPanel(this);
        jPanel1.add(calendarPanel, "CALENDARPANEL");

        playerPanel = new PlayerPanel(this);
        jPanel1.add(playerPanel, "PLAYERPANEL");

        playerRosaPanel = new PlayerRosaPanel(this);
        jPanel1.add(playerRosaPanel, "PLAYERROSAPANEL");

        playerCalendarPanel = new PlayerCalendarPanel(this);
        jPanel1.add(playerCalendarPanel, "PLAYERCALENDARPANEL");

        //logica di logout parziale
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                options.setEnabled(false);
                cardLayout.show(jPanel1, "LOGIN");

                rosaPanel.logout();
                calendarPanel.logout();
                createPanel.logout();
                System.out.println(session.getUtenteLoggato());
            }
        });

    }

    private void setMenu() {

        jMenuBar1 = new JMenuBar();
        jMenuBar1.setBackground(Color.LIGHT_GRAY);
        jMenuBar1.setOpaque(true);
        options = new JMenu();
        Logout = new JMenuItem();
        //jMenuItem2 = new JMenuItem();
        Logout.setText("Logout");
        Logout.setBackground(Color.LIGHT_GRAY);
        Logout.setOpaque(true);
        options.setText("Options");
        options.add(Logout);
        jMenuBar1.add(options);
        setJMenuBar(jMenuBar1);
        options.setEnabled(false);

        //logica di logout parziale
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                options.setEnabled(false);
                cardLayout.show(jPanel1, "LOGIN");
            }
        });

    }

    private void initializePersistence() {
        PersistenceHandler handler = new PersistenceHandler();
        handler.loadAll();
        TeamManager.getInstance().inizializzaDatiDaPersistence(handler);
    }

    public TeamManager getTM() {
        return tm;
    }

    public Session getSession() {
        return session;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public void enableMenu() {
        options.setEnabled(true);
    }

    private void setUpWindowCloseListener() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                String[] options = {"  Si  ", "  No  "};
                int result = JOptionPane.showOptionDialog(
                        frame,
                        "Vuoi uscire dall'applicazione?",
                        "Esci",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE, // no icona
                        null,
                        options, //bottoni customizzati
                        options[0] //default btn
                );

                if (result == JOptionPane.YES_OPTION) {
                    //aggiungere logica persistenza (salvataggio)
                    System.out.println("EXIT SUCCESS");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        options = new javax.swing.JMenu();
        Logout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jMenuBar1.setBackground(new java.awt.Color(252, 252, 252));
        jMenuBar1.setBorder(null);

        options.setText("Options");

        Logout.setText("Logout");
        options.add(Logout);

        jMenuBar1.add(options);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        new TeamManagerGUI().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Logout;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu options;
    // End of variables declaration//GEN-END:variables
}
