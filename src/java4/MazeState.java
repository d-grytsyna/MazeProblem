package java4;


import java.util.ArrayList;

public class MazeState {
    private String[][] maze;
    private ArrayList<OpenNode> openNodes;
    private Node currentNode;
    private Node alternativeNode;
    private double fLimit;
    private MazeResult solution;
    public MazeState(String[][] maze, ArrayList<OpenNode> openNodes, Node currentNode, double fLimit){
        this.maze = maze;
        this.openNodes = openNodes;
        this.currentNode = currentNode;
        this.fLimit = fLimit;
    }
    public String[][] getMaze(){
        return maze;
    }
    public ArrayList<OpenNode> getOpenNodes(){return openNodes;}
    public Node getCurrentNode(){return currentNode;}
    public double getfLimit(){return fLimit;}
    public void setMaze(String[][] maze){this.maze = maze;}
    public void setOpenNodes(ArrayList<OpenNode> openNodes){this.openNodes = openNodes;}
    public void setCurrentNode(Node currentNode){this.currentNode = currentNode;}
    public void setfLimit(double fLimit){this.fLimit = fLimit;}
    public void setAlternativeNode(Node alternativeNode){this.alternativeNode = alternativeNode;}
    public Node getAlternativeNode(){return alternativeNode;}
    public void setSolution(MazeResult solution){this.solution = solution;}
    public MazeResult getSolution(){return solution;}
}
