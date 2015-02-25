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
	public GUI(){
		init(400,300);	
	}//End of default constructor
	private void init(int width, int height){
		//Initialization for JFrame
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Creates new JPanel
		screen = new RendererPanel(new Graph(new File("Graphs/p5.txt")));	
		this.add(screen);

		//Make JFrame visible
		this.setVisible(true);
	}//End of init method

	private void update(){

	}//End of update method

	public static void main(String[] args){
		new GUI();
	}//End of main function
}//End of class
