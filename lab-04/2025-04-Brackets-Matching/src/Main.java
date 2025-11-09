
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
        int T;
        int[] nArr;
        String[] bracketsArr;
    }

    public static InputData reader() throws IOException {
        Reader in = new Reader();
        int T = Integer.parseInt(in.next());
        assert ((1 <= T) && (T <= 10));
        int[] nArr = new int[T];
        String[] bracketsArr = new String[T];
        for (int i = 0; i < T; ++i) {
            nArr[i] = Integer.parseInt(in.next());
            assert ((1 <= nArr[i]) && (nArr[i] <= 30000));
            bracketsArr[i] = in.nextLine();
            // 可能 nextLine() 读到空串, 需要再读一次
            if (bracketsArr[i] == null || bracketsArr[i].isEmpty()) {
                bracketsArr[i] = in.nextLine();
            }
        }
        InputData data = new InputData();
        data.T = T;
        data.nArr = nArr;
        data.bracketsArr = bracketsArr;
        return data;
    }

    // 处理
    public static String[] cal(InputData data) {
        String[] results = new String[data.T];
        for (int i = 0; i < data.T; ++i) {
            results[i] = isMatched(data.bracketsArr[i]) ? "YES" : "NO";
        }
        return results;
    }

    // 判断括号匹配
    private static boolean isMatched(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (!match(top, c)) return false;
            } else {
                // 非法字符
                return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean match(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    // 输出
    public static void output(String[] results) {
        for (String res : results) {
            System.out.print(res);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        InputData data = reader();
        String[] results = cal(data);
        output(results);
    }
}
