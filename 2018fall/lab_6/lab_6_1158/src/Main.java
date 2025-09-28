import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        final Reader reader = new Reader();
        final List<TestCase> testCases = readInput(reader);
        final List<Integer> answers = solveAll(testCases);
        writeOutput(answers);
    }

    private static List<TestCase> readInput(Reader reader) {
        final int T = reader.nextInt();
        final List<TestCase> testCases = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = reader.nextInt();
            final long[] weights = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                weights[i] = reader.nextLong();
            }
            final int[] edgeU = new int[Math.max(0, n - 1)];
            final int[] edgeV = new int[Math.max(0, n - 1)];
            for (int i = 0; i < n - 1; i++) {
                edgeU[i] = reader.nextInt();
                edgeV[i] = reader.nextInt();
            }
            testCases.add(new TestCase(n, weights, edgeU, edgeV));
        }
        return testCases;
    }

    private static List<Integer> solveAll(List<TestCase> testCases) {
        final List<Integer> answers = new ArrayList<>(testCases.size());
        for (final TestCase testCase : testCases) {
            answers.add(solveCase(testCase));
        }
        return answers;
    }

    private static int solveCase(TestCase testCase) {
        return new HongSetSolver(testCase).solve();
    }

    private static void writeOutput(List<Integer> answers) {
        final StringBuilder sb = new StringBuilder();
        for (final int answer : answers) {
            sb.append(answer).append('\n');
        }
        System.out.print(sb);
    }

    private static final class TestCase {
        final int n;
        final long[] weights;
        final int[] edgeU;
        final int[] edgeV;

        TestCase(int n, long[] weights, int[] edgeU, int[] edgeV) {
            this.n = n;
            this.weights = weights;
            this.edgeU = edgeU;
            this.edgeV = edgeV;
        }
    }

    private static final class HongSetSolver {
        private final int n;
        private final long[] weights;
        private final Graph graph;

        HongSetSolver(TestCase testCase) {
            this.n = testCase.n;
            this.weights = testCase.weights;
            this.graph = new Graph(testCase.n, Math.max(0, (testCase.n - 1) << 1));
            for (int i = 0; i < testCase.edgeU.length; i++) {
                graph.addUndirectedEdge(testCase.edgeU[i], testCase.edgeV[i]);
            }
        }

        int solve() {
            int maxSize = 0;
            for (int u = 1; u <= n; u++) {
                final int size = dfs(u, -1, weights[u]);
                maxSize = Math.max(maxSize, size);
            }
            return maxSize;
        }

        private int dfs(int u, int parent, long minWeight) {
            if (weights[u] < minWeight) {
                int maxFromChildren = 0;
                for (int ei = graph.head[u]; ei != -1; ei = graph.next[ei]) {
                    final int v = graph.to[ei];
                    if (v != parent) {
                        maxFromChildren = Math.max(maxFromChildren, dfs(v, u, minWeight));
                    }
                }
                return maxFromChildren;
            }

            int result = 1;
            for (int ei = graph.head[u]; ei != -1; ei = graph.next[ei]) {
                final int v = graph.to[ei];
                if (v != parent && weights[v] > weights[u]) {
                    result += dfs(v, u, weights[u]);
                }
            }
            return result;
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
