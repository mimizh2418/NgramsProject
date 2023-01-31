import sorting.HybridSort;
import sorting.SortingAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordTable {
    private final HashMap<String, WordEntry> entries;
    private final List<WordEntry> processedEntries;
    private int totalWords;
    private boolean areEntriesProcessed;

    public WordTable() {
        entries = new HashMap<>();
        processedEntries = new ArrayList<>();
        areEntriesProcessed = false;
    }

    public int getNumWords() {
        return totalWords;
    }

    public WordEntry recordWord(String word) {
        totalWords++;
        if (entries.containsKey(word)) {
            entries.get(word).incrementFrequency();
            return entries.get(word);
        }
        else {
            WordEntry entry = new WordEntry(word);
            processedEntries.add(entry);
            entries.put(word, entry);
            return entry;
        }
    }

    public WordEntry findEntry(String word) {
        return entries.get(word);
    }

    public void processEntries() {
        SortingAlgorithm sorter = new HybridSort();
        sorter.sort(processedEntries);
        double currentCumulative = 0;
        for (WordEntry entry : processedEntries) {
            double prob = (double) entry.getFrequency() / (double) totalWords;
            entry.setProbability(prob);
            currentCumulative += prob;
            entry.setCumulativeProbability(currentCumulative);
        }
        areEntriesProcessed = true;
    }

    public WordEntry randomEntry() {
        if (!areEntriesProcessed) processEntries();
        double randNum = Math.random();
        for (WordEntry entry : processedEntries) {
            if (entry.getCumulativeProbability() >= randNum) return entry;
        }
        throw new IllegalStateException("did not find word entry with cumulative probability less than " + randNum);
    }

    public void displayCommonWords(int numWords) {
        displayCommonWords(0, numWords);
    }

    private void displayCommonWords(int depth, int numWords) {
        if (!areEntriesProcessed) processEntries();
        for (int i = processedEntries.size() - 1; i >= processedEntries.size() - numWords; i--) {
            System.out.println("\t".repeat(depth) + processedEntries.get(i));
            if (processedEntries.get(i).getNextWords().getNumWords() > 0) {
                processedEntries.get(i).getNextWords().displayCommonWords(depth + 1, numWords);
            }
        }
    }
}
