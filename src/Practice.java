import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

public class Practice {

  /**
   * Returns the count of vertices with odd values that can be reached from the given starting vertex.
   * The starting vertex is included in the count if its value is odd.
   * If the starting vertex is null, returns 0.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 4
   *   |     |
   *   v     v
   *   8 --> 7 < -- 1
   *   |
   *   v
   *   9
   * 
   * Starting from 5, the odd nodes that can be reached are 5, 7, and 9.
   * Thus, given 5, the number of reachable odd nodes is 3.
   * @param starting the starting vertex (may be null)
   * @return the number of vertices with odd values reachable from the starting vertex
   */
  public static int oddVertices(Vertex<Integer> starting) {
    Set<Vertex<Integer>> visited = new HashSet<>();
    return oddVertices(starting, visited);
  }

  private static int oddVertices(Vertex<Integer> current, Set<Vertex<Integer>> visited){
    if(current==null) return 0;
    if(visited.contains(current)) return 0;
    visited.add(current);

    int totalOdd = 0;
    if(current.data %2 != 0) totalOdd++;
    
    for(Vertex<Integer> neighbor : current.neighbors){
      totalOdd += oddVertices(neighbor, visited);
    }

    return totalOdd;
  }

  /**
   * Returns a *sorted* list of all values reachable from the starting vertex (including the starting vertex itself).
   * If duplicate vertex data exists, duplicates should appear in the output.
   * If the starting vertex is null, returns an empty list.
   * They should be sorted in ascending numerical order.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 8
   *   |     |
   *   v     v
   *   8 --> 2 <-- 4
   * When starting from the vertex with value 5, the output should be:
   *   [2, 5, 8, 8]
   *
   * @param starting the starting vertex (may be null)
   * @return a sorted list of all reachable vertex values by 
   */
  public static List<Integer> sortedReachable(Vertex<Integer> starting) {
    return sortedReachable(starting, new HashSet<>(), new ArrayList<>());
  }

  private static List<Integer> sortedReachable(Vertex<Integer> current, Set<Vertex<Integer>> visited, List<Integer> nums){
    if(current==null || visited.contains(current)) return new ArrayList<>();
    visited.add(current);

    nums.add(current.data);

    for(Vertex<Integer> neighbor: current.neighbors){
      sortedReachable(neighbor, visited, nums);
    }

    Collections.sort(nums);

    return nums;
  }

  /**
   * Returns a sorted list of all values reachable from the given starting vertex in the provided graph.
   * The graph is represented as a map where each key is a vertex and its corresponding value is a set of neighbors.
   * It is assumed that there are no duplicate vertices.
   * If the starting vertex is not present as a key in the map, returns an empty list.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @return a sorted list of all reachable vertex values
   */
  public static List<Integer> sortedReachable(Map<Integer, Set<Integer>> graph, int starting) {
    return sortedReachable(graph, starting, new HashSet<>(), new ArrayList<>());
  }

  private static List<Integer> sortedReachable(Map<Integer, Set<Integer>> graph, int current, Set<Integer> visited, List<Integer> nums){
    if(visited.contains(current) || !graph.containsKey(current)) return new ArrayList<>();
    visited.add(current);

    nums.add(current);

    for(int neighbor : graph.get(current)){
      sortedReachable(graph, neighbor, visited, nums);
    }

    Collections.sort(nums);

    return nums;
  }

  /**
   * Returns true if and only if it is possible both to reach v2 from v1 and to reach v1 from v2.
   * A vertex is always considered reachable from itself.
   * If either v1 or v2 is null or if one cannot reach the other, returns false.
   *
   * Example:
   * If v1 and v2 are connected in a cycle, the method should return true.
   * If v1 equals v2, the method should also return true.
   *
   * @param <T> the type of data stored in the vertex
   * @param v1 the starting vertex
   * @param v2 the target vertex
   * @return true if there is a two-way connection between v1 and v2, false otherwise
   */
  public static <T> boolean twoWay(Vertex<T> v1, Vertex<T> v2) {
    return twoWay(v1, v2, new HashSet<>()) && twoWay(v2, v1, new HashSet<>());
  }

  private static <T> boolean twoWay(Vertex<T> current, Vertex<T> target, Set<Vertex<T>> visited){
    if(current==null || target==null || visited.contains(current)) return false;
    visited.add(current);

    if(current==target) return true;

    for(Vertex<T> neighbor : current.neighbors){
      if(twoWay(neighbor, target, visited)) return true;
    }

    return false;
  }

