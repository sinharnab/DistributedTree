package simulationDistHierarchy;

/**
 * THIS IS THE MAIN FILE TO BE RUN FOR THE SIMULATION
 */

import java.io.BufferedReader;
//import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;

//import com.clarkparsia.sparqlowl.parser.antlr.SparqlOwlParser.booleanLiteral_return;

//import sun.awt.SubRegionShowable;
//import sun.security.x509.UniqueIdentity;

public class SubTree {

	/**
	 * @param args
	 */
//	static Node head = null;
	Node root;
	List<SubTree> children;
	
/*	SubTree(Node n){
		root = n;
		children = new ArrayList<SubTree>();
	}*/
	
	SubTree(String id){
		root = new Node(id);
		children = null;
	}
	
	SubTree(String id, String[] myChildren){
		this(id);
		if(children == null)
			children = new ArrayList<SubTree>();
		for(int i=0; i < myChildren.length; i++)
			this.children.add(new SubTree(myChildren[i]));
	}
	
	SubTree(String id, String[] myChildren, SubTree[] myChildrenSubtree){
		this(id,myChildren);
		for(int i=0; i<myChildrenSubtree.length; i++)
			this.addChild(myChildrenSubtree[i]);
	}
	
	SubTree(String id, SubTree[] myChildrenSubtree){
		this(id);
		for(int i=0; i<myChildrenSubtree.length; i++)
			this.addChild(myChildrenSubtree[i]);
	}
	
	SubTree(String id, List<SubTree> nodeChildren){
		this(id);
		this.addChildren(nodeChildren);
	}
	
/*	static Node getHead() throws NullPointerException{
		return SubTree.head;
	}
	
	static void setHead(Node headNode){
		SubTree.head = headNode;
	}*/
	
	Node getRoot(){
		return this.root;
	}
	
	boolean addChild(SubTree childSubTree){
		if(children == null)
			children = new ArrayList<SubTree>();
		return children.add(childSubTree);
	}
	
	boolean addChildren(List<SubTree> childrenSubTree){
		if(children == null)
			children = new ArrayList<SubTree>();
		return children.addAll(childrenSubTree);
	}
	
	void printTraverseTree(){
		System.out.println(this.root.getId() + "\t");
		if(!this.isLeaf()){
			for(SubTree child : this.children)
				child.printTraverseTree();
		}
	}
	
	void printTreeNodes(){
		this.root.printNode();
		if(!this.isLeaf()){
			for(SubTree child : this.children)
				child.printTreeNodes();
		}
	}
	
	void populateParentSiblingId(){
		String myId = this.root.getId();
		if(!this.isLeaf()){
			for(SubTree child : this.children){
				child.root.setParentId(myId);
				child.root.setParentRemark(myId.toString());//adding parentId as remark to test 
				for(SubTree sibling : this.children){
					if(child.root.getId().equalsIgnoreCase(sibling.root.getId())){
					} else{
						child.root.addSiblingId(sibling.root.getId());
					}
				}
				child.populateParentSiblingId();
			}
		}
	}
	
	//this function has problem...
	void populateGrandChildrenId(){
		String myId = this.root.getId();
		if(!this.isLeaf()){
			for(SubTree child : this.children){
				if(!child.isLeaf()){
					for(SubTree gchild : child.children){
						gchild.root.setAncestorId(myId);
						gchild.root.setAncestorRemark(myId.toString());
						for(SubTree gsibling : child.children){
							if(gchild.root.getId().equalsIgnoreCase(gsibling.root.getId())){
							} else{
								gchild.root.addAncestorChildrenId(gsibling.root.getId());
							}
						}
						//gchild.populateGrandChildrenId();
					}
				}
				child.populateGrandChildrenId();
			}
		}
	}
	
	boolean searchTree(Node searchNode){
		boolean found = false;
		if(this.root.getId().equalsIgnoreCase(searchNode.getId()))
			found = found || true;
	//		return true;
		if(!this.isLeaf())
			for(SubTree child : this.children)
				found = found || child.searchTree(searchNode);
		return found;
	}
	
