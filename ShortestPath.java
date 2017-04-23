//package shorteshpath;
/* ShortestPath.java
   CSC 226 - Spring 2017
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.*;


//Do not change the name of the ShortestPath class
public class ShortestPath{

    //TODO: Your code here   
        public static int numVerts;
		private static int distance[];
		private static int pred[];

		
		public ShortestPath(int numVerts){
			this.numVerts=numVerts;
			distance=new int[numVerts+1];//shortest known distance from s
			pred=new int[numVerts];

		}
		
	/* ShortestPath(G)
		Given an adjacency matrix for graph G, calculates and stores the shortest paths to all the
                vertces from the source vertex.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static void ShortestPath(int[][] G, int source){
		numVerts = G.length;
		 //TODO: Your code here
		//sp will be true f vertex is in the shortest parth
		boolean sp[]=new boolean[numVerts];
		//distances to all the vertices is infinity
		for(int i=0;i<=numVerts;i++){
			distance[i]=Integer.MAX_VALUE;
		}
		//distance of source vertex to itself is 0
		distance[source]=0;
		//shortest path
		for(int j=0;j<numVerts;j++){
			//min distance vertex from the set of vertices
			int m=minDistance(sp);
			//vertex visited
			sp[m]=true;
			//distance valuesof adjacent vertices
			for(int k=0;k<numVerts;k++){
				//if there is an edge from u to v, and total weight of path 
				//from source to k through m is smaller than the current 
				//value of distance[k] then update distance[k] only 
				//if it is not in sp
				if(!sp[k]&&G[m][k]!=0 && distance[m]!=Integer.MAX_VALUE &&
				distance[m]+G[m][k]<distance[k]){
					distance[k]=distance[m]+G[m][k];
				}
			}
		}
	}
		
	//get vertex with minimum distance from unvisited
	private static int minDistance(boolean sp[]){
		//minimum value
		int min=Integer.MAX_VALUE;
		int minIndex=-1;
		for(int i=0;i<numVerts;i++){
			if(sp[i]==false && distance[i]<=min){
				min=distance[i];
				minIndex=i;
			}
		}
		return minIndex;
	}
	
    static void PrintPaths(int source){
        //TODO: Your code here 
		for(int i=0;i<numVerts;i++){
		   System.out.print("The path from "+source+" to "+i+" is: "+source);
			System.out.print(" --> "+i);
			System.out.print(" and the total distance is : "+distance[i]);
			System.out.println();
		}
		
			
    }
        
		
	/* main()
	   Contains code to test the ShortestPath function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			ShortestPath shortestpath=new ShortestPath(n);
			shortestpath.ShortestPath(G, 0);
            PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}





