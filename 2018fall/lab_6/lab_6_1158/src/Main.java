import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static long[] w;
    static Graph graph;

    // 计算以u为根, parent为父节点时的最大Hong Set大小
    static int dfs(int u, int parent, long minWeight) {
        // 如果当前节点权重小于最小权重要求, 不能作为Hong Set的一部分
        if (w[u] < minWeight) {
            // 但可以作为路径上的中间节点, 继续向下搜索
            int maxFromChildren = 0;
            for (int ei = graph.head[u]; ei != -1; ei = graph.next[ei]) {
                final int v = graph.to[ei];
                if (v != parent) {
                    maxFromChildren = Math.max(maxFromChildren, dfs(v, u, minWeight));
                }
            }
            return maxFromChildren;
        }

        // u可以作为Hong Set的一部分
        int result = 1; // 包含u本身

        // 遍历所有可以加入的子节点并累加贡献
        for (int ei = graph.head[u]; ei != -1; ei = graph.next[ei]) {
            final int v = graph.to[ei];
            if (v != parent && w[v] > w[u]) {
                result += dfs(v, u, w[u]);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        final var sc = new Reader();
        int T = sc.nextInt();

        while (T-- > 0) {
            n = sc.nextInt();
            w = new long[n + 1];
            graph = new Graph(n, (n - 1) << 1);

            for (int i = 1; i <= n; i++) {
                w[i] = sc.nextLong();
            }

            for (int i = 0; i < n - 1; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                graph.addUndirectedEdge(a, b);
            }

            int maxSize = 0;

            // 枚举每个节点作为Hong Set的起始节点
            for (int i = 1; i <= n; i++) {
                final int size = dfs(i, -1, w[i]);
                maxSize = Math.max(maxSize, size);
            }

            System.out.print(maxSize);
            System.out.print('\n');
        }
    }

    public static final class Reader {
        public final BufferedReader br;
        public StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

    }

    private static final class Graph {
        final int[] head;
        final int[] to;
        final int[] next;
        private int ptr = 0;

        Graph(final int n, final int capacity) {
            head = new int[n + 1];
            Arrays.fill(head, -1);
            to = new int[capacity];
            next = new int[capacity];
        }

        void addUndirectedEdge(final int u, final int v) {
            addEdge(u, v);
            addEdge(v, u);
        }

        private void addEdge(final int u, final int v) {
            to[ptr] = v;
            next[ptr] = head[u];
            head[u] = ptr++;
        }
    }
}
