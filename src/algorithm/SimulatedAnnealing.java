package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tools.Graph;

public class SimulatedAnnealing {
	private Graph graph;
	private Graph.Solution solution;
	private int temperature = 10000;
	private final static float alpha = 0.95f;
	private final static int innerLoopTimes = 50000;
	private final static int outerLoopTimes = 100;
	
	
	public SimulatedAnnealing(String filePath) {
		this.graph = new Graph(filePath);
		this.solution = this.graph.new Solution(null);
	}
	
	public void run() {
		boolean flag = true;
		int count = 0;
		float preCost = graph.getAllCost(solution);
		float cost = 0.0f;
		
		while (count < this.outerLoopTimes) {
			if (!flag) count = 0;
			flag = true;
			for (int i = 0; i < this.innerLoopTimes; i++) {
				Graph.Solution newSolution = getNewSolution(solution);
				cost = graph.getAllCost(newSolution);
				if (cost < preCost || Math.random() > Math.exp((cost - preCost) / temperature)) {
					solution = newSolution;
					preCost = cost;
					flag = false;
				}
			}
			updateTemperature();
			if (flag) count++;
			this.print();
		}
	}
	
	private Graph.Solution getNewSolution(Graph.Solution src) {
		ArrayList<Graph.Solution> ret = new ArrayList<Graph.Solution>();
		Graph.Solution ret2 = src.clone();
		Graph.Solution ret3 = src.clone();
		Graph.Solution ret4 = src.clone();
		
		/*TODO define the modify operate*/
		Random rand = new Random();
		int[] line = ret1.getData();
		
		int u = 0, v = 0, w = 0, temp = 0;
		//switch(Math.abs(rand.nextInt()) % 4) {
		//	case 0:
				u = Math.abs(rand.nextInt()) % line.length;
				v = Math.abs(rand.nextInt()) % line.length;
				temp = line[u];
				line[u] = line[v];
				line[v] = temp;
		//		break;
		line = ret2.getData();		
		//	case 1:
				u = Math.abs(rand.nextInt()) % (line.length - 1);
				v = Math.abs(rand.nextInt()) % (line.length - u - 1) + u + 1;
				for (int i = u; i < (u + v) / 2; i++) {
					temp = line[i];
					line[i] = line[v - i + u];
					line[v - i + u] = temp;
				}
		//		break;
		line = ret3.getData();		
		//	case 2:
				u = Math.abs(rand.nextInt()) % line.length;
				v = Math.abs(rand.nextInt()) % line.length;
				temp = line[u];
				line[u] = line[v];
				line[v] = temp;
				
				u = Math.abs(rand.nextInt()) % line.length;
				v = Math.abs(rand.nextInt()) % line.length;
				temp = line[u];
				line[u] = line[v];
				line[v] = temp;
		//		break;
		line = ret3.getData();	
		//	case 3:
				u = Math.abs(rand.nextInt()) % (line.length - 2);
				v = Math.abs(rand.nextInt()) % (line.length - u - 2) + u + 1;
				w = Math.abs(rand.nextInt()) % (line.length - v - 1) + v + 1;
				int[] tempArr = Arrays.copyOfRange(line, u, v + 1);
				for (int i = u, j = v + 1; j < w + 1; i++, j++) {
					line[i] = line[j];
				}
				for (int i = w - v + u, j = 0; i < w + 1; i++, j++) {
					line[i] = tempArr[j];
				}
		//		break;
		}
		return ret;
	}
	
	private void updateTemperature() {
		temperature *= this.alpha;
	}
	
	private void print() {
		System.out.println(graph.getAllCost(solution) + ": " + solution.toString());
	}
	
	public static void main(String[] args) {
		SimulatedAnnealing test = new SimulatedAnnealing("data/a280.xml");
		test.run();
		/*Random rand = new Random();
		int[] line =  new int[5];
		for (int i = 0; i < 5; i++) {
			line[i] = i;
		}
		int temp = 0;
		int u = Math.abs(rand.nextInt()) % (line.length - 2);
		int v = Math.abs(rand.nextInt()) % (line.length - u - 2) + u + 1;
		int w = Math.abs(rand.nextInt()) % (line.length - v - 1) + v + 1;
		int[] tempArr = Arrays.copyOfRange(line, u, v + 1);
		for (int i = u, j = v + 1; j < w + 1; i++, j++) {
			line[i] = line[j];
		}
		for (int i = w - v + u, j = 0; i < w + 1; i++, j++) {
			line[i] = tempArr[j];
		}
		System.out.println("u: " + u + " v: " + v + " w: " + w);
		for(int i = 0; i < 5; i++) {
			System.out.print(line[i] + " ");
		}*/
	}
	
}
