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

public class Vertex{
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
}//End of class
