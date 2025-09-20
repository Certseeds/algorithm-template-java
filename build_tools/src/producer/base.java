// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2020-2025 nanoseeds
package producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class base {
    private final List<lab> labs;

    public base(List<lab> labs) {
        this.labs = labs;
    }

    private static final String basePomTop = file_producer.read_file("template/parent/top.xml");
    private static final String basePomButtom = file_producer.read_file("template/parent/buttom.xml");

    public void writeFile() throws IOException {
        final Path of = Path.of(constant.PARENT_POM_FILE);
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format(basePomTop, labs));
        for (lab order : labs) {
            sb.append(order.subPom());
        }
        sb.append(basePomButtom);
        Files.writeString(of, sb.toString());
    }
}