    public void print() {
        print("", true);
    }
	
    List<String> createNodeList(){
    	List<String> myList = new ArrayList<String>();
    	if(this.root != null)
    		myList.add(this.root.getId());
		if(!this.isLeaf())
			for(SubTree child : this.children)
				myList.addAll(child.createNodeList());
		return myList;
    }
    
    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.root.getId() + this.root.getStatus());
        if(children != null){
            for (int i = 0; i < children.size() - 1; i++){
                children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
            }
            if(children.size() >= 1){
                children.get(children.size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
            }
        }
    }
    
    public void printParentId() {
    	printParentId("", true);
    }
    
    private void printParentId(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.root.getParentId());
        if(children != null){
            for (int i = 0; i < children.size() - 1; i++){
                children.get(i).printParentId(prefix + (isTail ? "    " : "│   "), false);
            }
            if(children.size() >= 1){
                children.get(children.size() - 1).printParentId(prefix + (isTail ?"    " : "│   "), true);
            }
        }
    }
    
    public void printAncestorId() {
    	printAncestorId("", true);
    }
    
    private void printAncestorId(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.root.getAncestorId());
        if(children != null){
            for (int i = 0; i < children.size() - 1; i++){
                children.get(i).printAncestorId(prefix + (isTail ? "    " : "│   "), false);
            }
            if(children.size() >= 1){
                children.get(children.size() - 1).printAncestorId(prefix + (isTail ?"    " : "│   "), true);
            }
        }
    }
    
    /**
     * to set parent, siblings, ancestor and ancestor children for each Node 
     */
    public void distributeAncestors(){
    	distributeAncestors(null);
    }

    /*
        private void distributeAncestors(Stack<SubTree> myAncestors){
    	if(myAncestors == null)
    		myAncestors = new Stack<SubTree>();
    	if(isLeaf()){}
    	else{
    		SubTree mySelf = this;
    	//	this.root.printNode();
    		if(countChildrenAsLeaf() < 1)
    			myAncestors.push(mySelf);
    		for(SubTree child : this.children){
    			if(child.isLeaf()){
    				if(myAncestors.isEmpty()){
    					//child.root.setParentId();
    					//set the siblings and parent field of the child
    					child.root.setParentId(mySelf.root.getId());
    					for(SubTree sibl : mySelf.children)
    						if(!child.root.getId().equalsIgnoreCase(sibl.root.getId())){
    							child.root.addSiblingId(sibl.root.getId());
    						}
    				} else{
    					//add parent, siblings, ancestor and ancestor children to child
    					child.root.setParentId(mySelf.root.getId());
    					for(SubTree sibl : mySelf.children)
    						if(!child.root.getId().equalsIgnoreCase(sibl.root.getId())){
    							child.root.addSiblingId(sibl.root.getId());
    						}
    					SubTree myAncest = myAncestors.pop();
    					child.root.setAncestorId(myAncest.root.getId());
    					for(SubTree AncesChildren : myAncest.children)
    						child.root.addAncestorChildrenId(AncesChildren.root.getId());
    				}
    			} else{
    				child.distributeAncestors(myAncestors);
    			}
    		}
    	}
    }*/
    
    /**
     * sets the parent, sibling information of the leaf nodes as well adds an ancestor information into the leaves
     * @param myAncestors a stack of type {@code SubTree}
     */
    private void distributeAncestors(Stack<SubTree> myAncestors){
    	if(myAncestors == null)
    		myAncestors = new Stack<SubTree>();
    	if(isLeaf()){}
    	else{
    		SubTree mySelf = this;
    		//	this.root.printNode();
    		//if(countChildrenAsLeaf() < 1)
    		//	myAncestors.push(mySelf);
    		for(SubTree child : this.children){
    			if(child.isLeaf()){
					//set the siblings and parent field of the child
					child.root.setParentId(mySelf.root.getId());
					for(SubTree sibl : mySelf.children)
						if(!child.root.getId().equalsIgnoreCase(sibl.root.getId())){
							child.root.addSiblingId(sibl.root.getId());
						}
    				/*if(myAncestors.isEmpty()){
    					//child.root.setParentId();
    					//set the siblings and parent field of the child
    					child.root.setParentId(mySelf.root.getId());
    					for(SubTree sibl : mySelf.children)
    						if(!child.root.getId().equalsIgnoreCase(sibl.root.getId())){
    							child.root.addSiblingId(sibl.root.getId());
    						}
    				} else{*/
    				if(!myAncestors.isEmpty()){
    					//add parent and siblings to child
    					/*child.root.setParentId(mySelf.root.getId());
    					for(SubTree sibl : mySelf.children)
    						if(!child.root.getId().equalsIgnoreCase(sibl.root.getId())){
    							child.root.addSiblingId(sibl.root.getId());
    						}*/

    					//add ancestor and ancestor children to child
    					SubTree myAncest = myAncestors.pop();
    					child.root.setAncestorId(myAncest.root.getId());
    					for(SubTree AncesChildren : myAncest.children)
    						child.root.addAncestorChildrenId(AncesChildren.root.getId());
    				}
    			} else{
//    				if(allChildrenAreLeaf() == false)
    					myAncestors.push(mySelf);
    				child.distributeAncestors(myAncestors);
    			}
    		}
    	}
    }
    
    List<Node> getLeafNodes(){
    	List<Node> leaves =  new ArrayList<Node>();
    	
    	if(this.isLeaf())
    		leaves.add(this.root);
    	else{
    		for(SubTree child : this.children)
    			leaves.addAll(child.getLeafNodes());
    	}
    	return leaves;
    }
	
	int countNodes(){
		int count = 1;
		if(!this.isLeaf()){
			for(SubTree child : this.children)
				count += child.countNodes();
		}
		return count;
	}
	
	int countChildrenAsLeaf(){
		int count = 0;
		if(!this.isLeaf())
			for(SubTree child : this.children)
				if(child.isLeaf())
					count++;
		return count;
	}
	
	boolean allChildrenAreLeaf(){
		
		boolean status = true;
		//int count = 0;
		if(!this.isLeaf())
			for(SubTree child : this.children)
				if(!child.isLeaf())
					status = false;
		return status;
	}
	
	boolean isLeaf(){
		if(children != null && !children.isEmpty())
			return false;
		else
			return true;
	}
	
	void makeTree(List<Node> theTags){
		
		List<SubTree> myList = new ArrayList<SubTree>();
		for(Node tag : theTags){
			SubTree ancest = tag.buildAncestorSubTree();
			ancest.arrangeSubTree(myList);
			SubTree paren = tag.getParentSubTree();
			paren.arrangeSubTree(myList);
		}
		myList.get(0).print();
	}
	
	List<SubTree> arrangeSubTreeList(List<SubTree> myTreeList){
		/**
		 * searching for trees in the list that could be added under the tag subtree
		 */
		//CONSIDER IF CHILDREN COULD BE NULL!!!!
		for(int i=0; i<children.size(); i++){
			String childTID = children.get(i).root.getId();
			for(int j=0; j<myTreeList.size(); j++){
				String head = myTreeList.get(j).root.nodeId;
				if(head.equalsIgnoreCase(childTID)){
					children.remove(i);
					children.add(i, myTreeList.get(j));//children.add(0, myTreeList.get(j));
					myTreeList.remove(j);
					j--;
				}
			}
		}

		/**
		 * inserting the tag subtree in the list
		 * either at the bottom of a tree in the list or at the end of the list
		 */
		boolean subTreeFound = false;
		int pos = -1;

		for(int i=0; i<myTreeList.size(); i++){
			//SubTree partial = myTreeList.get(i);
			if(myTreeList.get(i).searchTree(this.root) == true){
				subTreeFound = true;
				pos = i;
			}
		}
		
		if(subTreeFound == true){//there exists a partial tree in the list that can accomodate the tag subtree under it
			myTreeList.get(pos).insertInTree(this);
		} else{
			myTreeList.add(this);
		}
		
		return myTreeList;
	}
	
	boolean insertInTree(SubTree sTree){
		boolean inserted = false;
/*		if(this.root.getId().equalsIgnoreCase(searchNode.getId()))
			found = found || true;
	//		return true;
		if(!this.isLeaf())
			for(SubTree child : this.children)
				found = found || child.searchTree(searchNode);*/
					
					
		if(this.root.getId().equalsIgnoreCase(sTree.root.getId())){
			inserted = inserted || true;
			//CHECK REQUIRED FOR NULL CHILDREN POINTERS
			for(int i=0; this.children != null && i<this.children.size(); i++){
				for(int j=0; j<sTree.children.size(); j++){
					if(this.children.get(i).root.nodeId.equalsIgnoreCase(sTree.children.get(j).root.nodeId)){
						//the status of the nodes in the trees of the list are updated from the tag subtree as they are added
						this.children.get(i).root.setStatus(this.children.get(i).root.getStatus() || sTree.children.get(j).root.getStatus());
						sTree.children.remove(j);
						j--;
					}
				}
			}
/*			for(int i=0; i<this.children.size(); i++){
				for(int j=0; j<sTree.children.size(); j++){
					if(this.children.get(i).root.nodeId.equalsIgnoreCase(sTree.children.get(j).root.nodeId)){
						this.addChildren(sTree.children);
					}
				}
			}*/
			
			//this line is added for resiliency. In case the data from tags before had incomplete data about nodes
			this.addChildren(sTree.children);
		} else{
			if(!this.isLeaf())
				for(int i=0; i<children.size(); i++){
					inserted = inserted || children.get(i).insertInTree(sTree);
				}
		}
		return inserted;
	}
	
	void arrangeSubTree(List<SubTree> myTreeList){
		
		boolean rootFound = false;
		SubTree rootPos = null;
		int pos = 0;
		for(; pos<myTreeList.size(); pos++)
			if((rootPos = myTreeList.get(pos).getNodeFromTree(getRoot())) != null){
				rootFound = true;//the root node of the concerned subtree exists in the list
				break;
			}
		if(rootFound == true){
			//replace(rootPos, this);
			rootPos = this;
		}else{
			myTreeList.add(this);
		}
		
		for(SubTree childT : children){
/*			if(childT.isLeaf())
				continue;*/
			boolean childFound = false;
			int index = 0;
			for(; index<myTreeList.size(); index++){
				if(childT.root.getId().equalsIgnoreCase( myTreeList.get(index).root.nodeId )){
					//replace(childT, myTreeList.get(index));
					childT = myTreeList.get(index);
					myTreeList.remove(index);
					continue;
				}
			}
		}
	}
	
	/**
	 * SubTree b is replaced in place of SubTree a
	 * @param a
	 * @param b
	 */
	void replace(SubTree a, SubTree b){
		a = b;
	}
	
	/**
	 * 
	 * @param searchNode
	 * @return null if searchNode not found in the SubTree else returns the pointer to the SubTree
	 */
	SubTree getNodeFromTree(Node searchNode){
		boolean found = false;
		SubTree result = null;
		if(this.root.getId().equalsIgnoreCase(searchNode.getId())){
			found = found || true;
			result = this;
		}
	//		return true;
		if(!this.isLeaf())
			for(SubTree child : this.children){
				SubTree temp = null;
				if((temp = child.getNodeFromTree(searchNode)) != null)
						result = temp;
			}
		return result;
	}
	
	boolean checkTree(){
		boolean status = true;
		if(this.isLeaf()){
			status = status && this.root.getStatus();
		} else{
			for(SubTree child : this.children)
				status = status && child.checkTree();
		}
		return status;
	}
	
	/**
	 * Generates a tree with unique numeric node id and has option to generate complete tree
	 * @param depth of the tree to be generated
	 * @param children
	 * @param option true if want to generate complete tree with exact depth and children
	 * @return the random generated subtree
	 */
	SubTree generateTreeWithIntId(int depth, int children, boolean option){
		UniqueIdAndStats.setTreeParams(depth, children);
		return generateTreeWithIntId(depth, children, 1, option);
	}
	
	private SubTree generateTreeWithIntId(int depth, int children, int counterId, boolean option){
/*		if(depth <1)
			return null;*/
		
		SubTree temp = null;
		//UUID iden = UUID.randomUUID();
		
		if(this.root == null){
		//	UUID iden = UUID.randomUUID();
		//	String name = iden.toString().substring(26, 30);
		//	String name = Integer.toString(counterId++);
			String name = Integer.toString(UniqueIdAndStats.getId());
			temp = new SubTree(name);
		} else{
			temp = this;
		}

		int numChildren = 0;
		if(option == false){
			do{
				Random rand = new Random();
				numChildren = rand.nextInt(children+1);//number of children i.e. (leaves + internal nodes) between 0 and children
			} while(numChildren == 1);
		}else{
			numChildren = children;
		}
		
		String[] leafIds = null;
		if(depth == 1 & option == true)
			leafIds = UniqueIdAndStats.getLetterArray(numChildren);
		
		for(int i=0; i<numChildren; i++){
		//	UUID iden = UUID.randomUUID();
		//	String name = iden.toString().substring(26, 30);
		//	String name = Integer.toString(counterId++);
			String name;
			if(depth == 1 & option == true)
				name = leafIds[i];
			else
				name = Integer.toString(UniqueIdAndStats.getId());
			//String name = iden.toString();
			SubTree child = new SubTree(name);
			temp.addChild(child);
			
			if(option == false){
				Random rand = new Random();
				int prob = rand.nextInt(2);
				if(depth > 1 && prob == 1)
					child.generateTreeWithIntId(depth-1, children, counterId, option);
			}else{
				if(depth > 1)
					child.generateTreeWithIntId(depth-1, children, counterId, option);
			}
		}
		return temp;
	}
	
	SubTree generateTree(int depth, int children){
/*		if(depth <1)
			return null;*/
		
		SubTree temp = null;
		//UUID iden = UUID.randomUUID();
		
		if(this.root == null){
			UUID iden = UUID.randomUUID();
			String name = iden.toString().substring(26, 30);
			temp = new SubTree(name);
		} else{
			temp = this;
		}

		int numChildren =0;
		do{
			Random rand = new Random();
			numChildren = rand.nextInt(children+1);//number of children i.e. (leaves + internal nodes) between 0 and children
		} while(numChildren == 1);
		
		for(int i=0; i<numChildren; i++){
			UUID iden = UUID.randomUUID();
			String name = iden.toString().substring(26, 30);
			//String name = iden.toString();
			SubTree child = new SubTree(name);
			temp.addChild(child);
			Random rand = new Random();
			int prob = rand.nextInt(2);
			if(depth > 1 && prob == 1)
				child.generateTree(depth-1, children);
			
		}
		
/*		int numChildren = 0;
		while(numChildren <2){
			Random rand = new Random();
			numChildren = rand.nextInt(children);//number of children i.e. (leaves + internal nodes) between 0 and children
		}
		int numLeaves = 0;
		if(numChildren > 0){
			Random rand = new Random();
			numLeaves = rand.nextInt(numChildren);//random number of leaves from 0 to numChildren except 1
		}
		if(numLeaves ==1)
			numLeaves = 0;//1 leaf not possible for our structure. so converted to 0
		
		for(int i=1; i<=numLeaves; i++){
			UUID iden = UUID.randomUUID();
			String name = iden.toString().substring(7, 12);
			SubTree child = new SubTree(name);
			temp.addChild(child);
		}
		for(int i=1; i<=(numChildren-numLeaves); i++){
			UUID iden = UUID.randomUUID();
			String name = iden.toString().substring(7, 12);
			SubTree child = new SubTree(name);
			temp.addChild(child);
			if(depth > 1)
				child.generateTree(depth-1, children);
		}
*/		
		return temp;
	}
	
