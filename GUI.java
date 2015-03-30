import java.io.File;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.HashMap;
//Remove later
import java.util.ArrayList;
import java.util.TreeMap;

public class GUI extends JFrame{
	private RendererPanel screen = null;
	//private ImageBuffer[] buffer = null;
	private byte bufferNumber = 0;
	private long time = System.currentTimeMillis();
	public GUI(){
		init(400,300);	
	}//End of default constructor
	private void init(int width, int height){
		//Initialization for JFrame
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Creates new JPanel
		Graph graph = null;
		try{
			graph = GraphReader.readGraph(new File("Graphs/p5.txt"));
		}catch(Exception e){
			System.out.println("There was a problem reading file! " + e.getMessage());
        }
		screen = new RendererPanel(graph);	
		this.add(screen);

		//Make JFrame visible
		this.setVisible(true);

		while(true){
			organize(graph);
			update();
		}
	}//End of init method

	private void organize(Graph graph){
		if(graph != null){
			//Build force table
			HashMap<Vertex, Point2D> forcetable = new HashMap<Vertex, Point2D>();
			for(Vertex vertex : graph.getListOfVertices()){
				Point2D.Float force = new Point2D.Float(0.0f,0.0f);
				//Apply repulsion force on all vertices
				for(Vertex otherVertex : graph.getListOfVertices()){
					if(!vertex.equals(otherVertex)){
						float xdist = (float)(vertex.getCoordinate().getX() - otherVertex.getCoordinate().getX());	
						float ydist = (float)(vertex.getCoordinate().getY() - otherVertex.getCoordinate().getY());	
						float forcex = 1;
						float forcey = 1;
						if(xdist != 0) forcex = 1/Math.abs(xdist);
						if(ydist != 0) forcey = 1/Math.abs(ydist);
						//Prevent excessive force - arbitary number
						if(forcex > 20) forcex = 20;
						if(forcey > 20) forcey = 20;
						//Appropraite direction
						if(xdist > 0) forcex *= -1;
						if(ydist > 0) forcey *= -1;
						System.out.println("vertex: " + vertex.getLabel() + " x: " + forcex + " y: " + forcey
										+ " xdist: " + xdist + " ydist: " + ydist);
						//force.x += forcex;
						//force.y += forcey;
					}
				}
				for(Vertex neighbor : graph.getListOfNeighbors(vertex)){
						float xdist = (float)(vertex.getCoordinate().getX() - neighbor.getCoordinate().getX());	
						float ydist = (float)(vertex.getCoordinate().getY() - neighbor.getCoordinate().getY());	
						float forcex = xdist*0.001f;
						float forcey = ydist*0.001f;
						force.x -= forcex;
						force.y -= forcey;
				}
				forcetable.put(vertex, force);
			}
			//Apply timestep and apply force on each vertex
			for(Vertex vertex: graph.getListOfVertices()){
				long elapsed = (System.currentTimeMillis() - time);
				if(elapsed < 16){
					//program is running too fast, slow down
					try{ Thread.sleep(16 - elapsed); }catch(Exception e){}
					elapsed = 16;
				}
				float timestep = 19/(elapsed);
				float x = ((float)forcetable.get(vertex).getX()) * timestep;
				float y = ((float)forcetable.get(vertex).getY()) * timestep;
				//System.out.println("timestep: " + timestep + " x: " + x + " y: " + y);
				vertex.move(x, y);
			}
		}
	}//End of organize method

	private void update(){
		screen.revalidate();
		screen.repaint();
		time = System.currentTimeMillis();
	}//End of update method

	public static void main(String[] args){
		new GUI();
	}//End of main function
}//End of class
