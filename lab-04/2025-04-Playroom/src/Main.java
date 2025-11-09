
// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public final class Main {

    private static final int MOD = 514329;

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

        String nextLine() throws IOException {
            return br.readLine();
        }
    }

    // 输入
    public static String reader() throws IOException {
        Reader in = new Reader();
        String s = in.nextLine();
        assert (s != null && s.length() <= 100000);
        return s;
    }

    // 处理
    public static int cal(String s) {
        int n = s.length();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(-1); // 标记左括号
            } else if (c == ')') {
                int sum = 0;
                while (!stack.isEmpty() && stack.peek() != -1) {
                    sum = (sum + stack.pop()) % MOD;
                }
                stack.pop(); // 弹出左括号
                if (sum == 0) sum = 1; // ()
                else sum = (sum * 2) % MOD;
                stack.push(sum);
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) {
            ans = (ans + stack.pop()) % MOD;
        }
        return ans;
    }

    // 输出
    public static void output(int ans) {
        System.out.print(ans);
        System.out.print('\n');
    }

    public static void main(String[] args) throws IOException {
        String s = reader();
        int ans = cal(s);
        output(ans);
    }
}
