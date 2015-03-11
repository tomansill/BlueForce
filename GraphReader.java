import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class GraphReader{
	private GraphReader(){}

	public static Graph readGraph(File filepath) throws Exception, IOException{
		Graph graph = new Graph();
		TreeMap<Vertex, ArrayList<String>> map = getTreeMap(filepath);

		//Add all vertices first
		for(Vertex vertex : map.keySet()){
			graph.addVertex(vertex);
		}//End of loop

		//Connect all vertices now
		for(Map.Entry<Vertex, ArrayList<String>> entry : map.entrySet()){
			String label = entry.getKey().getLabel();
			ArrayList<String> neighbors = entry.getValue();
			for(String neighbor : neighbors){
				graph.connectVertices(label, neighbor);
			}//End of loop
		}//End of loop
		return graph;
	}//End of readGraph function
	
	private static TreeMap<Vertex, ArrayList<String>> getTreeMap(File filepath) throws Exception, IOException{
		if(!filepath.exists()) throw new IOException("File does not exist!");	
		FileReader fr = new FileReader(filepath);
		int currChar = 0;
		TreeMap<Vertex, ArrayList<String>> map = new TreeMap<Vertex, ArrayList<String>>();
		while((currChar = fr.read()) != -1){
			if(!Character.isWhitespace((char)currChar)){
				if(currChar == '{'){
					//Very hackish code because I'm too lazy to rewrite whole thing
					Object[] v = readVertex(fr);		
					Vertex vertex = (Vertex) v[0];
					ArrayList<String> neighbors = (ArrayList<String>) v[1];
					map.put(vertex, neighbors);
				}
			}
		}//End of loop
		return map;
	}//End of getTreeMap function
	private static Object[] readVertex(FileReader fr) throws Exception{
		int currChar = 0;
		String label = null;
		short state = -1;
		ArrayList<String> neighbors = null;
		Point2D.Float coordinate = null;
		while((currChar = fr.read()) != -1){
			if(!Character.isWhitespace((char)currChar)){
				if(currChar == '"') label = readString(fr);
				else if(currChar == '}'){
					if(label != null || state != -1 || neighbors != null){
						System.out.print("label: " + label + " state: " + state);
						if(coordinate != null){
							System.out.print(" coordinate: ");
							for(int i = 0; i < 3; i++) System.out.print(coordinate + " ");
							System.out.println(" neighbors: " + neighbors);
							Object[] cheapTuple = new Object[2];
							cheapTuple[0] = (Object) new Vertex(label, state, coordinate);
							cheapTuple[1] = (Object) neighbors;
							return cheapTuple; 
						}else{
							Object[] cheapTuple = new Object[2];
							cheapTuple[0] = (Object) new Vertex(label, state);
							cheapTuple[1] = (Object) neighbors;
							return cheapTuple; 
						}
					}
				}else if(currChar == '['){
					if(neighbors == null) neighbors = readNeighbors(fr);	
					else coordinate = readCoordinates(fr); 
				}else if((!Character.isLetter((char)currChar) && Character.isDigit((char)currChar)) || currChar == '-') state = readState(fr, currChar);  
			}	
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readVertex function

	private static String readString(FileReader fr) throws Exception{
		int currChar = 0;
		StringBuilder str = new StringBuilder();
		while((currChar = fr.read()) != -1){
			if(currChar == '"') return str.toString();
			else str.append((char)currChar); 
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readString function

	private static short readState(FileReader fr, int prevChar) throws Exception{
		StringBuilder str = new StringBuilder();
		int currChar = prevChar;
		boolean num = false;
		while(currChar != -1){
			if(Character.isDigit((char)currChar)){ 
				str.append((char)currChar);
				num = true;
			}else if((currChar == ',' || currChar == ']') && num){
				return Short.parseShort(str.toString());
			} 
			currChar = fr.read();
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readState function

	private static ArrayList<String> readNeighbors(FileReader fr) throws Exception{
		ArrayList<String> neighbors = new ArrayList<String>();
		int currChar = 0;
		while((currChar = fr.read()) != -1){
			if(currChar == '"') neighbors.add(readString(fr));	
			if(currChar == ']') return neighbors;
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readNeighbors function

	private static Point2D.Float readCoordinates(FileReader fr) throws Exception{
		float[] coordinates = {0, 0};
		StringBuilder str = new StringBuilder();
		int count = 0;
		int currChar = 0;
		while((currChar = fr.read()) != -1){
			if(currChar == ','){
				coordinates[count] = Float.parseFloat(str.toString());
				count++;
			}
			else if(currChar == ']') return new Point2D.Float(coordinates[0], coordinates[1]);
			else str.append((char)currChar);
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readCoordinates function
}//End of class
