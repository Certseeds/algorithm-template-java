// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    private static final class Data {
        final int n;
        final int[] piles;

        Data(int n, int[] piles) {
            this.n = n;
            this.piles = piles;
        }
    }

    private static final class Node {
        int val;
        final int id;
        Node prev;
        Node next;
        int version;

        Node(int val, int id) {
            this.val = val;
            this.id = id;
            this.version = 0;
        }
    }

    private static final class Item implements Comparable<Item> {
        final int value;
        final int id;
        final Node node;
        final int version;

        Item(int value, int id, Node node, int version) {
            this.value = value;
            this.id = id;
            this.node = node;
            this.version = version;
        }

        @Override
        public int compareTo(Item other) {
            if (this.value != other.value) {
                return Integer.compare(this.value, other.value);
            }
            return Integer.compare(this.id, other.id);
        }
    }

    public static Data reader() throws IOException {
        final var input = new Reader();
        if (!input.hasNext()) {
            return null;
        }
        final int n = input.nextInt();
        assert ((n >= 1) && (n <= 500000));
        final int[] piles = new int[n];
        for (int i = 0; i < n; i++) {
            final int val = input.nextInt();
            assert ((val >= 0) && (val < (1 << 30)));
            piles[i] = val;
        }
        return new Data(n, piles);
    }

    public static int cal(Data data) {
        final int n = data.n;
        final int[] piles = data.piles;
        if (n == 1) {
            return piles[0];
        }
        final Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(piles[i], i);
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
                nodes[i].prev = nodes[i - 1];
            }
        }
        final var pq = new PriorityQueue<Item>();
        for (int i = 0; i < n; i++) {
            final var node = nodes[i];
            pq.add(new Item(node.val, node.id, node, node.version));
        }
        int remaining = n;
        Node lastAlive = null;
        while (remaining > 1) {
            final Item current = pq.poll();
            if (current == null) {
                break;
            }
            final Node node = current.node;
            if (node.version != current.version) {
                continue;
            }
            final Node left = node.prev;
            final Node right = node.next;
            if ((left == null) && (right == null)) {
                lastAlive = node;
                break;
            }
            long leftMergeVal = Long.MIN_VALUE;
            long rightMergeVal = Long.MIN_VALUE;
            if (left != null) {
                leftMergeVal = ((long) left.val ^ (long) node.val) + 1L;
                assert (leftMergeVal < (1L << 31));
            }
            if (right != null) {
                rightMergeVal = ((long) node.val ^ (long) right.val) + 1L;
                assert (rightMergeVal < (1L << 31));
            }
            final boolean useLeft;
            if ((left != null) && (right != null)) {
                if (leftMergeVal == rightMergeVal) {
                    useLeft = true;
                } else {
                    useLeft = leftMergeVal > rightMergeVal;
                }
            } else {
                useLeft = (left != null);
            }
            if (useLeft) {
                final int newVal = (int) leftMergeVal;
                left.val = newVal;
                left.version++;
                pq.add(new Item(left.val, left.id, left, left.version));
                final Node nextNode = node.next;
                left.next = nextNode;
                if (nextNode != null) {
                    nextNode.prev = left;
                }
                node.prev = null;
                node.next = null;
                node.version++;
                lastAlive = left;
            } else {
                final int newVal = (int) rightMergeVal;
                node.val = newVal;
                node.version++;
                pq.add(new Item(node.val, node.id, node, node.version));
                final Node nextOfRight = right.next;
                node.next = nextOfRight;
                if (nextOfRight != null) {
                    nextOfRight.prev = node;
                }
                right.prev = null;
                right.next = null;
                right.version++;
                lastAlive = node;
            }
            remaining--;
        }
        if (lastAlive == null) {
            return piles[0];
        }
        return lastAlive.val;
    }

    public static void main(String[] args) throws IOException {
        final Data data = reader();
        if (data == null) {
            return;
        }
        final int result = cal(data);
        output(result);
    }

    public static void output(int number) {
        System.out.print(number);
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
                final String nextLine = nextLine();
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
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
