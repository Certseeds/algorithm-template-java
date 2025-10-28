// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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
        assert ((1 <= testCount) && (testCount <= 5));
        final CaseData[] cases = new CaseData[testCount];
        for (int caseIndex = 0; caseIndex < testCount; caseIndex++) {
            final int memberCount = input.nextInt();
            final int orderCount = input.nextInt();
            assert ((1 <= memberCount) && (memberCount <= 100_000));
            assert ((1 <= orderCount) && (orderCount <= 100_000));
            final int[] formation = new int[memberCount];
            final boolean[] seen = new boolean[memberCount];
            for (int index = 0; index < memberCount; index++) {
                final int memberId = input.nextInt();
                assert ((0 <= memberId) && (memberId < memberCount));
                assert (!seen[memberId]);
                seen[memberId] = true;
                formation[index] = memberId;
            }
            final Operation[] operations = new Operation[orderCount];
            for (int orderIndex = 0; orderIndex < orderCount; orderIndex++) {
                final int firstStart = input.nextInt();
                final int firstEnd = input.nextInt();
                final int secondStart = input.nextInt();
                final int secondEnd = input.nextInt();
                assert ((0 <= firstStart) && (firstStart < memberCount));
                assert ((0 <= firstEnd) && (firstEnd < memberCount));
                assert ((0 <= secondStart) && (secondStart < memberCount));
                assert ((0 <= secondEnd) && (secondEnd < memberCount));
                operations[orderIndex] = new Operation(firstStart, firstEnd, secondStart, secondEnd);
            }
            cases[caseIndex] = new CaseData(memberCount, formation, operations);
        }
        return new InputData(cases);
    }

    public static ResultData cal(InputData data) {
        final var cases = data.cases;
        final int[][] sequences = new int[cases.length][];
        for (int caseIndex = 0; caseIndex < cases.length; caseIndex++) {
            final var caseData = cases[caseIndex];
            final int memberCount = caseData.memberCount;
            final Map<Integer, Node> nodeMap = new HashMap<>(memberCount * 2);
            Node head = null;
            Node previous = null;
            for (int index = 0; index < memberCount; index++) {
                final int id = caseData.formation[index];
                final var node = new Node(id);
                nodeMap.put(id, node);
                if (head == null) {
                    head = node;
                }
                if (previous != null) {
                    previous.next = node;
                    node.prev = previous;
                }
                previous = node;
            }
            for (final var operation : caseData.operations) {
                final Node firstStartNode = nodeMap.get(operation.firstStart);
                final Node firstEndNode = nodeMap.get(operation.firstEnd);
                final Node secondStartNode = nodeMap.get(operation.secondStart);
                final Node secondEndNode = nodeMap.get(operation.secondEnd);
                assert (firstStartNode != null);
                assert (firstEndNode != null);
                assert (secondStartNode != null);
                assert (secondEndNode != null);
                head = swapSegments(head, firstStartNode, firstEndNode, secondStartNode, secondEndNode);
            }
            final int[] finalOrder = new int[memberCount];
            Node cursor = head;
            for (int index = 0; index < memberCount; index++) {
                assert (cursor != null);
                finalOrder[index] = cursor.id;
                cursor = cursor.next;
            }
            sequences[caseIndex] = finalOrder;
        }
        return new ResultData(sequences);
    }

    private static Node swapSegments(Node head, Node firstStart, Node firstEnd, Node secondStart, Node secondEnd) {
        final Node beforeFirst = firstStart.prev;
        final Node afterFirst = firstEnd.next;
        final Node beforeSecond = secondStart.prev;
        final Node afterSecond = secondEnd.next;
        final boolean adjacent = afterFirst == secondStart;

        if (beforeFirst != null) {
            beforeFirst.next = secondStart;
        } else {
            head = secondStart;
        }
        secondStart.prev = beforeFirst;

        if (adjacent) {
            secondEnd.next = firstStart;
            firstStart.prev = secondEnd;
        } else {
            secondEnd.next = afterFirst;
            if (afterFirst != null) {
                afterFirst.prev = secondEnd;
            }
            if (beforeSecond != null) {
                beforeSecond.next = firstStart;
            } else {
                head = firstStart;
            }
            firstStart.prev = beforeSecond;
        }

        firstEnd.next = afterSecond;
        if (afterSecond != null) {
            afterSecond.prev = firstEnd;
        }

        return head;
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        for (final var sequence : result.sequences) {
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
        private final int memberCount;
        private final int[] formation;
        private final Operation[] operations;

        private CaseData(int memberCount, int[] formation, Operation[] operations) {
            this.memberCount = memberCount;
            this.formation = formation;
            this.operations = operations;
        }
    }

    public static final class Operation {
        private final int firstStart;
        private final int firstEnd;
        private final int secondStart;
        private final int secondEnd;

        private Operation(int firstStart, int firstEnd, int secondStart, int secondEnd) {
            this.firstStart = firstStart;
            this.firstEnd = firstEnd;
            this.secondStart = secondStart;
            this.secondEnd = secondEnd;
        }
    }

    public static final class ResultData {
        private final int[][] sequences;

        private ResultData(int[][] sequences) {
            this.sequences = sequences;
        }
    }

    private static final class Node {
        private final int id;
        private Node prev;
        private Node next;

        private Node(int id) {
            this.id = id;
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
                    st = new StringTokenizer(br.readLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
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
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return str;
        }
    }
}
