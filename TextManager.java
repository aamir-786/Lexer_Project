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
