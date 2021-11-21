package dip107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
interface strInstrOut {
    public String process(String in) throws Exception;
}

public class LastVarFactory {
    TestUtils tu;
    strInstrOut method ;
    public String NumbersMessageStart;
    public String CountMessageStart;
    public LastVarFactory(TestUtils ttu, strInstrOut hook){this.tu = ttu; this.method = hook;};
    
    public void ExecTest() throws Exception{
        switch (tu.var.preLastNumber){
            case 0:
            case 1:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                v67_05();
                case 1:
                case 6:
                v67_05();
                case 2:
                case 7:
                v67_05();
                case 3:
                case 8:
                v67_05();
                case 4:
                case 9:
                v67_05();
                default:
                return;
            }
            case 2:
            case 3:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                v67_05();
                case 1:
                case 6:
                v67_05();
                case 2:
                case 7:
                v67_05();
                case 3:
                case 8:
                v67_05();
                case 4:
                case 9:
                v67_05();
                default:
                return;
            }
            case 4:
            case 5:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                v67_05();
                case 1:
                case 6:
                v67_05();
                case 2:
                case 7:
                v67_05();
                case 3:
                case 8:
                v67_05();
                case 4:
                case 9:
                v67_05();
                default:
                return;
            }
            case 6:
            case 7:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                v67_05();
                case 1:
                case 6:
                v67_05();
                case 2:
                case 7:
                v67_05();
                case 3:
                case 8:
                v67_05();
                case 4:
                case 9:
                v67_05();
                default:
                return;
            }
            case 8:
            case 9:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                v67_05();
                case 1:
                case 6:
                v67_05();
                case 2:
                case 7:
                v67_05();
                case 3:
                case 8:
                v67_05();
                case 4:
                case 9:
                v67_05();
                default:
                return;
            }
            default:
            return;
        }
    }

    void v67_05() throws Exception{
        
        int[][] inputArr = new int[8][5];
        Random r = new Random(1500);
        for(int i=0; i<inputArr[0].length; i++){
        inputArr[1][i] = r.nextInt(251) + 250;
        inputArr[3][i] = r.nextInt(251) + 250;
        inputArr[4][i] = r.nextInt(251) + 250;
        }
        Num inpNum = new Num(){{ i= inputArr;}};
        Number[][] defaultArray = tu.getArrSource(inpNum);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
        sb = TestUtils.join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        String[] output = this.method.process(1 +System.getProperty("line.separator")+ sb.toString()).split(System.getProperty("line.separator"));

        
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
            //inputArr index 0 = 1 nat count
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
}
