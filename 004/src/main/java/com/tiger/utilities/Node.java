package com.tiger.utilities;

import java.util.ArrayList;
import java.util.List;

import com.tiger.utilities.StringUtil;

/**
 * XML 节点
 * @author pengpan
 */
public class Node {
	
	private String name;
	
    private String value;
	
	private List<Node> childNodes;
	
	private List<Node> nodeAttribute;
	
	public Node(String name){
		this.name = name;
		childNodes = new ArrayList<Node>();
		nodeAttribute = new ArrayList<Node>();
	}
	public Node(String name,String value){
		this.name = name;
		this.value = value;
		childNodes = new ArrayList<Node>();
		nodeAttribute = new ArrayList<Node>();
	}
	
	public Node addChildNode(String name,String value){
		Node node = new Node(name,value);
		childNodes.add(node);
		return node;
	}
	public Node addChildNode(String name){
		Node node = new Node(name);
		childNodes.add(node);
		return node;
	}
	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Node> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Node> childNodes) {
		this.childNodes = childNodes;
	}

	public List<Node> getNodeAttribute() {
		return nodeAttribute;
	}

	public void setNodeAttribute(List<Node> nodeAttribute) {
		this.nodeAttribute = nodeAttribute;
	}

}
