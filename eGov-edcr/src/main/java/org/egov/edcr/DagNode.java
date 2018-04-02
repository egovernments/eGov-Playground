package org.egov.edcr;

import java.util.ArrayList;
import java.util.List;

public class DagNode {
	@Override
	public boolean equals(Object obj) {

		DagNode n = (DagNode) obj;
		return this.getValue().equalsIgnoreCase(n.getValue());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	private List<DagNode> parents;
	private List<DagNode> children;
	private String value;

	public DagNode(String val) {
		this.value = val;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<DagNode> getParents() {
		return parents;
	}

	public void setParents(List<DagNode> parents) {
		this.parents = parents;
	}

	public List<DagNode> getChildren() {
		return children;
	}

	public void setChildren(List<DagNode> children) {
		this.children = children;
	}

	public void addChild(String child) {

		DagNode childNode = new DagNode(child);
		childNode.addParent(this);
		if (children == null) {
			children = new ArrayList<>();
			children.add(childNode);
		} else {
			children.add(childNode);
		}

	}

	public void addChild(DagNode childNode) {

		if (children == null) {
			children = new ArrayList<>();
			children.add(childNode);
		} else {
			children.add(childNode);
		}

	}

	public void addParent(DagNode dagNode) {
		if (this.parents == null) {
			this.parents = new ArrayList<>();
			this.parents.add(dagNode);
		} else {
			this.parents.add(dagNode);
			dagNode.addChild(this);
		}
	}

	public String print(DagNode node, String s, DagNode startNode, DagNode root) {

		if (node.getParents() != null && node.parents.contains(root)) {

			s = root.getValue();

		}
		if (node.getChildren() == null) {
         if(!s.isEmpty())
			System.out.println(s + "-->" + node.value);
         else
        	 System.out.println( node.value);

			return s;
		} else {

			s = s + "-->" + node.value;
		}

		for (DagNode child : node.getChildren()) {
			s = print(child, s, node, root);
		}

		return s;
	}
}
