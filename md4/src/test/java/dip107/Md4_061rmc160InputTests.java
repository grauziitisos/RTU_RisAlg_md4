package dip107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Md4_061rmc160InputTests {
    private ByteArrayOutputStream byteArrayOutputStream;
    private String ObjectUnderTestName = "dip107.Md4_061rmc160";
    private String WrongInputErrorMessage = "input-output error";
    private String NumbersMessageStart = "numbers: ";
    private String CountMessageStart = "count: ";


    int g(Random r) {
        return r.nextInt(491) + 10;
    }

/*    @ParameterizedTest
@CsvSource({ "{ 1, 2, 3 }", "{ 1, 2, 4 }", "'baz, qux', 3" })
void testWithCsvSource(int[] first) {
    assertNotNull(first);
    //assertNotEquals("2", second);
}*/
private int[][] getArrSource(int mode){
    Random r = new Random();
    switch(mode){
        case 1:
        return new int[][] { 
            { 10, 11, 12, 500, 499 }, 
            { 20, 22, 25, 27, 29 },
            { 11, 11, 11, 250, 490 }, 
            { g(r), g(r), g(r), g(r), g(r) },
            { g(r), g(r), g(r), g(r), g(r) }, 
            { g(r), g(r), g(r), g(r), g(r) },
            { g(r), g(r), g(r), g(r), g(r) }, 
            { g(r), g(r), g(r), g(r), g(r) } 
            };
        
        default:
    return new int[][] { 
        { g(r), g(r), g(r), g(r), g(r) }, 
        { g(r), g(r), g(r), g(r), g(r) },
        { g(r), g(r), g(r), g(r), g(r) }, 
        { g(r), g(r), g(r), g(r), g(r) },
        { g(r), g(r), g(r), g(r), g(r) }, 
        { g(r), g(r), g(r), g(r), g(r) },
        { g(r), g(r), g(r), g(r), g(r) }, 
        { g(r), g(r), g(r), g(r), g(r) } 
        };
    }
}

private int[][] getArrSource(int[][] markerArray){
    int[][]templateArr = getArrSource(-1);
    int maxI = templateArr.length< markerArray.length ? templateArr.length : markerArray.length;
    int maxJ = templateArr[0].length< markerArray[0].length ? templateArr[0].length : markerArray[0].length;
    for(int i =0 ; i<maxI; i++)
    for(int j=0; j<maxJ; j++)
    if(markerArray[i][j] != 0)
    templateArr[i][j] = markerArray[i][j];
    return templateArr;
}

private String[] makePatternsFromArray(int[][] inpt){
    ArrayList<String> o = new ArrayList<String>();
    String sbuilder ="";
    for (int i=0; i<inpt.length; i++)
    {
        sbuilder = "\\s*";
        for(int j=0;j<inpt[i].length-1;j++)
        sbuilder += inpt[i][j]+"\\s+";
        sbuilder += inpt[i][inpt[i].length-1]+"\\s*";
        o.add(sbuilder);
    }
    return o.toArray(String[]::new);
}

//IntStream.of()....
public static StringBuilder join(StringBuilder sb, CharSequence delimiter, int... arr) {
    if (null == delimiter || null == arr) throw new NullPointerException();

    sb.append(arr[0]);
    for (int i = 1; i < arr.length; i++) sb.append(delimiter).append(arr[i]);

    return sb;
}


    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void shouldPrintInputedArray(int input) throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        int[][] defaultArray = getArrSource(input);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
        sb = join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        runTest(getSimulatedUserInput(1 +System.getProperty("line.separator")+ sb.toString()), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        
        Boolean hasOutItems = output.length > 2 + defaultArray.length;
        int hadItemsCount = 0;
        // skip input mode for this test
        if (input == 1)
            return;
        String[] arrPatterns = makePatternsFromArray(defaultArray);
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
        int[][] defaultArray = getArrSource(1);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
            if (i==4){
                for(int j=0;j<defaultArray[i].length-1; j++)
                sb.append(defaultArray[i][0]+System.getProperty("line.separator"));
                sb.append(input+System.getProperty("line.separator"));
            }
            else
        sb = join(sb, " ", defaultArray[i]);
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
        int[][] inputArr = new int[8][5];
        Random r = new Random(1500);
        for(int i=0; i<inputArr[0].length; i++){
        inputArr[1][i] = r.nextInt(251) + 250;
        inputArr[3][i] = r.nextInt(251) + 250;
        inputArr[4][i] = r.nextInt(251) + 250;
        }
        int[][] defaultArray = getArrSource(inputArr);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
        sb = join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        runTest(getSimulatedUserInput(1 +System.getProperty("line.separator")+ sb.toString()), ObjectUnderTestName);
        String[] output = byteArrayOutputStream.toString().split(System.getProperty("line.separator"));
        
        Pattern numbersPattern = Pattern.compile("^"+NumbersMessageStart+"\\s*((\\d+\\s*)+)$");
        Pattern countPattern = Pattern.compile("^"+CountMessageStart+"\\s*(\\d+)\\s*$");

        if (output.length > 2){
            Matcher numbersMatcher = numbersPattern.matcher(output[output.length - 2]);
            Matcher countMatcher = countPattern.matcher(output[output.length - 1]);
            assertTrue(numbersMatcher.matches(), "One line before the last line should start with '"+NumbersMessageStart+"' and contain at least one number");
            assertTrue(countMatcher.matches(), "The last line should start with '"+CountMessageStart+"' and contain one number, signifying the count");
            assertTrue( 3 <= Integer.parseInt(countMatcher.group(1)), "The count should be at least '"+3+"");
            String[] splited = numbersMatcher.group(1).split("\\s+");
            int flag = 1;
            //array indx 0 = 1 nat count
            for(int t : new int[]{1+1,3+1,4+1}){
                //to test against repeated numbers, can not optimize here!
                //if (flag==8) break;
                for(int i = 0; i< splited.length; i++ )
                if( t ==Integer.parseInt(splited[i])){
                flag = (flag << 1);
                break;
                }
            }
            assertEquals(8, flag);
        }else
            assertTrue(false, "the program should output at least two lines! Output had lines: " + output.length);
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