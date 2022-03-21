import java.util.ArrayList;

class Edge
{
    public int weight;
    public int dest;
    public int src;
    public Edge next;
    public Edge(int weight, int src, int dest)
    {
        this.weight = weight;
        this.dest = dest;
        this.src = src;
        this.next = null;
    }
}
class State
{
    public int parent;
    public int rank;
    public State(int parent, int rank)
    {
        this.parent = parent;
        this.rank = rank;
    }
}
public class Graph
{
    public int vertices;
    public ArrayList < ArrayList < Edge >> graphEdge;
    public Graph(int vertices)
    {
        this.vertices = vertices;
        this.graphEdge = new ArrayList < ArrayList < Edge >> (vertices);
        for (int i = 0; i < this.vertices; ++i)
        {
            this.graphEdge.add(new ArrayList < Edge > ());
        }
    }
    public void addEdge(int src, int dest, int w)
    {
        if (dest < 0 || dest >= this.vertices || src < 0 || src >= this.vertices)
        {
            return;
        }
        graphEdge.get(src).add(new Edge(w, src, dest));
        if (dest == src)
        {
            return;
        }
        graphEdge.get(dest).add(new Edge(w, dest, src));
    }

    public int find(State[] subsets, int i)
    {
        if (subsets[i].parent != i)
        {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }
    void findUnion(State[] subsets, int x, int y)
    {
        int a = find(subsets, x);
        int b = find(subsets, y);
        if (subsets[a].rank < subsets[b].rank)
        {
            subsets[a].parent = b;
        }
        else if (subsets[a].rank > subsets[b].rank)
        {
            subsets[b].parent = a;
        }
        else
        {
            subsets[b].parent = a;
            subsets[a].rank++;
        }
    }
    public void boruvkaMST()
    {
        int result = 0;
        int selector = this.vertices;
        State[] subsets = new State[this.vertices];
        Edge[] cheapest = new Edge[this.vertices];
        for (int v = 0; v < this.vertices; ++v)
        {
            subsets[v] = new State(v, 0);
        }
        while (selector > 1)
        {
            for (int v = 0; v < this.vertices; ++v)
            {
                cheapest[v] = null;
            }
            for (int k = 0; k < this.vertices; k++)
            {
                for (int i = 0; i < this.graphEdge.get(k).size(); ++i)
                {
                    int set1 = find(subsets,
                            this.graphEdge.get(k).get(i).src);
                    int set2 = find(subsets,
                            this.graphEdge.get(k).get(i).dest);
                    if (set1 != set2)
                    {
                        if (cheapest[k] == null)
                        {
                            cheapest[k] = this.graphEdge.get(k).get(i);
                        }
                        else if (cheapest[k].weight >
                                this.graphEdge.get(k).get(i).weight)
                        {
                            cheapest[k] = this.graphEdge.get(k).get(i);
                        }
                    }
                }
            }
            for (int i = 0; i < this.vertices; i++)
            {
                if (cheapest[i] != null)
                {
                    int set1 = find(subsets, cheapest[i].src);
                    int set2 = find(subsets, cheapest[i].dest);
                    if (set1 != set2)
                    {
                        selector--;
                        findUnion(subsets, set1, set2);
                        
                        System.out.print("\n Include Edge (" +
                                cheapest[i].src + " - " +
                                cheapest[i].dest + ") weight " +
                                cheapest[i].weight);
                    }
                }
            }
        }
    }

    public void boruvkaMaxST()
    {
        int result = 0;
        int selector = this.vertices;
        State[] subsets = new State[this.vertices];
        Edge[] cheapest = new Edge[this.vertices];
        for (int v = 0; v < this.vertices; ++v)
        {
            subsets[v] = new State(v, 0);
        }
        while (selector > 1) {
            for (int v = 0; v < this.vertices; ++v) {
                cheapest[v] = null;
            }
            for (int k = 0; k < this.vertices; k++) {
                for (int i = 0; i < this.graphEdge.get(k).size(); ++i) {
                    int set1 = find(subsets,
                            this.graphEdge.get(k).get(i).src);
                    int set2 = find(subsets,
                            this.graphEdge.get(k).get(i).dest);
                    if (set1 != set2) {
                        if (cheapest[k] == null) {
                            cheapest[k] = this.graphEdge.get(k).get(i);
                        } else if (cheapest[k].weight <
                                this.graphEdge.get(k).get(i).weight) {
                            cheapest[k] = this.graphEdge.get(k).get(i);
                        }
                    }
                }
            }
            for (int i = 0; i < this.vertices; i++) {
                if (cheapest[i] != null) {
                    int set1 = find(subsets, cheapest[i].src);
                    int set2 = find(subsets, cheapest[i].dest);
                    if (set1 != set2) {
                        
                        selector--;
                        findUnion(subsets, set1, set2);
                        
                        System.out.print("\n Include Edge (" +
                                cheapest[i].src + " - " +
                                cheapest[i].dest + ") weight " +
                                cheapest[i].weight);
                    }
                }
            }
        }
    }



    public static void main(String[] args)
    {
        Graph g = new Graph(8);
        g.addEdge(0, 2, 38);
        g.addEdge(0, 3, 95);
        g.addEdge(0, 5, 1);
        g.addEdge(0, 6, 56);
        g.addEdge(1, 4, 79);
        g.addEdge(1, 6, 36);
        g.addEdge(1, 7, 19);
        g.addEdge(2, 3, 51);
        g.addEdge(2, 6, 44);
        g.addEdge(3, 5, 44);
        g.addEdge(4, 5, 93);
        g.addEdge(4, 6, 41);
        g.addEdge(4, 7, 48);
        g.addEdge(5, 6, 1);

        
        System.out.println("\n Boruvka MinST:  ");
        g.boruvkaMST();

        System.out.println("\n _________________________________");

        System.out.println("\n Boruvka MaxST:  ");
        g.boruvkaMaxST();
    }
}
