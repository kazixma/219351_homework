package Problem5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import NeighborSeq.Node;

public class WebApp7 {
	public static Map<Integer, Node> allNodes; 
	public static Node getNode(int value){
		Integer key = new Integer(value);
		if (allNodes.containsKey(key)) {
			return allNodes.get(key);
		} else {
			Node newNode = new Node();
			newNode.setValue(key);
			allNodes.put(key, newNode);
			return newNode;
		}
	}
	public static void main(String [] args) throws IOException{
		allNodes = new HashMap<Integer, Node>(); 
		PrintWriter out = new PrintWriter("/Users/Mistral/wad/out3.txt", "UTF-8");
	    BufferedReader br = new BufferedReader(new FileReader("/Users/Mistral/Downloads/web-Google.txt"));
	    String line;
	    while((line = br.readLine()) != null){
	        String[] values = line.split("\t");
	        int from = Integer.parseInt(values[0]);
	        int to = Integer.parseInt(values[1]);
	        Node node = getNode(from);
	        node.addChild(getNode(to));
	    }
	    br.close();
	    for (Integer key : allNodes.keySet()) {
	    	Node node = allNodes.get(key);
	    	out.print(key + ": ");
	    	for(int i = 0 ; i < node.getNumChildren() ; i++){
	    		Node cache = node.children.get(i);
	    		out.print(cache.getValue() + ", ");
	    		for(int j = 0 ; j < cache.getNumChildren() ; j++){
	    			Node cache2 = cache.children.get(j);
	    			out.print(cache2.getValue() + ", ");
	    		}
	    	}
	    }
	    out.close();
	}
}
