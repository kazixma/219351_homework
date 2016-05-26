import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
public class HadoopHW5 {
	public static void main(String [] args){
		String filepath = "C:/Users/MAD/workspace/HW5/web-Google.txt";
		File file = new File(filepath);
		int totalNode = 5105039;
		String[] node = new String[totalNode];
		String[] nodeName = new String[totalNode];
		int[] nodeCount = new int[totalNode];
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\t");
				node[i] = parts[1];
				i++;
			}
			br.close();
		}
		catch(IOException e){

		}
		Arrays.sort(node);
		int singleNodeCount = 0;
		int runner = 0;
		String lastNode = " ";
		for(int j = 0; j < node.length ; j++){
			if(node[j].equals(lastNode)){
				nodeCount[runner-1] = ++singleNodeCount;
			}
			else{
				singleNodeCount = 1;
				lastNode = node[j];
				nodeName[runner] = node[j];
				nodeCount[runner] = singleNodeCount;
				runner++;
			}
		}
		for(int i = 0; i < runner ; i++){
			System.out.println(nodeName[i] + "\t" + nodeCount[i]);
		}

	}
}
