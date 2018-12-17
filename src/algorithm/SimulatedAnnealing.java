package algorithm;

import tools.Graph;

public class SimulatedAnnealing {
	private Graph graph;
	private Graph.Solution preSolution;
	private Graph.Solution solution;
	private int temperature = 1000;
	
	
	public SimulatedAnnealing(String filePath) {
		this.graph = new Graph(filePath);
		this.solution = this.graph.new Solution(null);
		preSolution = solution.clone();
	}
	
	public void run() {
		
	}
	
	private void updateTemperature() {
		
	}
	
}
