package avengers;

import java.util.Arrays;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	String LocateTitanInputFile = args[0];
        String LocateTitanOutputFile = args[1];

        StdIn.setFile(LocateTitanInputFile);
        StdOut.setFile(LocateTitanOutputFile);



        int num = StdIn.readInt();
        System.out.println(num);
        StdIn.readLine();
        double[] temp = new double[num];
        int[][] adj = new int[num][num];

        for(int i=0;i<num;i++){

            int key = StdIn.readInt();
            double check = StdIn.readDouble();

            temp[key]=check;
            StdIn.readLine();
        }

        for(int i=0;i<num;i++){
            System.out.println(temp[i]);
        }

        for(int i=0;i<num;i++){
            for(int j=0; j<num; j++){

                int val = StdIn.readInt();

                adj[i][j] = (int) (val/(temp[i]*temp[j])); 

            }
            StdIn.readLine();
        }
        for(int i=0; i<num; i++){
            for(int j=0; j<num; j++){
                System.out.print(adj[i][j] + "\t");
            }
        }

        System.out.println(num);
        int[] minCost2 = algo(adj,0);
        for(int i=0; i<num-1; i++){

            System.out.print(minCost2[i] + " \t");

        }

        System.out.println(minCost2[num-1]);

        StdIn.setFile(LocateTitanOutputFile);
        StdOut.print(minCost2[num-1]);
    }   


    private static int[] algo(int[][] arr, int first){

        int[] d = new int[arr.length];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[first] = 0;
        boolean[] check=new boolean[arr.length];

        while(true){
            int key = -1;
            int sd = Integer.MAX_VALUE;
            for(int i=0; i<arr.length; i++){
                if(d[i]<sd&&!check[i]){
                    sd = d[i];
                    key = i;
                }
            }
            if(key== -1)  {  
                return d;
            }
            for(int i=0; i<arr[key].length; i++){
                if(arr[key][i] != 0 && d[i] > d[key] + arr[key][i]){
                    d[i] = d[key]+arr[key][i];

                }
            }
            check[key]=true;
        }
        /*
    
    boolean[] djikstra = new boolean[numGenerators];
    for (int i = 0; i < numGenerators; i++){
      dist[i] = Integer.MAX_VALUE;
      djikstra[i] = false;
    }
    dist[0] = 0;
    for (int i = 0; i < numGenerators - 1; i++){
      int u = min(dist, djikstra);
      djikstra[u] = true;
      for (int j = 0; j < numGenerators; j++)
        if (!djikstra[j] && adj[u][j] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + adj[u][j] < dist[j])
          dist[j] = dist[u] + adj[u][j];
    }
   StdOut.println( dist[dist.length - 1]);
   
  }
        
    */
      }
        

    
}
