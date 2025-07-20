![Logo del mio progetto](logo.png)

# ⚽ TeamManager

**TeamManager** è un'applicazione desktop Java per la gestione completa di una squadra di calcio, pensata per allenatori e staff tecnico. Consente di gestire giocatori, rosa, eventi (allenamenti e amichevoli), statistiche, disponibilità e utenti.

---

## 🚀 Funzionalità principali

- ✅ Gestione anagrafica giocatori
- 📋 Gestione rosa e ruoli
- 📅 Gestione calendario eventi:
  - Allenamenti (con tipologia e note)
  - Amichevoli (con squadra avversaria)
- 📈 Gestione disponibilità dei giocatori
- 📊 Registrazione e analisi delle statistiche individuali
- 🔒 Login con utenti predefiniti (es. Allenatore, Preparatore, ecc.)
- 💾 Salvataggio e caricamento dei dati tramite file XML
- 🧪 Struttura modulare con test unitari JUnit

---

## 📦 Architettura

Il progetto segue il pattern **MVC (Model-View-Controller)** con il supporto del pattern **Facade**.

### 📁 Packages principali

- `model` — contiene le classi dei dati: `Giocatore`, `Evento`, `Statistica`, `Disponibilità`, ecc.
- `controller` — include:
  - `TeamManager`: **facade** del sistema
  - `PersistenceHandler`: gestisce caricamento/salvataggio XML
- `view` — gestione dell'interfaccia grafica Java Swing (es. `TeamManagerGUI`)
- `test` — test JUnit (es. `TeamManagerTest`)

---

## 🧠 Persistenza

I dati vengono caricati all'avvio dell'applicazione e salvati alla chiusura. I file XML si trovano nella cartella `dati/`, nella root del progetto (allo stesso livello del `.jar`):

### 📂 Struttura file XML

- `dati/giocatori.xml` — Giocatori anagrafici
- `dati/rosa.xml` — Rosa (ruolo, status, numero maglia, ecc.)
- `dati/eventi.xml` — Allenamenti e amichevoli, con disponibilità e statistiche
- `dati/utenti.xml` — Utenti (solo caricamento, non modificabile da interfaccia)

---

## 📄 Esempio struttura `eventi.xml`

```xml
<eventi>
    <allenamento id="1">
        <data>2023-04-05</data>
        <orario>10:00:00</orario>
        <durata>90</durata>
        <luogo>Campo Principale</luogo>
        <tipologia>Ripresa atletica</tipologia>
        <note>Recupero post-partita</note>
        <disponibilità>
            <disponibile idGiocatore="4" presente="true">
                <motivazione></motivazione>
                <statisticaAllenamento id="101"
                    velocitaMax="28.5"
                    velocitaMedia="16.2"
                    valutazioneForzaFisica="7"
                    valutazioneForzaTiro="8"
                    frequenzaCardiacaMedia="135" />
            </disponibile>
        </disponibilità>
    </allenamento>
</eventi>
```

---

## 🧪 Test

Il progetto include test JUnit nella classe `TeamManagerTest`, ad esempio:

- Creazione ed eliminazione giocatori
- Singleton di `TeamManager`
- Inizializzazione da `PersistenceHandler`

---

## 🔧 Avvio del programma

1. Apri il progetto con **NetBeans** (o IDE compatibile)
2. Assicurati che esista la cartella `dati/` nella root, con i file `.xml`
3. Avvia il metodo `main()` di `TeamManagerGUI`
4. Login con un utente (email/password predefiniti nel file `utenti.xml`)

---

## 📋 Dipendenze

- Java 17+
- Librerie standard Java (`javax.xml`, `org.w3c.dom`)
- Java Swing (GUI)
- JUnit 4 (test)

---

## 🙋‍♂️ Autore

**Giuseppe Ravesi** , **Vincenzo Venezia**, **Antonio Scarvaglieri**

---

## 📄 License

This project is licensed under the **Creative Commons Attribution 4.0 International License (CC BY 4.0)**.  
You are free to use, share, and modify it for any purpose, including commercial ones, **as long as you give appropriate credit** to the original author.

[Read the full license here](https://creativecommons.org/licenses/by/4.0/)
---

## 💡 Suggerimenti

- Evita di aprire i file XML vuoti: se assenti o malformati, l'app mostra un messaggio ma non va in crash
- Lavora sulle stesse istanze caricate da `PersistenceHandler` tramite `TeamManager.inizializzaDatiDaPersistence(handler)`

---

Buon lavoro con **TeamManager**! 💪⚽
