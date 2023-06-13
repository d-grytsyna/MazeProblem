package java4;

public class OpenNode {
    private Node node;
    private double distance;
    public OpenNode(Node node, double distance){
        this.node = node;
        this.distance = distance;
    }
    public Node getNode(){
        return node;
    }
    public double getDistance(){
        return distance;
    }
}
