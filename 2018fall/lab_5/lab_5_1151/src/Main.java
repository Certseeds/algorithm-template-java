// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private static final int ALPHABET_SIZE = 26;

    static class TrieNode {
        final TrieNode[] children = new TrieNode[ALPHABET_SIZE];
        TrieNode parent; // OPTIMIZED: Use single parent reference instead of a List
        TrieNode fail;
        boolean isEndOfWord;
        int minLen = -1; // Min length to a word
        boolean visited; // OPTIMIZED: For BFS traversal instead of a Map
    }

    public static final class TestCase {
        final List<String> words;
        final String opSeq;

        public TestCase(List<String> words, String opSeq) {
            this.words = words;
            this.opSeq = opSeq;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int n = in.nextInt();
        final List<String> words = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            words.add(in.next());
        }
        final String opSeq = in.next();
        return List.of(new TestCase(words, opSeq));
    }

    public static List<List<Integer>> cal(List<TestCase> inputs) {
        final List<List<Integer>> allResults = new ArrayList<>();
        for (final TestCase tc : inputs) {
            // 1. Build Trie and parent links
            final TrieNode root = new TrieNode();
            int totalLength = 0; // For capacity estimation
            for (final String word : tc.words) {
                TrieNode curr = root;
                totalLength += word.length();
                for (final char ch : word.toCharArray()) {
                    final int index = ch - 'a';
                    if (curr.children[index] == null) {
                        curr.children[index] = new TrieNode();
                        curr.children[index].parent = curr; // OPTIMIZED: Set single parent
                    }
                    curr = curr.children[index];
                }
                curr.isEndOfWord = true;
            }

            // 2. Build Fail links (BFS) and collect all nodes
            final Queue<TrieNode> queue = new ArrayDeque<>();
            // OPTIMIZED: Pre-allocate capacity for allNodes
            final List<TrieNode> allNodes = new ArrayList<>(totalLength + 1);
            final Queue<TrieNode> minLenQueue = new ArrayDeque<>();

            root.visited = true;
            allNodes.add(root);

            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (root.children[i] != null) {
                    root.children[i].fail = root;
                    queue.add(root.children[i]);
                    // OPTIMIZED: Redundant visited check removed, root children are always new
                    root.children[i].visited = true;
                    allNodes.add(root.children[i]);
                } else {
                    root.children[i] = root;
                }
            }

            while (!queue.isEmpty()) {
                final TrieNode curr = queue.poll();
                curr.isEndOfWord |= curr.fail.isEndOfWord; // Propagate end of word

                for (int i = 0; i < ALPHABET_SIZE; i++) {
                    if (curr.children[i] != null) {
                        curr.children[i].fail = curr.fail.children[i];
                        queue.add(curr.children[i]);
                        if (!curr.children[i].visited) {
                            curr.children[i].visited = true;
                            allNodes.add(curr.children[i]);
                        }
                    } else {
                        curr.children[i] = curr.fail.children[i];
                    }
                }
            }

            // 3. BFS from word-end nodes to calculate minLen
            for (final TrieNode node : allNodes) {
                if (node.isEndOfWord) {
                    node.minLen = 0;
                    minLenQueue.add(node);
                }
            }

            while (!minLenQueue.isEmpty()) {
                final TrieNode u = minLenQueue.poll();
                // Traverse backwards using parent links
                final TrieNode v = u.parent;
                if (v != null && v.minLen == -1) {
                    v.minLen = u.minLen + 1;
                    minLenQueue.add(v);
                }
            }


            // 4. Process operations
            final List<Integer> results = new ArrayList<>(tc.opSeq.length() + 1);
            // OPTIMIZED: Use ArrayDeque as a stack for path tracking
            final Deque<TrieNode> path = new ArrayDeque<>();
            path.add(root);
            results.add(root.minLen); // Initial state

            TrieNode currentNode = root;
            for (final char op : tc.opSeq.toCharArray()) {
                if (op == '-') {
                    if (path.size() > 1) {
                        path.removeLast();
                    }
                    currentNode = path.peekLast();
                } else {
                    final int index = op - 'a';
                    currentNode = currentNode.children[index];
                    path.addLast(currentNode);
                }
                results.add(currentNode.minLen);
            }
            allResults.add(results);
        }
        return allResults;
    }

    public static void output(List<List<Integer>> allDecides) {
        // OPTIMIZED: Use StringBuilder for faster I/O
        final StringBuilder sb = new StringBuilder();
        for (final List<Integer> decides : allDecides) {
            for (final int decide : decides) {
                sb.append(decide).append('\n');
            }
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        output(cal(reader()));
    }

    public static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
