package projectEncryption;

import projectEncryption.Algorithem;

import java.io.*;
import java.util.*;

public class HammiltonCircuitEncryption extends Algorithem
{
private List<Integer> keys;
    public  HammiltonCircuitEncryption()
    {
        super();
        while(this.keys == null) {
            Map<Integer, List<Integer>> graph = HamiltonianCircuit.generateRandomGraph(8, 0.3);
            this.keys = HamiltonianCircuit.findHamiltonianCircuit(graph);
        }
    }

    private class HamiltonianCircuit {

    // Generate a random graph with the specified number of vertices and probability of having an edge between any two vertices
    public static Map<Integer, List<Integer>> generateRandomGraph(int numVertices, double probabilityOfEdge) {
        // Create a map to represent the graph, where the keys are the vertices and the values are the lists of vertices that are connected to the key vertex
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Initialize the graph with empty lists of connected vertices for each vertex
        for (int i = 0; i < numVertices; i++) {
            graph.put(i, new ArrayList<>());
        }

        // Use a random number generator to add edges between the vertices with the given probability
        Random rand = new Random();
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (rand.nextDouble() < probabilityOfEdge) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        return graph;
    }

    // Find a Hamiltonian circuit in the given graph, if one exists
    public static List<Integer> findHamiltonianCircuit(Map<Integer, List<Integer>> graph) {
        // Make a copy of the graph so that we can remove edges as we visit them
        Map<Integer, List<Integer>> graphCopy = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            graphCopy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        // Keep track of the current path and the set of visited vertices
        List<Integer> path = new ArrayList<>();
        List<Integer> visited = new ArrayList<>();

        // Starting at an arbitrary vertex, depth-first search through the graph to find a Hamiltonian circuit
        dfs(graphCopy, 0, path, visited);

        // If we were able to visit all vertices, return the path
        if (visited.size() == graph.size()) {
            return path;
        }
        // Otherwise, return null to indicate that a Hamiltonian circuit was not found
        else {
            return null;
        }
    }

    // Recursive depth-first search function to find a Hamiltonian circuit
    private static void dfs(Map<Integer, List<Integer>> graph, int vertex, List<Integer> path, List<Integer> visited) {
        try {


            visited.add(vertex);
            path.add(vertex);
            for (int neighbor : graph.get(vertex)) {
                graph.get(neighbor).remove(Integer.valueOf(vertex));
                if (!visited.contains(neighbor)) {
                    dfs(graph, neighbor, path, visited);
                }
            }
        }
        catch (Exception e)
        {
            dfs(graph,vertex,path,visited);
        }
    }
}
    @Override
    public String encrypt(File f)
    {
        try {
            int temp,index = 0,size = this.keys.size();
            FileOutputStream out;
            FileInputStream in;
            File outFile =  new File(f.getParentFile(),"4"+f.getName());
            in  = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            while((temp = in.read())!= -1)
            {
                out.write(super.MoveBites(temp,this.keys.get(index++)));
                if(size == index)
                    index = 0;
            }
            for(int i = 0;i<size;i++)
                out.write(this.keys.get(i));
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return  "";
    }
    public void showKeys()
    {
        System.out.println(this.keys);
    }

    @Override
    public String decrypt(File f) {
        try {


            int temp, index = 0;
            int[] vec = new int[8];
            FileOutputStream out;
            FileInputStream in;
            File outFile = new File(f.getParentFile(),  f.getName().substring(1) );
            in = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            long sizeOfFile = f.length();
            in.skip(sizeOfFile-8);
            while((temp = in.read()) != -1)
            {
                vec[index++] = temp;
            }
            in = new FileInputStream(f);
            index = 0;
            while ((temp = in.read()) != -1)
            {
                out.write(super.MoveBites(temp,vec[index++]));
                if(index > 7)
                    index = 0;
            }
            out.getChannel().truncate(out.getChannel().size()-8);
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return  "";
    }

}
