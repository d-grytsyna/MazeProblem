package java4;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java4.Node;
public class Main {
    static long time = System.currentTimeMillis();
    static int stateRBFS = 0;
    static int iterationsRBFS = 0;
    static int memoryOpenNodes = 0;
    public static ArrayList<Node> BFS(String[][] maze,int mazeRows, int mazeCols, int startX, int startY){
            int states = 1;
            int iters = 0;
            int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            ArrayList<ArrayList<Node>> pathes = new ArrayList<ArrayList<Node>>();
            LinkedList<Node> queue = new LinkedList<Node>();
            ArrayList<Node> path = new ArrayList<>();
            boolean[][] visited = new boolean[mazeRows][mazeCols];
            Node start = new Node(maze[0][0], startX, startY);
            Node current_node = new Node();
            queue.add(start);
            pathes.add(path);
        if(((double)System.currentTimeMillis()-time)/1000/60<30) {
            while (queue.size() != 0) {
                current_node = queue.poll();
                path = pathes.get(0);
                pathes.remove(0);
                for (int i = 0; i < DIRECTIONS.length; i++) {
                    iters++;
                    int row = current_node.getX() + DIRECTIONS[i][0];
                    int col = current_node.getY() + DIRECTIONS[i][1];
                    if (row >= 0 && row < mazeRows && col >= 0 && col < mazeCols) {
                        if (!visited[row][col]) {
                            visited[row][col] = true;
                            Node next_node = new Node(maze[row][col], row, col);
                            if (next_node.getType().equals("E")) {
                                path.add(next_node);
                                System.out.println("States BFS " + states);
                                System.out.println("Iterations BFS " + iters);
                                return path;
                            }
                            if (next_node.getType().equals(" ")) {
                                path.add(next_node);
                                states++;
                                queue.add(next_node);
                                ArrayList<Node> path2 = new ArrayList<>();
                                path2.addAll(path);
                                pathes.add(path2);
                                path.remove(path.size() - 1);
                            }
                        }

                    }
                }
                path.clear();
            }
        }
            return path;

    }
    public static void RBFS(String[][] maze, int mazeRows, int mazeCols, int startX, int startY, int endX, int endY){
        stateRBFS = 1;
        iterationsRBFS = 0;
        memoryOpenNodes = 1;
        Node start = new Node("S", startX, startY);
        MazeState mazeState = new MazeState(maze,new ArrayList<OpenNode>(),start, Double.MAX_VALUE);
        MazeResult result = RecursiveSearch(mazeState,Double.MAX_VALUE, mazeRows, mazeCols, endX,endY);
        result.result[startX][startY]="S";
        result.result[endX][endY]="E";
        for(int i=0; i<mazeRows;i++){
            String line ="";
            for(int j=0; j<mazeCols; j++){
              line += result.result[i][j];
            }
            System.out.println(line);
        }
        System.out.println("States RBFS " + stateRBFS);
        System.out.println("Iterations RBFS " + iterationsRBFS);
        System.out.println("Memory nodes " + memoryOpenNodes);
    }

