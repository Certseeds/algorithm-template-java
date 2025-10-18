// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {


    // read: 读入所有数据
    public static class InputData {
        int n, t;
        int[] a, b;
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final InputData data = new InputData();
        data.n = input.nextInt();
        // README.md 限定: 1 <= n <= 1000
        assert ((1 <= data.n) && (data.n <= 1000)) : "n out of range";
        data.a = new int[data.n];
        for (int i = 0; i < data.n; i++) {
            data.a[i] = input.nextInt();
            // README.md 限定: 0 <= a[i] <= 1e9
            assert ((0 <= data.a[i]) && (data.a[i] <= 1_000_000_000L)) : "a[i] out of range";
        }
        data.t = input.nextInt();
        // README.md 限定: 1 <= T <= 1000
        assert ((1 <= data.t) && (data.t <= 1000)) : "t out of range";
        data.b = new int[data.t];
        for (int i = 0; i < data.t; i++) {
            data.b[i] = input.nextInt();
            // README.md 限定: 0 <= b[i] <= 1e9
            assert ((0 <= data.b[i]) && (data.b[i] <= 1_000_000_000L)) : "b[i] out of range";
        }
        return data;
    }

    private static final String OUTPUT_YES = "yes";
    private static final String OUTPUT_NO = "no";

    // cal: 处理逻辑
    public static List<String> cal(InputData data) {
        final HashSet<Integer> set = new HashSet<>(data.n);
        for (int v : data.a) {
            set.add(v);
        }
        final List<String> res = new ArrayList<>(data.t);
        for (int i = 0; i < data.t; i++) {
            res.add(set.contains(data.b[i]) ? OUTPUT_YES : OUTPUT_NO);
        }
        return res;
    }

    // write: 输出
    public static void output(Iterable<String> result) {
        final StringBuilder sb = new StringBuilder();
        for (String s : result) {
            sb.append(s).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final List<String> result = cal(data);
        output(result);
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
