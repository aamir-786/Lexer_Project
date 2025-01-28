// Complete code in a single file for running the Lexer project
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

// Token class
class Token {
    private String type;
    private String value;
    private int lineNumber;
    private int columnNumber;

    public Token(String type, String value, int lineNumber, int columnNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Token(type=%s, value=%s, line=%d, col=%d)", type, value, lineNumber, columnNumber);
    }
}

// TextManager class
class TextManager {
    private BufferedReader reader;

    public TextManager(String input) {
        this.reader = new BufferedReader(new StringReader(input));
    }

    public String readWord() throws IOException {
        StringBuilder word = new StringBuilder();
        int character;

        while ((character = reader.read()) != -1) {
            if (Character.isWhitespace(character)) {
                if (character == '\n') {
                    return "\n"; // Return a newline marker
                }
                if (word.length() > 0) {
                    break; // End of a word
                }
            } else {
                word.append((char) character);
            }
        }

        return word.length() > 0 ? word.toString() : null; // Return null when input ends
    }
}

// Lexer class
class Lexer {
    private TextManager textManager;
    private List<Token> tokens;

    // Define keywords as per Tran Language Definition
    private static final String[] KEYWORDS = {"if", "else", "while", "return"};

    public Lexer(String input) {
        this.textManager = new TextManager(input);
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() throws IOException {
        String word;

        while ((word = textManager.readWord()) != null) {
            if ("\n".equals(word)) {
                tokens.add(new Token("NEWLINE", "\n", 0, 0));
            } else if (isKeyword(word)) {
                tokens.add(new Token("KEYWORD", word, 0, 0));
            } else {
                tokens.add(new Token("WORD", word, 0, 0));
            }
        }

        return tokens;
    }

    private boolean isKeyword(String word) {
        for (String keyword : KEYWORDS) {
            if (keyword.equals(word)) {
                return true;
            }
        }
        return false;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        String input = "if hello world\nreturn";
        Lexer lexer = new Lexer(input);

        try {
            List<Token> tokens = lexer.tokenize();
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (Exception e) {
            System.err.println("An error occurred during tokenization: " + e.getMessage());
        }

        // Unit tests
        runTests();
    }

    private static void runTests() {
        System.out.println("Running tests...");

        testSimpleKeyword();
        testMultipleKeywords();
        testWordAndKeyword();
        testNewlines();
        testEmptyInput();

        System.out.println("All tests passed.");
    }

    private static void testSimpleKeyword() {
        Lexer lexer = new Lexer("if");
        try {
            List<Token> tokens = lexer.tokenize();
            assert tokens.size() == 1 : "Expected 1 token";
            assert tokens.get(0).getType().equals("KEYWORD") : "Expected KEYWORD token";
            assert tokens.get(0).getValue().equals("if") : "Expected value 'if'";
        } catch (IOException e) {
            assert false : "Unexpected exception: " + e.getMessage();
        }
    }

    private static void testMultipleKeywords() {
        Lexer lexer = new Lexer("if while return");
        try {
            List<Token> tokens = lexer.tokenize();
            assert tokens.size() == 3 : "Expected 3 tokens";
            assert tokens.get(0).getType().equals("KEYWORD") && tokens.get(0).getValue().equals("if") : "Expected KEYWORD 'if'";
            assert tokens.get(1).getType().equals("KEYWORD") && tokens.get(1).getValue().equals("while") : "Expected KEYWORD 'while'";
            assert tokens.get(2).getType().equals("KEYWORD") && tokens.get(2).getValue().equals("return") : "Expected KEYWORD 'return'";
        } catch (IOException e) {
            assert false : "Unexpected exception: " + e.getMessage();
        }
    }

    private static void testWordAndKeyword() {
        Lexer lexer = new Lexer("if hello world");
        try {
            List<Token> tokens = lexer.tokenize();
            assert tokens.size() == 3 : "Expected 3 tokens";
            assert tokens.get(0).getType().equals("KEYWORD") && tokens.get(0).getValue().equals("if") : "Expected KEYWORD 'if'";
            assert tokens.get(1).getType().equals("WORD") && tokens.get(1).getValue().equals("hello") : "Expected WORD 'hello'";
            assert tokens.get(2).getType().equals("WORD") && tokens.get(2).getValue().equals("world") : "Expected WORD 'world'";
        } catch (IOException e) {
            assert false : "Unexpected exception: " + e.getMessage();
        }
    }

    private static void testNewlines() {
        Lexer lexer = new Lexer("hello\nworld");
        try {
            List<Token> tokens = lexer.tokenize();
            assert tokens.size() == 3 : "Expected 3 tokens";
            assert tokens.get(0).getType().equals("WORD") && tokens.get(0).getValue().equals("hello") : "Expected WORD 'hello'";
            assert tokens.get(1).getType().equals("NEWLINE") && tokens.get(1).getValue().equals("\n") : "Expected NEWLINE";
            assert tokens.get(2).getType().equals("WORD") && tokens.get(2).getValue().equals("world") : "Expected WORD 'world'";
        } catch (IOException e) {
            assert false : "Unexpected exception: " + e.getMessage();
        }
    }

    private static void testEmptyInput() {
        Lexer lexer = new Lexer("");
        try {
            List<Token> tokens = lexer.tokenize();
            assert tokens.size() == 0 : "Expected 0 tokens";
        } catch (IOException e) {
            assert false : "Unexpected exception: " + e.getMessage();
        }
    }
}
