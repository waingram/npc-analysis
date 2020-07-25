package edu.vt.cs.etd;

import com.google.common.collect.ImmutableSet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NeuralParsCitEvaluation {

    private static final Set<String> EMPTYS = ImmutableSet.of("", ",", ".", ":");
    private static final Set<String> FIELDS = ImmutableSet.of(
            "author",
            "booktitle",
            "date",
            "editor",
            "institution",
            "journal",
            "location",
            "note",
            "pages",
            "publisher",
            "title",
            "volume");

    public static void main(String[] args) {
        try {
            String npcPath = "/Users/waingram/Projects/npc-analysis/data/neural_parscit_parsed_citations.txt";

            String pathCitations = "/Users/waingram/Projects/npc-analysis/data/sample17-ran100-v2/sample17-ran100-v2.citestr_combined.txt";
            String pathResult = "results/npc_analysis.txt";

            Path citationsFile = Paths.get(pathCitations);
            Path npcFile = Paths.get(npcPath);

            List<String> npcLines = Files.lines(npcFile).collect(Collectors.toList());
            Iterator<String> npcIterator = npcLines.iterator();

            Path resultsFile = Paths.get(pathResult);
            if (Files.exists(resultsFile)) {
                Files.delete(resultsFile);
            }

            int count = 0;
            int index = 0;
            List<String> references = Files.lines(citationsFile).collect(Collectors.toList());
            for (String reference : references) {

                if (index == 2) index++;

                Path annPath = Paths.get(citationsFile.getParent().toString(),
                        citationsFile.getFileName().toString()
                                .replace("combined", String.format("%02d", index++))
                                .replace(".txt", ".ann"));

                List<String> groundTruth = new ArrayList<>();
                Files.lines(annPath).forEach(groundTruth::add);
                groundTruth.sort(Comparator.comparing(l -> Integer.parseInt(l.split("\\t")[1].split("\\s")[1])));

                for (String groundTruthLine : groundTruth) {
                    String[] parts = groundTruthLine.split("\\t");
                    String trueLabel = parts[1].split("\\s")[0];
                    if (trueLabel.equals("authors")) trueLabel = "author";
                    if (trueLabel.equals("address")) trueLabel = "location";
                    if (trueLabel.equals("year")) trueLabel = "date";
                    String value = parts[2];
                    if (trueLabel.equals("year_extra")) continue;
                    String[] gtTokens = value.split("[\\s\"“”]+");

                    for (String trueValue : gtTokens) {
                        if (EMPTYS.contains(trueValue)) {
                            continue;
                        }
                        if (npcIterator.hasNext()) {

                            String[] npcTokens = npcIterator.next().split("\t");
                            String predictedValue = npcTokens[0];
                            String predictedLabel = npcTokens[1];

                            if (!FIELDS.contains(trueLabel)) continue;

                            Files.write(resultsFile, (String.format("%s\t%s\t%s\t%s%s",
                                    trueValue,
                                    predictedValue,
                                    trueLabel,
                                    predictedLabel,
                                    System.lineSeparator())).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

                        } else {
                            break;
                        }
                    }
                }

                Files.write(resultsFile, (System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

//                if (++count > 3) break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}