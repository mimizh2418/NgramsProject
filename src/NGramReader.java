import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NGramReader {
    private final WordTable table;
    private final int n;

    public NGramReader(int n, String filename) throws IOException {
        List<String> wordList = TextReader.readFile(filename);
        table = new WordTable();
        this.n  = n;

        LinkedList<String> prevWords = new LinkedList<>();
        for (String word : wordList) {
            addNewWord(table, prevWords, word);
            prevWords.add(word);
            if (prevWords.size() >= n) prevWords.remove(0);
        }
    }

    public void displayStats() {
        table.displayCommonWords(3);
    }

    public void displayRandomText(int numWords) {
        LinkedList<String> prevWords = new LinkedList<>();
        int maxLineLength = 100;
        int currentLineLength = 0;
        for (int i = 0; i < numWords; i++) {
            String generatedWord = generateRandomWord(table, prevWords);
            if (generatedWord.length() + currentLineLength + 1 > maxLineLength) {
                System.out.println();
                currentLineLength = 0;
            }
            currentLineLength += generatedWord.length();
            System.out.print(generatedWord + " ");
            prevWords.add(generatedWord);
            if (prevWords.size() >= n) prevWords.remove(0);
        }
    }

    private String generateRandomWord(WordTable table, List<String> prevWords) {
        if (prevWords.size() == 0) return table.randomEntry().getWord();
        return  generateRandomWord(table.findEntry(prevWords.get(0)).getNextWords(), prevWords.subList(1, prevWords.size()));
    }

    private void addNewWord(WordTable table, List<String> prevWords, String word) {
        if (prevWords.isEmpty()) {
            table.recordWord(word);
            return;
        }
        addNDepthWord(table, prevWords, word);
        addNewWord(table, prevWords.subList(1, prevWords.size()), word);
    }

    private void addNDepthWord(WordTable table, List<String> prevWords, String word) {
        if (prevWords.isEmpty()) table.recordWord(word);
        else addNDepthWord(table.findEntry(prevWords.get(0)).getNextWords(), prevWords.subList(1, prevWords.size()), word);
    }
}
