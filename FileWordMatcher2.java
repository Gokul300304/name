import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileWordMatcher {

    public static void main(String[] args) throws IOException {
        // Path to your input files
        Path file1Path = Paths.get("C:\\Users\\Administrator\\Downloads\\nginx_input.csv"); // File with comma-separated words
        Path file2Path = Paths.get("C:\\Users\\Administrator\\Downloads\\nginx_dev.log");   // The file to be searched
        Path outputPath = Paths.get("C:\\Users\\Administrator\\Documents\\output.txt");     // Path for the output file

        // Read File 1 into a list of word pairs
        List<String[]> wordPairs = readWordPairsFromFile(file1Path);

        // Open a BufferedWriter to write to output.txt
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            Files.lines(file2Path).forEach(line -> {
                boolean matched = false;

                // Check each word pair from the wordPairs list
                for (String[] pair : wordPairs) {
                    String word1 = pair[0];
                    String word2 = pair[1];

                    // Check if both words are present in the line
                    if (line.contains(word1) && line.contains(word2)) {
                        matched = true;
                        break; // If one pair matches, no need to check further
                    }
                }

                // Write the line with match status to the output file
                try {
                    if (matched) {
                        writer.write("\"" + line + "\" - Matched\n");
                    } else {
                        writer.write("\"" + line + "\" - Unmatched\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read word pairs from File 1
    private static List<String[]> readWordPairsFromFile(Path filePath) throws IOException {
        List<String[]> wordPairs = new ArrayList<>();

        // Read all lines from file and process them
        Files.lines(filePath).forEach(line -> {
            String[] words = line.split(",");
            if (words.length == 2) {
                wordPairs.add(new String[]{words[0].trim(), words[1].trim()});
            }
        });

        return wordPairs;
    }
}
