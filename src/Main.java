import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NGramReader reader = new NGramReader(3, "textfiles/Red Headed League.txt");
        reader.displayStats();
        reader.displayRandomText(500);
    }
}