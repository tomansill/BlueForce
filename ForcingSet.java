/**
 * @author Thomas B. Ansill
 * @date April 08, 2014	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;

public class ForcingSet{
	/** Zero Forcing Set Algorithm
	 * @param graph to be solved
	 * @return Solved graph or null as unsolved graph
	 */
	public static Graph ZeroForcingSet(Graph original, int forcingnumber){
		//Retains the original graph and create a working copy of original
		Graph graph = new Graph(original);
		//Create a steps list
		HashSet<String> steps = null;
		do{
			steps = new HashSet<String>();
			for(String vertex : graph.getFilledVertices()){
				ArrayList<String> neighbors = graph.getUnfilledNeighbors(vertex);
				if(neighbors.size() <= forcingnumber && neighbors.size() != 0){
					steps.add(neighbors.get(0));
				}
			}//End of for loop
			for(String vertex : steps){
				graph.fillVertex(vertex);
			}//End of for loop
		}while((graph.getNumOfUnfilledVertices() != 0) && !steps.isEmpty());
		if(graph.getNumOfUnfilledVertices() != 0) return null; //Graph is not solved
		return graph;
	}//End of ZeroForcingSet method
	
	public static Graph SkewZeroForcingSet(Graph original, int forcingnumber){
		//Retains the original graph and create a working copy of original
		Graph graph = new Graph(original);
		//Create a steps list
		HashSet<String> steps = null;
		do{
			steps = new HashSet<String>();
			for(String vertex : graph.getVertices()){
				ArrayList<String> neighbors = graph.getUnfilledNeighbors(vertex);
				if(neighbors.size() <= forcingnumber && neighbors.size() != 0){
					steps.add(neighbors.get(0));
				}
			}//End of for loop
			for(String vertex : steps){
				graph.fillVertex(vertex);
			}//End of for loop
		}while((graph.getNumOfUnfilledVertices() != 0) && !steps.isEmpty());
		if(graph.getNumOfUnfilledVertices() != 0) return null; //Graph is not solved
		return graph;
	}//End of SkewZeroForcingSet method
	
	public static Graph PositiveSemiDefiniteZeroForcingSet(Graph original, int forcingnumber){
		if(original.getNumOfFilledVertices() == 0) return null;
		//Retains the original graph and create a working copy of original
		Graph graph = new Graph(original);
		//Create a steps list
		HashSet<String> steps = null;
		do{
			steps = new HashSet<String>();
			for(String vertex : graph.getFilledVertices()){
				ArrayList<String> neighbors = graph.getUnfilledNeighbors(vertex);
				if(neighbors.size() <= forcingnumber && neighbors.size() != 0){
					steps.add(neighbors.get(0));
				}
			}//End of for loop
			for(String vertex : steps){
				graph.fillVertex(vertex);
			}//End of for loop
		}while((graph.getNumOfUnfilledVertices() != 0) && !steps.isEmpty());
		
		//Graph is not solved
		if(graph.getNumOfUnfilledVertices() != 0){
			//Attempt to divide graphs
			ArrayList<HashSet<String>> subgraphs = new ArrayList<HashSet<String>>();
			HashSet<String> visited = new HashSet<String>();
			
			//Find the subgraphs - iterate through every filled vertices
			for(String filledVertex : graph.getFilledVertices()){
				//Iterate through unfilled neighbors
				for(String unfilledNeighbor : graph.getUnfilledNeighbors(filledVertex)){
					//Check if that neighbor is not in visited(not tested yet)
					if(!visited.contains(unfilledNeighbor)){
						//Put that neighbor to visited list so it won't be used again
						visited.add(unfilledNeighbor);
						//Run the Depth-First Search and look for network
						HashSet<String> network = new HashSet<String>();
						network.add(unfilledNeighbor);
						Stack<String> stack = new Stack<String>(); //Create stack for DFS-Like search
						stack.add(unfilledNeighbor); //Start with this neighbor
						//Search for it
						while(!stack.isEmpty()){
							String focus = stack.pop();
							//Get focus's neighbors
							for(String neighbor : graph.getNeighbors(focus)){
								//Check if that neighbor of focus is not yet in the network
								if(!network.contains(neighbor)){
									//Add it to the network
									network.add(neighbor);
									//Add it to the visited so it won't be used again
									visited.add(neighbor);
									//If graph is not filled, continue the search on that branch
									if(!graph.isFilled(neighbor)) stack.add(neighbor);
								}
							}//End of for loop
						}//End of while loop
						subgraphs.add(network);
					}
				}//End of for loop
			}//End of for loop
			
			//Necessary to prevent infinite recursion
			if(subgraphs.size() == 1) return null;
			else{
				//For each subgraph, recursively run PSZFS on them
				for(HashSet<String> subgraph : subgraphs){
					//Create the adjacency list for subgraph
					TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
					for(String vertex : subgraph){
						//Create Vertex
						ArrayList<String> neighbors = new ArrayList<String>();
						for(String oldNeighbor : graph.getNeighbors(vertex)){
							//Make sure subgraph don't contain other vertices that are not used
							if(subgraph.contains(oldNeighbor)){
								neighbors.add(oldNeighbor);
							}
						}//End of for loop
						list.put(vertex, new Vertex(vertex, graph.isFilled(vertex), neighbors));
					}//End of for loop
					Graph newSubGraph = new Graph(list);
					newSubGraph = PositiveSemiDefiniteZeroForcingSet(newSubGraph, forcingnumber);
					//If return value is null, graph is unsolvable
					if(newSubGraph == null) return null;
					//If return value is not null, merge filled vertices with main graph
					for(String vertex : newSubGraph.getFilledVertices()){
						graph.fillVertex(vertex);
					}//End of for loop
				}//End of for loop
			}
		}
		return graph;
	}//End of PositiveSemiDefiniteZeroForcingSet method
	
	public static boolean isSolvable(Graph original, int type, int forcingnumber){
		Graph graph = new Graph(original);
		switch(type){
			case 1:	graph = ZeroForcingSet(graph, forcingnumber);
					break;
			case 2: graph = SkewZeroForcingSet(graph, forcingnumber);
					break;
			case 3: graph = PositiveSemiDefiniteZeroForcingSet(graph, forcingnumber);
			default: break;
		}//End of switch statement
		if(graph == null){ return false; }
		return true;
	}//End of isSolvable method
	
	public static ArrayList<Graph> forwardSearcher(Graph original, boolean maximum, int type, int forcingnumber, boolean debug){
		if(debug) System.out.println("\nDebugging enabled!\n\r");
		Graph graph = new Graph(original);
		if(maximum){
			//Unfill the graph
			for(String vertex : graph.getFilledVertices()){
				graph.unfillVertex(vertex);
			}//End of for loop
		}else{
			//Fill the graph
			for(String vertex : graph.getUnfilledVertices()){
				graph.fillVertex(vertex);
			}//End of for loop
		}
		//Initialize
		HashSet<Graph> results = new HashSet<Graph>(); //used to store results
		int maximumN = -1;	//used for comparator to find the maximum filled vertices - set at -1 as "impossible-to-fail"
		HashSet<Graph> predecessors = new HashSet<Graph>(); //used to store graphs that are already tested
		predecessors.add(graph); //The original will be first to run so add it to the predecessors list
		Stack<Graph> stack = new Stack<Graph>(); //Stack variable
		stack.add(graph); //The original will be the first to run
		
		//Debugging stuff
		int iterations = 0;
		int subconfigurations = 0;
		
		//Run the searcher
		while(!stack.isEmpty()){ //Run until stack is empty
			graph = stack.pop(); //Pops the stack and load the graph
			
			if(maximum){
				//Prune the graph if it is solvable
				if(!isSolvable(graph, type, forcingnumber)){
					//Check if graph is the new maximum
					int newmax = graph.getNumOfFilledVertices();
					if(newmax > maximumN){
						//New maximum number of filled vertices is found
						results.clear(); //Clears the results 
						results.add(graph);
						maximumN = newmax;
					}else if(newmax == maximumN){
						//Same amount of maximum and new way to fail the graph
						results.add(graph);
					}
					
					//Make subconfigurations from unfilled vertices if there is more than one unfilled vertex
					if(graph.getNumOfUnfilledVertices() > 1){
						for(String vertex : graph.getUnfilledVertices()){
							subconfigurations++;
							//Clone the graph
							Graph subGraph = new Graph(graph);
							//Fill that vertex
							subGraph.fillVertex(vertex);
							//Add the subgraph to the stack if it does not exist in the predecessors
							if(!predecessors.contains(subGraph)){
								stack.add(subGraph);
								predecessors.add(subGraph);
							}
						}//End of for loop
					}
				}
			}else{
				//Prune the graph if it is solvable
				if(isSolvable(graph, type, forcingnumber)){
					//Check if graph is the new maximum
					int newmax = graph.getNumOfFilledVertices();
					if(newmax < maximumN || maximumN == -1){
						//New maximum number of filled vertices is found
						results.clear(); //Clears the results 
						results.add(graph);
						maximumN = newmax;
					}else if(newmax == maximumN){
						//Same amount of maximum and new way to fail the graph
						results.add(graph);
					}
					
					//Make subconfigurations from unfilled vertices if there is more than one unfilled vertex
					if(graph.getNumOfFilledVertices() > 1){
						for(String vertex : graph.getFilledVertices()){
							subconfigurations++;
							//Clone the graph
							Graph subGraph = new Graph(graph);
							//Fill that vertex
							subGraph.unfillVertex(vertex);
							//Add the subgraph to the stack if it does not exist in the predecessors
							if(!predecessors.contains(subGraph)){
								stack.add(subGraph);
								predecessors.add(subGraph);
							}
						}//End of for loop
					}
				}
			}
			iterations++;
			if(debug) System.out.print("\rIterations: " + iterations
										+ "  Subconfigs: " + subconfigurations
										+ "  Uniques: " + predecessors.size()
										+ "  Stack: " + stack.size()
										+ "  Max V: " + maximumN);
		}//End of while loop
		
		return new ArrayList<Graph>(results); //Returns the results in ArrayList form
	}//End of forwardSearcher method

	public static ArrayList<Graph> reverseSearcher(Graph original, boolean maximum, int type, int forcingnumber, boolean debug){
		if(debug) System.out.println("\nDebugging enabled!\n\r");
		Graph graph = new Graph(original);
		if(maximum){
			//fill the graph
			for(String vertex : graph.getUnfilledVertices()){
				graph.fillVertex(vertex);
			}//End of for loop
		}else{
			//unfill the graph
			for(String vertex : graph.getUnfilledVertices()){
				graph.unfillVertex(vertex);
			}//End of for loop
		}
		//Initialize
		int mVertices = -1;
		boolean maxed = false;
		HashSet<Graph> results = new HashSet<Graph>(); //used to store results
		HashSet<Graph> predecessors = new HashSet<Graph>(); //used to store graphs that are already tested
		Queue<Graph> queue = new LinkedList<Graph>(); //Queue variable
		queue.offer(graph);//The original will be the first to run
		
		int iterations = 0;
		int subconfigurations = 0;
		int debugMaxinum = 0;
		
		//Run the searcher
		while(!queue.isEmpty()){
			graph = queue.poll(); //Polls the queue and load the graph
			//If graph is solvable - create subconfigurations
			//If maximum is found or graph is unsolvable, collect the graphs
			if((maximum&&isSolvable(graph, type, forcingnumber)) || (!maximum&&!isSolvable(graph, type, forcingnumber))){
				//Make subconfigurations from filled vertices if there is more than one filled vertex
				if(maximum){
					if(graph.getNumOfFilledVertices() > 1  && !maxed){
						for(String vertex : graph.getFilledVertices()){
							//Clone the graph
							Graph subGraph = new Graph(graph);
							//unfill that vertex
							subGraph.unfillVertex(vertex);
							subconfigurations++;
							//Add the subgraph to the queue if it does not exist in the predecessors
							if(!predecessors.contains(subGraph)){
								queue.add(subGraph);
								predecessors.add(subGraph);
							}
						}//End of for loop
					}
				}else{
					if(graph.getNumOfUnfilledVertices() > 1  && !maxed){
						for(String vertex : graph.getUnfilledVertices()){
							//Clone the graph
							Graph subGraph = new Graph(graph);
							//fill that vertex
							subGraph.fillVertex(vertex);
							subconfigurations++;
							//Add the subgraph to the queue if it does not exist in the predecessors
							if(!predecessors.contains(subGraph)){
								queue.add(subGraph);
								predecessors.add(subGraph);
							}
						}//End of for loop
					}
				}
			}else{
				if(!maxed) mVertices = graph.getNumOfFilledVertices();
				maxed = true;
				//Graph is unsolvable therefore add it to the results list
				//if(mVertices == -1) mVertices = maxnum;
				if(mVertices == graph.getNumOfFilledVertices()){ results.add(graph);
				}else{ return new ArrayList<Graph>(results); }; 
			}
			debugMaxinum = Math.max(debugMaxinum, graph.getNumOfFilledVertices());
			iterations++;
			if(debug) System.out.print("\rIterations: " + iterations
										+ "  Subconfigs: " + subconfigurations
										+ "  Uniques: " + predecessors.size()
										+ "  Queue: " + queue.size()
										+ "  Max V: " + debugMaxinum);
		}//End of while loop
		return new ArrayList<Graph>(results);
	}//End of reverseSearcher method
	
	public static ArrayList<Graph> unionSearcher(Graph original, boolean maximum, int type, int forcingnumber, boolean debug){
		if(debug) System.out.println("\nDebugging enabled!");
		
		//Create an inner thread class for each searchers
		class SearchThread extends Thread{
			private int type;
			private boolean maximum;
			private int searcher;
			private int forcingnumber;
			private Graph graph = null;
			private ArrayList<Graph> result = null;
			public SearchThread(Graph original, boolean maximum, int searcher, int type, int forcingnumber){
				this.graph = original;
				this.maximum = maximum;
				this.searcher = searcher;
				this.type = type;
				this.forcingnumber = forcingnumber;
			}//End of constructor
			
			boolean finish = false;
			public boolean isFinished(){ return finish; }
			public ArrayList<Graph> getResult(){ return result; }
			@Override
			public void run(){
				if(searcher == 0) result = forwardSearcher(graph, maximum, type, forcingnumber, false);
				else result = reverseSearcher(graph, maximum, type, forcingnumber, false);
				finish = true;
			}//End of run method
		};
		
		//Create threads
		SearchThread forward = new SearchThread(original, maximum, 0, type, forcingnumber);
		SearchThread reverse = new SearchThread(original, maximum, 1, type, forcingnumber);
		if(debug) System.out.println("DEBUG: Threads executed!\r");
		
		//Run both threads
		forward.start();
		reverse.start();
		if(debug) System.out.println("DEBUG: Waiting for threads!\r");
		int indeter = 0;
		//Wait for either threads to finish
		while(!forward.isFinished() && !reverse.isFinished()){
			StringBuilder string = new StringBuilder("####################");
			string.setCharAt(indeter, '-');
			System.out.print("\rWorking... : " + string);
			if(indeter < string.length()-1){
					indeter++;
			}else indeter = 0;
			try{
				Thread.sleep(100);
			}catch(Exception e){};
		}//End of while loop
		System.out.print("\r                                    ");
		forward.interrupt();
		reverse.interrupt();
		if(debug) System.out.println("\rDEBUG: Threads finished!");
		
		//Get results
		ArrayList<Graph> result = forward.getResult();
		if(result != null){
			if(debug) System.out.println("DEBUG: Forward Finishes!");
			return result;
		}
		if(debug) System.out.println("DEBUG: Else, Reverse Finishes or all is fail.");
		result = reverse.getResult();
		return result;
	}//End of unionSearcher method
}//End of class