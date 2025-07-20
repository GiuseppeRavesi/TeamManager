package controller;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Allenamento;
import model.Amichevole;
import model.Disponibilità;
import model.Evento;
import model.Giocatore;
import model.GiocatoreInRosa;
import model.Statistica;
import model.StatisticaAllenamento;
import model.StatisticaAmichevole;
import model.Utente;
import model.enums.Ruolo;
import model.enums.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Giuseppe Ravesi
 */
public class PersistenceHandler {

    private static final String GIOCATORI_FILE = "giocatori.xml";
    private static final String ROSA_FILE = "rosa.xml";
    private static final String EVENTI_FILE = "eventi.xml";
    private static final String UTENTI_FILE = "utenti.xml";

    private List<Giocatore> listaGiocatori;
    private List<GiocatoreInRosa> rosa;
    private List<Evento> listaEventi;
    private List<Utente> listaUtenti;

    public PersistenceHandler() {
        this.listaGiocatori = new ArrayList<>();
        this.rosa = new ArrayList<>();
        this.listaEventi = new ArrayList<>();
        this.listaUtenti = new ArrayList<>();
    }

    public List<Giocatore> getListaGiocatori() {
        return listaGiocatori;
    }

    public List<GiocatoreInRosa> getRosa() {
        return rosa;
    }

    public List<Evento> getListaEventi() {
        return listaEventi;
    }

    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public void loadAll() {
        loadGiocatori();
        loadRosa();
        loadEventi();
        loadUtenti();
    }

