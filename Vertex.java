/**
 * @author Thomas B. Ansill
 * @author Brian T. Podlisny
 * @date March 5, 2015	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Vertex implements Comparable<Vertex>{
	/** Value for vertex label */
	private String label;
	/** Coordinate point for a given vertex */
	private Point2D coordinate;
	/** Value for vertex's state */
	private short state;

	/** Parametrized Constructor for Vertex Object
	 *	@param label Vertex Label
	 */
	public Vertex(String label){
		this.label = label;
		this.state = 0;
		this.coordinate = new Point2D.Float();
	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 *	@param label Vertex Label
	 *	@param state Vertex's state
	 */
	public Vertex(String label, short state){
		this.label = label;
		this.state = state;
		this.coordinate = new Point2D.Float();
	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 *	@param label Vertex Label
	 *	@param coordinate coordinate of vertex
	 */
	public Vertex(String label, Point2D coordinate){
		this.label = label;
		this.state = 0;
		this.coordinate = coordinate;		
	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 *	@param label Vertex Label
	 *	@param state Vertex's state
	 *	@param coordinate coordinate of vertex
	 */
	public Vertex(String label, short state, Point2D coordinate){
		this.label = label;
		this.state = state;
		this.coordinate = coordinate;
	}//End of constructor

	/** Deepcopies an existing Vertex into a new one
	 *	@param vertex Existing Vertex to be deep copied from
	 */
	public Vertex(Vertex vertex){
		this.label = vertex.getLabel();
		this.state = vertex.getState();
		//Does this need to be deepcopied?
		this.coordinate = vertex.getCoordinate();
	}//End of constructor
	
	/** Accessor for vertex's label
     *	@return label
	 */
	public String getLabel(){return label;}

	/** Acessor for vertex's coordinate
	 *	@return coordinate
	 */
	public synchronized Point2D getCoordinate(){ return coordinate;}

	/** Move the vertex
	 *	@param x delta in x axis
	 *	@param y delta in y axis
	 */
	public synchronized void move(float x, float y){
		this.coordinate.setLocation(this.coordinate.getX() + x, this.coordinate.getY() + y);	
	}//End of move method

	/** sets the coordinate of the point
	 *	@param point location
	 */
	public synchronized void setCoordinate(Point2D point){ this.coordinate.setLocation(point); }
	
	/** sets the coordinate of the point
	 *	@param x x-coordinate
	 *	@param y y-coordinate
	 */
	public synchronized void setCoordinate(int x, int y){ this.coordinate.setLocation(new Point2D.Float(x, y)); }

	/** Accessor for vertex's filled status
     *	@return Vertex's state
	 */
	public synchronized short getState(){return this.state;}

	/** Hashing Method - Not sure if necessary anymore */
	public int hashCode(){return label.hashCode();}

	/** Sets the vertex to a specific state
	 *	@param state State 
 	 */
	public synchronized void setState(short state){ this.state = state; }

	/** Comparatable method
	 *	@param vertex Vertex to be compared with this vertex
	 *	@return 1 if greater, 0 if equal, -1 if less than
	 */
	 @Override
	public int compareTo(Vertex vertex){
		int lab = this.label.compareTo(vertex.getLabel());
		if(lab != 0) return lab;	
		if(this.state == vertex.getState()) return 0;
		else if(this.state >= vertex.getState()) return 1;
		else return -1;
	}//End of compareTo method

	/** Equals method
	 *	@param obj Object to be compared to
	 *	@return true if equal, otherwise false
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Vertex)) return false;
		//cast
		Vertex vertex = (Vertex)obj;
		if(this.compareTo(vertex) != 0) return false;
		return true;
	}//End of equals method
}//End of class
