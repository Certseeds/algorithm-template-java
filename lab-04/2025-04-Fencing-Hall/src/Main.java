
// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public final class Main {

    // 快读类
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        String nextLine() throws IOException {
            return br.readLine();
        }
    }

    // 输入
    private static class InputData {
        int k, n;
        int[] a;
    }

    public static InputData reader() throws IOException {
        Reader in = new Reader();
        int k = in.nextInt();
        int n = in.nextInt();
        assert ((0 <= k) && (k <= 2_000_000_000));
        assert ((1 <= n) && (n <= 3_000_000));
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
            assert ((1 <= a[i]) && (a[i] <= 2_000_000_000));
        }
        InputData data = new InputData();
        data.k = k;
        data.n = n;
        data.a = a;
        return data;
    }

    // 处理
    public static int cal(InputData data) {
        int n = data.n, k = data.k;
        int[] a = data.a;
        int ans = 0, l = 0;
        Deque<Integer> maxQ = new ArrayDeque<>();
        Deque<Integer> minQ = new ArrayDeque<>();
        for (int r = 0; r < n; ++r) {
            while (!maxQ.isEmpty() && a[maxQ.peekLast()] <= a[r]) maxQ.pollLast();
            maxQ.addLast(r);
            while (!minQ.isEmpty() && a[minQ.peekLast()] >= a[r]) minQ.pollLast();
            minQ.addLast(r);
            while (a[maxQ.peekFirst()] - a[minQ.peekFirst()] > k) {
                l++;
                if (maxQ.peekFirst() < l) maxQ.pollFirst();
                if (minQ.peekFirst() < l) minQ.pollFirst();
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    // 输出
    public static void output(int ans) {
        System.out.print(ans);
        System.out.print('\n');
    }

    public static void main(String[] args) throws IOException {
        InputData data = reader();
        int ans = cal(data);
        output(ans);
    }
}
