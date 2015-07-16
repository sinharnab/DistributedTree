package simulationDistHierarchy;

import java.util.ArrayList;
import java.util.List;

public class Node {

	/**
	 * @param args
	 */
	String nodeId;
	
	String parentId;
	String parentRemark;
	List<String> nodeSiblings;
	
	String ancestorId;
	String ancestorRemark;
	List<String> ancestorChildren;
	
	boolean status = false;
	
	void printNode(){
		System.out.println("Node => " + nodeId);
		System.out.println("Parent => " + parentId);
		System.out.println("Siblings => " + nodeSiblings);
		//for(String sibl : nodeSiblings)
			//System.out.print(sibl + "    ");
				
		System.out.println("Ancestor => " + ancestorId);
		System.out.println("Ancestor Children => " + ancestorChildren);
		//for(String ansChildren : ancestorChildren)
			//System.out.print(ansChildren + "    ");

		System.out.println();
	}
	
	public Node(String id) {
		nodeId = padString(id, 6);
		//nodeId = nodeId.replace(' ', '*');
		//nodeId = id;
		//String.format("%-6s", nodeId);
		//this.leaf = leaf;
	}
	
	String padString(String s, int n){
		String temp = s;
		for(int i=s.length(); i<n; i++)
			temp += " ";
		return temp;
	}
	
	String getId(){
		return nodeId;
	}
	
	String getParentId(){
		return parentId;
	}
	
	String getAncestorId(){
		return ancestorId;
	}
	
	boolean getStatus(){return status;}
	
	void setParentId(String parId){
		parentId = parId;
	}
	
	void setParentRemark(String parRemark){
		parentRemark = parRemark;
	}
	
	void setStatus(boolean stat){
		status = stat;
	}
	
	/**
	 * reads the structure of the ancestor part and creates a SubTree
	 * WARNING : it returns NULL if the node contains no ancestor information
	 * @return SubTree structure of the ancestor and its children described in the Node
	 */
	SubTree buildAncestorSubTree(){
		SubTree ancestor = null;
		//if(!this.getAncestorId().equalsIgnoreCase("null")){
		if(this.getAncestorId() != null){
			ancestor = new SubTree(ancestorId, ancestorChildren.toArray(new String[0]));
		}
		return ancestor;
	}
	
	/**
	 * reads the structure of the parent and sibling part and creates a SubTree
	 * WARNING : it returns NULL if the node contains no parent information
	 * @return SubTree structure of the parent, Node and siblings described in the Node
	 */
	SubTree getParentSubTree(){
		SubTree parent = null;
		if(!this.getParentId().equalsIgnoreCase(null)){
			List<String> temp = new ArrayList<String>(nodeSiblings);
			temp.add(nodeId);
			parent = new SubTree(parentId, temp.toArray(new String[0]));
		}
		return parent;
	}
	
	SubTree buildParentSubTree(){
		SubTree temp = null;
		if(parentId.equalsIgnoreCase(null)){
			temp = new SubTree(nodeId);
			temp.root.setStatus(true);

		} else{
			temp = new SubTree(parentId);
			SubTree child = new SubTree(nodeId);
			child.root.setStatus(true);
			temp.addChild(child);
			SubTree x = null;
			for(String sibId : nodeSiblings){
				x = new SubTree(sibId);
				temp.addChild(x);
			}
		}
		return temp;
	}
	
	void addSiblingId(String sibId){
		if(nodeSiblings == null)
			nodeSiblings = new ArrayList<String>();
		nodeSiblings.add(sibId);
	}
	
	void setAncestorId(String ancesId){
		ancestorId = ancesId;
	}
	
	void setAncestorRemark(String ancesRemark){
		ancestorRemark = ancesRemark;
	}
	
	void addAncestorChildrenId(String ancesId){
		if(ancestorChildren == null)
			ancestorChildren = new ArrayList<String>();
		ancestorChildren.add(ancesId);
	}

	
	List<SubTree> addTagToBuildTree(List<SubTree> myList){
		
		//List<SubTree> myList = new ArrayList<SubTree>();
		SubTree ancest = this.buildAncestorSubTree();
		//ancest.arrangeSubTree(myList);
		if(ancest != null){
			
			System.out.println("\n>>>>> List before adding ancestor \n");
			for(int i=0; i<myList.size(); i++)
				myList.get(i).print();
			
			myList = ancest.arrangeSubTreeList(myList);

		}
		//SubTree paren = this.getParentSubTree();
		SubTree paren = this.buildParentSubTree();
		//paren.arrangeSubTree(myList);
		if(paren != null){
			
			System.out.println("\n>>>>> List before adding node subtree \n");
			for(int i=0; i<myList.size(); i++)
				myList.get(i).print();
			
			myList = paren.arrangeSubTreeList(myList);
		}

		//myList.get(0).print();
		
		/*
		 * this loop can be commented to avoid printing the intermediate list
		 */
//		for(int i=0; i<myList.size(); i++){
//			myList.get(i).print();
			//System.out.println(myList.get(i).checkTree());
//		}
		
		return myList;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
