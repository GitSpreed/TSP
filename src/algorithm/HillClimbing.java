package algorithm;

import java.util.Arrays;
import java.util.Random;

import tools.Graph;

public class HillClimbing {
	
	private Graph graph;
	private Graph.Solution solution;
	
	public HillClimbing(String filePath) {
		this.graph = new Graph(filePath);
		this.solution = graph.new Solution(null);
	}
	
	public float run() {
		boolean flag = true;
		int n = 0;
		while (flag) {
			flag = false;
			
			Graph.Solution[] mins = tryMin();
			float minCost = graph.getAllCost(solution);
			int index = -1;
			for (int i = 0; i < graph.getSize(); i++) {
				if (graph.getAllCost(mins[i]) < minCost) {
					index = i;
					minCost = graph.getAllCost(mins[i]);
					flag = true;
				}
			}
			if (flag) {
				solution = mins[index];
				//System.out.println(n++ + ": " + graph.getAllCost(solution) + " -> " + solution.toString());
			}
			
		}
		return graph.getAllCost(solution);
	}
	
	private Graph.Solution[] tryMin() {
		Graph.Solution[] ret = new Graph.Solution[graph.getSize()];
		for (int i = 0; i < graph.getSize(); i++) {
			/*ret[i] = this.solution.clone();
			int[] data = ret[i].getData();
			int temp = data[i];
			data[i] = data[(i + 1) % data.length];
			data[(i + 1) % data.length] = temp;*/
			ret[i] = getNewSolution(solution);
		}
		return ret;
	}
	
	private Graph.Solution getNewSolution(Graph.Solution src) {
		Graph.Solution[] ret = new Graph.Solution[4];
		for (int i = 0; i < 4; i++) {
			ret[i] = src.clone();
		}
		//Graph.Solution ret = src.clone();
		
		Random rand = new Random();
		int[] line = ret[0].getData();
		
		int u = 0, v = 0, w = 0, temp = 0;
		//switch(Math.abs(rand.nextInt()) % 4) {
		//	case 0:
				u = Math.abs(rand.nextInt()) % line.length;
				v = Math.abs(rand.nextInt()) % line.length;
				temp = line[u];
				line[u] = line[v];
				line[v] = temp;
		//		break;
		line = ret[1].getData();		
		//	case 1:
				u = Math.abs(rand.nextInt()) % (line.length - 1);
				v = Math.abs(rand.nextInt()) % (line.length - u - 1) + u + 1;
				for (int i = u; i < (u + v) / 2; i++) {
					temp = line[i];
					line[i] = line[v - i + u];
					line[v - i + u] = temp;
				}
		//		break;
		line = ret[2].getData();		
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
				//break;
		line = ret[3].getData();	
			//case 3:
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
				//break;
		//}
		float minValue = this.graph.getAllCost(ret[0]);
		int minIndex = 0;
		for (int i = 1; i < 4; i++) {
			float curValue = this.graph.getAllCost(ret[i]);
			if (curValue < minValue) {
				minValue = curValue;
				minIndex = i;
			}
		}
		return ret[minIndex];
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			HillClimbing test = new HillClimbing("data/bier127.xml");
			System.out.print(test.run() + ", ");
		}
		
	}

}
