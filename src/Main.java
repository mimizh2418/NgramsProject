import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NGramModel reader = new NGramModel(4, new File("textfiles/books"));
        reader.displayStats();
        reader.displayRandomText(500);
    }
}