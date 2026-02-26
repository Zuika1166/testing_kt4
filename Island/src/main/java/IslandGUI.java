import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class IslandGUI extends JFrame {
    private static final int CELL_SIZE = 15;
    private static final int CANVAS_WIDTH = 900;
    private static final int CANVAS_HEIGHT = 450;

    private Island island;
    private IslandPanel islandPanel;
    private JLabel tickLabel;
    private JLabel totalAnimalsLabel;
    private JLabel totalPlantsLabel;
    private JTextArea statisticsArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JPanel controlPanel;
    private JPanel statisticsPanel;

    private Map<String, Color> animalColors;
    private Map<String, String> animalSymbols;

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JSpinner animalsSpinner;
    private JSpinner ticksSpinner;

    public IslandGUI() {
        initializeAnimalColorsAndSymbols();
        setupGUI();
    }

    private void initializeAnimalColorsAndSymbols() {
        animalColors = new HashMap<>();
        animalSymbols = new HashMap<>();

        animalColors.put("Wolf", new Color(139, 0, 0));
        animalColors.put("Snake", new Color(0, 100, 0));
        animalColors.put("Fox", new Color(255, 140, 0));
        animalColors.put("Bear", new Color(165, 42, 42));
        animalColors.put("Eagle", new Color(255, 215, 0));

        animalColors.put("Horse", new Color(0, 0, 139));
        animalColors.put("Deer", new Color(139, 69, 19));
        animalColors.put("Rabbit", new Color(211, 211, 211));
        animalColors.put("Mouse", new Color(128, 128, 128));
        animalColors.put("Goat", Color.WHITE);
        animalColors.put("Sheep", new Color(220, 220, 220));
        animalColors.put("Boar", new Color(47, 79, 79));
        animalColors.put("Buffalo", new Color(139, 0, 0));
        animalColors.put("Duck", Color.CYAN);
        animalColors.put("Caterpillar", new Color(50, 205, 50));

        animalSymbols.put("Wolf", "W");
        animalSymbols.put("Snake", "S");
        animalSymbols.put("Fox", "F");
        animalSymbols.put("Bear", "B");
        animalSymbols.put("Eagle", "E");
        animalSymbols.put("Horse", "H");
        animalSymbols.put("Deer", "D");
        animalSymbols.put("Rabbit", "R");
        animalSymbols.put("Mouse", "M");
        animalSymbols.put("Goat", "G");
        animalSymbols.put("Sheep", "S");
        animalSymbols.put("Boar", "B");
        animalSymbols.put("Buffalo", "U");
        animalSymbols.put("Duck", "K");
        animalSymbols.put("Caterpillar", "C");
    }

    private void setupGUI() {
        setTitle("Island Ecosystem Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createControlPanel();
        createIslandPanel();
        createStatisticsPanel();
        createConfigurationPanel();

        pack();
        setLocationRelativeTo(null);
        setSize(1400, 800);
    }

    private void createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(60, 63, 65));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tickLabel = createStyledLabel("Tick: 0");
        totalAnimalsLabel = createStyledLabel("Animals: 0");
        totalPlantsLabel = createStyledLabel("Plants: 0");

        startButton = createStyledButton("Start", new Color(76, 175, 80));
        stopButton = createStyledButton("Stop", new Color(244, 67, 54));
        resetButton = createStyledButton("Reset", new Color(255, 152, 0));

        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());
        resetButton.addActionListener(e -> resetSimulation());

        stopButton.setEnabled(false);

        controlPanel.add(tickLabel);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(totalAnimalsLabel);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(totalPlantsLabel);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);

        add(controlPanel, BorderLayout.NORTH);
    }

    private void createIslandPanel() {
        JPanel islandContainer = new JPanel(new BorderLayout());
        islandContainer.setBackground(new Color(43, 43, 43));

        islandPanel = new IslandPanel();
        JScrollPane scrollPane = new JScrollPane(islandPanel);
        scrollPane.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        scrollPane.getViewport().setBackground(new Color(43, 43, 43));

        islandContainer.add(scrollPane, BorderLayout.CENTER);
        add(islandContainer, BorderLayout.CENTER);
    }

    private void createStatisticsPanel() {
        statisticsPanel = new JPanel(new BorderLayout());
        statisticsPanel.setPreferredSize(new Dimension(350, 600));
        statisticsPanel.setBackground(new Color(60, 63, 65));
        statisticsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(85, 85, 85)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel statsTitle = new JLabel("Statistics");
        statsTitle.setForeground(Color.WHITE);
        statsTitle.setFont(new Font("Arial", Font.BOLD, 16));

        statisticsArea = new JTextArea();
        statisticsArea.setEditable(false);
        statisticsArea.setBackground(new Color(43, 43, 43));
        statisticsArea.setForeground(Color.WHITE);
        statisticsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statisticsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane statsScroll = new JScrollPane(statisticsArea);
        statsScroll.setPreferredSize(new Dimension(300, 400));

        JPanel legendPanel = createLegendPanel();

        statisticsPanel.add(statsTitle, BorderLayout.NORTH);
        statisticsPanel.add(statsScroll, BorderLayout.CENTER);
        statisticsPanel.add(legendPanel, BorderLayout.SOUTH);

        add(statisticsPanel, BorderLayout.EAST);
    }

    private void createConfigurationPanel() {
        JPanel configPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        configPanel.setBackground(new Color(60, 63, 65));
        configPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Configuration"),
                new EmptyBorder(10, 10, 10, 10)
        ));

        configPanel.setPreferredSize(new Dimension(300, 150));

        widthSpinner = new JSpinner(new SpinnerNumberModel(60, 10, 200, 10));
        heightSpinner = new JSpinner(new SpinnerNumberModel(30, 10, 100, 5));
        animalsSpinner = new JSpinner(new SpinnerNumberModel(500, 100, 5000, 100));
        ticksSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 1000, 10));

        addConfigRow(configPanel, "Island Width:", widthSpinner);
        addConfigRow(configPanel, "Island Height:", heightSpinner);
        addConfigRow(configPanel, "Initial Animals:", animalsSpinner);
        addConfigRow(configPanel, "Max Ticks:", ticksSpinner);

        JButton applyButton = createStyledButton("Apply", new Color(33, 150, 243));
        applyButton.addActionListener(e -> applyConfiguration());

        configPanel.add(new JLabel());
        configPanel.add(applyButton);

        add(configPanel, BorderLayout.WEST);
    }

    private void addConfigRow(JPanel panel, String label, JSpinner spinner) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        spinner.setPreferredSize(new Dimension(80, 25));
        panel.add(spinner);
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new BorderLayout());
        legendPanel.setBackground(new Color(43, 43, 43));
        legendPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Legend"),
                new EmptyBorder(10, 5, 5, 5)
        ));

        JPanel symbolsPanel = new JPanel(new GridLayout(0, 2, 5, 2));
        symbolsPanel.setBackground(new Color(43, 43, 43));

        for (Map.Entry<String, String> entry : animalSymbols.entrySet()) {
            JPanel symbolItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
            symbolItem.setBackground(new Color(43, 43, 43));

            JLabel symbolLabel = new JLabel(entry.getValue());
            symbolLabel.setForeground(animalColors.get(entry.getKey()));
            symbolLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel nameLabel = new JLabel(entry.getKey());
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));

            symbolItem.add(symbolLabel);
            symbolItem.add(nameLabel);
            symbolsPanel.add(symbolItem);
        }


        JPanel plantItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plantItem.setBackground(new Color(43, 43, 43));
        JLabel plantSymbol = new JLabel("P");
        plantSymbol.setForeground(new Color(50, 205, 50));
        JLabel plantName = new JLabel("Plants");
        plantName.setForeground(Color.WHITE);
        plantName.setFont(new Font("Arial", Font.PLAIN, 10));
        plantItem.add(plantSymbol);
        plantItem.add(plantName);
        symbolsPanel.add(plantItem);

        JScrollPane legendScroll = new JScrollPane(symbolsPanel);
        legendScroll.setPreferredSize(new Dimension(300, 200));
        legendScroll.getViewport().setBackground(new Color(43, 43, 43));

        legendPanel.add(legendScroll, BorderLayout.CENTER);
        return legendPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(85, 85, 85)),
                new EmptyBorder(5, 10, 5, 10)
        ));
        label.setOpaque(true);
        label.setBackground(new Color(85, 85, 85));
        return label;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker()),
                new EmptyBorder(8, 15, 8, 15)
        ));
        return button;
    }

    private void startSimulation() {
        if (island == null || !island.isRunning()) {
            if (island != null) {
                island.stopSimulation();
            }

            int width = (Integer) widthSpinner.getValue();
            int height = (Integer) heightSpinner.getValue();

            island = new Island(width, height);
            islandPanel.setIsland(island);

            // Запускаем в отдельном потоке
            new Thread(() -> {
                island.startSimulation();
            }).start();

            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            Timer guiTimer = new Timer(1000, e -> updateGUI());
            guiTimer.start();
        }
    }

    private void stopSimulation() {
        if (island != null && island.isRunning()) {
            island.stopSimulation();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private void resetSimulation() {
        stopSimulation();
        SwingUtilities.invokeLater(() -> {
            tickLabel.setText("Tick: 0");
            totalAnimalsLabel.setText("Animals: 0");
            totalPlantsLabel.setText("Plants: 0");
            statisticsArea.setText("");
            if (islandPanel != null) {
                islandPanel.repaint();
            }
        });
    }

    private void updateGUI() {
        if (island != null) {
            SwingUtilities.invokeLater(() -> {
                tickLabel.setText("Tick: " + island.getCurrentTick());
                totalAnimalsLabel.setText("Animals: " + island.getTotalAnimals());
                totalPlantsLabel.setText("Plants: " + island.getTotalPlants());

                StringBuilder stats = new StringBuilder();
                stats.append("=== Island Statistics ===\n");
                stats.append("Tick: ").append(island.getCurrentTick()).append("\n");
                stats.append("Total Animals: ").append(island.getTotalAnimals()).append("\n");
                stats.append("Total Plants: ").append(island.getTotalPlants()).append("\n\n");
                stats.append("Animals by type:\n");

                Map<String, Integer> animalCounts = island.getAnimalCounts();
                animalCounts.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .forEach(entry -> {
                            stats.append(String.format("  %-12s: %d\n", entry.getKey(), entry.getValue()));
                        });

                statisticsArea.setText(stats.toString());

                if (islandPanel != null) {
                    islandPanel.repaint();
                }
            });
        }
    }

    private void applyConfiguration() {
        JOptionPane.showMessageDialog(this,
                "Configuration will be applied on next simulation start",
                "Configuration",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new IslandGUI().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    class IslandPanel extends JPanel {
        private Island currentIsland;

        public IslandPanel() {
            setBackground(new Color(43, 43, 43));
            setPreferredSize(new Dimension(2000, 2000));
        }

        public void setIsland(Island island) {
            this.currentIsland = island;
            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (currentIsland == null) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cellWidth = CELL_SIZE;
            int cellHeight = CELL_SIZE;

            for (int x = 0; x < currentIsland.getWidth(); x++) {
                for (int y = 0; y < currentIsland.getHeight(); y++) {
                    IslandCell cell = currentIsland.getCell(x, y);
                    if (cell != null) {
                        drawCell(g2d, cell, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }

        private void drawCell(Graphics2D g2d, IslandCell cell, int x, int y, int width, int height) {

            g2d.setColor(new Color(0, 100, 0));
            g2d.fillRect(x, y, width, height);

            int plants = cell.getPlantsCount();
            if (plants > 0) {
                float plantDensity = Math.min(1.0f, plants / 50.0f);
                Color plantColor = new Color(50, 205, 50, (int)(plantDensity * 76));
                g2d.setColor(plantColor);
                g2d.fillRect(x, y, width, height);
            }

            Map<String, Integer> animalsInCell = new HashMap<>();
            for (Animal animal : cell.getAnimals()) {
                if (animal.isAlive()) {
                    String animalName = animal.getName();
                    animalsInCell.put(animalName, animalsInCell.getOrDefault(animalName, 0) + 1);
                }
            }

            int counter = 0;
            for (Map.Entry<String, Integer> entry : animalsInCell.entrySet()) {
                if (counter >= 4) break;

                String animalName = entry.getKey();
                Color color = animalColors.getOrDefault(animalName, Color.WHITE);
                String symbol = animalSymbols.getOrDefault(animalName, "?");

                int offsetX = x + (counter % 2) * (width / 2);
                int offsetY = y + (counter / 2) * (height / 2);

                g2d.setColor(color.darker().darker());
                g2d.fillOval(offsetX + 2, offsetY + 2, width/2 - 4, height/2 - 4);

                g2d.setColor(color);
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                g2d.drawString(symbol, offsetX + 4, offsetY + height/2 - 2);

                if (entry.getValue() > 1) {
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, 8));
                    g2d.drawString(String.valueOf(entry.getValue()), offsetX + width/2 - 8, offsetY + height/2 - 2);
                }

                counter++;
            }

            g2d.setColor(new Color(50, 50, 50));
            g2d.setStroke(new BasicStroke(0.5f));
            g2d.drawRect(x, y, width, height);
        }
    }
}