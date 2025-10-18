// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.LongPredicate;

public final class Main {

    // 读取：快读+assert
    public static Data reader() throws IOException {
        final var in = new Reader();
        final long x1 = in.nextLong();
        final long y1 = in.nextLong();
        assert ((0L <= x1) && (x1 <= 1000_000_000L));
        assert ((0L <= y1) && (y1 <= 1000_000_000L));
        final long x2 = in.nextLong();
        final long y2 = in.nextLong();
        assert ((0L <= x2) && (x2 <= 1000_000_000L));
        assert ((0L <= y2) && (y2 <= 1000_000_000L));
        final int n = in.nextInt();
        assert ((1 <= n) && (n <= 100_000));
        final String s = in.next();
        assert (s != null);
        assert (s.length() == n);
        for (int i = 0; i < n; ++i) {
            final char c = s.charAt(i);
            assert (c == 'U' || c == 'D' || c == 'L' || c == 'R');
        }
        return new Data(x1, y1, x2, y2, n, s);
    }

    // 处理：二分时间，前缀和计算 Neko 位置
    public static long cal(Data d) {
        final int n = d.n;
        final char[] cs = d.s.toCharArray();
        final long[] px = new long[n + 1];
        final long[] py = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            final char c = cs[i - 1];
            px[i] = px[i - 1];
            py[i] = py[i - 1];
            switch (c) {
                case 'U': {
                    py[i] += 1;
                    break;
                }
                case 'D': {
                    py[i] -= 1;
                    break;
                }
                case 'L': {
                    px[i] -= 1;
                    break;
                }
                case 'R': {
                    px[i] += 1;
                    break;
                }
            }
        }
        final long cycX = px[n];
        final long cycY = py[n];

        // 检查在时间 t 时的最小距离是否可达
        final LongPredicate ok = (long t) -> {
            final long cycles = t / n;
            final int rem = (int) (t % n);
            final long nx = d.x2 + cycles * cycX + px[rem];
            final long ny = d.y2 + cycles * cycY + py[rem];
            final long dist = Math.abs(nx - d.x1) + Math.abs(ny - d.y1);
            return dist <= t;
        };

        // 指数扩展搜上界
        long lo = 0L, hi = 1L;
        while (hi < 4_000_000_000_000_000_000L && !ok.test(hi)) {
            hi <<= 1;
        }
        if (hi >= 4_000_000_000_000_000_000L && !ok.test(hi)) {
            return -1L;
        }
        long ans = hi;
        while (lo <= hi) {
            final long mid = lo + ((hi - lo) >>> 1);
            if (ok.test(mid)) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ans;
    }

    // 输出：一行换行
    public static void output(long result) {
        System.out.print(result);
        System.out.print('\n');
    }

    public static void main(String[] args) throws IOException {
        final Data data = reader();
        final long ans = cal(data);
        output(ans);
    }

    // 数据类，封装输入
    private static final class Data {
        final long x1, y1, x2, y2;
        final int n;
        final String s;

        Data(long x1, long y1, long x2, long y2, int n, String s) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.n = n;
            this.s = s;
        }
    }

    // 快读
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
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
