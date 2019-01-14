package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Graph {
	
	private float[][] map;
	private int size;
	
	public Graph(float[][] src, int _size) {
		this.map = src.clone();
		this.size = _size;
	}
	
	public Graph(String filePath) {
		this.readFromXml(filePath);
	}
	
	public float getCost(int src, int dst) throws RuntimeException{
		if (src >= this.size || dst >= this.size) 
			throw new RuntimeException("out of the range of Graph: " + this.size);
		return map[src][dst];
	}
	
	public float getAllCost(Solution s) {
		float ret = 0.0f;
		for (int i = 0; i < this.size - 1; i++) {
			ret += this.map[s.line[i]][s.line[i + 1]];
		}
		ret += this.map[s.line[0]][s.line[Graph.this.size - 1]];
		return ret;
	}
	
	
	
	private void readFromXml(String filePath) {
		SAXReader reader = new SAXReader();
		InputStream in = null;
		Document dom = null;
		try {
			in = new FileInputStream(filePath);
			dom = reader.read(in);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		
		Element root = dom.getRootElement();
		Element graphNode = root.elements().get(5);
		List<Element> childNodes = graphNode.elements();
		
		this.size = childNodes.size();
		this.map = new float[size][size];
		
		for (int i = 0; i < childNodes.size(); i++) {
			List<Element> nodes = childNodes.get(i).elements();
			for (int j = 0; j < nodes.size(); j++) {
				Element node = nodes.get(j);
				map[i][Integer.parseInt(node.getStringValue())] = Float.parseFloat(node.attributeValue("cost"));
			}
			map[i][i] = -1;
		}
		
	}
	
	public int getSize() {
		return this.size;
	}
	
	public class Solution {
		private int[] line;
		
		public Solution() {
			this.line = new int[Graph.this.size];
			for (int i = 0; i < line.length; i++) {
				line[i] = i;
			}
			System.out.println("I'm ruby.");
		}
		
		public Solution(int[] src) {
			//random order
			if (src == null) {
				this.line = new int[Graph.this.size];
				
				int len = Graph.this.size;
				int[] source = new int[len];
				Random rand = new Random();

				for (int i = 0 ; i < Graph.this.size; i++) {
					source[i] = i;
				}
				for (int i = 0; i < Graph.this.size; i++) {
					int randInt = Math.abs(rand.nextInt() % len--);
					this.line[i] = source[randInt];
					source[randInt] = source[len];
				}
				return ;
			}
			//src order
			this.line = Arrays.copyOf(src, Graph.this.size);
		}
		
		public int[] getData() {
			return this.line;
		}
		
		public Solution clone() {
			Solution ret = new Solution(this.line);
			return ret;
		}
		
		public String toString() {
			String str = "(" + line[0];
			for (int i = 1; i < this.line.length; i++) {
				str += ", " + line[i];
			}
			str += ")";
			return str;
		}
		
		public boolean equals(Solution src) {
			if (this.line.length != src.line.length) return false;
			for (int i = 0; i < this.line.length; i++) {
				if (this.line[i] != src.line[i]) return false;
			}
			return true;
		}
	}
	
	public static void main(String[] args) {
		Graph g = new Graph("data/a280.xml");
	}

}
