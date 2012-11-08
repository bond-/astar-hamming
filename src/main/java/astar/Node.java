package astar;

public class Node implements Comparable<Node>{
    int cost;
    String value;

    @Override
    public int compareTo(Node o) {
        return this.cost<o.cost?-1:this.cost==o.cost?0:1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!value.equals(node.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
