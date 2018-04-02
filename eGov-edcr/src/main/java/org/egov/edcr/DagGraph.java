package org.egov.edcr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DagGraph {

	public static void main(String args[]) throws IOException {

		List<DagNode> nodes = new ArrayList<>();
		Path path = Paths.get("/home/mani/Dag.txt");
		List<String> contents = Files.readAllLines(path);

		for (String s : contents) {
			String[] split = s.split(",");
			if (split.length == 2) {
				DagNode node = new DagNode(split[0]);
				DagNode child = new DagNode(split[1]);
				// child.getParents().add(node);
				if (nodes.isEmpty()) {

					node.addChild(child);
					//child.addParent(node);
					nodes.add(node);
				} else {

					DagNode result = lookUpNode(nodes, node);
					if (result != null) {
						result.addChild(child);

					} else if (result == null) {

						result = lookUpNode(nodes, child);
						if (result != null) {
							result.addParent(node);
							nodes.add(node);
						}
						if (result == null) {
							node.addChild(child);
							nodes.add(node);
						}
					}
				}

			} else {
				DagNode node = new DagNode(split[0]);
				nodes.add(node);
			}
		}

		for (DagNode node : nodes) {
			String result = "";
			result = node.print(node, result, node, node);

		}

	}

 

	private static DagNode lookUpNode(List<DagNode> nodes, DagNode s) {
		if (nodes == null)
			return null;
		DagNode search = null;
		for (DagNode node : nodes) {

			if (node.getValue().equals(s.getValue()))
				return node;
			else
				search = lookUpNode(node.getChildren(), s);
		 
			if (search != null)
				return search;

		}

		return search;
	}
}
