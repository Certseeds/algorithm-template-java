// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final long result = cal(data);
        output(result);
    }

    public static InputData reader() throws IOException {
        final Reader input = new Reader();
        final int n = input.nextInt();
        final int p = input.nextInt();
        final int q = input.nextInt();
        assert ((1 <= n) && (n <= 200000));
        assert ((0 <= p) && (p <= 20));
        assert ((0 <= q) && (q <= 200000));
        final long[] heights = new long[n];
        final long[] strengths = new long[n];
        for (int i = 0; i < n; ++i) {
            final long h = input.nextLong();
            final long s = input.nextLong();
            assert ((1 <= h) && (h <= 1_000_000_000L));
            assert ((1 <= s) && (s <= 1_000_000_000L));
            heights[i] = h;
            strengths[i] = s;
        }
        return new InputData(n, p, q, heights, strengths);
    }

    public static long cal(InputData data) {
        final int n = data.n;
        final int p = data.p;
        final int q = data.q;
        final long[] heights = data.heights;
        final long[] strengths = data.strengths;

        long base = 0L;
        final long[] gainWithoutFh = new long[n];
        for (int i = 0; i < n; ++i) {
            base += strengths[i];
            final long diff = heights[i] - strengths[i];
            gainWithoutFh[i] = diff > 0L ? diff : 0L;
        }

        final int count = Math.min(q, n);
        final Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; ++i) {
            pairs[i] = new Pair(gainWithoutFh[i], i);
        }
        Arrays.sort(pairs, (a, b) -> {
            if (a.gain == b.gain) {
                return Integer.compare(a.index, b.index);
            }
            return Long.compare(b.gain, a.gain);
        });

        final long[] sortedGains = new long[n];
        final int[] position = new int[n];
        final long[] prefix = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            sortedGains[i] = pairs[i].gain;
            position[pairs[i].index] = i;
            prefix[i + 1] = prefix[i] + sortedGains[i];
        }

        long best = base + prefix[count];
        final long multiplier = 1L << p;

        for (int idx = 0; idx < n; ++idx) {
            final long newHeight = heights[idx] * multiplier;
            final long improvedGain = Math.max(newHeight - strengths[idx], 0L);
            final int insertion = lowerBoundDesc(sortedGains, improvedGain);
            final int originalPos = position[idx];
            final int adjustedPos = (insertion <= originalPos) ? insertion : insertion - 1;

            final long sumTopExcl;
            if (count == 0) {
                sumTopExcl = 0L;
            } else if (originalPos >= count) {
                sumTopExcl = prefix[count];
            } else {
                sumTopExcl = prefix[count] - sortedGains[originalPos] + ((count < n) ? sortedGains[count] : 0L);
            }

            final long totalGainTop;
            if (adjustedPos >= count) {
                totalGainTop = sumTopExcl;
            } else {
                final long drop;
                if (count == 0) {
                    drop = 0L;
                } else if (originalPos >= count) {
                    drop = sortedGains[count - 1];
                } else {
                    drop = (count < n) ? sortedGains[count] : 0L;
                }
                totalGainTop = sumTopExcl + improvedGain - drop;
            }

            final long candidate = base + totalGainTop;
            if (candidate > best) {
                best = candidate;
            }
        }
        return best;
    }

    public static void output(long result) {
        System.out.print(result);
        System.out.print('\n');
    }

    private static int lowerBoundDesc(long[] arr, long target) {
        int low = 0;
        int high = arr.length;
        while (low < high) {
            final int mid = (low + high) >>> 1;
            if (arr[mid] > target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private static final class InputData {
        private final int n;
        private final int p;
        private final int q;
        private final long[] heights;
        private final long[] strengths;

        private InputData(int n, int p, int q, long[] heights, long[] strengths) {
            this.n = n;
            this.p = p;
            this.q = q;
            this.heights = heights;
            this.strengths = strengths;
        }
    }

    private static final class Pair {
        private final long gain;
        private final int index;

        private Pair(long gain, int index) {
            this.gain = gain;
            this.index = index;
        }
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        private String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                final String line = br.readLine();
                if (line == null) {
                    return null;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        private int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