    private void loadGiocatori() {
        try {
            File file = new File("dati/giocatori.xml");
            if (!file.exists()) {
                System.out.println("File giocatori.xml non trovato. Nessun giocatore caricato.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList giocatoreNodes = doc.getElementsByTagName("giocatore");

            for (int i = 0; i < giocatoreNodes.getLength(); i++) {
                Element elem = (Element) giocatoreNodes.item(i);

                int id = Integer.parseInt(elem.getAttribute("id"));
                String nome = elem.getElementsByTagName("nome").item(0).getTextContent();
                String cognome = elem.getElementsByTagName("cognome").item(0).getTextContent();
                LocalDate dataNascita = LocalDate.parse(elem.getElementsByTagName("dataNascita").item(0).getTextContent());
                String nazionalità = elem.getElementsByTagName("nazionalita").item(0).getTextContent();
                String email = elem.getElementsByTagName("email").item(0).getTextContent();

                Giocatore g = new Giocatore(id, nome, cognome, dataNascita, nazionalità, email);
                listaGiocatori.add(g);
            }
            System.out.println("Caricamento giocatori completato. Giocatori nell'archivio: " + listaGiocatori.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadRosa() {
        try {
            File file = new File("dati/rosa.xml");
            if (!file.exists()) {
                System.out.println("File rosa.xml non trovato.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodoRosa = doc.getElementsByTagName("giocatoreInRosa");

            for (int i = 0; i < nodoRosa.getLength(); i++) {
                Element elem = (Element) nodoRosa.item(i);

                String ruoloStr = elem.getElementsByTagName("ruolo").item(0).getTextContent();
                String statusStr = elem.getElementsByTagName("status").item(0).getTextContent();

                Ruolo ruolo = Ruolo.valueOf(ruoloStr.toUpperCase());
                Status status = Status.valueOf(statusStr.toUpperCase());

                int numeroMaglia = Integer.parseInt(elem.getElementsByTagName("numeroMaglia").item(0).getTextContent());
                LocalDate dataInserimento = LocalDate.parse(elem.getElementsByTagName("dataEntrata").item(0).getTextContent());

                Element giocatoreElem = (Element) elem.getElementsByTagName("giocatore").item(0);
                int idGiocatore = Integer.parseInt(giocatoreElem.getAttribute("id"));
                String nome = giocatoreElem.getElementsByTagName("nome").item(0).getTextContent();
                String cognome = giocatoreElem.getElementsByTagName("cognome").item(0).getTextContent();
                LocalDate dataNascita = LocalDate.parse(giocatoreElem.getElementsByTagName("dataNascita").item(0).getTextContent());
                String nazionalita = giocatoreElem.getElementsByTagName("nazionalita").item(0).getTextContent();
                String email = giocatoreElem.getElementsByTagName("email").item(0).getTextContent();

                Giocatore g = new Giocatore(idGiocatore, nome, cognome, dataNascita, nazionalita, email);
                GiocatoreInRosa gir = new GiocatoreInRosa(g, ruolo, status, numeroMaglia, dataInserimento);
                rosa.add(gir);
            }

            System.out.println("Caricamento rosa completato. Giocatori in rosa: " + rosa.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEventi() {
        File file = new File("dati/eventi.xml");
        if (!file.exists()) {
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList eventoNodes = doc.getElementsByTagName("evento");

            for (int i = 0; i < eventoNodes.getLength(); i++) {
                Element eventoElem = (Element) eventoNodes.item(i);

                int id = Integer.parseInt(eventoElem.getAttribute("id"));
                String tipo = eventoElem.getAttribute("tipo"); 
                LocalDate data = LocalDate.parse(eventoElem.getElementsByTagName("data").item(0).getTextContent());
                LocalTime orario = LocalTime.parse(eventoElem.getElementsByTagName("orario").item(0).getTextContent());
                int durata = Integer.parseInt(eventoElem.getElementsByTagName("durata").item(0).getTextContent());
                String luogo = eventoElem.getElementsByTagName("luogo").item(0).getTextContent();

                Evento evento;
                if (tipo.equals("amichevole")) {
                    String squadraAvversaria = eventoElem.getElementsByTagName("squadraAvversaria").item(0).getTextContent();
                    evento = new Amichevole(id, data, orario, durata, luogo, squadraAvversaria);
                } else {
                    String tipologia = eventoElem.getElementsByTagName("tipologia").item(0).getTextContent();
                    String note = eventoElem.getElementsByTagName("note").item(0).getTextContent();
                    evento = new Allenamento(id, data, orario, durata, luogo, tipologia, note);
                }

                NodeList disponibiliNodes = eventoElem.getElementsByTagName("disponibile");
                for (int j = 0; j < disponibiliNodes.getLength(); j++) {
                    Element dispElem = (Element) disponibiliNodes.item(j);

                    int idGiocatore = Integer.parseInt(dispElem.getAttribute("idGiocatore"));
                    boolean presenza = Boolean.parseBoolean(dispElem.getAttribute("presente"));
                    String motivazione = dispElem.getElementsByTagName("motivazione").item(0).getTextContent();

                    Statistica statistica = null;
                    if (dispElem.getElementsByTagName("statisticaAmichevole").getLength() > 0) {
                        Element s = (Element) dispElem.getElementsByTagName("statisticaAmichevole").item(0);
                        statistica = new StatisticaAmichevole(
                                Integer.parseInt(s.getAttribute("id")),
                                idGiocatore,
                                id,
                                Integer.parseInt(s.getAttribute("minutiGiocati")),
                                Integer.parseInt(s.getAttribute("goal")),
                                Integer.parseInt(s.getAttribute("autogoal")),
                                Integer.parseInt(s.getAttribute("cartelliniGialli")),
                                Integer.parseInt(s.getAttribute("cartelliniRossi")),
                                Float.parseFloat(s.getAttribute("distanzaTotalePercorsa")),
                                Integer.parseInt(s.getAttribute("falliCommessi")),
                                Integer.parseInt(s.getAttribute("assist")),
                                Integer.parseInt(s.getAttribute("parate")),
                                Integer.parseInt(s.getAttribute("intercettiRiusciti")),
                                Integer.parseInt(s.getAttribute("passaggiChiave")),
                                Integer.parseInt(s.getAttribute("tiriTotali"))
                        );
                    } else if (dispElem.getElementsByTagName("statisticaAllenamento").getLength() > 0) {
                        Element s = (Element) dispElem.getElementsByTagName("statisticaAllenamento").item(0);
                        statistica = new StatisticaAllenamento(
                                Integer.parseInt(s.getAttribute("id")),
                                idGiocatore,
                                id,
                                Float.parseFloat(s.getAttribute("velocitaMax")),
                                Float.parseFloat(s.getAttribute("velocitaMedia")),
                                Integer.parseInt(s.getAttribute("valutazioneForzaFisica")),
                                Integer.parseInt(s.getAttribute("valutazioneForzaTiro")),
                                Integer.parseInt(s.getAttribute("frequenzaCardiacaMedia"))
                        );
                    }

                    Disponibilità disponibilita = new Disponibilità(idGiocatore, id, presenza, motivazione);
                    disponibilita.setStatistica(statistica);
                    evento.getDisponibilità().add(disponibilita);
                }

                listaEventi.add(evento);
            }
            System.out.println("Caricamento eventi completato. Eventi nel calendario: " + listaEventi.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadUtenti() {
        File file = new File("dati/utenti.xml");
        if (!file.exists()) {
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList utenteNodes = doc.getElementsByTagName("utente");

            for (int i = 0; i < utenteNodes.getLength(); i++) {
                Element utenteElem = (Element) utenteNodes.item(i);

                String email = utenteElem.getElementsByTagName("email").item(0).getTextContent();
                String password = utenteElem.getElementsByTagName("password").item(0).getTextContent();
                String ruolo = utenteElem.getElementsByTagName("ruolo").item(0).getTextContent();
                int id = Integer.parseInt(utenteElem.getAttribute("id"));

                Utente utente = new Utente(email, password, ruolo, id);
                listaUtenti.add(utente);
            }
            System.out.println("Caricamento utenti completato. numero Utenti: " + listaUtenti.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
