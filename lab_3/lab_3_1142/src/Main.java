// SPDX-License-Identifier: Apache-2.0 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final List<Integer> powers;
        private final int k;

        public one(List<Integer> powers, int k) {
            this.powers = powers;
            this.k = k;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int k = input.nextInt();
            assert ((1 <= n) && (n <= 500_000));
            assert ((0 <= k) & (k <= Math.min(n, 80)));
            final List<Integer> powers = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                final int power = input.nextInt();
                assert ((0 <= power) & (power <= 500_000));
                powers.add(power);
            }
            cases.add(new one(powers, k));
        }
        return cases;
    }

    private static final class node {
        private node pre, next;
        private final int position;

        public node(int position) {
            this.pre = null;
            this.next = null;
            this.position = position;
        }
    }

    private static int kth(node nod, int k, int n) {
        int countL = k, countR = 0;
        final int[] lefts = new int[k];
        final int[] rights = new int[k];
        for (node left = nod; left != left.pre && countL > 0; left = left.pre, countL--) {
            lefts[countL - 1] = left.position - left.pre.position;
        }
        for (node right = nod; right != right.next && countR < k; right = right.next, countR++) {
            rights[countR] = right.next.position - right.position;
        }
        int sums = 0;
        for (int left = 0; left < k; left++) {
            sums += lefts[left] * rights[left];
        }
        return sums;
    }

    public static List<Long> cal(List<one> nums) {
        final List<Long> results = nums.stream()
            .map(one -> {
                final int n = one.powers.size();
                final int k = one.k;
                final node[] nodes = new node[n + 2];
                final Map<Integer, Integer> num_to_pos = new HashMap<>();// 因为是一对一的的满射, 可以用一个数组替代
                for (int i = 1; i <= n; i++) {
                    num_to_pos.put(one.powers.get(i - 1), i);
                }
                nodes[0] = new node(0);
                for (int i = 1; i <= n; i++) {
                    nodes[i] = new node(i);
                }
                nodes[n + 1] = new node(n + 1);
                nodes[0].pre = nodes[0];
                for (int i = 1; i <= n; i++) {
                    nodes[i].pre = nodes[i - 1];
                    nodes[i].next = nodes[i + 1];
                }
                nodes[n + 1].next = nodes[n + 1];
                long sum = 0;
                for (int i = 1; i <= n; i++) { // 遍历左k个, 右k个, 记录长度
                    final int order = num_to_pos.get(i);// from the number, find the position
                    sum += i * kth(nodes[order], k, n);
                    nodes[order].pre.next = nodes[order].next;
                    nodes[order].next.pre = nodes[order].pre;
                    nodes[order].pre = null;
                    nodes[order].next = null;
                }
                return sum;
            })
            .collect(Collectors.toList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<Long> decides) {
        for (var decide : decides) {
            System.out.printf("%d\n", decide);
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