/*	boolean searchTree(SubTree myTree){
		boolean result = false;
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
/*		SubTree base = new SubTree("Base");
		SubTree leg1 = new SubTree("Leg1");
		SubTree inter1 = new SubTree("Inter1");
		inter1.addChild(leg1);
		inter1.addChild(base);
		
		inter1.print();
		System.out.println(inter1.countNodes());
		
		SubTree leg2 = new SubTree("Leg2");
		SubTree leg3 = new SubTree("Leg3");
		SubTree inter2 = new SubTree("Inter2");
		inter2.addChild(leg2);
		inter2.addChild(leg3);
		
		SubTree leg4 = new SubTree("Leg4");
		SubTree table = new SubTree("Table");
		table.addChild(inter1);
		table.addChild(inter2);
	//	table.addChild(leg3);
		table.addChild(leg4);		
		table.print();
		System.out.println(table.countNodes());
		
		Node srch = new Node("Leg3");
		System.out.println(inter1.searchTree(srch));
		System.out.println(table.searchTree(srch));
		
		table.traverseTree();*/
		
/*		SubTree inter1 = new SubTree("Inter1", new String[] {"Base","Leg1"});
		SubTree inter2 = new SubTree("Inter2", new String[] {"leg2","Leg3"});
		SubTree table = new SubTree("Table", new String[] {"leg4"}, new SubTree[] {inter1, inter2});
		table.print();
		table.distributeAncestors();*/
		
		//table.populateParentSiblingId();
		//table.printParentId();
		
		//table.populateGrandChildrenId();
		//table.printAncestorId();
		//table.printTreeNodes();
		
		
