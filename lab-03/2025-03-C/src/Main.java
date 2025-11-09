// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        assert ((1 <= testCount) && (testCount <= 20));
        final CaseData[] cases = new CaseData[testCount];
        for (int index = 0; index < testCount; index++) {
            final int commandLength = input.nextInt();
            assert ((20 <= commandLength) && (commandLength <= 100_000));
            final String commandLine = input.next();
            assert (commandLine.length() == commandLength);
            cases[index] = new CaseData(commandLine.toCharArray());
        }
        return new InputData(cases);
    }

    public static ResultData cal(InputData data) {
        final String[] lines = new String[data.cases.length];
        for (int caseIndex = 0; caseIndex < data.cases.length; caseIndex++) {
            final var commands = data.cases[caseIndex].commands;
            final var head = new Node('\0');
            final var tail = new Node('\0');
            head.next = tail;
            tail.prev = head;
            Node cursor = tail;
            boolean replaceMode = false;
            for (char command : commands) {
                switch (command) {
                    case 'r':
                        replaceMode = true;
                        break;
                    case 'I':
                        cursor = head.next;
                        if (cursor == null) {
                            cursor = tail;
                        } else if (cursor == tail) {
                            cursor = tail;
                        }
                        break;
                    case 'H':
                        if (cursor.prev != null && cursor.prev != head) {
                            cursor = cursor.prev;
                        }
                        break;
                    case 'L':
                        if (cursor != tail) {
                            cursor = cursor.next;
                            if (cursor == null) {
                                cursor = tail;
                            }
                        }
                        break;
                    case 'x':
                        if (cursor == tail) {
                            break;
                        }
                        final Node toRemove = cursor;
                        cursor = cursor.next;
                        unlink(toRemove);
                        if (cursor == null) {
                            cursor = tail;
                        }
                        break;
                    default:
                        if (replaceMode) {
                            if (cursor == tail) {
                                final var node = new Node(command);
                                insertBefore(tail, node);
                                cursor = node;
                            } else {
                                cursor.value = command;
                            }
                            replaceMode = false;
                        } else {
                            final var node = new Node(command);
                            insertBefore(cursor, node);
                        }
                        break;
                }
            }
            final var builder = new StringBuilder();
            Node iteration = head.next;
            while (iteration != null && iteration != tail) {
                builder.append(iteration.value);
                iteration = iteration.next;
            }
            lines[caseIndex] = builder.toString();
        }
        return new ResultData(lines);
    }

    private static void insertBefore(Node target, Node node) {
        final Node previous = target.prev;
        node.prev = previous;
        node.next = target;
        previous.next = node;
        target.prev = node;
    }

    private static void unlink(Node node) {
        final Node previous = node.prev;
        final Node nextNode = node.next;
        if (previous != null) {
            previous.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = previous;
        }
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        for (String line : result.lines) {
            builder.append(line).append('\n');
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
        private final char[] commands;

        private CaseData(char[] commands) {
            this.commands = commands;
        }
    }

    public static final class ResultData {
        private final String[] lines;

        private ResultData(String[] lines) {
            this.lines = lines;
        }
    }

    private static final class Node {
        private char value;
        private Node prev;
        private Node next;

        private Node(char value) {
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
