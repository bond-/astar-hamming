package pathfinder.heuristics;

/**
 * The HammingHeuristic class
 */
public class HammingHeuristic{

    /**
     * Calculates and returns the distance to goal/stop word
     * @param s1    The start word
     * @param s2    The stop word
     * @return  The hamming distance between two strings
     */
    public int getDistanceToGoal(String s1, String s2){
        if(s1.length()!=s2.length())
            return -1;
        int hammingDistance = 0;
        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)!=s2.charAt(i))
                hammingDistance++;
        }
        return hammingDistance;
    }
}
