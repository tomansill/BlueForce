/**
 * @author Thomas B. Ansill
 * @date April 08, 2014	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.awt.geom.Point2D;
import java.util.*;
import java.util.Map.Entry;

public class GraphBuilder{

    public static Graph buildRandomGraph(int constrict) {
        Graph graphified = new Graph();
        // RNG
        Random RNJesus = new Random();

        //Create random amount of vertex with random value for state and add them to graph
        Integer NumOfVertex = Math.abs(RNJesus.nextInt(constrict));
        int MaxElementsInRow = 10;

        System.out.println(NumOfVertex);
        for (Integer i = 0; i < NumOfVertex; i++) {
            Point2D.Float coordinate = new Point2D.Float((float)((i%MaxElementsInRow)*30), (float)((i/MaxElementsInRow)*30));
            Vertex vertex = new Vertex(i.toString(), Math.abs(RNJesus.nextInt(constrict)), coordinate);
            graphified.addVertex(vertex);
        }
        // Now we randomly connect the vertices...

        for (Integer i = 0; i < Math.abs(RNJesus.nextInt(constrict)); i++) {
            String firstVertex = ((Integer) RNJesus.nextInt(NumOfVertex)).toString();
            String secondVertex = ((Integer) RNJesus.nextInt(NumOfVertex)).toString();
            graphified.connectVertices(firstVertex, secondVertex);
        }
        return graphified;
    }

    public static Graph buildRandomGraph() {
        Graph graphified = new Graph();
        // RNG
        Random RNJesus = new Random();

        //Create random amount of vertex with random value for state and add them to graph
        Integer NumOfVertex = Math.abs(RNJesus.nextInt());
        int MaxElementsInRow = 10;

        System.out.println(NumOfVertex);
        for (Integer i = 0; i < NumOfVertex; i++) {
            Point2D.Float coordinate = new Point2D.Float((float)((i%MaxElementsInRow)*30), (float)(i/MaxElementsInRow));
            Vertex vertex = new Vertex(i.toString(), Math.abs(RNJesus.nextInt()), coordinate);
            graphified.addVertex(vertex);
        }
        // Now we randomly connect the vertices...

        for (Integer i = 0; i < Math.abs(RNJesus.nextInt()); i++) {
            String firstVertex = ((Integer) RNJesus.nextInt(NumOfVertex)).toString();
            String secondVertex = ((Integer) RNJesus.nextInt(NumOfVertex)).toString();
            graphified.connectVertices(firstVertex, secondVertex);
        }
        return graphified;
    }
	
/*	public static Graph buildPathGraph(int vertices){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		for(int i = 1; i < vertices + 1; i++){
			String label = convertToReadableLabel(i);
			ArrayList<String> neighbors = new ArrayList<String>();
			//Behind
			if(i != 1){
				neighbors.add(convertToReadableLabel(i-1));
			}
			//Front
			if(i != vertices){
				neighbors.add(convertToReadableLabel(i+1));
			}
			list.put(label, new Vertex(label, false, neighbors));
		}//End of for loop
		return new Graph(list);
	}//End of buildPathGraph method
	
	public static Graph buildCycleGraph(int vertices){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		for(int i = 1; i < vertices + 1; i++){
			String label = convertToReadableLabel(i);
			ArrayList<String> neighbors = new ArrayList<String>();
			//Behind
			if(i != 1){
				neighbors.add(convertToReadableLabel(i-1));
			}else{
				//Wraps around
				neighbors.add(convertToReadableLabel(vertices));
			}
			//Front
			if(i != vertices){
				neighbors.add(convertToReadableLabel(i+1));
			}else{
				//Wraps around
				neighbors.add(convertToReadableLabel(1));
			}
			list.put(label, new Vertex(label, neighbors, false));
		}//End of for loop
		return new Graph(list);
	}//End of buildCircleGraph method
	
	public static Graph buildWheelGraph(int vertices){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		ArrayList<String> mneighbors = new ArrayList<String>();
		for(int i = 1; i < vertices; i++){
			String label = convertToReadableLabel(i);
			mneighbors.add(label);
			ArrayList<String> neighbors = new ArrayList<String>();
			//Behind
			if(i != 1){
				neighbors.add(convertToReadableLabel(i-1));
			}else{
				//Wraps around
				neighbors.add(convertToReadableLabel(vertices-1));
			}
			//Front
			if(i != vertices-1){
				neighbors.add(convertToReadableLabel(i+1));
			}else{
				//Wraps around
				neighbors.add(convertToReadableLabel(1));
			}
			//Links to the middle Vertex
			neighbors.add(convertToReadableLabel(vertices));
			list.put(label, new Vertex(label, neighbors, false));
		}//End of for loop
		list.put(convertToReadableLabel(vertices), new Vertex(convertToReadableLabel(vertices), mneighbors, false));
		return new Graph(list);
	}//End of buildWheelGraph method
	
	public static Graph buildBiPartiteGraph(int vertice1, int vertice2){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		for(int i = 1; i < vertice1+1; i++){
			String label = "A" + i;
			ArrayList<String> neighbors = new ArrayList<String>();
			for(int j = 1; j < vertice2+1; j++){
				neighbors.add("B" + j);
			}//End of for loop
			list.put(label, new Vertex(label, neighbors, false));
		}//End of for loop
		for(int i = 1; i < vertice2+1; i++){
			String label = "B" + i;
			ArrayList<String> neighbors = new ArrayList<String>();
			for(int j = 1; j < vertice1+1; j++){
				neighbors.add("A" + j);
			}//End of for loop
			list.put(label, new Vertex(label, neighbors, false));
		}//End of for loop
		return new Graph(list);
	}//End of buildBiPartiteGraph method
	
	public static Graph buildCompleteGraph(int vertice){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		for(int i = 1; i < vertice + 1; i++){
			String label = convertToReadableLabel(i);
			ArrayList<String> neighbors = new ArrayList<String>();
			for(int j = 1; j < vertice + 1; j++){
				if(j != i){
					neighbors.add(convertToReadableLabel(j));
				}
			}//End of for loop
			list.put(label, new Vertex(label, neighbors, false));
		}//End of for loop
		return new Graph(list);
	}//End of buildCompleteGraph method
	public static Graph buildGridGraph(int width, int length){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		for(int i = 0; i < width; i++){
			for(int j = 0; j < length; j++){
				String label = "(" + i + "," + j + ")";
				ArrayList<String> neighbors = new ArrayList<String>();
				//Left side
				if(i != 0){ neighbors.add("(" + (i-1) + "," + j + ")"); }
				//Right side
				if(i != width-1){ neighbors.add("(" + (i+1) + "," + j + ")"); }
				//Up side
				if(j != 0){ neighbors.add("(" + i + "," + (j-1) + ")"); }
				//Down side
				if(j != length-1){ neighbors.add("(" + i + "," + (j+1) + ")"); }
				list.put(label, new Vertex(label, neighbors, false));
			}//End of for loop
		}//End of for loop
		return new Graph(list);
	}//End of buildGridGraph method
	public static Graph buildTriangleGridGraph(int numOfVSide){
		TreeMap<String, Vertex> list = new TreeMap<String, Vertex>();
		int count = 1;
		for(int i = numOfVSide; i >= 0; i--){
			for(int j = 0; j < i; j++){
				String label = convertToReadableLabel(count);
				ArrayList<String> neighbors = new ArrayList<String>();
				//Direct down
				if(i != numOfVSide){
					neighbors.add(convertToReadableLabel(count-i-1));
					neighbors.add(convertToReadableLabel(count-i));
				}
				//Vertex to the left
				if(j != 0) neighbors.add(convertToReadableLabel(count-1));
				//Vertex to the right
				if(j != i-1) neighbors.add(convertToReadableLabel(count+1));
				//Direct upleft
				if(j != 0 && i != 0) neighbors.add(convertToReadableLabel(count+i-1));
				//Direct upright
				if(j != i-1 && i != 0) neighbors.add(convertToReadableLabel(count+i));
				list.put(label, new Vertex(label, neighbors, false));
				count++;
			}//End of for loop
		}//End of for loop
		return new Graph(list);
	}//End of buildTriangleGridGraph method
	
	public static String convertToReadableLabel(int number){
		int carryover = 0;
		while(true){
			if(number > 51){
				number -= 51;
				carryover++;
			}else{
				break;
			}
		}//End of while loop
		int nchar = 64 + number; //Captialized A
		if(nchar > 90) nchar += 7;
		String str = "";
		if(carryover != 0) str += convertToReadableLabel(carryover);
		str += (char)nchar;
		return str;
	}//End of convertToReadableLabel method
	*/
}//End of class