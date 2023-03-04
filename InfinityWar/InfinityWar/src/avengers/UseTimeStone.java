package avengers;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        String UseTimeStoneInput = args[0];
        String UseTimeStoneOutput = args[1];

        StdIn.setFile(UseTimeStoneInput);
        StdOut.setFile(UseTimeStoneOutput);

        int t = StdIn.readInt();
        StdIn.readLine();

        int events = 0;
        events = StdIn.readInt();

        System.out.println( events );

        StdIn.readLine();

        int[] temp = new int[events];
        int[][] adj = new int[events][events];

        ArrayList<ArrayList<Integer>> adjL;
        adjL = new ArrayList<ArrayList<Integer>>();

        for(int i=0;i<events;i++){

            int key=StdIn.readInt();
            int check=StdIn.readInt();

            temp[key]=check;

            StdIn.readLine();

        }
        for(int i=0;i<events;i++){
            System.out.println(temp[i]);

        }
        for(int i=0;i<events;i++){
            adjL.add(new ArrayList<Integer>());
            for(int j=0; j<events;j++){

                int num = StdIn.readInt();
                adj[i][j]=num;
                if(num==1){
                    adjL.get(i).add(j);

                }
            }

            StdIn.readLine();
        }
        System.out.println();
        for(int i=0;i<events; i++){
            for(int j=0; j<events; j++){
                //System.out.print(adj[j][i] + "\t")
                System.out.print(adj[i][j] + "\t");

            }

            System.out.println();
        }
        System.out.println(" n = " + events);
        for(int i=0; i<events; i++){
            for(int j=0;j<adjL.get(i).size(); j++){

                System.out.print(i + " ->" + adjL.get(i).get(j) + "\t" );
            }
            System.out.println();
        }
        //Lists for dfs
        ArrayList<String> screw = new ArrayList<String>();
        ArrayList<Integer> thiss = new ArrayList<Integer>();
        ArrayList<Integer> method = new ArrayList<Integer>();

        thiss.add(0);
        screw.add((thiss.get(0)).toString());
        method.add(temp[0]);

        
        int[] ans= new int[2];

        ans[0]=0;
        ans[1]=0;

        start0(events,temp,adjL ,ans ,t);

        StdOut.println(ans[0]);
        StdOut.println(ans[1]);
        StdOut.close();

    }
    public static void showRoute(int yuh,int dist,boolean[] vis,List<Integer> pList,ArrayList<ArrayList<Integer>> adjL,int[] temparr,int[] res,int token){

        if(yuh==dist){
            System.out.println(pList);
            res[0]++;
            int util=showEU(pList, temparr);

            if(util>=token){
                res[1]++;
            }
            return;

        }
        vis[yuh] = true;
        for(int i: adjL.get(yuh)){
            if(adjL.get(yuh).size()==0){
                //break;
                continue;
            }
            if(!vis[i]){
                pList.add(i);
                showRoute(i , dist , vis , pList , adjL , temparr , res , token);
                pList.remove(Integer.valueOf(i));

            }
        }

        vis[yuh] = false;

    }
    private static int showEU(List<Integer> route , int[] temp){
        int s = 0;
        for(int i: route){
            s+=temp[i];
        }
        return s;

    }
    private static void start0(int n , int[] temparr , ArrayList<ArrayList<Integer>> adjL , int[] res , int token){

        for(int i = 0; i < n; i++){

            boolean[] check = new boolean[n];
            ArrayList<Integer> plist = new ArrayList<Integer>();

            plist.add(0);
            showRoute(0 , i , check , plist , adjL , temparr, res , token);

        }
    }
}
