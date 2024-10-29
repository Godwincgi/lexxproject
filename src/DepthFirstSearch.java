import java.util.*;

public class DepthFirstSearch {
    private Map<String, List<String>> adjacencyList;

    public DepthFirstSearch() {
        adjacencyList = new HashMap<>();
    }

    // Add an edge between two nodes
    public void addEdge(String start, String end) {
        adjacencyList.computeIfAbsent(start, k -> new ArrayList<>()).add(end);
        adjacencyList.computeIfAbsent(end, k -> new ArrayList<>()).add(start);
    }

    // DFS algorithm to find a path from start to end
    public List<String> findPath(String start, String end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            return Collections.emptyList();
        }

        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        if (dfs(start, end, visited, path)) {
            return path;
        }

        return Collections.emptyList(); // No path found
    }

    private boolean dfs(String current, String end, Set<String> visited, List<String> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        for (String neighbor : adjacencyList.get(current)) {
            if (!visited.contains(neighbor)) {
                if (dfs(neighbor, end, visited, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1); // Backtrack
        return false;
    }
}
