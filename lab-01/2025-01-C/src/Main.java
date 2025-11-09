// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    // 读取方法，快读+assert
    public static Data reader() throws IOException {
        final var input = new Reader();
        final int t = input.nextInt();
        assert ((1 <= t) && (t <= 100000));
        final int[] ns = new int[t];
        for (int i = 0; i < t; ++i) {
            ns[i] = input.nextInt();
            assert ((1 <= ns[i]) && (ns[i] <= 1000000));
        }
        return new Data(t, ns);
    }

    // 处理函数，返回结果列表
    public static long[] cal(final Data data) {
        final long[] results = new long[data.t];
        for (int i = 0; i < data.t; ++i) {
            final int n = data.ns[i];
            final long sum1 = (long) n * (n + 1) * (2L * n + 1) / 6;
            final long sum2 = (long) n * (n + 1) / 2;
            results[i] = (sum1 + sum2) / 2;
        }
        return results;
    }

    // 输出函数，所有结果都输出换行
    public static void output(final long[] results) {
        for (final long x : results) {
            System.out.print(x);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        final Data data = reader();
        final long[] results = cal(data);
        output(results);
    }

    // 数据类，封装所有输入
    private static final class Data {
        final int t;
        final int[] ns;

        Data(int t, int[] ns) {
            this.t = t;
            this.ns = ns;
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

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