  /**
   * Returns whether there exists a path from the starting to ending vertex that includes only positive values.
   * 
   * The graph is represented as a map where each key is a vertex and each value is a set of directly reachable neighbor vertices. A vertex is always considered reachable from itself.
   * If the starting or ending vertex is not positive or is not present in the keys of the map, or if no valid path exists,
   * returns false.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @param ending the ending vertex value
   * @return whether there exists a valid positive path from starting to ending
   */
  public static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int starting, int ending) { //key is the vertex, values are the neighbors
    return positivePathExists(graph, starting, ending, new HashSet<>());
  }

  private static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int current, int target, Set<Integer> visited){
    if(!graph.containsKey(current) || !graph.containsKey(target) || visited.contains(current)) return false;
    visited.add(current);

    if(current <= 0 || target <= 0) return false;
    if(current==target) return true; 
    
    for(int neighbor : graph.get(current)){
      if(positivePathExists(graph, neighbor, target, visited)) return true;
    }

    return false;
  }

  /**
   * Returns true if a professional has anyone in their extended network (reachable through any number of links)
   * that works for the given company. The search includes the professional themself.
   * If the professional is null, returns false.
   *
   * @param person the professional to start the search from (may be null)
   * @param companyName the name of the company to check for employment
   * @return true if a person in the extended network works at the specified company, false otherwise
   */
  public static boolean hasExtendedConnectionAtCompany(Professional person, String companyName) {
    return hasExtendedConnectionAtCompany(person, companyName, new HashSet<>());
  }

  public static boolean hasExtendedConnectionAtCompany(Professional currentPerson, String targetCompany, Set<Professional> visited){
    if(currentPerson==null || targetCompany==null || visited.contains(currentPerson)) return false;
    visited.add(currentPerson);

    if(currentPerson.getCompany()==targetCompany) return true;

    for(Professional person : currentPerson.getConnections()){
      if(hasExtendedConnectionAtCompany(person, targetCompany, visited)) return true;
    }

    return false;
  }

  /**
   * Returns a list of possible next moves starting from a given position.
   * 
   * Starting from current, which is a [row, column] location, a player can move 
   * by one according to the directions provided.
   * 
   * The directions are given as a 2D array, where each inner array is a [row, column]
   * pair that describes a move. For example, if given the below array it would be possible
   * to move to the right, up, down, or down/left diagonally.
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * However, the player can not move off the edge of the board, or onto any 
   * location that has an 'X' (capital X).
   * 
   * The possible moves are returned as a List of [row, column] pairs. The List
   * can be in any order.
   * 
   * Example:
   * 
   * board: 
   * {
   *  {' ', ' ', 'X'},
   *  {'X', ' ', ' '},
   *  {' ', ' ', ' '}
   * }
   * 
   * current:
   * {1, 2}
   * 
   * directions:
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * expected output (order of list is unimportant):
   * [{2, 2}, {2, 1}]
   * 
   * Explanation:
   * The player starts at {1, 2}.
   * The four directions the player might have to go are right, up, down, and down/left (based on the directions array).
   * They cannot go right because that would go off the edge of the board.
   * They cannot go up because there is an X.
   * They can go down.
   * They can go down/left.
   * The resultant list has the two possible positions.
   * 
   * 
   * You can assume the board is rectangular.
   * You can assume valid input (no nulls, properly sized arrays, current is in-bounds,
   * directions only move 1 square, any row/column pairs are arrays of length 2,
   * directions are unique).
   * 
   * If there are no possible moves, the method returns an empty list.
   * 
   * @param board a rectangular array where 'X' represent an impassible location
   * @param current the [row, column] starting position of the player
   * @param directions an array of [row, column] possible directions
   * @return an unsorted list of next moves
   */
  public static List<int[]> nextMoves(char[][] board, int[] current, int[][] directions) {
    return possibleMoves(board, current);
  }

  public static List<int[]> possibleMoves(char[][] board, int[] current){
    int curR = current[0];
    int curC = current[1];
    
    int newR, newC;
    List<int[]> possible = new ArrayList<>();

    //up
    newR = curR -1;
    newC = curC;
    if(newR >= 0 && board[newR][newC] != 'X'){
        possible.add(new int[] {newR, newC});
    }
    //down
    newR = curR +1;
    newC = curC;
    if(newR < board.length && board[newR][newC] != 'X'){
        possible.add(new int[] {newR, newC});
    }
    //left
    newR = curR;
    newC = curC -1;
    if(newC >= 0 && board[newR][newC] != 'X'){
        possible.add(new int[] {newR, newC});
    }
    //right
    newR = curR;
    newC = curC+1;
    if(newC < board[newR].length && board[newR][newC] != 'X'){
        possible.add(new int[] {newR, newC});
    }

    return possible;
  }
}
