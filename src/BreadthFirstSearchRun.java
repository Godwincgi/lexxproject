import java.util.*;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to manage the transport routes
class BreadthTransportSystem {
    private Map<String, List<String>> adjacencyList;

    public BreadthTransportSystem() {
        adjacencyList = new HashMap<>();
    }

    public void addRoute(String start, String end) {
        adjacencyList.computeIfAbsent(start, k -> new ArrayList<>()).add(end);
        adjacencyList.computeIfAbsent(end, k -> new ArrayList<>()).add(start);
    }

    public List<String> findShortestPath(String start, String end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            return Collections.emptyList();
        }

        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(Arrays.asList(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String lastNode = path.get(path.size() - 1);

            if (lastNode.equals(end)) {
                return path;
            }

            for (String neighbor : adjacencyList.get(lastNode)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        return Collections.emptyList();
    }
}

// Main class to run the breadth-first search application
public class BreadthFirstSearchRun extends JPanel {
    private BreadthTransportSystem transportSystem;

    public BreadthFirstSearchRun() {
        transportSystem = new BreadthTransportSystem();
        transportSystem.addRoute("Abuja", "Lagos");
        transportSystem.addRoute("Abuja", "Calabar");
        transportSystem.addRoute("Lagos", "Uyo");
        transportSystem.addRoute("Calabar", "Uyo");
        transportSystem.addRoute("Calabar", "Enugu");
        transportSystem.addRoute("Uyo", "Enugu");

        JFrame frame = new JFrame("Breadth First Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set the background of the main panel to white

        // Panel for city labels with padding
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(new Color(102, 204, 102)); // Keeping the original green color for labels
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        JLabel[] cityLabels = { new JLabel("Abuja"), new JLabel("Lagos"), new JLabel("Calabar"),
                                new JLabel("Uyo"), new JLabel("Enugu") };

        for (JLabel label : cityLabels) {
            label.setFont(labelFont);
            label.setBorder(new EmptyBorder(0, 15, 0, 15));
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
        add(labelPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Adding main panel to frame
        frame.add(this);

        // Action Listener for button click
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startText.getText();
                String end = endText.getText();
                List<String> path = transportSystem.findShortestPath(start, end);
                if (path.isEmpty()) {
                    resultArea.setText("No path found.");
                } else {
                    resultArea.setText("Shortest path:  " + String.join(" -> ", path));
                }
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BreadthFirstSearchRun::new);
    }
}
