import java.util.Arrays;

public final class star {
	private final int nodeCount;
	private final int[] head;
	private final int[] to;
	private final int[] weight;
	private final int[] next;
	private int edgeIndex;

	public star(int nodeCount, int maxEdges) {
		assert (nodeCount > 0);
		assert (maxEdges >= 0);
		this.nodeCount = nodeCount;
		this.head = new int[nodeCount + 1];
		Arrays.fill(this.head, -1);
		this.to = new int[maxEdges];
		this.weight = new int[maxEdges];
		this.next = new int[maxEdges];
		this.edgeIndex = 0;
	}

	public int addEdge(int from, int toNode, int edgeWeight) {
		assert ((1 <= from) && (from <= nodeCount));
		assert ((1 <= toNode) && (toNode <= nodeCount));
		assert (edgeIndex < to.length);
		final int idx = edgeIndex;
		to[idx] = toNode;
		weight[idx] = edgeWeight;
		next[idx] = head[from];
		head[from] = idx;
		edgeIndex++;
		return idx;
	}

	public int head(int node) {
		assert ((1 <= node) && (node <= nodeCount));
		return head[node];
	}

	public int to(int edgeIdx) {
		assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
		return to[edgeIdx];
	}

	public int weight(int edgeIdx) {
		assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
		return weight[edgeIdx];
	}

	public int next(int edgeIdx) {
		assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
		return next[edgeIdx];
	}

	public int edgeCount() {
		return edgeIndex;
	}

	public int nodeCount() {
		return nodeCount;
	}
}
