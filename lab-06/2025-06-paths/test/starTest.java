// SPDX-License-Identifier: Apache-2.0

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class starTest {
    @Test
    public void addEdgeShouldPreserveForwardStarOrder() {
        final var star = new star(4, 4);
        final int first = star.addEdge(1, 2, 7);
        final int second = star.addEdge(1, 3, 5);
        final int third = star.addEdge(2, 4, 11);
        Assertions.assertEquals(3, star.edgeCount());
        Assertions.assertEquals(third, star.head(2));
        Assertions.assertEquals(2, star.to(first));
        Assertions.assertEquals(7, star.weight(first));
        Assertions.assertEquals(3, star.to(second));
        Assertions.assertEquals(5, star.weight(second));
        final int[] adjacency = new int[2];
        int index = 0;
        for (int edge = star.head(1); edge != -1; edge = star.next(edge)) {
            adjacency[index++] = star.to(edge);
        }
        Assertions.assertArrayEquals(new int[]{3, 2}, adjacency);
    }

    @Test
    public void edgeIndicesShouldIncreaseSequentially() {
        final var star = new star(3, 3);
        Assertions.assertEquals(0, star.edgeCount());
        final int e0 = star.addEdge(1, 2, 1);
        final int e1 = star.addEdge(2, 3, 2);
        final int e2 = star.addEdge(3, 1, 3);
        Assertions.assertEquals(0, e0);
        Assertions.assertEquals(1, e1);
        Assertions.assertEquals(2, e2);
        Assertions.assertEquals(3, star.edgeCount());
        Assertions.assertEquals(1, star.weight(e0));
        Assertions.assertEquals(2, star.weight(e1));
        Assertions.assertEquals(3, star.weight(e2));
    }
}
