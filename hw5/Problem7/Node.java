package Problem5;
import java.util.ArrayList;
import java.util.List;

public class Node {
	public List<Node> children = new ArrayList<>();
	private Integer value;
	
	public void addChild(Node value){
		children.add(value);	
	}
	public boolean checkEqual(Integer value){
		return this.value.intValue() == value.intValue();	
	}
	public Integer getValue(){
		return value;
			
	}
	public void setValue(Integer value){
		this.value = value;
	}
	public int getNumChildren(){
		return children.size();
	}
	public List<Node> getChildren(){
		
		return this.children;
		
	}
	
	
	
	
	
}