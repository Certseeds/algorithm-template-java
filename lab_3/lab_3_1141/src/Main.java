// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final int n, m, e;

        public one(int n, int m, int e) {
            this.n = n;
            this.m = m;
            this.e = e;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            final int e = input.nextInt();
            assert ((1 <= n) && (n <= 100));
            assert ((1 <= m) && (m <= 100));
            assert ((0 <= e) && (e <= n));
            cases.add(new one(n, m, e));
        }
        return cases;
    }

    private static final class node {
        private static final node head = new node(0x3f3f3f3f);
        public node pre, next;
        private final int value;

        public node(int value) {
            this.value = value;
        }
    }

    public static List<Boolean> cal(List<one> nums) {
        final List<Boolean> results = nums.stream()
            .map(one -> {
                final int n = one.n, m = one.m, e = one.e;
                node fst = node.head;
                for (int i = 0; i < n; i++) {
                    fst.next = new node(i);
                    fst.next.pre = fst;
                    fst = fst.next;
                }
                fst.next = node.head.next;
                node.head.next.pre = fst;
                for (int i = 0; i < n; i++) { // after math.min(n,m), it must be done
                    final int index = (m) % (n - i);
                    node rolling = node.head;
                    for (int j = 0; j < index; j++) {
                        rolling = rolling.next;
                    }
                    final node temp = rolling.next;
                    if (temp != null) {
                        //if (temp.next != null) {
                        temp.next.pre = temp.pre;
                        //}
                        //if (temp.pre != null) {
                        temp.pre.next = temp.next;
                        node.head.next = temp.next;
                        //}
                        temp.next = null;
                        temp.pre = null;
                    }
                }
                return node.head.next.value == e;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<Boolean> decides) {
        for (var decide : decides) {
            if (decide) {
                System.out.print("Yes\n");
            } else {
                System.out.print("No\n");
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
    }
}
