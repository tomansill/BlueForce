import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class GraphReader{
	private GraphReader(){}
	public static TreeMap<String, Vertex> readGraph(File filepath) throws Exception, IOException{
		if(!filepath.exists()) throw new IOException("File does not exist!");	
		FileReader fr = new FileReader(filepath);
		int currChar = 0;
		TreeMap<String, Vertex> map = new TreeMap<String, Vertex>();
		while((currChar = fr.read()) != -1){
			if(!Character.isWhitespace((char)currChar)){
				if(currChar == '{'){
					Vertex v = readVertex(fr);		
					String label = v.getLabel();
					map.put(label, v);
				}
			}
		}//End of loop
		return map;
	}//End of readGraph function
	private static Vertex readVertex(FileReader fr) throws Exception{
		int currChar = 0;
		String label = null;
		short state = -1;
		ArrayList<String> neighbors = null;
		int[] coordinate = null;
		while((currChar = fr.read()) != -1){
			if(!Character.isWhitespace((char)currChar)){
				if(currChar == '"') label = readString(fr);
				else if(currChar == '}'){
					if(label != null || state != -1 || neighbors != null){
						if(coordinate != null) return new Vertex(label, state, neighbors, coordinate); 
						else return new Vertex(label, state, neighbors);
					}
				}else if(currChar == '['){
					if(neighbors == null) neighbors = readNeighbors(fr);	
					else coordinate = readCoordinates(fr); 
				}else if(Character.isLetter((char)currChar) || currChar == '-') state = readState(fr, currChar);  
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

	private static short readState(FileReader fr, int prev) throws Exception{
		StringBuilder str = new StringBuilder();
		str.append((char)prev);
		int currChar = 0;
		while((currChar = fr.read()) != -1){
			if(!Character.isLetter((char)currChar)) str.append((char)currChar);
			else return Short.parseShort(str.toString());
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

	private static int[] readCoordinates(FileReader fr) throws Exception{
		int[] coordinates = {0, 0, 0};
		StringBuilder str = new StringBuilder();
		int count = 0;
		int currChar = 0;
		while((currChar = fr.read()) != -1){
			if(currChar == ','){
				coordinates[count] = Integer.parseInt(str.toString());
				count++;
			}
			else if(currChar == ']') return coordinates;
			else str.append((char)currChar);
		}//End of loop
		throw new Exception("Input is not properly formatted!");
	}//End of readCoordinates function
}//End of class
