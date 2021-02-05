import java.util.LinkedHashSet;
import java.util.Set;

public class Field {
    char[][] cells;
    final int SIZE = 9;

    public Field(String inputLine) throws Exception {

        cells = new char[SIZE][SIZE];
        inputLine = inputLine.replaceAll("I", "|");
        inputLine = inputLine.replaceAll("_", "-");

        String[] inputLines = inputLine.split("\n");

        if (inputLines.length == 0) {
            throw new Exception("After entering the number of mines, you must output the playing field");
        }
        checkCorrectField(inputLines);

        for (int i = 2; i < SIZE + 2; i++) {
            String fieldLine = inputLines[i];

            char[] fieldCells = fieldLine.substring(2, fieldLine.length() - 1).toCharArray();
            for (char each : fieldCells) {
                if (each != '.') {
                    throw new Exception("The playing field at the beginning should consist only of dot symbols.\n" +
                            "You have the symbol " + each);
                }
            }
        }
    }

    public void checkCorrectField(String[] inputLines) throws Exception {

        if (inputLines[0].contains("|123456789|")) {
            throw new Exception("You must number the field header with numbers from 1 to 9");
        }

        if (!inputLines[1].contains("—|———") && inputLines[SIZE + 2].contains("—|———")) {
            throw new Exception("You must place the game board inside a frame of symbols |---------|");
        }

        for (int i = 2; i < SIZE + 2; i++) {
            String fieldLine = inputLines[i];
            if (fieldLine.isBlank()) {
                throw new Exception("The playing field should not be empty!");
            }

            if (fieldLine.charAt(0) != Character.forDigit(i - 1, 10)) {
                throw new Exception("The lines of the playing field must be numbered from 1 to 9\n" +
                        "Your string starts with the character: " + fieldLine.charAt(0));
            }

            if (fieldLine.charAt(1) != '|' && fieldLine.charAt(fieldLine.length() - 1) != '|') {
                throw new Exception("Each line of the playing field must be inside the \\`|\\` characters, like |........|");
            }


            if (fieldLine.length() < SIZE + 3) {
                throw new Exception("Each line of the playing field must contain 9 cells!\n" +
                        "Your line of the playing field is " + (fieldLine.length() - 3) + " cells.");
            }
        }
    }

    public void checkFieldExist(String inputLine) throws Exception {

        String[] inputLines = inputLine.split("\n");

        if (inputLines.length == 0) {
            throw new Exception("The playing field was not found after entering the command.");
        }

        checkCorrectField(inputLines);

    }

    public void parseField(String inputLine) {
        String[] inputLines = inputLine.split("\n");
        for (int i = 2; i < SIZE + 2; i++) {
            String fieldLine = inputLines[i];
            cells[i - 2] = fieldLine.substring(2, fieldLine.length() - 1).toCharArray();
        }
    }

    public void checkFirstOpenField(int mines, String inputLine) throws Exception {
        parseField(inputLine);
        Set<Character> usedChars = new LinkedHashSet<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                usedChars.add(cells[i][j]);
            }
        }
        if (usedChars.size() > (2 + mines)) {
            throw new Exception("At this stage, the playing field should only consist of "
                    + (2 + mines) + " different symbols");
        }
    }
}
