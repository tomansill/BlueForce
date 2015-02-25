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
		g2d.setColor(Color.BLACK);

		//Begin drawing
		if(graph != null){
			for(String vertex : graph.getVertices()){
				System.out.println("Vertex: " + vertex);
				drawVertex(graph.getVertex(vertex), g2d);
			}//End of loop
		}
	}//End of update method
	private void drawVertex(Vertex v, Graphics2D g){
		g.fillOval((int)(scale*20), (int)(scale*20), (int)(scale*20), (int)(scale*20));
	}//End of drawVertex method
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.drawPanel(g);
	}//End of paintComponent method
}//End of class
