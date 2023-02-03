/**
 * The {@code WordEntry} class stores statistics about a word in a
 * given text. It stores the word, the frequency of this word, and the
 * probability of this word occurring in the text.
 */

public class WordEntry implements Comparable<WordEntry> {

    private final String word;
    private int freq;
    private double prob;
    private double cumProb;
    private final WordTable nextWords;

    public WordEntry(String word) {
        this.word = word;
        freq = 1;
        nextWords = new WordTable();
    }

    public void incrementFrequency() {
        freq++;
    }

    public int getFrequency() {
        return freq;
    }

    public void setProbability(double prob) {
        this.prob = prob;
    }

    public double getProbability() {
        return prob;
    }

    public void setCumulativeProbability(double cumProb) {
        this.cumProb = cumProb;
    }

    public double getCumulativeProbability() {
        return cumProb;
    }

    public String getWord() {
        return word;
    }

    public WordTable getNextWords() {
        return nextWords;
    }

    @Override
    public int compareTo(WordEntry o) {
        return Integer.compare(freq, o.freq);
    }

    @Override
    public String toString() {
        return String.format("%s: %d frequency (%f probability)",
                word, freq, prob);
    }
}
