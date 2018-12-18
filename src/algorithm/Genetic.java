package algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import tools.Graph;

public class Genetic {
	
	private static final int totalNumberOfIndividuals = 100;
	private static final int numberOfReserved = 20;
	private static final int numberOfGeneration = 1000;
	private static final float ProbabilityOfMutation = 0.3f;
	
	private Graph graph;
	private Gen[] population;
	
	public Genetic(String filePath) {
		this.graph = new Graph(filePath);
		population = new Gen[Genetic.totalNumberOfIndividuals];
		
		for (int i = 0; i < Genetic.totalNumberOfIndividuals; i++) {
			population[i] = new Gen(this.graph, null);
		}
		this.sortPopulation();
	}
	
	public void run() {
		Random rand = new Random();
		for (int i = 0; i < Genetic.numberOfGeneration; i++) {
			for (int j = Genetic.numberOfReserved; j < Genetic.totalNumberOfIndividuals; j++) {
				if (i == Genetic.totalNumberOfIndividuals - 1 || Math.random() < Genetic.ProbabilityOfMutation) {
					int randNum = Math.abs(rand.nextInt()) % Genetic.numberOfReserved;
					population[j] = this.geneticMutation(population[randNum]);
				} else {
					int randf = Math.abs(rand.nextInt()) % Genetic.numberOfReserved;
					int randm;
					do {
						randm = Math.abs(rand.nextInt()) % Genetic.numberOfReserved;
					} while (randf == randm);
					
					Gen[] tempArr = this.geneticCrossover(population[randf], population[randm]);
					population[j++] = tempArr[0];
					population[j] = tempArr[1];
				}
			}
			this.sortPopulation();
			System.out.println(graph.getAllCost(population[0]) + ": " + population[0].toString());
		}
		
	}
	
	private Gen[] geneticCrossover(Gen father, Gen mother) {
		
		return null;
	}
	
	private Gen geneticMutation(Gen father) {
		Random rand = new Random();
		Gen ret = new Gen(this.graph, father.getData());
		int[] line = ret.getData();
		int u = 0, v = 0, w = 0, temp = 0;
		switch(Math.abs(rand.nextInt()) % 4) {
			case 0:
				u = Math.abs(rand.nextInt()) % line.length;
				v = Math.abs(rand.nextInt()) % line.length;
				temp = line[u];
				line[u] = line[v];
				line[v] = temp;
				break;
				
			case 1:
				u = Math.abs(rand.nextInt()) % (line.length - 1);
				v = Math.abs(rand.nextInt()) % (line.length - u - 1) + u + 1;
				for (int i = u; i < (u + v) / 2; i++) {
					temp = line[i];
					line[i] = line[v - i + u];
					line[v - i + u] = temp;
				}
				break;
				
			case 2:
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
				break;
		
			case 3:
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
				break;
		}
		ret.setCost(this.graph.getAllCost(ret));
		return ret;
	}
	
	private void sortPopulation() {
		Arrays.sort(this.population, new Comparator<Gen>() {
			@Override
			public int compare(Gen obj1, Gen obj2) {
				return (int) (obj1.getCost() - obj2.getCost());
			}
		});
	}
	
	public class Gen extends Graph.Solution {
		private float cost;
		
		public Gen(Graph g) {
			g.super();
			this.cost = g.getAllCost(this);
		}
		
		public Gen(Graph g, int[] src) {
			g.super(src);
			this.cost = g.getAllCost(this);
		}
		
		public void setCost(float _cost) {
			this.cost = _cost;
		}
		
		public float getCost() {
			return this.cost;
		}
	}
}