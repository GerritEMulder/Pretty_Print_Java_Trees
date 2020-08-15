

import java.util.*;
import java.io.*;

/**
 * 
 * @author Gerrit Mulder
 * Name: Gerrit Mulder
 * 
 * The PooPoo class
 * This is the main class for running the tree fills and printing them out
 * 
 */
public class PooPoo{
	
	// private variable accessible to all methods
	private static BinaryTreeNode<String> root=null;
	private static BinaryTreeNode<String> node=null;
	private static final boolean DEBUG=false;
	
	public static void main(String[] args) {
	
		String[] file = args[args.length - 1].split("/");
		String listFileName = file[1];
		
		// Create listReader Object
		InStringFile listReader=new InStringFile(listFileName);
		StringTokenizer tokenizer;
		String line = listReader.read();
		tokenizer=new StringTokenizer(line);
		int numTokens=tokenizer.countTokens();
		System.out.println(numTokens + " token in " + listFileName);
	
		for(int i=0;i<numTokens;i++){
		
			String element=tokenizer.nextToken();
			if(DEBUG) System.out.println("element read: " + element);
			node=new BinaryTreeNode<String>(element);
			root=insertInBinaryTree(root,node);
		}
	
		int n=countBinaryTreeNodes(root);
		System.out.println("Number of nodes in the tree:" + n);
	
		int h=findHeight(root);
		System.out.format("Height of tree: %d\n",h);
		System.out.format("Height density: %6.2f\n",h*100.0/n);
		int w=findWidth(root);
		System.out.format("Width of tree: %d\n",w);
		System.out.format("Width density: %6.2f\n",w*100.0/n);
		PrettyPrintTree.printNode(root);
	}
	
	////////////////////////////////////////////////////
	// insert a BinaryTreetNode<String> node in a binary tree
	// rooted by BinaryTreetNode<String> root
	////////////////////////////////////////////////////
	public static BinaryTreeNode<String> insertInBinaryTree(BinaryTreeNode<String> root, BinaryTreeNode<String> node){
	
		
		// If the root is null then make the root reference the node
		if(root == null){
			root = node;
		}
		
		else{
			
			// Creates a double variable which is the number returned from the compareTo method 
			double treeNumber = root.getElement().compareTo(node.getElement());
			
			// Sets the left branches of root recursively  
			if(treeNumber >= 0){
				root.setLeft(insertInBinaryTree(root.getLeft(), node));
			}
			
			// Sets the right branches of the root recursively
			else if(treeNumber < 0){
				root.setRight(insertInBinaryTree(root.getRight(), node));
			}
		}
		
		// Returns the tree once the tree has been filled
		return root;
	}
	
	////////////////////////////////////////////////////
	// The height of a tree is the length of the path from 
	// the root to the deepest node in the tree
	// An empty tree has height -1
	// A tree with just 1 node (the root) has height 0
	////////////////////////////////////////////////////
	public static int findHeight(BinaryTreeNode<String> node){
		
		// Returns -1 if the node ever equals null 
		if (node == null){
			return -1;
		}
		
		// Else recursively returns 1 plus the max of the left or right side of the tree
		else{
			return 1 + Math.max(findHeight(node.getLeft()), findHeight(node.getRight()));
		}
	
	}
	
	////////////////////////////////////////////////////
	// Compute the maximum width of the binary tree
	// Use a level order search with a queue
	////////////////////////////////////////////////////
	public static int findWidth(BinaryTreeNode<String> root){
		
		// Variables for the max and current width's of the tree
		int currentWidth = 0;
		int maxWidth = 0;
		
		// If the root is null return zero
		if(root == null){
			return 0;
		}
		
		// Creates a new queue and enqueues the root
		LinkedQueue<BinaryTreeNode<String>> queue = new LinkedQueue<BinaryTreeNode<String>>();
		queue.enqueue(root);
		
		// While the queue is not empty 
		while(!queue.isEmpty()){
			
			// Sets the current width as the size of the queue
			currentWidth = queue.size();
			
			// If the current width is bigger then the max width
			// Set the max width to be the current width
			if(currentWidth > maxWidth){
				maxWidth = currentWidth;
			}
			
			// While loop to empty the queue and the fill it with the next level of the tree
			while(currentWidth > 0){
				BinaryTreeNode<String> temp = queue.dequeue();
				
				// If the right and left nodes aren't null then enqueue them
				if(temp.getLeft() != null){
					queue.enqueue(temp.getLeft());
				}
				if(temp.getRight() != null){
					queue.enqueue(temp.getRight());
				}
				currentWidth--;
			}
		}
		
		// Returns the max width once it has been found
		return maxWidth;
	}
	
	////////////////////////////////////////////////////
	// The number of nodes in a binary tree is the 
	// number of nodes in the root's left subtree
	// plus the number of nodes in its right subtree 
	// plus one (for the root itself).
	////////////////////////////////////////////////////
	public static int countBinaryTreeNodes(BinaryTreeNode<String> root){
		
		// This method is almost the same as the findWidth method 
		// Except that it adds the number of node on each level of the tree 
		// In order to find the number of nodes in the whole tree
		int nodeCount = 0;
		int count = 0;
		LinkedQueue<BinaryTreeNode<String>> queue = new LinkedQueue<BinaryTreeNode<String>>();
		queue.enqueue(root);
		
		while(!queue.isEmpty()){
			nodeCount = nodeCount + queue.size();
			count = queue.size();
			
			while(count > 0){
				BinaryTreeNode<String> temp = queue.dequeue();
				
				if(temp.getLeft() != null){
					queue.enqueue(temp.getLeft());
				}
				if(temp.getRight() != null){
					queue.enqueue(temp.getRight());
				}
				count--;
			}
		}
		
		// Returns the number of nodes in the tree
		return nodeCount;
	}

}
