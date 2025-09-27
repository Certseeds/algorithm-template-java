import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int K;
        public final int[] a;

        public TestCase(int n, int K, int[] a) {
            this.n = n;
            this.K = K;
            this.a = a;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int K = in.nextInt();
            assert ((1 <= K) && (K <= n) && (n <= 200000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = in.nextInt();
            tests.add(new TestCase(n, K, a));
        }
        return tests;
    }

    // naive bubble simulation: perform K passes of "one bubble operation"
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int K = tc.K;
            final int[] a = Arrays.copyOf(tc.a, n);

            // If K is large enough to fully sort by repeated bubble passes, you could optimize by
            // checking K >= n and sorting, but here we keep it purely naive as requested.
            for (int pass = 0; pass < K; pass++) {
                boolean swapped = false;
                for (int i = 0; i + 1 < n; i++) {
                    if (a[i] > a[i + 1]) {
                        int tmp = a[i];
                        a[i] = a[i + 1];
                        a[i + 1] = tmp;
                        swapped = true;
                    }
                }
                if (!swapped) {
                    break; // already sorted
                }
            }

            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(a[i]);
            }
            out.add(sb.toString());
        }
        return out;
    }

    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                final String line = br.readLine();
                if (line == null) return "";
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