    public static MazeResult RecursiveSearch(MazeState mazeState, double flimit,int mazeRows, int mazeCols, int endX, int endY){
        if(mazeState.getSolution()!=null) {
            return mazeState.getSolution();
        }
        stateRBFS++;
        MazeResult solution = new MazeResult();
        ArrayList<OpenNode> mazeOpenNodes = mazeState.getOpenNodes();
        Node currentNode = mazeState.getCurrentNode();
        String[][] mazePath = mazeState.getMaze();
        mazePath[currentNode.getX()][currentNode.getY()]=".";
        mazeState.setMaze(mazePath);
        if(currentNode.getType().equals("E")){
            solution.result = mazeState.getMaze();
            return solution;
        }
        ArrayList<Node> successors = new ArrayList<>();
        int directions[][] = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
        for(int i=0; i<4; i++){
            iterationsRBFS++;
             int row = currentNode.getX() + directions[i][0];
             int col = currentNode.getY() + directions[i][1];
             if(row>=0&&row<mazeRows&&col>=0&&col<mazeCols){
                 Node child_node = new Node(mazeState.getMaze()[row][col],row,col);
                 if(!child_node.getType().equals("#")&&!child_node.getType().equals("."))successors.add(child_node);
             }
         }
        if(successors.isEmpty()){
            solution.result = null;
            return solution;
        }
        double min_dist = Math.sqrt(Math.pow(endX-successors.get(0).getX(),2)+Math.pow(endY-successors.get(0).getY(),2));
        int min_index = 0;
        for(int i=0; i<successors.size(); i++){
            double childDistance = Math.sqrt(Math.pow(endX-successors.get(i).getX(),2)+Math.pow(endY-successors.get(i).getY(),2));
            if(childDistance<min_dist){
                min_dist = childDistance;
                min_index = i;
            }
        }
        for(int j=0; j<successors.size(); j++){
            if(j!=min_index){
            OpenNode childNode = new OpenNode(successors.get(j),Math.sqrt(Math.pow(endX-successors.get(j).getX(),2)+Math.pow(endY-successors.get(j).getY(),2)));
            mazeOpenNodes.add(childNode);
            memoryOpenNodes++;
            }
        }
        mazeState.setOpenNodes(mazeOpenNodes);

        for(;;){
            mazeState.setCurrentNode(successors.get(min_index));
            if(min_dist>flimit){
                ArrayList<OpenNode> nodes = mazeState.getOpenNodes();
                OpenNode current = new OpenNode(mazeState.getCurrentNode(), min_dist);
                nodes.add(current);
                mazeState.setOpenNodes(nodes);
                solution.result = null;
                return solution;
            }
            if(!mazeOpenNodes.isEmpty()){
            double alternative = mazeOpenNodes.get(0).getDistance();
            int alternative_index = 0;
            for(int j=0; j<mazeOpenNodes.size(); j++){
                if(mazeOpenNodes.get(j).getDistance()<alternative){
                    alternative = mazeOpenNodes.get(j).getDistance();
                    alternative_index = j;
                }
            }
            mazeState.setAlternativeNode(mazeOpenNodes.get(alternative_index).getNode());
            flimit = mazeOpenNodes.get(alternative_index).getDistance();
            }
            if(((double)System.currentTimeMillis()-time)/1000/60<30) solution =RecursiveSearch(mazeState,flimit, mazeRows, mazeCols, endX, endY);
            if(solution.result!=null){
                mazeState.setSolution(solution);
                return solution;
            }else{
                mazeState.setCurrentNode(mazeState.getAlternativeNode());
                ArrayList<OpenNode> newAlternativeOpenNodes = mazeState.getOpenNodes();

                for(OpenNode el:newAlternativeOpenNodes){
                    if(el.getNode().getX()==mazeState.getCurrentNode().getX()&&el.getNode().getY()==mazeState.getCurrentNode().getY()){
                        newAlternativeOpenNodes.remove(el);
                        break;
                    }
                }
                if(!newAlternativeOpenNodes.isEmpty()){
                double alternative2 = newAlternativeOpenNodes.get(0).getDistance();
                int alternative_index2 = 0;
                for(int j=0; j<newAlternativeOpenNodes.size(); j++){
                    if(newAlternativeOpenNodes.get(j).getDistance()<alternative2){
                        alternative2 = newAlternativeOpenNodes.get(j).getDistance();
                        alternative_index2 = j;
                    }
                }
                mazeState.setAlternativeNode(newAlternativeOpenNodes.get(alternative_index2).getNode());
                flimit = newAlternativeOpenNodes.get(alternative_index2).getDistance();
                }
                if(((double)System.currentTimeMillis()-time)/1000/60<30)  solution =RecursiveSearch(mazeState,flimit, mazeRows, mazeCols, endX, endY);
            }
        }

    }
    public static void main(String[] args) throws IOException {
        String[][] maze7 = {
                {" "," "," ","#","#","#","#","#","#","#"},
                {" ","#"," ","#"," "," "," "," "," ","#"},
                {" ","#"," ","#","#"," ","#"," ","#"," "},
                {" ","#"," ","#"," "," ","#"," ","#"," "},
                {" ","#"," ","#","#"," ","#"," "," "," "},
                {" ","#"," ","#","#"," ","#"," ","#","#"},
                {" ","#"," "," "," "," ","#"," ","#"," "},
                {"S","#","#","#","#"," ","#"," "," ","E"}
        };

        File file = new File("src/java4/maze.txt");
        BufferedReader mazeReader =  new BufferedReader(new FileReader("src/java4/maze.txt"));
        String[][] arrMaze = new String[8][10];
        if(file.length()<1073741824){
        String line;
        int i =0;
        while((line=mazeReader.readLine())!=null){
            for(int j=0;j<10;j++){
                arrMaze[i][j] = Character.toString(line.charAt(j));
            }
            i++;
        }
        }
     //  BFSprint(arrMaze, 8, 10, 0, 1);
        RBFS(arrMaze, 8, 10, 0,1,0,9);
    }
    public static void BFSprint(String[][] maze, int mazeRows, int mazeCols, int startX, int startY){
        for(int i=0; i<maze.length;i++){
            String line = "";
            for(int j=0;j<10;j++){
                line += maze[i][j];
            }
            System.out.println(line);
        }
        System.out.println("Maze after BFS");
        ArrayList<Node> path = BFS(maze, mazeRows, mazeCols, startX, startY);
        for(int i=0; i< path.size(); i++){
            if(!maze[path.get(i).getX()][path.get(i).getY()].equals("E")) maze[path.get(i).getX()][path.get(i).getY()]=".";
        }

        for(int i=0; i<maze.length;i++){
            String line = "";
            for(int j=0;j<10;j++){
                line += maze[i][j];
            }
            System.out.println(line);
        }
    }
}
