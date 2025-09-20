// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2020-2025 nanoseeds
package producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class lab {

    private static final String sub_pom_top = file_producer.read_file("template/subpom/top.xml");
    private static final String sub_pom_buttom = file_producer.read_file("template/subpom/buttom.xml");
    private final String lab_order;
    private final List<question> ques_order;

    public lab(String lab_order, List<String> ques_order) {
        this.lab_order = lab_order;
        this.ques_order = ques_order.stream()
            .map(x -> new question(lab_order, x))
            .collect(Collectors.toList());
    }

    public String subPom() {
        return String.format("        <module>lab_%1$s</module>\n", lab_order);
    }

    public List<question> getQues_order() {
        return ques_order;
    }

    public void create_dir() {
        file_producer.ensureAndCreate.accept(String.format(constant.LAB_PATH, lab_order));
    }

    public void create_pom() throws IOException {
        final Path of = Path.of(String.format(constant.SUB_POM_FILE, lab_order));
        System.out.println(of);
        if (!Files.exists(of)) {
            Files.createFile(of);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format(sub_pom_top, lab_order));
        for (question ques : ques_order) {
            sb.append(ques.subPom());
        }
        sb.append(sub_pom_buttom);
        Files.writeString(of, sb.toString());
    }

}
