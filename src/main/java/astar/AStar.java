package astar;

import astar.heuristics.HammingHeuristic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.out;

public class AStar{
	private HashSet<Node> input;
	private HashSet<String> closed;
	private ArrayList<String> cameFrom;
	private HashSet<String> dictionary;
	private HammingHeuristic heuristic;
	
	public AStar(HammingHeuristic heuristic) throws IOException {
		this.heuristic = heuristic;
		input = new HashSet<Node>();
		closed = new HashSet<String>();
		cameFrom = new ArrayList<String>();
		dictionary = new HashSet<String>();
		loadDictionary();
	}
	
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
        return cameFrom.toString();
	}
	
	public String processDictionary(String start,String stop,String current){
        input.clear();
        for (String value : dictionary) {
            if (!closed.contains(value)) {
                Node node = new Node();
                node.cost = getCost(start, stop, current);
                node.value = value;
                input.add(node);
                closed.add(value);
            }
        }
        return findLowestCostNode();
	}

    private String findLowestCostNode() {
        int cost = Integer.MAX_VALUE;
        Node minCostNode = null;
        for (Node node : input) {
            if(node.cost<cost){
                cost = node.cost;
                minCostNode = node;
            }
        }
        return minCostNode!=null?minCostNode.value:null;
    }

    public int getCost(String start,String stop, String current){
        int g,h;
        g = heuristic.getDistanceToGoal(start,current);
        h = heuristic.getDistanceToGoal(current,stop);
        return g+h;
    }
	
	public void loadDictionary() throws IOException {
		File dic = new File("src/main/resources/en_US.dic.txt");
        Scanner scanner = new Scanner(dic);
        while (scanner.hasNext())
            dictionary.add(scanner.nextLine().toLowerCase());
	}

    public static void main(String[] args){
        String start,stop;
        start = "duck";
        stop = "ruby";

        try {
            AStar aStar = new AStar(new HammingHeuristic());
            out.println(aStar.getShortestPath(start, stop));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