/*		List<Node> physicalObjectTags = table.getLeafNodes();
		for(Node tag : physicalObjectTags)
			tag.printNode();

		System.out.println(table.countNodes());*/

/*		SubTree eleven =  new SubTree("11", new String[] {"13","14","15"});
		SubTree ten =  new SubTree("10", new String[] {"24","25"});
		SubTree eighteen =  new SubTree("18", new String[] {"19","20","21"});
		SubTree four =  new SubTree("4", new String[] {"5","6"});
		
		SubTree nine = new SubTree("9", new String[] {"12"}, new SubTree[] {eleven});
		SubTree eight = new SubTree("8", new SubTree[] {nine, ten});
		SubTree twentyThree =  new SubTree("23", new String[] {"16","17"}, new SubTree[]{eighteen});
		SubTree twentyTwo = new SubTree("22", new String[] {"7"}, new SubTree[] {eight});
		SubTree two = new SubTree("2", new String[] {"3"}, new SubTree[] {four});
		SubTree one = new SubTree("1", new SubTree[] {two, twentyTwo, twentyThree});
		
		one.print();
		one.distributeAncestors();
		List<Node> physicalObjectTags = one.getLeafNodes();
		Collections.shuffle(physicalObjectTags);
		
		for(Node tag : physicalObjectTags)
			tag.printNode();
				
		List<SubTree> myList = new ArrayList<SubTree>();
		for(Node tag : physicalObjectTags){
			System.out.println("\n----- Iteration -----\n");
			tag.printNode();
			myList = tag.addTagToBuildTree(myList);
			if(myList.size() == 1 && myList.get(0).checkTree())
				System.out.println("\nAssembly complete !!");
			else
				System.out.println("\nAssembly incomplete !!");
		}*/

