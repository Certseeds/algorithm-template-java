// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
    private static final class single {
        private final Set<String> algorithms;
        private final List<String> words;

        public single(Set<String> algorithms, List<String> words) {
            this.algorithms = algorithms;
            this.words = words;
        }
    }

    public static List<single> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<single>();
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 10));
            final Set<String> algorithms = new HashSet<>(n);
            for (int j = 0; j < n; j++) {
                algorithms.add(input.nextLine().toLowerCase());
            }
            final int m = input.nextInt();
            assert ((1 <= m) && (m <= 1000));
            final List<String> words = new ArrayList<>(m);
            for (int j = 0; j < m; j++) {
                words.add(input.next().toLowerCase());
            }
            cases.add(new single(algorithms, words));
        }
        return cases;
    }

    public static List<Boolean> cal(List<single> nums) {
        final List<Boolean> results = nums.stream()
            .map(one -> one.words
                .stream()
                .anyMatch(one.algorithms::contains))
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Boolean> decides) {
        for (final var decide : decides) {
            if (decide) {
                System.out.print("Appeared\n");
            } else {
                System.out.print("Not appeared\n");
            }
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

        int nextInt() {return Integer.parseInt(next());}

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
