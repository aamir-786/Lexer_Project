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
