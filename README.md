<h2>Solving maze using algorithms:<br>
BFS - Breadth-first search<br>
RBFS - Recursive Best-First Search</h2>

<h6>The RBFS algorithm uses the following heuristic function
H3 - Euclidean distance from a given point to the exit.</h6>

The breadth-first search expands all nodes gradually, keeping all
open nodes. Each processed node must remain in memory because it
belongs to the path and is the ancestor of the next node. So, when searching in
width search, the most difficult problem in comparison with the significant execution time
execution time is to ensure the need for memory.

Unlike BFS, RBFS does not blindly select the next node, it sorts the
the queue according to a heuristic function. This algorithm simply selects
unvisited open node with the best heuristic value for
for the next visit. Whenever the cost of the current node exceeds the
the cost of another node, the algorithm continues the search along a new path, where
the current node becomes an alternative node, and the previous current node is added to the
to the available open nodes.
