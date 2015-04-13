import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class GUI extends JFrame{
	private RendererPanel screen = null;
	//private ImageBuffer[] buffer = null;
	private byte bufferNumber = 0;
	private HashMap<Vertex, Point2D> velocityTable = new HashMap<Vertex, Point2D>();
	private long time = System.currentTimeMillis();
	private boolean simulation = true;
	private Graph graph = null;
	private Boolean alive = true;
	public GUI(Graph graph){
		this.graph = graph;
		init(800,640);
		screen.setGraph(graph);	
		for(Vertex vertex : graph.getListOfVertices()) 	velocityTable.put(vertex, new Point2D.Float(0.0f, 0.0f));
	}//End of default constructor
	public void run(){
		while(alive && (graph != null)){
			organize(graph);
			update();
			//System.out.print("\rrunning");
		}
		//System.out.println("\rend");
	}
	private void init(int width, int height){
		//Initialization for JFrame
		this.setSize(width, height);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e){
					alive = false;
				}
			});
		//Creates new JPanel
		Graph graph = null;
		screen = new RendererPanel(null);	
		this.add(screen);

		//Make JFrame visible
		this.setVisible(true);
	}//End of init method

	private void organize(Graph graph){
		if(graph != null){
			if(simulation){
				centerGraph(graph);
				HashSet<ArrayList<Vertex>> visitedPair = new HashSet<ArrayList<Vertex>>();
				HashSet<ArrayList<Vertex>> visitedEdge = new HashSet<ArrayList<Vertex>>();
				//Build force table
				HashMap<Vertex, Point2D> forceTable = new HashMap<Vertex, Point2D>();
				for(Vertex vertex : graph.getListOfVertices()){
					Point2D.Float forceVector = new Point2D.Float(0.0f,0.0f);
					//Apply repulsion force on all vertices
					for(Vertex otherVertex : graph.getListOfVertices()){
						if(!vertex.equals(otherVertex)){
							float xdist = (float)(otherVertex.getCoordinate().getX() - vertex.getCoordinate().getX());	
							float ydist = (float)(otherVertex.getCoordinate().getY() - vertex.getCoordinate().getY());	
							float distance = (float)Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
							float force = (float)(10000/Math.pow(distance, 2));
							float forcex = ((graph.getListOfNeighbors(vertex).size())*xdist*force)/distance;
							float forcey = ((graph.getListOfNeighbors(vertex).size())*ydist*force)/distance;
							if(xdist < 0.01f && xdist > -0.01f){
								forcex = 1.1f;
								if(Math.random() > 0.5f) forcex *= -1.0f;
							}
							if(ydist < 0.01f && ydist > -0.01f){
								forcey = 1.0f;
								if(Math.random() > 0.5f) forcey *= -1.0f;
							}
							forceVector.x -= forcex;
							forceVector.y -= forcey;
						}
					}
					for(Vertex neighbor : graph.getListOfNeighbors(vertex)){
						float xdist = (float)(vertex.getCoordinate().getX() - neighbor.getCoordinate().getX());	
						float ydist = (float)(vertex.getCoordinate().getY() - neighbor.getCoordinate().getY());	
						float forcex = xdist*0.1f;
						float forcey = ydist*0.1f;
						forceVector.x -= forcex;
						forceVector.y -= forcey;
					}
					forceTable.put(vertex, forceVector);
				}
				//Apply timestep and apply force on each vertex
				boolean stopsim = true;
				for(Vertex vertex: graph.getListOfVertices()){
					//Change velocity
					Point2D force = forceTable.get(vertex);
					Point2D velocity = velocityTable.get(vertex);
					float velox = (float)(velocity.getX() + force.getX());
					float veloy = (float)(velocity.getY() + force.getY());
					//Apply  friction
					float frictionCoefficient = 0.05f;
					velox = velox - (velox * frictionCoefficient);
					veloy = veloy - (veloy * frictionCoefficient);
					//if velocity is too small, just stop the velocity, dont let it move more
					if((velox > 0.6f || velox < -0.6f) || (veloy > 0.6f || veloy < -0.6f)) stopsim = false;
					velocity = new Point2D.Float(velox, veloy);

					long elapsed = (System.currentTimeMillis() - time);
					if(elapsed < 7){ //over 144fps
						//program is running too fast, slow down
						try{ Thread.sleep(7); }catch(Exception e){}
						elapsed = 7;
					}
					double speed = 1.0;
					double timestep = (elapsed/1000.0f)*speed;
					float x = (float)(velocity.getX() * timestep);
					float y = (float)(velocity.getY() * timestep);
					vertex.move(x, y);
					velocityTable.put(vertex, velocity);
				}
				//if(stopsim) simulation = false;
			}
		}
	}//End of organize method

	private void centerGraph(Graph graph){
		if(graph != null){
			float minX = Float.POSITIVE_INFINITY;
			float maxX = Float.NEGATIVE_INFINITY;
			float minY = Float.POSITIVE_INFINITY;
			float maxY = Float.NEGATIVE_INFINITY;
			for(Vertex vertex : graph.getListOfVertices()){
				Point2D coord = vertex.getCoordinate();
				minX = Math.min(minX, (float)coord.getX());
				minY = Math.min(minY, (float)coord.getY());
				maxX = Math.max(maxX, (float)coord.getX());
				maxY = Math.max(maxY, (float)coord.getY());
			}
			float length = maxX - minX;
			float height = maxY - minY;
			for(Vertex vertex : graph.getListOfVertices()) vertex.move(-(minX+(length/2)), -(minY+(height/2)));
		}
	}

	private void update(){
		screen.revalidate();
		screen.repaint();
		time = System.currentTimeMillis();
	}//End of update method
}//End of class
