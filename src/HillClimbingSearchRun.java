import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HillClimbingSearchRun {
    private HillClimbingSystem transportSystem;
    private HillClimbing hillClimbing;

    public HillClimbingSearchRun() {
        transportSystem = new HillClimbingSystem();
        // Sample routes
        transportSystem.addRoute("Abuja", "Lagos");
        transportSystem.addRoute("Abuja", "Calabar");
        transportSystem.addRoute("Lagos", "Uyo");
        transportSystem.addRoute("Calabar", "Uyo");
        transportSystem.addRoute("Calabar", "Enugu");
        transportSystem.addRoute("Uyo", "Enugu");

        hillClimbing = new HillClimbing(transportSystem);

        // Create the GUI
        JFrame frame = new JFrame("Hill Climbing Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE); // Set the background of the main panel to white

        // Panel for city labels with padding
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(new Color(102, 204, 102)); // Keeping the original green color for labels
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        JLabel[] cityLabels = {
            new JLabel("Abuja"), new JLabel("Lagos"), new JLabel("Calabar"),
            new JLabel("Uyo"), new JLabel("Enugu")
        };

        for (JLabel label : cityLabels) {
            label.setFont(labelFont);
            label.setForeground(Color.WHITE);
            labelPanel.add(label);
        }

        // Panel for user inputs with structured layout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setOpaque(true); // Make it opaque
        inputPanel.setBackground(Color.WHITE); // Set input panel background to white
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel startLabel = new JLabel("Position:");
        startLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        inputPanel.add(startLabel, gbc);

        gbc.gridx = 1;
        JTextField startText = new JTextField(25);
        inputPanel.add(startText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel endLabel = new JLabel("Destination:");
        endLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        inputPanel.add(endLabel, gbc);

        gbc.gridx = 1;
        JTextField endText = new JTextField(25);
        inputPanel.add(endText, gbc);

        // Search button with padding and color
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Find Path");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        searchButton.setBackground(new Color(12, 196, 43));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        inputPanel.add(searchButton, gbc);

        // Text area for results with scroll pane
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(true); // Make it opaque
        bottomPanel.setBackground(Color.WHITE); // Set bottom panel background to white
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Available Routes Listed Above", SwingConstants.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        bottomPanel.add(footerLabel, BorderLayout.SOUTH);

        // Adding components to the main panel
        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Adding main panel to frame
        frame.add(panel);

        // Action Listener for button click
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startText.getText();
                String end = endText.getText();
                List<String> path = hillClimbing.findPath(start, end);
                if (path.isEmpty()) {
                    resultArea.setText("No path found.");
                } else {
                    resultArea.setText("Path: " + String.join(" -> ", path));
                }
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HillClimbingSearchRun::new);
    }
}
