package tw.sysbase.entity.pub;

import java.util.ArrayList;
import java.util.List;

public class BuildTree {
	public static <T> Tree<T> build(List<Tree<T>> nodes) {

		if (nodes == null) {
			return null;
		}
		List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

		for (Tree<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || "".equals(pid)) {
				topNodes.add(children);

				continue;
			}

			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setParent(true);
					parent.setChildren(true);

					continue;
				}
			}

		}

		Tree<T> root = new Tree<T>();
		if (topNodes.size() == 1) {
			root = topNodes.get(0);
		} else {
			root.setId(nodes.get(0).getRootId());
			root.setParentId("-1");
			root.setParent(false);
			root.setChildren(true);
			root.setChildren(topNodes);

		}

		return root;
	}
}