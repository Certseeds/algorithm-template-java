import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static long[] w;
    static List<Integer>[] adj;

    // 计算以u为根，parent为父节点时的最大Hong Set大小
    static int dfs(int u, int parent, long minWeight) {
        // 如果当前节点权重小于最小权重要求，不能作为Hong Set的一部分
        if (w[u] < minWeight) {
            // 但可以作为路径上的中间节点，继续向下搜索
            int maxFromChildren = 0;
            for (int v : adj[u]) {
                if (v != parent) {
                    maxFromChildren = Math.max(maxFromChildren, dfs(v, u, minWeight));
                }
            }
            return maxFromChildren;
        }

        // u可以作为Hong Set的一部分
        int result = 1; // 包含u本身

        // 收集所有可以加入的子节点
        final List<Integer> validChildren = new ArrayList<>();
        for (int v : adj[u]) {
            if (v != parent && w[v] > w[u]) {
                validChildren.add(v);
            }
        }

        // 对每个有效子节点，计算其贡献
        for (int v : validChildren) {
            result += dfs(v, u, w[u]);
        }

        return result;
    }

    public static void main(String[] args) {
        final var sc = new Reader();
        int T = sc.nextInt();

        while (T-- > 0) {
            n = sc.nextInt();
            w = new long[n + 1];
            adj = new List[n + 1];

            for (int i = 1; i <= n; i++) {
                w[i] = sc.nextLong();
                adj[i] = new ArrayList<>();
            }

            for (int i = 0; i < n - 1; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                adj[a].add(b);
                adj[b].add(a);
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
}
