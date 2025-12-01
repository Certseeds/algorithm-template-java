// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    private static final class InputData {
        final int n;
        final int m;
        final int k;
        final long[] a;
        final long[] b;

        InputData(int n, int m, int k, long[] a, long[] b) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.a = a;
            this.b = b;
        }
    }

    private static final class Item implements Comparable<Item> {
        final long product;
        final int i;
        final int j;

        Item(long product, int i, int j) {
            this.product = product;
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Item other) {
            return Long.compare(this.product, other.product);
        }
    }

    // 手写 MinHeap 数据结构
    private static final class MinHeap {
        private final long[][] heap;
        private int size;

        MinHeap(int capacity) {
            this.heap = new long[capacity][3];
            this.size = 0;
        }

        void add(long product, int i, int j) {
            heap[size][0] = product;
            heap[size][1] = i;
            heap[size][2] = j;
            size++;
            siftUp(size - 1);
        }

        long peekProduct() {
            return heap[0][0];
        }

        int peekI() {
            return (int) heap[0][1];
        }

        int peekJ() {
            return (int) heap[0][2];
        }

        void replaceTop(long product, int i, int j) {
            heap[0][0] = product;
            heap[0][1] = i;
            heap[0][2] = j;
            siftDown(0);
        }

        void removeTop() {
            size--;
            if (size > 0) {
                heap[0][0] = heap[size][0];
                heap[0][1] = heap[size][1];
                heap[0][2] = heap[size][2];
                siftDown(0);
            }
        }

        int size() {
            return size;
        }

        private void siftUp(int idx) {
            while (idx > 0) {
                final int parent = (idx - 1) / 2;
                if (heap[idx][0] < heap[parent][0]) {
                    swap(idx, parent);
                    idx = parent;
                } else {
                    break;
                }
            }
        }

        private void siftDown(int idx) {
            while (true) {
                int smallest = idx;
                final int left = 2 * idx + 1;
                final int right = 2 * idx + 2;
                if ((left < size) && (heap[left][0] < heap[smallest][0])) {
                    smallest = left;
                }
                if ((right < size) && (heap[right][0] < heap[smallest][0])) {
                    smallest = right;
                }
                if (smallest != idx) {
                    swap(idx, smallest);
                    idx = smallest;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            final long t0 = heap[i][0], t1 = heap[i][1], t2 = heap[i][2];
            heap[i][0] = heap[j][0];
            heap[i][1] = heap[j][1];
            heap[i][2] = heap[j][2];
            heap[j][0] = t0;
            heap[j][1] = t1;
            heap[j][2] = t2;
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int n = in.nextInt();
        final int m = in.nextInt();
        final int k = in.nextInt();
        assert ((1 <= n) && (n <= 500_000));
        assert ((1 <= m) && (m <= 500_000));
        assert ((1 <= k) && (k <= Math.min(500_000, (long) n * m)));
        final long[] a = new long[n];
        final long[] b = new long[m];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
            assert ((1 <= a[i]) && (a[i] <= 1_000_000_000L));
        }
        for (int i = 0; i < m; i++) {
            b[i] = in.nextLong();
            assert ((1 <= b[i]) && (b[i] <= 1_000_000_000L));
        }
        return new InputData(n, m, k, a, b);
    }

    // 使用 PriorityQueue 的实现
    public static long[] calWithPQ(InputData data) {
        final int n = data.n;
        final int m = data.m;
        final int k = data.k;
        final long[] a = data.a.clone();
        final long[] b = data.b.clone();

        Arrays.sort(a);
        Arrays.sort(b);

        final var pq = new PriorityQueue<Item>();

        for (int i = 0; i < Math.min(n, k); i++) {
            pq.add(new Item(a[i] * b[0], i, 0));
        }

        final long[] results = new long[k];
        for (int i = 0; i < k; i++) {
            final var top = pq.poll();
            results[i] = top.product;
            final int ai = top.i;
            final int bj = top.j;

            if (bj + 1 < m) {
                pq.add(new Item(a[ai] * b[bj + 1], ai, bj + 1));
            }
        }

        return results;
    }

    // 使用手写 MinHeap 的实现
    public static long[] calWithMinHeap(InputData data) {
        final int n = data.n;
        final int m = data.m;
        final int k = data.k;
        final long[] a = data.a.clone();
        final long[] b = data.b.clone();

        Arrays.sort(a);
        Arrays.sort(b);

        final int heapCapacity = Math.min(k, n);
        final var minHeap = new MinHeap(heapCapacity);

        for (int i = 0; i < Math.min(n, k); i++) {
            minHeap.add(a[i] * b[0], i, 0);
        }

        final long[] results = new long[k];
        for (int i = 0; i < k; i++) {
            results[i] = minHeap.peekProduct();
            final int ai = minHeap.peekI();
            final int bj = minHeap.peekJ();

            if (bj + 1 < m) {
                minHeap.replaceTop(a[ai] * b[bj + 1], ai, bj + 1);
            } else {
                minHeap.removeTop();
            }
        }

        return results;
    }

    // 同时使用两种实现并用 assert 验证结果一致
    public static long[] cal(InputData data) {
        final long[] resultPQ = calWithPQ(data);
        final long[] resultMinHeap = calWithMinHeap(data);

        assert (resultPQ.length == resultMinHeap.length);
        for (int i = 0; i < resultPQ.length; i++) {
            assert (resultPQ[i] == resultMinHeap[i]) : "Mismatch at index " + i;
        }

        return resultPQ;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final long[] results = cal(data);
        output(results);
    }

    public static void output(long[] results) {
        final var sb = new StringBuilder();
        for (int i = 0; i < results.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(results[i]);
        }
        System.out.print(sb);
        System.out.print('\n');
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

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

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
