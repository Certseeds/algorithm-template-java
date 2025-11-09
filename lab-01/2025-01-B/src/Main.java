// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    // 读取方法，快读+assert
    public static Data reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int q = input.nextInt();
        assert ((1 <= n) && (n <= 100000));
        assert ((1 <= q) && (q <= 100000));
        final int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = input.nextInt();
            assert ((1 <= arr[i]) && (arr[i] <= 1000000000));
            if (i > 0) assert (arr[i] >= arr[i - 1]); // 非递减
        }
        final int[] xs = new int[q], ys = new int[q];
        for (int i = 0; i < q; ++i) {
            xs[i] = input.nextInt();
            ys[i] = input.nextInt();
            assert ((1 <= xs[i]) && (xs[i] <= ys[i]) && (ys[i] <= 1000000000));
        }
        return new Data(n, q, arr, xs, ys);
    }

    // 处理函数，返回结果列表
    public static String[] cal(final Data data) {
        final String[] results = new String[data.q];
        for (int i = 0; i < data.q; ++i) {
            final int left = upperBound(data.arr, data.xs[i]); // first > x
            final int right = lowerBound(data.arr, data.ys[i]); // first >= y
            final int cnt = right - left;
            if (cnt <= 0) {
                results[i] = "NO";
            } else {
                results[i] = "YES " + cnt;
            }
        }
        return results;
    }

    // 输出函数，所有结果都输出换行
    public static void output(final String[] results) {
        for (final String s : results) {
            System.out.print(s);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        final Data data = reader();
        final String[] results = cal(data);
        output(results);
    }

    // Returns first index where arr[idx] > x
    private static int upperBound(int[] arr, int x) {
        int l = 0;
        for (int r = arr.length; l < r; ) {
            final int m = l + (r - l) / 2;
            if (arr[m] <= x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    // Returns first index where arr[idx] >= y
    private static int lowerBound(int[] arr, int y) {
        int l = 0;
        for (int r = arr.length; l < r; ) {
            final int m = l + (r - l) / 2;
            if (arr[m] < y) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    // 数据类，封装所有输入
    private static final class Data {
        final int n, q;
        final int[] arr;
        final int[] xs, ys;

        Data(int n, int q, int[] arr, int[] xs, int[] ys) {
            this.n = n;
            this.q = q;
            this.arr = arr;
            this.xs = xs;
            this.ys = ys;
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
    }
}
