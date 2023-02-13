import utils.Timer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NGramModel {
    private final WordTable table;
    private final int n;

    /**
     * Creates a new {@link NGramModel}, with n being the specified integer and reads text from the specified file. If
     * the specified file is a directory, recursively traverse the directory and record the words in each {@code .txt}
     * file.
     * @param n the depth of this reader
     * @param file the file/directory to read from
     * @throws IOException when the file is not found
     */
    public NGramModel(int n, File file) throws IOException {
        if (!file.isDirectory() && !file.getName().endsWith("txt")) throw new IllegalArgumentException("given file is not a .txt file");
        System.out.println("Reading text...");
        Timer timer = new Timer();
        List<List<String>> listWordList = TextReader.readFiles(file);
        System.out.printf("Done reading text (%dms).%n", timer.getTimeElapsed());
        table = new WordTable();
        this.n  = n;

        System.out.println("Processing text...");
        timer.reset();
        for (List<String> wordList : listWordList) {
            LinkedList<String> prevWords = new LinkedList<>();
            for (String word : wordList) {
                addNewWord(table, prevWords, word);
                prevWords.add(word);
                if (prevWords.size() >= n) prevWords.remove(0);
            }
        }
        System.out.printf("Done processing text (%dms).%n", timer.getTimeElapsed());
    }

    public void displayStats() {
        table.displayCommonWords(3);
    }

    /**
     * Displays random text based on the text previously read
     * @param numWords the number of words to display
     */
    public void displayRandomText(int numWords) {
        LinkedList<String> prevWords = new LinkedList<>();
        int maxLineLength = 100;
        int currentLineLength = 0;
        for (int i = 0; i < numWords; i++) {
            String generatedWord;
            while (true) {
                generatedWord = generateRandomWord(table, prevWords);
                if (generatedWord == null) prevWords.removeFirst();
                else break;
            }
            if (generatedWord.length() + currentLineLength > maxLineLength) {
                System.out.println();
                currentLineLength = 0;
            }
            currentLineLength += generatedWord.length();
            System.out.print(generatedWord + " ");
            prevWords.add(generatedWord);
            if (prevWords.size() >= n) prevWords.remove(0);
        }
    }

    /**
     * Recursively generates a random word from a given {@link WordTable} and list of previous words
     * @param table the table to start from
     * @param prevWords the list of previously generated words (empty if no words have been generated)
     * @return the word that was generated, {@code null} if there is no such word
     */
    private String generateRandomWord(WordTable table, List<String> prevWords) {
        if (table.getNumWords() <= 0) return null;
        if (prevWords.size() == 0) return table.randomEntry().getWord();
        return generateRandomWord(table.findEntry(prevWords.get(0)).getNextWords(), prevWords.subList(1, prevWords.size()));
    }

    /**
     * Adds a new word to the specified {@link WordTable} and to the sub tables of the sequence of previous words, to
     * the appropriate depth
     * @param table the base table to add the word to.
     * @param prevWords the words that appeared before the specified word (empty
     * @param word the word to be added
     */
    private void addNewWord(WordTable table, List<String> prevWords, String word) {
        if (prevWords.isEmpty()) {
            table.recordWord(word);
            return;
        }
        addNDepthWord(table, prevWords, word);
        addNewWord(table, prevWords.subList(1, prevWords.size()), word);
    }


    /**
     * Recursively goes through the sequence of previous words, finding the sub table with the depth of this sequence,
     * and adds the specified word to it.
     * @param table the base table to start from
     * @param prevWords the sequence of previous words
     * @param word the word to be added
     */
    private void addNDepthWord(WordTable table, List<String> prevWords, String word) {
        if (prevWords.isEmpty()) table.recordWord(word);
        else addNDepthWord(table.findEntry(prevWords.get(0)).getNextWords(), prevWords.subList(1, prevWords.size()), word);
    }
}
