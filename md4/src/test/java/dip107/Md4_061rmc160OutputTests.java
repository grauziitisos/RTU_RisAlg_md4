package dip107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Md4_061rmc160OutputTests {
    private ByteArrayOutputStream byteArrayOutputStream;
    private String ObjectUnderTestName = "dip107.Md4_061rmc160";
    private String WrongInputErrorMessage = "input-output error";

    @Test
    public void shouldHaveClassNamed() throws Exception {
        Class<?> cls = null;
        try {
            cls = Class.forName(ObjectUnderTestName);
        } catch (ClassNotFoundException ce) {
            assertNotNull(cls, "Properly named class not found! You should create a class inside package dip107 :"
                    + ObjectUnderTestName);
        }
        assertNotNull(cls, "class should have name (package_name.fileName)" + ObjectUnderTestName);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void shouldPrintAplnrVardsUzvardsGrupasNr(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        runTest(getSimulatedUserInput(input + ""), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        assertEquals("061RMC160 Oskars Grauzis 4", output[0]);
    }

    @ParameterizedTest
    @ValueSource(floats = { 1, 2, 3 })
    public void shouldPrintNumberPrompt(float input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        runTest(getSimulatedUserInput(input + ""), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        Boolean hasOutItem = output.length > 1;
        if (hasOutItem)
            assertEquals("Kā aizpildīt masīvu (1, 2 vai 3)?", output[1]);
        else
            assertTrue(false,
                    "the filling mode prompt should be the second line outputted! Output had lines: " + output.length);
    }

    int g(Random r) {
        return r.nextInt(491) + 10;
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public void shouldPrintResultTitle(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        String t =formSimulatedUserInput(input);
        runTest(t, ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        Boolean hasOutItem = output.length > 2, hadCount = false;
        if (hasOutItem) {
            // kaa jaabuut? "a=result:"-> ja in dos \r\n "result:"-> ja formateshu lai
            // butu \r\n...
            for (int i = 2; i < output.length; i++) {
                if (output[i].matches("^result:.*")) {
                    hadCount = true;
                    break;
                }
            }
            assertTrue(hadCount, "Should contain a line starting with text 'output'!");
        } else
            assertTrue(false,
                    "the result: text should be at least the third line from the end outputted! Output had lines: "
                            + output.length + System.getProperty("line.separator") + "output: "
                            + byteArrayOutputStream.toString() + "input: " + t);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public void shouldPrintResultTiitles(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        runTest(formSimulatedUserInput(input), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));

        Boolean hadResult = false, hadNumbers = false, hadCount = false;
        Boolean hasOutItem = output.length > 2;
        assertTrue(hasOutItem, "shohuld output more than 3 lines.");
        // kaa jaabuut? 2-> ja in dos \r\n 3-> ja formateshu lai butu \r\n...
        for (int i = 2; i < output.length; i++) {
            if (output[i].matches("^count:.*"))
                hadCount = true;
            else if (output[i].matches("^result:.*"))
                hadResult = true;
            else if (output[i].matches("^numbers:.*"))
                hadNumbers = true;
            if (hadResult && hadNumbers && hadCount)
                break;
        }
        assertTrue(hadCount && hadResult && hadNumbers,
                "Should have results lines starting with words 'result', 'numbers', 'count' followed by column!");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public void shouldPrintFormattedResults(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        runTest(getSimulatedUserInput(input + ""), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        MdVariant vart = new MdVariant(ObjectUnderTestName);
        double[][] defaultArray = vart.getDefaultArray();
        Boolean hasOutItems = output.length > 2 + defaultArray.length;
        int hadItemsCount = 0;
        // skip input mode for this test
        if (input == 1)
            return;
        String arrPattern = vart.getArrayOutputPattern();
        if (hasOutItems) {
            for (int i = 2; i < output.length; i++) {
                if (hadItemsCount == defaultArray.length)
                    return;
                if (hadItemsCount > 0) {
                    //allow whitespaces
                    assertTrue(output[i].trim().matches(arrPattern),
                            "Line number " + (i + 1) + System.getProperty("line.separator") + "Should output result: "
                                    + vart.getDefaultArray()[0].length
                                    + " numbers per line and number range required in specification!"
                                    + System.getProperty("line.separator") + "output was: " + output[i]
                                    + System.getProperty("line.separator"));
                    hadItemsCount++;
                } else {
                    if (output[i].trim().matches(arrPattern))
                        hadItemsCount = 1;
                }
            }
            assertTrue(hadItemsCount >0,
                    "No properly formatted Array output lines found!! the array output should start at the third line outputted or later and have "
                            + defaultArray[0].length + " rows of properly formatted results and match "+ vart.getArrayOutputPattern()+" regex ! Output had lines: "
                            + output.length + System.getProperty("line.separator") + "output was: "
                            + byteArrayOutputStream.toString() + System.getProperty("line.separator"));
        } else
            assertTrue(false,
                    "the array output should start at the third line outputted or later and have "
                            + defaultArray[0].length + " rows of properly formatted results! Output had lines: "
                            + output.length + System.getProperty("line.separator") + "output was: "
                            + byteArrayOutputStream.toString() + System.getProperty("line.separator"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "fas", "-+1", "š", "0.0.0.0", "8k8", "4", "10" })
    public void shouldTellWrongMenuInput(String input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        runTest(getSimulatedUserInput(input + ""), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        if (output.length > 1)
            assertEquals(WrongInputErrorMessage, output[output.length - 1],
                    "on error should output '"+WrongInputErrorMessage+"'");
        else
            assertTrue(false, "the program should output at least one line! Output had lines: " + output.length);
    }

    // endregion
    // region utils
    private String getSimulatedUserInput(String... inputs) {
        return String.join(System.getProperty("line.separator"), inputs) + System.getProperty("line.separator");
    }

    private String formSimulatedUserInput(int input){
        String t =input+System.getProperty("line.separator");
        if(input == 1){
            t+= System.getProperty("line.separator");
            Random r = new Random(1200);
            for(int i=0; i<5*8; i++)
                t+= g(r)+System.getProperty("line.separator");
        }
        return t;
    }

    private void runTest(String data, String className) throws Exception {

        InputStream input = new ByteArrayInputStream(data.getBytes("UTF-8"));
        ;

        Class<?> cls = Class.forName(className);
        Object t = cls.getDeclaredConstructor().newInstance();
        Method meth = t.getClass().getDeclaredMethod("testableMain", InputStream.class, PrintStream.class);

        meth.invoke(t, input, new PrintStream(byteArrayOutputStream));
    }
    // endregion
}