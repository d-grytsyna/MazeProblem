package java4;

public class Node {
    private String type;
    private int x;
    private int y;
    public Node(){}
    public Node(String type1, int x1, int y1){
        type = type1;
        x = x1;
        y = y1;
    }
    public String getType() {return type;}
    public void setType(String type1){type=type1;}
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int x1){
        x = x1;
    }
    public void setY(int y1){
        y = y1;
    }

}
