import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

	//	while(true){
	//		update();
	//	}
	}//End of init method

	private void update(){
		screen.revalidate();
		screen.repaint();
	}//End of update method

	public static void main(String[] args){
		new GUI();
	}//End of main function
}//End of class
