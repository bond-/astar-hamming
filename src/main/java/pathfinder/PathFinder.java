package pathfinder;

import pathfinder.heuristics.HammingHeuristic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Uses Dijkstra and HammingDistance heuristic
 */
public class PathFinder{
    private HashSet<Node> input;
    private HashSet<String> closed;
    private ArrayList<String> cameFrom;
    private HashSet<String> dictionary;
    private HammingHeuristic heuristic;

    /**
     * Accepts a HammingDistance heuristic and initializes the class
     * @param heuristic    The hamming distance heuristic
     * @throws IOException
     */
    public PathFinder(HammingHeuristic heuristic) throws IOException {
        this.heuristic = heuristic;
        input = new HashSet<Node>();
        closed = new HashSet<String>();
        cameFrom = new ArrayList<String>();
        dictionary = new HashSet<String>();
        loadDictionary();
    }

    /**
     * Prints out the shortest possible path from start word to stop word using words from a existing dictionary of the same length
     * Time complexity: O(n)
     * @param start    The start word
     * @param stop     The stop word
     * @return  The shortest path
     */
    public String getShortestPath(String start,String stop){
        int cameFromSize = cameFrom.size();
        String nextBest = null;
        while (!stop.equals(nextBest) && closed.size()<dictionary.size()){
            if(cameFromSize>0){
                nextBest = processDictionary(start,stop,cameFrom.get(cameFromSize-1));
            }else{
                nextBest = processDictionary(start,stop,start);
            }
            cameFrom.add(nextBest);
        }
        return "Shortest path: " + cameFrom.toString();
    }

    /**
     * Checks the words from dictionary
     * Time complexity: O(n)
     * @param start      The start word
     * @param stop       The stop word
     * @param current    The current word, currently unused
     * @return  The lowest cost word which would act as a checkpoint
     */
    private String processDictionary(String start,String stop,String current){
        input.clear();
        int limit = start.length();
        for (String value : dictionary) {
            if (!closed.contains(value) && value.length()==limit && heuristic.getDistanceToGoal(start,value)==1) {
                Node node = new Node();
                node.cost = getCost(start, stop, value);
//                System.out.println("Value: "+value+", Cost: "+node.cost);
                node.value = value;
                input.add(node);
                closed.add(value);
            }
        }
        return findLowestCostNode(stop);
    }

    /**
     * Finds the lowest cost node after looking up the complete dictionary
     * Time complexity: O(n)
     * @param stop    The Stop word
     * @return The lowest cost node
     */
    private String findLowestCostNode(String stop) {
        if(input.size()!=0){
            int cost = Integer.MAX_VALUE;
            Node minCostNode = null;
            for (Node node : input) {
                //out.println("Cost: "+node.cost+", Value: "+node.value);
                if(node.cost<cost){
                    cost = node.cost;
                    minCostNode = node;
                }
                if(node.value.equals(stop)){
                    break;
                }
            }
            String s = minCostNode != null ? minCostNode.value : null;
            //out.println("Low cost node: "+s+" Cost: "+minCostNode.cost);
            return s;
        }else {
            throw new RuntimeException("No Path available");
        }
    }

    /**
     * Gets the total cost from start to stop via the current word
     * Time complexity: O(n)
     * @param start      The start word
     * @param stop       The stop word
     * @param current    The current word
     * @return  The cost in terms of an integer
     */
    private int getCost(String start,String stop, String current){
        int g,h;
        g = heuristic.getDistanceToGoal(start,current);
        h = heuristic.getDistanceToGoal(current,stop);
        //out.println("Start: " + start + " Stop: " + stop + " Current: " + current + " g: " + g + " h: " + h);
        return g+h;
    }

    /**
     * Loads a set of dictionary words, used by OpenOffice
     * @throws IOException
     */
    private void loadDictionary() throws IOException {
        File dic = new File("src/main/resources/en_US.dic.txt");
        Scanner scanner = new Scanner(dic);
        while (scanner.hasNext())
            dictionary.add(scanner.nextLine().toLowerCase());
    }

    public static void main(String[] args){
        String start,stop;
        start = "cat";
        stop = "cot";
        out.println("Start word: "+start+", Stop word: "+stop);
        try {
            PathFinder aStar = new PathFinder(new HammingHeuristic());
            out.println(aStar.getShortestPath(start, stop));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
