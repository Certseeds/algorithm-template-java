// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static List<String> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert testcases >= 1 && testcases <= 10 : "T must be between 1 and 10";
        final List<String> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            int n = input.nextInt();
            final String s = input.next();
            assert s.length() == n : "n should be equal to the length of the string";
            assert n >= 1 && n <= 30000 : "n must be between 1 and 30000";
            assert s.matches("[\\{\\}\\[\\]\\(\\)]+") : "String must contain only brackets";
            cases.add(s);
        }
        return cases;
    }

    public static List<String> cal(List<String> inputs) {
        final List<String> results = new ArrayList<>();
        for (String s : inputs) {
            if (isBalanced(s)) {
                results.add("YES");
            } else {
                results.add("NO");
            }
        }
        return results;
    }

    private static boolean isBalanced(String s) {
        final Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') || (c == '}' && top != '{') || (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<String> decides) {
        for (var decide : decides) {
            System.out.print(decide);
            System.out.print('\n');
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
