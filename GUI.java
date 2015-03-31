import java.io.File;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.HashMap;

public class GUI extends JFrame{
	private RendererPanel screen = null;
	//private ImageBuffer[] buffer = null;
	private byte bufferNumber = 0;
	private long time = System.currentTimeMillis();
	public GUI(Graph graph){
		init(400,300);
		screen.setGraph(graph);	
		while(true){
			organize(graph);
			update();
		}
	}//End of default constructor
	private void init(int width, int height){
		//Initialization for JFrame
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Creates new JPanel
		Graph graph = null;
		screen = new RendererPanel(null);	
		this.add(screen);

		//Make JFrame visible
		this.setVisible(true);
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
						float forcex = 1.0f;
						float forcey = 1.0f;
						if(xdist != 0) forcex = 1/xdist;
						if(ydist != 0) forcey = 1/ydist;
						System.out.println("forcex: " + forcex + "\tforcey: " + forcey);
						force.x += forcex;
						force.y += forcey;
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
				if(elapsed == 0){
					//program is running too fast, slow down
					try{ Thread.sleep(1); }catch(Exception e){}
					elapsed = 1;
				}
				float timestep = 16/(elapsed);
				float x = ((float)forcetable.get(vertex).getX()) * timestep;
				float y = ((float)forcetable.get(vertex).getY()) * timestep;
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
		try{
			//Graph graph = GraphReader.readGraph(new File("Graphs/p5.txt"));
			Graph graph = GraphBuilder.buildPathGraph(3);
			new GUI(graph);
		}catch(Exception e){
			System.out.println("There was a problem reading file! " + e.getMessage());
		}
	}
}//End of class
