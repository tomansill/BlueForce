import java.awt.Canvas;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

		//Collect info
		if(graph != null){
			ArrayList<Point2D> vertices = new ArrayList<Point2D>();
			HashSet<ArrayList<Point2D>> edges = new HashSet<ArrayList<Point2D>>();
			for(Vertex vertex : graph.getListOfVertices()){
				Point2D coordinate = vertex.getCoordinate();
				vertices.add(coordinate);
				for(Vertex neighbor : graph.getListOfNeighbors(vertex)){
					ArrayList<Point2D> edge = new ArrayList<Point2D>();
					edge.add(vertex.getCoordinate());
					edge.add(neighbor.getCoordinate());
					if(!edges.contains(edge)){ 
						Collections.reverse(edge);
						if(!edges.contains(edge)){
							edges.add(edge);
						}
					}
				}
			}
			//Begin drawing
			for(ArrayList<Point2D> edge : edges) drawEdge(edge.get(0), edge.get(1), g2d);
			for(Point2D vertex : vertices) drawVertex(vertex, g2d);
		}
	}//End of update method
	private void drawVertex(Point2D coordinate , Graphics2D g){
		int x = (int) coordinate.getX() + viewportCoordinate[0];
		int y = (int) coordinate.getY() + viewportCoordinate[1];
		g.fillOval(x-10, y-10, 20, 20);
		g.setColor(Color.WHITE);
		g.fillOval(x-8, y-8, 16, 16);
		g.setColor(Color.BLACK);
	}//End of drawVertex method

	private void drawEdge(Point2D start, Point2D end, Graphics2D g){
		g.drawLine((int)start.getX()+viewportCoordinate[0], (int)start.getY()+viewportCoordinate[1], (int)end.getX()+viewportCoordinate[0], (int)end.getY()+viewportCoordinate[1]);
	}//End of drawEdge method

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.drawPanel(g);
	}//End of paintComponent method
}//End of class
