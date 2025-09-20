// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static List<String> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((testcases >= 0) && (testcases <= 1000)) : "T must be between 0 and 1000";
        final List<String> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final String s = input.next();
            assert (s.length() <= 100) : "string.length() must be <= 100";
            assert (s.matches("[a-z0-9]+")) : "String must contain only lowercase letters and digits";
            cases.add(s);
        }
        return cases;
    }

    private static final String target = "lanran";

    public static List<String> cal(List<String> inputs) {
        final List<String> results = new ArrayList<>();
        for (String s : inputs) {
            int i = 0; // pointer for string s
            int j = 0; // pointer for target
            while (i < s.length() && j < target.length()) {
                if (s.charAt(i) == target.charAt(j)) {
                    j++;
                }
                i++;
            }
            if (j == target.length()) {
                results.add("YES");
            } else {
                results.add("NO");
            }
        }
        return results;
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
