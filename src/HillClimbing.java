import java.util.*;

public class HillClimbing {
    private HillClimbingSystem transportSystem;

    public HillClimbing(HillClimbingSystem transportSystem) {
        this.transportSystem = transportSystem;
    }

    // Heuristic function to estimate the 'cost' of moving to the next node
    private int heuristic(String current, String goal) {
        // For simplicity, we use a dummy heuristic that returns 0
        return 0;
    }

    // Hill Climbing algorithm to find a path from start to end
    public List<String> findPath(String start, String end) {
        if (!transportSystem.isValidNode(start) || !transportSystem.isValidNode(end)) {
            return Collections.emptyList();
        }

        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        path.add(start);
        visited.add(start);

        while (true) {
            String current = path.get(path.size() - 1);
            if (current.equals(end)) {
                return path;
            }

            List<String> neighbors = transportSystem.getNeighbors(current);
            if (neighbors.isEmpty()) {
                return Collections.emptyList();
            }

            String next = null;
            int bestHeuristic = Integer.MAX_VALUE;

            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    int h = heuristic(neighbor, end);
                    if (h < bestHeuristic) {
                        bestHeuristic = h;
                        next = neighbor;
                    }
                }
            }

            if (next == null) {
                return Collections.emptyList(); // No more nodes to explore
            }

            path.add(next);
            visited.add(next);
        }
    }
}
