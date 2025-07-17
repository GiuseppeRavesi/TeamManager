package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Evento;
import model.Giocatore;
import model.GiocatoreInRosa;
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

    public void saveAll() {
        saveGiocatori();
        saveRosa();
        saveEventi();
        saveUtenti();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveGiocatori() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("giocatori");
            doc.appendChild(root);

            for (Giocatore g : listaGiocatori) {
                Element giocatoreElem = doc.createElement("giocatore");
                giocatoreElem.setAttribute("id", String.valueOf(g.getId()));

                Element nome = doc.createElement("nome");
                nome.setTextContent(g.getNome());
                giocatoreElem.appendChild(nome);

                Element cognome = doc.createElement("cognome");
                cognome.setTextContent(g.getCognome());
                giocatoreElem.appendChild(cognome);

                Element dataNascita = doc.createElement("dataNascita");
                dataNascita.setTextContent(g.getDataNascita().toString());
                giocatoreElem.appendChild(dataNascita);

                Element nazionalita = doc.createElement("nazionalita");
                nazionalita.setTextContent(g.getNazionalita());
                giocatoreElem.appendChild(nazionalita);

                Element email = doc.createElement("email");
                email.setTextContent(g.getEmail());
                giocatoreElem.appendChild(email);

                root.appendChild(giocatoreElem);
            }

            File file = new File("dati/giocatori.xml");
            file.getParentFile().mkdirs();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println("Giocatori salvati con successo.");
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
                
                Ruolo ruolo = Ruolo.valueOf(elem.getAttribute("ruolo"));
                Status status = Status.valueOf(elem.getAttribute("status"));
                int numeroMaglia = Integer.parseInt(elem.getAttribute("numeroMaglia"));
                LocalDate dataInserimento = LocalDate.parse(elem.getAttribute("dataInserimento"));

                // Parsing del giocatore annidato
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

    private void saveRosa() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("rosa");
            doc.appendChild(root);

            for (GiocatoreInRosa g : rosa) {
                Element giocatoreInRosaElem = doc.createElement("giocatoreInRosa");

                // Giocatore interno
                Element giocatoreElem = doc.createElement("giocatore");

                giocatoreElem.setAttribute("id", String.valueOf(g.getGiocatore().getId()));

                Element nome = doc.createElement("nome");
                nome.setTextContent(g.getGiocatore().getNome());
                giocatoreElem.appendChild(nome);

                Element cognome = doc.createElement("cognome");
                cognome.setTextContent(g.getGiocatore().getCognome());
                giocatoreElem.appendChild(cognome);

                Element nascita = doc.createElement("dataNascita");
                nascita.setTextContent(g.getGiocatore().getDataNascita().toString());
                giocatoreElem.appendChild(nascita);

                Element nazionalita = doc.createElement("nazionalita");
                nazionalita.setTextContent(g.getGiocatore().getNazionalita());
                giocatoreElem.appendChild(nazionalita);

                Element email = doc.createElement("email");
                email.setTextContent(g.getGiocatore().getEmail());
                giocatoreElem.appendChild(email);

                giocatoreInRosaElem.appendChild(giocatoreElem);

                // Dati specifici del GiocatoreInRosa
                Element ruolo = doc.createElement("ruolo");
                ruolo.setTextContent(g.getRuolo().name());
                giocatoreInRosaElem.appendChild(ruolo);

                Element status = doc.createElement("status");
                status.setTextContent(g.getStatus().name());
                giocatoreInRosaElem.appendChild(status);

                Element numeroMaglia = doc.createElement("numeroMaglia");
                numeroMaglia.setTextContent(String.valueOf(g.getNumMaglia()));
                giocatoreInRosaElem.appendChild(numeroMaglia);

                Element dataEntrata = doc.createElement("dataEntrata");
                dataEntrata.setTextContent(g.getDataInserimento().toString());
                giocatoreInRosaElem.appendChild(dataEntrata);

                root.appendChild(giocatoreInRosaElem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("dati/" + ROSA_FILE));
            transformer.transform(source, result);

            System.out.println("Rosa salvata correttamente in " + ROSA_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEventi() {
        /* ... */ }

    private void saveEventi() {
        /* ... */ }

    private void loadUtenti() {
        /* ... */ }

    private void saveUtenti() {
        /* ... */ }
}
