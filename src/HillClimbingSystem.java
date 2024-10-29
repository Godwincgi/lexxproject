import java.util.*;

public class HillClimbingSystem {
    private Map<String, List<String>> adjacencyList;

    public HillClimbingSystem() {
        adjacencyList = new HashMap<>();
    }

    // Add a route to the transport system
    public void addRoute(String start, String end) {
        adjacencyList.computeIfAbsent(start, k -> new ArrayList<>()).add(end);
        adjacencyList.computeIfAbsent(end, k -> new ArrayList<>()).add(start);
    }

    // Get the neighbors of a node
    public List<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    // Check if a node is valid
    public boolean isValidNode(String node) {
        return adjacencyList.containsKey(node);
    }
}
