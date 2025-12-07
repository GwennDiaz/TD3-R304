package Interface;

import Characters.Character;
import Characters.Gallics.Gallic;
import Characters.Gender;
import Characters.Romans.Legionnaire;
import Consommable.FoodItem;
import Location.InvasionTheater;
import Location.Location;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class Game extends JFrame {

    private InvasionTheater theater;
    private JTabbedPane tabsLocations;
    private JTextArea consoleOutput;
    private JLabel turnLabel;

    public Game(InvasionTheater theater) {
        this.theater = theater;

        // --- 1. CONFIGURATION FENÊTRE ---
        setTitle("Invasion Theater - Command Center");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Espacement de 10px

        // Redirection de la console (System.out) vers la zone de texte
        setupConsoleOutput();

        // --- 2. CENTRE : Les Lieux (Onglets) ---
        tabsLocations = new JTabbedPane();
        tabsLocations.setFont(new Font("Arial", Font.BOLD, 14));
        refreshLocationTabs();
        add(tabsLocations, BorderLayout.CENTER);

        // --- 3. BAS : Actions et Logs ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // A. Barre d'actions (Boutons du Chef)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        actionPanel.setBorder(new TitledBorder("Ordres du Chef"));
        actionPanel.setBackground(new Color(230, 230, 240));

        JButton btnHeal = new JButton("💊 Soigner Tout le monde");
        JButton btnFeed = new JButton("🍖 Festin (Nourrir)");
        JButton btnRecruit = new JButton("➕ Recruter Soldat");

        // Style des boutons
        styleButton(btnHeal, new Color(100, 200, 100));
        styleButton(btnFeed, new Color(200, 150, 50));
        styleButton(btnRecruit, new Color(50, 100, 200));

        actionPanel.add(btnHeal);
        actionPanel.add(btnFeed);
        actionPanel.add(btnRecruit);

        // B. Bouton Tour Suivant (Distinct)
        JButton btnNextTurn = new JButton(">>> TOUR SUIVANT >>>");
        btnNextTurn.setFont(new Font("Arial", Font.BOLD, 16));
        btnNextTurn.setBackground(new Color(200, 50, 50));
        btnNextTurn.setForeground(Color.WHITE);
        btnNextTurn.setFocusPainted(false);

        JPanel turnPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel("Tour : 1", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnPanel.add(turnLabel, BorderLayout.NORTH);
        turnPanel.add(btnNextTurn, BorderLayout.CENTER);

        // C. Zone de Logs (L'ancienne bande noire, devenue Journal)
        consoleOutput = new JTextArea(8, 50);
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        consoleOutput.setBackground(new Color(250, 250, 250)); // Fond blanc cassé
        consoleOutput.setForeground(Color.DARK_GRAY);
        JScrollPane scrollLog = new JScrollPane(consoleOutput);
        scrollLog.setBorder(new TitledBorder("Journal des événements"));

        // Assemblage du bas
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.add(actionPanel, BorderLayout.CENTER);
        controlsPanel.add(turnPanel, BorderLayout.EAST);

        bottomPanel.add(controlsPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollLog, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. LISTENERS (Actions des boutons) ---

        btnNextTurn.addActionListener(e -> {
            theater.executeOneTurn();
            refreshUI();
        });

        btnHeal.addActionListener(e -> {
            Location currentLoc = getCurrentLocation();
            if(currentLoc != null) {
                currentLoc.healAllCharacters(); // Suppose que tu as cette méthode
                System.out.println(">> Ordre donné : Soigner tout le monde dans " + currentLoc.getName());
                refreshUI();
            }
        });

        btnFeed.addActionListener(e -> {
            Location currentLoc = getCurrentLocation();
            if(currentLoc != null) {
                currentLoc.feedAllCharacters(); // Suppose que tu as cette méthode
                System.out.println(">> Ordre donné : Festin organisé dans " + currentLoc.getName());
                refreshUI();
            }
        });

        btnRecruit.addActionListener(e -> handleRecruitment());
    }

    // --- LOGIQUE INTERNE ---

    private void refreshUI() {
        turnLabel.setText("Tour : " + theater.getTurn());
        refreshLocationTabs();
    }

    private Location getCurrentLocation() {
        int index = tabsLocations.getSelectedIndex();
        if (index >= 0 && index < theater.getPlaces().size()) {
            return theater.getPlaces().get(index);
        }
        return null;
    }

    private void handleRecruitment() {
        Location loc = getCurrentLocation();
        if (loc == null) return;

        // Fenêtre Popup pour demander le nom
        String name = JOptionPane.showInputDialog(this, "Nom de la recrue :");
        if (name == null || name.trim().isEmpty()) return;

        // Fenêtre Popup pour le type
        String[] options = {"Légionnaire", "Gaulois"};
        int choice = JOptionPane.showOptionDialog(this, "Quel type ?", "Recrutement",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Character newChar = null;
        if (choice == 0) {
            // Création Romain (Adapte les stats selon ton constructeur)
            newChar = new Legionnaire(name, Gender.MALE, 1.80, 25, 50, 50, 100, 0, 10, 0, "Recrue");
        } else if (choice == 1) {
            // Création Gaulois (Adapte selon tes classes dispos, ex: Blacksmith ou Gallic)
            // newChar = new Gallic(name, Gender.MALE, ...);
            // Pour l'exemple j'utilise Legionnaire mais tu devrais mettre un Gaulois ici
            newChar = new Legionnaire(name + " (G)", Gender.MALE, 1.70, 30, 60, 60, 100, 0, 10, 0, "Résistant");
        }

        if (newChar != null) {
            loc.addCharacter(newChar);
            System.out.println(">> Nouvelle recrue : " + name + " a rejoint " + loc.getName());
            refreshUI();
        }
    }

    // --- VISUEL ---

    private void refreshLocationTabs() {
        int selected = tabsLocations.getSelectedIndex();
        tabsLocations.removeAll();

        for (Location loc : theater.getPlaces()) {
            JPanel panel = new JPanel(new BorderLayout());

            // Liste des persos
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Character c : loc.getPresentCharacters()) {
                String etat = c.isDead() ? "💀" : "❤️";
                model.addElement(etat + " " + c.getName() + " | HP:" + c.getHealth() + " | Faim:" + c.getHunger());
            }
            JList<String> list = new JList<>(model);
            list.setFont(new Font("Monospaced", Font.PLAIN, 14));
            panel.add(new JScrollPane(list), BorderLayout.CENTER);

            // Info stock
            JTextArea stockInfo = new JTextArea();
            stockInfo.setEditable(false);
            stockInfo.setText("Vivres :\n");
            // Ajoute ici la boucle sur loc.getStockFood() si tu veux afficher la nourriture

            panel.add(new JScrollPane(stockInfo), BorderLayout.EAST);

            tabsLocations.addTab(loc.getName(), panel);
        }

        if (selected >= 0 && selected < tabsLocations.getTabCount()) {
            tabsLocations.setSelectedIndex(selected);
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
    }

    private void setupConsoleOutput() {
        PrintStream printStream = new PrintStream(new CustomOutputStream(consoleOutput));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    private class CustomOutputStream extends OutputStream {
        private JTextArea textArea;
        public CustomOutputStream(JTextArea textArea) { this.textArea = textArea; }
        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}