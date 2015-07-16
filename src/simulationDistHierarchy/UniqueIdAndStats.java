package simulationDistHierarchy;

import java.util.List;
import java.util.Random;

public class UniqueIdAndStats {

	static int identifier = 0;
	
	static double leavesCount = 0;
	static double selfETcount = 0;
	static double ancestorETcount = 0;
	static int depth = 1;
	static int children = 2;
	
	static void resetId(){
		identifier = 0;
	}
	
	static void resetId(int n){
		identifier = n;
	}
	
	static int getId(){
		return identifier++;
	}
	
	static void resetStats(){
		leavesCount = 0;
		selfETcount = 0;
		ancestorETcount = 0;
	}
	
	static void setTreeParams(int depth, int children){
		UniqueIdAndStats.depth = depth;
		UniqueIdAndStats.children = children;
	}
	
	static void countStats(SubTree myTree){
		List<Node> leaves = myTree.getLeafNodes();
		leavesCount = leaves.size();
		for(Node leaf : leaves){
			if(leaf.nodeId != null)
				selfETcount++;
			if(leaf.parentId != null)
				selfETcount++;
			if(leaf.nodeSiblings != null)
				selfETcount += leaf.nodeSiblings.size();
			if(leaf.ancestorId != null)
				ancestorETcount++;
			if(leaf.ancestorChildren != null)
				ancestorETcount += leaf.ancestorChildren.size();
		}
	}
	
	static void printStats(){
		System.out.println("Depth " + depth);
		System.out.println("Children " + children);
		System.out.println("#Nodes in tree " + ((Math.pow(children, (depth+1))-1)/(children-1)));
		System.out.println("#Leaves " + leavesCount);
	//	System.out.println("#Ratio (Nodes/Leaves) " + (((Math.pow(children, (depth+1))-1)/(children-1))/leavesCount));
		System.out.println("#selfETcount " + selfETcount);
	//	System.out.println("#Average selfETcount " + selfETcount/leavesCount);
		System.out.println("#ancestorETcount " + ancestorETcount);
	//	System.out.println("#Average ancestorETcount " + ancestorETcount/leavesCount);
		System.out.println("#IDs " + (selfETcount + ancestorETcount));
	//	System.out.println("#Average IDs per leaf " + ((selfETcount/leavesCount)+(ancestorETcount/leavesCount)));
	}
	
	static String[] getLetterArray(int n){
		String[] temp0 = new String[n];
		char[] temp = new char[n];
		for(int i=0; i<n; i++){
			String id = "";
			for(int j=0; j<10; j++){
				Random rand = new Random();
				//int prob1 = rand.nextInt(26);
				//int prob2 = rand.nextInt(26);
				//temp0[i] = "" + ((char) (prob1 + 65)) + ((char) (prob2 + 65));
				int prob = rand.nextInt(26);
				id += ((char) (prob + 65));
				//temp0[i] = "" + ((char) (prob + 65));
				temp[i] = (char) (prob + 65);
			}
			temp0[i] = id;
		}
		return temp0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
