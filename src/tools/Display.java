package tools;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display {
	
	private static final int width = 400; 
	private static final int height = 400; 
	private JFrame jframe;
	private TSPGraph graph;
	private int[] px, py;
	
	public Display(String coordinate) {
		jframe =  new JFrame();
		jframe.setVisible(true);
		graph = new TSPGraph(coordinate);
	}
	
	public void draw(Graph.Solution s) {
		
	}
	
	class TSPGraph extends JPanel {
		
		int[] px, py;
		private Graph.Solution solution = null;
		
		protected TSPGraph(String coordinate) {

		}
		
		protected void setSolution(Graph.Solution s) {
			this.solution = s;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
		}
		
	}
}
