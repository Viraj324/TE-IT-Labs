import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Graph {
class Edge {
int src, dest, weight;
Edge() { src = dest = weight = 0; }
}

int V, E;
Edge edge[];

Graph(int v, int e) {
V = v;
E = e;
edge = new Edge[e];
for (int i = 0; i < e; ++i)
edge[i] = new Edge();
}

void addEdge(int src, int dest, int weight, int count) {
edge[count].src = src;
edge[count].dest = dest;
edge[count].weight = weight;
}

void BellmanFord(Graph graph, int src) {
int V = graph.V, E = graph.E;
int dist[] = new int[V];

int predecessor[] = new int[V];
Arrays.fill(predecessor, -1);

for (int i = 0; i < V; ++i)
dist[i] = Integer.MAX_VALUE;
dist[src] = 0;

for (int i = 1; i < V; ++i) {
for (int j = 0; j < E; ++j) {
int u = graph.edge[j].src;
int v = graph.edge[j].dest;
int weight = graph.edge[j].weight;
if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
dist[v] = dist[u] + weight;
predecessor[v] = u;
}
}
}

for (int j = 0; j < E; ++j) {
int u = graph.edge[j].src;
int v = graph.edge[j].dest;
int weight = graph.edge[j].weight;
if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
System.out.println("Graph contains negative weight cycle");
return;
}
}

System.out.println("Vertex\tDistance from Source\tPath");
for (int i = 0; i < V; ++i) {
System.out.print(i + "\t\t" + dist[i] + "\t\t\t");

printPath(predecessor, i);
System.out.println();
}
}

void printPath(int[] predecessor, int v) {
List<Integer> path = new ArrayList<>();
while (v != -1) {
path.add(v);
v = predecessor[v];
}
for (int i = path.size() - 1; i >= 0; i--) {
System.out.print(path.get(i));
if (i != 0) {
System.out.print(" -> ");
}
}
}

public static void main(String[] args) {
int V = 5;
int E = 8;
Graph graph = new Graph(V, E);

graph.addEdge(0, 1, -1, 0);
graph.addEdge(0, 2, 4, 1);
graph.addEdge(1, 2, 3, 2);
graph.addEdge(1, 3, 2, 3);
graph.addEdge(1, 4, 2, 4);
graph.addEdge(3, 2, 5, 5);
graph.addEdge(3, 1, 1, 6);
graph.addEdge(4, 3, -3, 7);

graph.BellmanFord(graph, 0);
}
}