/*		SubTree eleven =  new SubTree("11", new String[] {"13","14","15"});
		SubTree ten =  new SubTree("10", new String[] {"24","25"});
		SubTree eighteen =  new SubTree("18", new String[] {"19","20","21"});
		SubTree four =  new SubTree("4", new String[] {"5","6"});

		SubTree nine = new SubTree("9", new String[] {"12"}, new SubTree[] {eleven});
		SubTree eight = new SubTree("8", new SubTree[] {nine, ten});
		SubTree twentyThree =  new SubTree("23", new String[] {"16","17"}, new SubTree[]{eighteen});
		SubTree twentyTwo = new SubTree("22", new String[] {"7"}, new SubTree[] {eight});
		SubTree two = new SubTree("2", new String[] {"3"}, new SubTree[] {four});
		SubTree one = new SubTree("1", new SubTree[] {two, twentyTwo, twentyThree});*/
		
		//THIS IS THE EXAMPLE USED IN THE PAPER
		
		SubTree eleven =  new SubTree("11", new String[] {"M","Q","P"});
		SubTree ten =  new SubTree("10", new String[] {"I","R"});
		SubTree eighteen =  new SubTree("18", new String[] {"Q","P","M"});
		SubTree four =  new SubTree("4", new String[] {"A","P"});

		SubTree nine = new SubTree("9", new String[] {"T"}, new SubTree[] {eleven});
		SubTree eight = new SubTree("8", new SubTree[] {nine, ten});
		SubTree twentyThree =  new SubTree("23", new String[] {"A","T"}, new SubTree[]{eighteen});
		SubTree twentyTwo = new SubTree("22", new String[] {"D"}, new SubTree[] {eight});
		SubTree two = new SubTree("2", new String[] {"L"}, new SubTree[] {four});
		SubTree one = new SubTree("1", new SubTree[] {two, twentyTwo, twentyThree});

		one.print();
		one.distributeAncestors();
		List<Node> physicalObjectTags = one.getLeafNodes();
		Collections.shuffle(physicalObjectTags);

		for(Node tag : physicalObjectTags)
			tag.printNode();

		List<SubTree> myList = new ArrayList<SubTree>();
		for(Node tag : physicalObjectTags){
			System.out.println("\n----- Iteration -----\n");
			tag.printNode();
			myList = tag.addTagToBuildTree(myList);
			if(myList.size() == 1 && myList.get(0).checkTree())
				System.out.println("\nAssembly complete !!");
			else
				System.out.println("\nAssembly incomplete !!");
		}

		
		//UNCOMMENT FROM THIS POINT UNTIL THE END
