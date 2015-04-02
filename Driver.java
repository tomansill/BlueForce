/**
 * @author Thomas B. Ansill
 * @date April 08, 2014	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver{

	private static Graph graph = null;
	private static boolean debug = false;
	private static int defaultForcingNumber = 1;

	private static void terminal(){
	
		String str = "";
		str +=    	"-= Forcing Set Solver v0.5 =-"
				+ "\n\n   Author: Thomas B. Ansill"
				+ "\n   University: Rochester Institute of Technology"
				+ "\n   This program is used only for research purposes";
		System.out.println(str + "\n");
	
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.print(">>: ");
			parse(in.nextLine(), in);
		}//End of while loop
	}//End of terminal method
	
	private static void parse(String input, Scanner in){
		//Tokenizes the String
		input.toLowerCase();
		ArrayList<String> pieces = new ArrayList<String>(Arrays.asList(input.toLowerCase().split("\\s+")));
		if(pieces.size() != 0){
			input = pieces.get(0);
			pieces.remove(0);
			switch (input){
				case "exit":		
				case "quit":		in.close();
									System.exit(0);
				case "cls":			
				case "clear":		for(int i = 0; i < 40; i ++){
										System.out.println();
									}//End of for loop
									break;
				case "load":		load(pieces, in);
									break;
				case "builder":
				case "build":
				case "generate":	generate(pieces, in);
									break;
				case "help":		help();
									break;
				case "print":		
				case "display":		if(graph == null) break;
									System.out.println(graph);
									break;
				case "forcingnumber":
				case "number":		
				case "forcing":		if(graph == null) break;
									changeForcingNumber(pieces, in);
									break;
				case "solve":		if(graph == null) break;
									solve(pieces, in);
									break;
				case "findmin":		if(graph == null) break;
									findMax(false, pieces, in);
									break;
				case "findmax":		if(graph == null) break;
									findMax(true, pieces, in);
									break;
				case "debug":		if(!debug) debug = true;
									else debug = false;
									break;
				default:			System.out.println("Command not recognized - try typing 'help' for more information");
			}//End of switch statement
		}
	}//End of parse method
	
	private static void changeForcingNumber(ArrayList<String> pieces, Scanner in){
		if(pieces.size() == 0){
			boolean stay = true;
			while(stay){
				System.out.print(">> Input the forcing number ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					break;
				}else{
					try{
						int number = Integer.parseInt(input);
						if(number >= 0){
							defaultForcingNumber = number+1;
							break;
						}else{
							System.out.println("Error - number must be a positive integer."); 
						}
					}catch(NumberFormatException e){
						System.out.println("Error - input must be integers."); 
					}
				}
			}//End of while loop
		}else{
			String input = pieces.get(0);
			pieces.remove(0);
			/*if(input.equals("number")){
				if(pieces.size() == 0){
					changeForcingNumber(pieces, in);
				}
				input = pieces.get(0);
				pieces.remove(0);
			}*/
			try{
				int number = Integer.parseInt(input);
				if(number >= 0){
					defaultForcingNumber = number+1;
				}else{
					System.out.println("Error - number must be a positive integer."); 
				}
			}catch(NumberFormatException e){
				System.out.println("Error - input must be integers."); 
				
			}
		}
	}//End of changeForcingNumber method
	
	private static void load(ArrayList<String> pieces, Scanner in){
		if(pieces.size() == 0){
			while(true){
				System.out.print(">>Enter the file path to the graph file ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					//quits the method
					break;
				}else if(input.equals("exit")){
					//Forces quit
					in.close();
					System.exit(0);
				}else{
					File path = new File(input);
					if(!path.exists()){
						//Asks the user to check input
						System.out.println("This file path does not exist!");
					}else{
						//Opens the file
						System.out.println("Graph loaded!");
						graph = new Graph(path);
						break;
					}
				}
			}//End of while loop
		}else{
			File path = new File(pieces.get(0));
			if(!path.exists()){
				//Asks the user to check input
				System.out.println("This file path does not exist!");
			}else{
				//Opens the file
				System.out.println("Graph loaded!");
				graph = new Graph(path);
			}
		}
	}//End of load method
	
	private static void solve(ArrayList<String> pieces, Scanner in){
		if(pieces.size() == 0){
			String types = "Forcing Types:\n"
							+  "- Zero Forcing Set: 1\n"
							+  "- Skew Zero Forcing Set: 2\n"
							+  "- Positive Semi-Definite Forcing Set: 3\n\n";
			System.out.print(types);
			boolean stay = true;
			while(stay){
				System.out.print(">>Enter the type of Forcing ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					//quits the method
					break;
				}else{
					switch(input){
						case "1":	solveResult(graph, 1);
									stay = false;
									break;
						case "2":	solveResult(graph, 2);
									stay = false;
									break;
						case "3":	solveResult(graph, 3);
									stay = false;
									break;
						default: 	System.out.println("Input not recognized");
					}//End of switch statement
				}
			}//End of while loop
		}else{
			switch(pieces.get(0)){
				case "1":	solveResult(graph, 1);
							break;
				case "2":	solveResult(graph, 2);
							break;
				case "3":	solveResult(graph, 3);
							break;
				default: 	System.out.println("Input not recognized");
			}//End of switch statement
		}
	}//End of solve method
	
	private static void solveResult(Graph original, int select){
		Graph graph = null;
		switch(select){
			case 1: graph = ForcingSet.ZeroForcingSet(original, defaultForcingNumber);
					break;
			case 2: graph = ForcingSet.SkewZeroForcingSet(original, defaultForcingNumber);
					break;
			case 3: graph = ForcingSet.PositiveSemiDefiniteZeroForcingSet(original, defaultForcingNumber);
					break;
			default: break;
		}//End of switch statement
		System.out.println("== Original ==\n" + original);
		System.out.println("== Results ==");
		if(graph != null){
			System.out.println(graph);
		}else{
			System.out.println("Unsolvable!");
		}
	}//End of solveResult
	
	private static void findMax(boolean max, ArrayList<String> pieces, Scanner in){
		if(pieces.size() == 0){
			//Assume user wants Union Searcher if there's no more pieces
			findMax1(max, pieces, in, 3);
		
			/*
			String types = "Searcher Types:\n"
							+  "- Forward: 1\n"
							+  "- Reverse: 2\n" 
							+  "- Union: 3\n\n" ;
			System.out.print(types);
			boolean stay = true;
			while(stay){
				System.out.print(">>Enter the type of Searcher ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					//quits the method
					break;
				}else{
					switch(input){
						case "forward":
						case "1":	findMax1(pieces, in, 1);
									stay = false;
									break;
						case "reverse":
						case "2":	findMax1(pieces, in, 2);
									stay = false;
									break;
						case "union":
						case "3":	findMax1(pieces, in, 3);
									stay = false;
									break;
						default: 	System.out.println("Input not recognized");
					}//End of switch statement
				}
			}//End of while loop*/
		}else{
			String label = pieces.get(0);
			
			switch(label){
				case "forward":
							pieces.remove(0);
							findMax1(max, pieces, in, 1);
							break;
				case "reverse":
							pieces.remove(0);
							findMax1(max, pieces, in, 2);
							break;	
				default: 	
							findMax1(max, pieces, in, 3);
			}//End of switch statement
		}
	}//End of findMax method
	
	private static void findMax1(boolean max, ArrayList<String> pieces, Scanner in, int searcher){
		if(pieces.size() == 0){
			String types = "Forcing Types:\n"
							+  "- Zero Forcing Set: 1\n"
							+  "- Skew Zero Forcing Set: 2\n"
							+  "- Positive Semi-Definite Forcing Set: 3\n\n";
			System.out.print(types);
			boolean stay = true;
			while(stay){
				System.out.print(">>Enter the type of Forcing ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					//quits the method
					break;
				}else{
					switch(input){
						case "1":	findMaxResult(graph, max, 1, searcher);
									stay = false;
									break;
						case "2":	findMaxResult(graph, max, 2, searcher);
									stay = false;
									break;
						case "3":	findMaxResult(graph, max, 3, searcher);
									stay = false;
									break;
						default: 	System.out.println("Input not recognized");
					}//End of switch statement
				}
			}//End of while loop
		}else{
			switch(pieces.get(0)){
				case "1":	findMaxResult(graph, max, 1, searcher);
							break;
				case "2":	findMaxResult(graph, max, 2, searcher);
							break;
				case "3":	findMaxResult(graph, max, 3, searcher);
							break;
				default: 	System.out.println("Input not recognized");
			}//End of switch statement
		}
	}//End of findMax method
	
	private static void findMaxResult(Graph original, boolean max, int select, int searcher){
		ArrayList<Graph> list = null;
		long time = System.currentTimeMillis();
		if(searcher == 1){
			list = ForcingSet.forwardSearcher(original, max, select, defaultForcingNumber, debug);
		}else if(searcher == 2){
			list = ForcingSet.reverseSearcher(original, max, select, defaultForcingNumber, debug);
		}else{
			list = ForcingSet.unionSearcher(original, max, select, defaultForcingNumber, debug);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("\n== Results ==");
		if(list.size() != 0){
			int count = 1;
			for(Graph graph : list){
				System.out.println("-  Graph " + count + "  -\n" + graph);
				count++;
			}
			if(list.size() == 1){
				System.out.println("There is only one graph.");
			}else{
				System.out.println("There are " + list.size() + " graphs.");
				
			}
			System.out.println("The number of filled vertices: " + list.get(0).getNumOfFilledVertices());
		}else{
			System.out.println("No such configuration exists");
		}
		if(debug) System.out.println("The search took " + time/1000.0 + " seconds!");
	}//End of solveResult
	
	private static void help(){
		String output = "";
		output += "\nHelpful Information:\n"
				+ "Commands:\n"
				+ "quit\t\t - quits the program\n"
				+ "help\t\t - displays help information\n"
				+ "load\t\t - loads a graph from disk\n"
				+ "builder\t\t - creates a graph\n";
		if(graph != null){
			output +="print\t\t - prints the graph on the screen\n"
					+"solve\t\t - solves the graph with your chosen method\n"
					+"forcingnumber\t - changes the forcing number\n"
					+"findmax\t\t - finds the maxinum number of filled\n"
					+"\t\t    vertices in a failed graph\n";
		}
		System.out.println(output);
	}//End of help method
	
	private static void generate(ArrayList<String> pieces, Scanner in){
		boolean scanner = false;
		if(pieces.size() == 0){
			scanner = true;
			System.out.print(">>Builder: ");
			String input = in.nextLine();
			input.toLowerCase();
			pieces = new ArrayList<String>(Arrays.asList(input.split("\\s+")));
		}
		String type = pieces.get(0);
		pieces.remove(0);
		do{
			switch(type){
				case "path": 		scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for Path graph");
									if(param1 == -1) break;
									graph = GraphBuilder.buildPathGraph(param1);
									}
									break;
				case "circle":
				case "cycle":		scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for Cycle graph");
									if(param1 == -1) break;
									graph = GraphBuilder.buildCycleGraph(param1);
									}
									break;
				case "wheel":		scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for Wheel graph");
									if(param1 == -1) break;
									graph = GraphBuilder.buildWheelGraph(param1);
									}
									break;
				case "bipartite":	scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for first side of Bipartite graph");
									if(param1 == -1) break;
									int param2 = builderParam(pieces, in, "Enter the number of vertices for second side of Bipartite graph");
									if(param2 == -1) break;
									graph = GraphBuilder.buildBiPartiteGraph(param1, param2);
									}
									break;
				case "complete":	scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for Complete graph");
									if(param1 == -1) break;
									graph = GraphBuilder.buildCompleteGraph(param1);
									}
									break;
				case "grid":		scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices for width of Grid graph");
									if(param1 == -1) break;
									int param2 = builderParam(pieces, in, "Enter the number of vertices for length of Grid graph");
									if(param2 == -1) break;
									graph = GraphBuilder.buildGridGraph(param1, param2);
									}
									break;
				case "completetriangles":
				case "trianglegrid": scanner = false;
									{
									int param1 = builderParam(pieces, in, "Enter the number of vertices on one side of Triangle Grid graph");
									if(param1 == -1) break;
									graph = GraphBuilder.buildTriangleGridGraph(param1);
									}
									break;
				case "cancel":		scanner = false;
									break;
				default:			System.out.println("Graph type not supported");
									if(scanner){
										System.out.print(">>Builder: ");
										String input = in.nextLine();
										input.toLowerCase();
										pieces = new ArrayList<String>(Arrays.asList(input.split("\\s+")));
										type = pieces.get(0);
										pieces.remove(0);
									}
			}//End of switch statement
		}while(scanner);
	}//End of for loop
	
	private static int builderParam(ArrayList<String> pieces, Scanner in, String question){
		if(pieces.size() == 0){
			boolean stay = true;
			while(stay){
				System.out.print(">>" + question + " ('cancel' to cancel): ");
				String input = in.nextLine().toLowerCase();
				System.out.println();
				if(input.equals("cancel") || input.equals("c")){
					//quits the method
					return -1;
				}else{
					try{
						int number = Integer.parseInt(input);
						if(number > 0){
							return number;
						}else{
							System.out.println("Builder Error - number must be 1 or higher."); 
						}
					}catch(NumberFormatException e){
						System.out.println("Builder Error - input must be integers."); 
					}
				}
			}//End of while loop
			return -1;
		}else{
			String input = pieces.get(0);
			pieces.remove(0);
			try{
				int number = Integer.parseInt(input);
				if(number > 0){
					return number;
				}else{
					System.out.println("Builder Error - number must be 1 or higher."); 
				}
			}catch(NumberFormatException e){
				System.out.println("Builder Error - input must be integers."); 
				
			}
			return -1;
		}
	}//End of builderParam method

	public static void main(String[] args){
		terminal();
	}//End of main method
}//End of class