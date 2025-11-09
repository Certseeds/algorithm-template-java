// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

// ref <https://acm.sustech.edu.cn/onlinejudge/problem.php?id=1133>
public final class Main {
    private static final class one {
        private final List<Integer> rooms;
        private final List<Integer> dList;
        private final List<Integer> sList;
        private final List<Integer> tList;

        public one(List<Integer> rooms, List<Integer> dList, List<Integer> sList, List<Integer> tList) {
            this.rooms = rooms;
            this.dList = dList;
            this.sList = sList;
            this.tList = tList;
        }
    }

    private static final class SegTreeLazyRangeSet {
        // dj max value is 1_000_000_000, so huge, so use long
        private final long[] lazy;
        // private final long[]  tree;
        private final long[] minn;
        private final List<Integer> arr;

        private void maintain(int cl, int cr, int p) {
            if (cl != cr && lazy[p] != 0) {
                lazy[p * 2] += lazy[p];
                lazy[p * 2 + 1] += lazy[p];
                minn[p * 2] += lazy[p];
                minn[p * 2 + 1] += lazy[p];
                lazy[p] = 0;
            }
        }


        private long queryMin(int l, int r, int cl, int cr, int p) {
            if (l <= cl && cr <= r) {
                return minn[p];
            }
            final int m = cl + (cr - cl) / 2;
            long minn = Integer.MAX_VALUE;
            maintain(cl, cr, p);
            if (l <= m) {
                minn = Math.min(minn, queryMin(l, r, cl, m, p * 2));
            }
            if (r > m) {
                minn = Math.min(minn, queryMin(l, r, m + 1, cr, p * 2 + 1));
            }
            return minn;
        }

        private void range_add(int l, int r, int val, int cl, int cr, int p) {
            if (l <= cl && cr <= r) {
                lazy[p] += val;
                minn[p] += val;
                return;
            }
            final int m = cl + (cr - cl) / 2;
            maintain(cl, cr, p);
            if (l <= m) {
                range_add(l, r, val, cl, m, p * 2);
            }
            if (r > m) {
                range_add(l, r, val, m + 1, cr, p * 2 + 1);
            }
            // tree[p] = tree[p * 2] + tree[p * 2 + 1];
            minn[p] = Math.min(minn[p * 2], minn[p * 2 + 1]);
        }

        private void build(int s, int t, int p) {
            if (s == t) {
                // tree[p] = arr[s];
                minn[p] = arr.get(s);
                return;
            }
            final int m = s + (t - s) / 2;
            build(s, m, p * 2);
            build(m + 1, t, p * 2 + 1);
            // tree[p] = tree[p * 2] + tree[p * 2 + 1];
            minn[p] = Math.min(minn[p * 2], minn[p * 2 + 1]);
        }

        private final int root, end;

        SegTreeLazyRangeSet(List<Integer> v) {
            final int n = v.size();
            final int n4 = n * 4;
            // tree = new long[n4];
            lazy = new long[n4];
            minn = new long[n4];
            arr = v;
            end = n - 1;
            root = 1;
            build(0, end, 1);
        }

        // public int range_sum(int l, int r) {return range_sum(l, r, 0, end, root);}

        public long queryMin(int l, int r) {
            return queryMin(l, r, 0, end, root);
        }

        public void range_add(int l, int r, int val) {
            range_add(l, r, val, 0, end, root);
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final List<one> cases = new ArrayList<>();
        while (input.hasNext()) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 1000_000));
            assert ((1 <= m) && (m <= 1000_000));
            final var rooms = new ArrayList<Integer>(n);
            final var dList = new ArrayList<Integer>(m);
            final var sList = new ArrayList<Integer>(m);
            final var tList = new ArrayList<Integer>(m);
            for (int i = 0; i < n; i++) {
                final int r = input.nextInt();
                assert ((0 <= r) && (r <= 1000_000_000));
                rooms.add(r);
            }
            for (int i = 0; i < m; i++) {
                final int dj = input.nextInt();
                final int sj = input.nextInt();
                final int tj = input.nextInt();
                assert ((0 <= dj) && (dj <= 1000_000_000));
                assert ((1 <= sj) && (sj <= tj) && (tj <= n));
                dList.add(dj);
                sList.add(sj);
                tList.add(tj);
            }
            cases.add(new one(rooms, dList, sList, tList));
        }
        return cases;
    }

    public static List<Integer> cal(List<one> nums) {
        final List<Integer> results = nums.stream().map(
            one -> {
                final SegTreeLazyRangeSet set = new SegTreeLazyRangeSet(one.rooms);
                final int operators = one.dList.size();
                final int rooms = one.rooms.size() - 1;
                for (int i = 0; i < operators; i++) {
                    set.range_add(one.sList.get(i) - 1, one.tList.get(i) - 1, -1 * one.dList.get(i));
                    final long minn = set.queryMin(0, rooms);
                    if (minn < 0) {
                        return i + 1;
                    }
                }
                return 0;
            }).collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Integer> decides) {
        for (var decide : decides) {
            if (decide == 0) {
                System.out.print("0\n");
            } else {
                System.out.print("-1\n");
                // output the elements in decide sperate by " "
                System.out.printf("%d\n", decide);
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
            st = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
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
