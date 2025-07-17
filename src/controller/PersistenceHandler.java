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

    public void saveAll() {
        saveGiocatori();
        saveRosa();
        saveEventi();
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
                LocalDate data = LocalDate.parse(eventoElem.getElementsByTagName("data").item(0).getTextContent());
                LocalTime orario = LocalTime.parse(eventoElem.getElementsByTagName("orario").item(0).getTextContent());
                int durata = Integer.parseInt(eventoElem.getElementsByTagName("durata").item(0).getTextContent());
                String luogo = eventoElem.getElementsByTagName("luogo").item(0).getTextContent();

                Evento evento;
                if (eventoElem.getNodeName().equals("amichevole")) {
                    String squadraAvversaria = eventoElem.getElementsByTagName("squadraAvversaria").item(0).getTextContent();
                    evento = new Amichevole(id, data, orario, durata, luogo, squadraAvversaria);
                } else { // Allenamento
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveEventi() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("eventi");
            doc.appendChild(root);

            for (Evento evento : listaEventi) {
                Element eventoElem;
                if (evento instanceof Amichevole a) {
                    eventoElem = doc.createElement("amichevole");
                    Element avv = doc.createElement("squadraAvversaria");
                    avv.setTextContent(a.getSquadraAvversaria());
                    eventoElem.appendChild(avv);
                } else if (evento instanceof Allenamento a) {
                    eventoElem = doc.createElement("allenamento");
                    Element tipo = doc.createElement("tipologia");
                    tipo.setTextContent(a.getTipologia());
                    Element note = doc.createElement("note");
                    note.setTextContent(a.getNote());
                    eventoElem.appendChild(tipo);
                    eventoElem.appendChild(note);
                } else {
                    continue;
                }

                eventoElem.setAttribute("id", String.valueOf(evento.getId()));

                Element data = doc.createElement("data");
                data.setTextContent(evento.getData().toString());
                eventoElem.appendChild(data);

                Element orario = doc.createElement("orario");
                orario.setTextContent(evento.getOrario().toString());
                eventoElem.appendChild(orario);

                Element durata = doc.createElement("durata");
                durata.setTextContent(String.valueOf(evento.getDurata()));
                eventoElem.appendChild(durata);

                Element luogo = doc.createElement("luogo");
                luogo.setTextContent(evento.getLuogo());
                eventoElem.appendChild(luogo);

                Element disponibilitaElem = doc.createElement("disponibilità");

                for (Disponibilità d : evento.getDisponibilità()) {
                    Element dispElem = doc.createElement("disponibile");
                    dispElem.setAttribute("idGiocatore", String.valueOf(d.getIdGiocatore()));
                    dispElem.setAttribute("presente", String.valueOf(d.isPresenza()));

                    Element motiv = doc.createElement("motivazione");
                    motiv.setTextContent(d.getMotivazione());
                    dispElem.appendChild(motiv);

                    Statistica stat = d.getStatistica();
                    if (stat != null) {
                        Element statElem;
                        if (stat instanceof StatisticaAllenamento sa) {
                            statElem = doc.createElement("statisticaAllenamento");
                            statElem.setAttribute("id", String.valueOf(sa.getId()));
                            statElem.setAttribute("velocitaMax", String.valueOf(sa.getVelocitàMax()));
                            statElem.setAttribute("velocitaMedia", String.valueOf(sa.getVelocitàMedia()));
                            statElem.setAttribute("valutazioneForzaFisica", String.valueOf(sa.getValutazioneForzaFisica()));
                            statElem.setAttribute("valutazioneForzaTiro", String.valueOf(sa.getValutazioneForzaTiro()));
                            statElem.setAttribute("frequenzaCardiacaMedia", String.valueOf(sa.getFrequenzaCardiacaMedia()));
                        } else if (stat instanceof StatisticaAmichevole sa) {
                            statElem = doc.createElement("statisticaAmichevole");
                            statElem.setAttribute("id", String.valueOf(sa.getId()));
                            statElem.setAttribute("minutiGiocati", String.valueOf(sa.getMinutiGiocati()));
                            statElem.setAttribute("goal", String.valueOf(sa.getGoal()));
                            statElem.setAttribute("autogoal", String.valueOf(sa.getAutogoal()));
                            statElem.setAttribute("cartelliniGialli", String.valueOf(sa.getCartelliniGialli()));
                            statElem.setAttribute("cartelliniRossi", String.valueOf(sa.getCartelliniRossi()));
                            statElem.setAttribute("distanzaTotalePercorsa", String.valueOf(sa.getDistanzaTotalePercorsa()));
                            statElem.setAttribute("falliCommessi", String.valueOf(sa.getFalliCommessi()));
                            statElem.setAttribute("assist", String.valueOf(sa.getAssist()));
                            statElem.setAttribute("parate", String.valueOf(sa.getParate()));
                            statElem.setAttribute("intercettiRiusciti", String.valueOf(sa.getIntercettiRiusciti()));
                            statElem.setAttribute("passaggiChiave", String.valueOf(sa.getPassaggiChiave()));
                            statElem.setAttribute("tiriTotali", String.valueOf(sa.getTiriTotali()));
                        } else {
                            continue;
                        }
                        dispElem.appendChild(statElem);
                    }

                    disponibilitaElem.appendChild(dispElem);
                }

                eventoElem.appendChild(disponibilitaElem);
                root.appendChild(eventoElem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("dati/eventi.xml"));
            transformer.transform(source, result);

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
