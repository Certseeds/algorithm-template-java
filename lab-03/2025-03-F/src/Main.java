// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public final class Main {

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final var result = cal(data);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int testCount = input.nextInt();
        assert ((1 <= testCount) && (testCount <= 10));
        final CaseData[] cases = new CaseData[testCount];
        for (int caseIndex = 0; caseIndex < testCount; caseIndex++) {
            final int length = input.nextInt();
            assert ((1 <= length) && (length <= 100_000));
            final int[] values = new int[length];
            for (int index = 0; index < length; index++) {
                final int value = input.nextInt();
                assert ((1 <= value) && (value <= 100_000));
                values[index] = value;
            }
            cases[caseIndex] = new CaseData(values);
        }
        return new InputData(cases);
    }

    public static ResultData cal(InputData data) {
        final int[][] sequences = new int[data.cases.length][];
        for (int caseIndex = 0; caseIndex < data.cases.length; caseIndex++) {
            final var caseData = data.cases[caseIndex];
            final var list = LinkedList.fromArray(caseData.values);
            Deque<Node> workQueue = list.initialCandidates();
            while (!workQueue.isEmpty()) {
                final Deque<Node> currentRound = new ArrayDeque<>();
                final Set<Node> seen = new HashSet<>();
                while (!workQueue.isEmpty()) {
                    final Node node = workQueue.removeFirst();
                    if (!seen.add(node)) {
                        continue;
                    }
                    if (!list.isPresent(node)) {
                        continue;
                    }
                    if (list.shouldRemove(node)) {
                        currentRound.addLast(node);
                    }
                }
                if (currentRound.isEmpty()) {
                    break;
                }
                final Deque<Node> nextRound = new ArrayDeque<>();
                final Set<Node> nextSeen = new HashSet<>();
                while (!currentRound.isEmpty()) {
                    final Node node = currentRound.removeFirst();
                    final Node previous = node.prev;
                    final Node next = node.next;
                    list.unlink(node);
                    if ((previous != list.head) && (previous != null) && list.shouldRemove(previous)
                        && nextSeen.add(previous)) {
                        nextRound.addLast(previous);
                    }
                    if ((next != list.tail) && (next != null) && list.shouldRemove(next)
                        && nextSeen.add(next)) {
                        nextRound.addLast(next);
                    }
                }
                workQueue = nextRound;
            }
            sequences[caseIndex] = list.toArray();
        }
        return new ResultData(sequences);
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        for (int[] sequence : result.sequences) {
            for (int index = 0; index < sequence.length; index++) {
                builder.append(sequence[index]);
                if (index + 1 < sequence.length) {
                    builder.append(' ');
                }
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    public static final class InputData {
        private final CaseData[] cases;

        private InputData(CaseData[] cases) {
            this.cases = cases;
        }
    }

    public static final class CaseData {
        private final int[] values;

        private CaseData(int[] values) {
            this.values = values;
        }
    }

    public static final class ResultData {
        private final int[][] sequences;

        private ResultData(int[][] sequences) {
            this.sequences = sequences;
        }
    }

    private static final class LinkedList {
        private final Node head;
        private final Node tail;

        private LinkedList() {
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
        }

        private static LinkedList fromArray(int[] values) {
            final var list = new LinkedList();
            Node previous = list.head;
            for (int value : values) {
                final var node = new Node(value);
                previous.next = node;
                node.prev = previous;
                previous = node;
            }
            previous.next = list.tail;
            list.tail.prev = previous;
            return list;
        }

        private Deque<Node> initialCandidates() {
            final Deque<Node> queue = new ArrayDeque<>();
            Node current = head.next;
            while (current != tail) {
                if (shouldRemove(current)) {
                    queue.addLast(current);
                }
                current = current.next;
            }
            return queue;
        }

        private boolean shouldRemove(Node node) {
            final Node previous = node.prev;
            final Node next = node.next;
            if ((previous == head) && (next == tail)) {
                return false;
            }
            if (previous == head) {
                return next.value < node.value;
            }
            if (next == tail) {
                return previous.value > node.value;
            }
            return (previous.value > node.value) || (node.value > next.value);
        }

        private boolean isPresent(Node node) {
            return (node.prev != null) && (node.next != null);
        }

        private void unlink(Node node) {
            final Node previous = node.prev;
            final Node next = node.next;
            previous.next = next;
            next.prev = previous;
            node.prev = null;
            node.next = null;
        }

        private int[] toArray() {
            int length = 0;
            Node current = head.next;
            while (current != tail) {
                length++;
                current = current.next;
            }
            final int[] result = new int[length];
            current = head.next;
            for (int index = 0; index < length; index++) {
                result[index] = current.value;
                current = current.next;
            }
            return result;
        }
    }

    private static final class Node {
        private final int value;
        private Node prev;
        private Node next;

        private Node(int value) {
            this.value = value;
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
            while ((st == null) || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) {
                        return "";
                    }
                    st = new StringTokenizer(line);
                } catch (IOException exception) {
                    throw new IllegalStateException(exception);
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
            try {
                return br.readLine();
            } catch (IOException exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
