import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class MinesweeperTest extends StageTest {

    Field field;


    @DynamicTest (timeLimit = 10000)
    CheckResult test1() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();

        String askMines = output.toLowerCase().trim();

        if (askMines.length() == 0 || askMines.contains("....") || !askMines.contains("how many mines")) {
            throw new WrongAnswer("In the first line you have to ask how many mines to place on the field.\n" +
                    "Your line:\n" + askMines);
        }

        String firstFieldOutput = pr.execute("1").toLowerCase().strip();
        try {
            field = new Field(firstFieldOutput);
        } catch (Exception e) {
            return CheckResult.wrong(e.getMessage());
        }

        if (!firstFieldOutput.contains("set/unset mines marks")) {
            throw new WrongAnswer("You should ask to mark the coordinates");
        }

        String fieldOutput = pr.execute("1 1 free").toLowerCase().strip();

        if (fieldOutput.contains("failed")) {
            throw new WrongAnswer("You should not be on the first attempt to climb on the mine.");
        }

        try {
            field.checkFieldExist(fieldOutput);
        } catch (Exception e) {
            return CheckResult.wrong(e.getMessage());
        }

        try {
            field.checkFirstOpenField(1, fieldOutput);
        } catch (Exception e) {
            return CheckResult.wrong(e.getMessage());
        }

        return CheckResult.correct();
    }

    @DynamicTest (timeLimit = 10000)
    CheckResult test2() {
        TestedProgram pr = new TestedProgram();
        pr.start();

        pr.execute("80");

        String fieldOutput = pr.execute("5 5 free").toLowerCase().strip();

        if (fieldOutput.contains("failed")) {
            throw new WrongAnswer("You should not be on the first attempt to climb on the mine.");
        }

        return CheckResult.correct();
    }

    @DynamicTest (timeLimit = 10000)
    CheckResult test3() {
        TestedProgram pr = new TestedProgram();
        pr.start();

        pr.execute("80");

        String fieldOutput = pr.execute("1 1 free").toLowerCase().strip();

        if (fieldOutput.contains("failed")) {
            throw new WrongAnswer("You should not be on the first attempt to climb on the mine.");
        }

        if (!fieldOutput.contains("congratulations! you found all")) {
            throw new WrongAnswer("If there are only unmarked mines left on the field, you must win!");
        }

        return CheckResult.correct();
    }

    @DynamicTest (timeLimit = 10000)
    CheckResult test4() {
        TestedProgram pr = new TestedProgram();
        pr.start();

        pr.execute("79");

        String fieldOutput = pr.execute("5 5 free").toLowerCase().strip();

        if (fieldOutput.contains("failed")) {
            throw new WrongAnswer("You should not be on the first attempt to climb on the mine.");
        }

        fieldOutput = pr.execute("5 4 free").toLowerCase().strip();

        if (fieldOutput.contains("set/unset")) {
            fieldOutput = pr.execute("5 3 free").toLowerCase().strip();
        }
        if (!fieldOutput.contains("failed")) {
            throw new WrongAnswer("The game should end when you mark the cell with the mine as free.");
        }

        return CheckResult.correct();
    }


}
