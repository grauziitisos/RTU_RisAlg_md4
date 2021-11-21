package dip107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Md4_061rmc160InputTests {
    private ByteArrayOutputStream byteArrayOutputStream;
    private String ObjectUnderTestName = "dip107.Md4_061rmc160";
    private String WrongInputErrorMessage = "input-output error";
    private String NumbersMessageStart = "numbers: ";
    private String CountMessageStart = "count: ";
    private MdVariant var = new MdVariant(ObjectUnderTestName);
    private TestUtils tu = new TestUtils(var);

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void shouldPrintInputedArray(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        Number[][] defaultArray= tu.getArrSource(input);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
        sb = TestUtils.join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        runTest(getSimulatedUserInput(1 +System.getProperty("line.separator")+ sb.toString()), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        
        Boolean hasOutItems = output.length > 2 + defaultArray.length;
        int hadItemsCount = 0;
        // skip input mode for this test
        if (input == 1)
            return;
        String[] arrPatterns = tu.makePatternsFromArray(defaultArray);
        if (hasOutItems) {
            for (int i = 2; i < output.length; i++) {
                if (hadItemsCount == defaultArray.length)
                    return;
                if (hadItemsCount > 0) {
                    //allow whitespaces
                    assertTrue(output[i].matches(arrPatterns[hadItemsCount]),
                            "Line number " + (i + 1) + System.getProperty("line.separator") + "Should output result: "
                                    + defaultArray[0].length
                                    + " numbers per line and number range required in specification!"
                                    + System.getProperty("line.separator") + "output was: " + output[i]
                                    + System.getProperty("line.separator"));
                    hadItemsCount++;
                } else {
                    if (output[i].matches(arrPatterns[hadItemsCount]))
                        hadItemsCount = 1;
                }
            }
            assertTrue(hadItemsCount >0,
                    "No properly formatted Array output lines found!! the array output should start at the third line outputted or later and have "
                            + defaultArray[0].length + " rows of properly formatted results and match "+ arrPatterns[hadItemsCount]+" regex ! Output had lines: "
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
    @ValueSource(strings = { "fas", "-+1", "š", "0.0.0.0", "8k8", "4", "9", "0.5", "", "501", "Inf" })
    public void shouldTellWrongInput(String input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        Number[][] defaultArray = tu.getArrSource(1);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
            if (i==4){
                for(int j=0;j<defaultArray[i].length-1; j++)
                sb.append(defaultArray[i][0]+System.getProperty("line.separator"));
                sb.append(input+System.getProperty("line.separator"));
            }
            else
        sb = TestUtils.join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        runTest(getSimulatedUserInput(1 +System.getProperty("line.separator")+ sb.toString()), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        if (output.length > 1)
            assertEquals(WrongInputErrorMessage, output[output.length - 1],
                    "on error should output '"+WrongInputErrorMessage+"'");
        else
            assertTrue(false, "the program should output at least one line! Output had lines: " + output.length);
    }

   // @ParameterizedTest
   // @ValueSource(strings = { "fas", "-+1", "š", "0.0.0.0", "8k8", "4", "9", "0.5", "", "501", "Inf" })
   @Test 
   public void shouldOutputAtLeastKnownHighers(/*String input*/) throws Exception {
    byteArrayOutputStream = new ByteArrayOutputStream();
    strInstrOut m = (String inp) ->{
        runTest(getSimulatedUserInput(inp), ObjectUnderTestName);
        return byteArrayOutputStream.toString();
    };
    LastVarFactory f = new LastVarFactory(tu, m);
    f.NumbersMessageStart = NumbersMessageStart;
    f.CountMessageStart = CountMessageStart;
    f.ExecTest();
 }

    // endregion
    // region utils
    private String getSimulatedUserInput(String... inputs) {
        return String.join(System.getProperty("line.separator"), inputs) + System.getProperty("line.separator");
    }

   /* private String formSimulatedUserInput(int input){
        String t =input+System.getProperty("line.separator");
        if(input == 1){
            t+= System.getProperty("line.separator");
            Random r = new Random(1200);
            for(int i=0; i<5*8; i++)
                t+= var.g(r)+System.getProperty("line.separator");
        }
        return t;
    }*/

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