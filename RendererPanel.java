import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
public class RendererPanel extends JPanel{
	private Graph graph = null;
	private int[] viewportCoordinate = {0,0}; 
	private float scale = 1.0f;
	public RendererPanel(){};
	public RendererPanel(Graph g){
		this.graph = g;
	}//End of constructor
	public void setGraph(Graph g){
		this.graph = g;
	}//End of setGraph method
	public Graph getGraph(){ return this.graph; }
	private void drawPanel(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		//Set some rendering attributes
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
							RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		g.setColor(Color.BLACK);
		//Get all data before drawing
		HashSet<ArrayList<Vector>> edgeList = new HashSet<ArrayList<Vector>>();
		ArrayList<Vector> vertices = new ArrayList<Vector>();
		for(String v : graph.getVertices()){
			Vertex ver = graph.getVertex(v);
			int[] vcoord = ver.getCoordinate();
			for(String neighbor : ver.getNeighbors()){
				Vertex neg = graph.getVertex(neighbor);	
				int[] coord = neg.getCoordinate();
			}//End of loop
		}//End of loop
		//Begin drawing
		for(String v : graph.getVertices()){
			Vertex ver = graph.getVertex(v);
			//Append the edges list
			drawVertex(ver, g2d);	
		}//End of loop.
	}//End of update method
	private void drawVertex(int x, int y, Graphics2D g){
		g.fillOval((int)(scale*coordinate[0]), (int)(scale*coordinate[1]), (int)(scale*20), (int)(scale*20));
	}//End of drawVertex method
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.drawPanel(g);
	}//End of paintComponent method
}//End of class