/*		
		SubTree randHead =  new SubTree("head");
		randHead.generateTreeWithIntId(6, 3, false);
	//	randHead.generateTree(6, 3);
		randHead.print();
		randHead.distributeAncestors();
		
		//Verifying for unique id's in the tree

		boolean unique = true;
		List<String> treeNodes = randHead.createNodeList();
		for(int i=0; i<treeNodes.size(); i++)
			for(int j=0; j<treeNodes.size(); j++)
				if(i!=j && treeNodes.get(i).equalsIgnoreCase(treeNodes.get(j))){
					unique = false;
					System.out.println("Duplicate node id is : " + treeNodes.get(i));
					//System.exit(0);
				}
		System.out.println("No Duplicate node ids");
		
		//Fetching the Physical Tags
		List<Node> physicalObjectTags = randHead.getLeafNodes();
		System.out.println("Number of tags : " + physicalObjectTags.size());
		
		//statistics entry
		UniqueIdAndStats.resetStats();
		UniqueIdAndStats.countStats(randHead);

		try {
			System.out.println("\nPress Enter...");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String text = br.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.shuffle(physicalObjectTags);

		//Printing individual tags
		for(Node tag : physicalObjectTags)
			tag.printNode();

		//Assembling to build tree from the tags
		List<SubTree> myList = new ArrayList<SubTree>();
		for(Node tag : physicalObjectTags){
			System.out.println("\n----- Iteration -----\n");
			tag.printNode();
			myList = tag.addTagToBuildTree(myList);
			if(myList.size() == 1 && myList.get(0).checkTree()){
				System.out.println("\nAssembly complete !!");
				System.out.println("Number of tags added : " + myList.get(0).getLeafNodes().size());
			}
			else
				System.out.println("\nAssembly incomplete !!");
		}

		try {
			System.out.println("\nPress Enter...");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String text = br.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myList.get(0).print();
		
		//printing statistics
		UniqueIdAndStats.printStats();
		*/
	}	
}