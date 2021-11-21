package dip107;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FunctionalInterface
interface strInstrOut {
    public String process(String in) throws Exception;
}


@FunctionalInterface
interface markRowMaker {
    public Number[] makeMarkRow(int rowLength, Random r, boolean returnConstant) throws Exception;
}

@FunctionalInterface
interface uniformIntNumberGenerator {
    public Number[] generate(IntSupplier supplier, int limit) throws Exception;
}

@FunctionalInterface
interface atLeastnthIntNumberGenerator {
    public Number[] generate(IntSupplier supplier1, IntSupplier supplier2, IntSupplier supplier3, int limit, Random r, boolean returnConstant) throws Exception;
}

public class LastVarFactory {
    TestUtils tu;
    strInstrOut method ;
    public String NumbersMessageStart;
    public String CountMessageStart;
    public LastVarFactory(TestUtils ttu, strInstrOut hook){this.tu = ttu; this.method = hook;};
    
    public void ExecTest() throws Exception{
        markRowMaker mk;
        mk = (rowLength, r, returnConstant)->{
            Number[] result = new Number[rowLength];
            for(int i=0; i<rowLength; i++)
            result[i] = r.nextInt(251) + 250;
            return result;
        };
        uniformIntNumberGenerator intGen= (supplier, limit)->{
            return IntStream
            .generate(supplier)
            .limit(limit)
            .boxed()
            .toArray(Number[]::new);
        };
        atLeastnthIntNumberGenerator alLast1 =(supplier1, supplier2, supplier3, limit, r, returnConstant)->{
            Number[] result = new Number[limit];
            if(returnConstant) {
                    int markNumber = r.nextInt(limit);
                    result[markNumber] = supplier1.getAsInt();
                    for(int i=0; i<limit; i++)
                    if(i!=markNumber)
                    result[i] = supplier2.getAsInt();
            }else
                for(int i=0; i<limit; i++)
                result[i] = supplier3.getAsInt();
                return result;
        };
        switch (tu.var.preLastNumber){
            // no diapazona [1; 30]
            case 0:
            case 1:
            switch (tu.var.lastNumber){
                // vismaz vienā sacīkstē ieņēma 1. vietu
                case 0:
                case 5:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // nekad, nevienā sacīkstē, neieņēma 1. vietu
                case 1:
                case 6:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // visās sacīkstēs ieņēma vietu ne zemāku kā 10
                case 2:
                case 7:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(10)+1, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // visās sacīkstes ieņēma vietas, zemākas nekā 10
                case 3:
                case 8:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(21)+10, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                //  visās sacīkstēs ieņēma vietu ne zemāku kā 3
                case 4:
                case 9:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(3)+1, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                default:
                return;
            }
            case 2:
            case 3:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 1:
                case 6:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 2:
                case 7:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 3:
                case 8:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 4:
                case 9:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                default:
                return;
            }
            case 4:
            case 5:
            switch (tu.var.lastNumber){
                case 0:
                case 5:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 1:
                case 6:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 2:
                case 7:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 3:
                case 8:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                case 4:
                case 9:
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                default:
                return;
            }
            // no diapazona [10; 500]
            case 6:
            case 7:
            switch (tu.var.lastNumber){
                // visās sacensības ieguva minimums 250 punktus
                case 0:
                case 5:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(251) + 250, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // visās sacensības ieguva mazāk nekā 250 punktus
                case 1:
                case 6:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(240) + 10, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // vismaz vienā no sacensībām ieguva minimums 250 punktus
                case 2:
                case 7:
                mk = (rowLength, r, returnConstant)->{
                Number[] result = new Number[rowLength];
                if(returnConstant) {
                        int markNumber = r.nextInt(rowLength);
                        result[markNumber] = 250;
                        for(int i=0; i<rowLength; i++)
                        if(i!=markNumber)
                        result[i] = r.nextInt(240)+10;
                }else
                    for(int i=0; i<rowLength; i++)
                    result[i] = r.nextInt(240) + 10;
                    return result;
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // vismaz vienās sacensībās ieguva mazāk nekā 250 punktus
                case 3:
                case 8:
                mk = (rowLength, r, returnConstant)->{
                    Number[] result = new Number[rowLength];
                    int markNumber = r.nextInt(rowLength);
                    result[markNumber] = r.nextInt(240)+10;
                            for(int i=0; i<rowLength; i++)
                            if(i!=markNumber)
                            if(returnConstant)
                            result[i] = r.nextInt(251)+250;
                            else
                            result[i] = r.nextInt(491)+10;
                        return result;
                    };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                // vismaz divās sacensībās ieguva mazāk nekā 250 punktus.
                case 4:
                case 9:
                mk = (rowLength, r, returnConstant)->{
                    Number[] result = new Number[rowLength];
                    int markNumber = r.nextInt(rowLength);
                    int markNumber2 = r.nextInt(rowLength);
                    while(markNumber == markNumber2) markNumber2 = r.nextInt(rowLength);
                    result[markNumber] = r.nextInt(240)+10;
                    result[markNumber2] = r.nextInt(240)+10;
                            for(int i=0; i<rowLength; i++)
                            if(i!=markNumber && i!=markNumber2)
                            if(returnConstant)
                            result[i] = r.nextInt(251)+250;
                            else
                            result[i] = r.nextInt(491)+10;
                        return result;
                    };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                default:
                return;
            }
            // no diapazona [0; 10].
            case 8:
            case 9:
            switch (tu.var.lastNumber){
                //kam nav atzīmes mazākas par 5
                case 0:
                case 5:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(6) + 5, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                //could merge all the "at least one" in one general function...
                //kas nenokārtoja vismaz vienu eksāmenu.
                case 1:
                case 6:
                mk = (rowLength, r, returnConstant)->{
                    Number[] result = new Number[rowLength];
                    int markNumber = r.nextInt(rowLength);
                    result[markNumber] = r.nextInt(4);
                            for(int i=0; i<rowLength; i++)
                            if(i!=markNumber)
                            if(returnConstant)
                            result[i] = r.nextInt(4);
                            else
                            result[i] = r.nextInt(11);
                        return result;
                    };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                //kas saņēma vismaz vienu atzīmi 10
                case 2:
                case 7:
                mk = (rowLength, r, returnConstant)->{
                    Number[] result = new Number[rowLength];
                    int markNumber = r.nextInt(rowLength);
                    result[markNumber] = 10;
                            for(int i=0; i<rowLength; i++)
                            if(i!=markNumber)
                            result[i] = r.nextInt(11);
                        return result;
                    };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                //kas nenokārtoja vismaz 3 eksāmenus
                case 3:
                case 8:
                mk = (rowLength, r, returnConstant)->{
                    Number[] result = new Number[rowLength];
                    int markNumber = r.nextInt(rowLength);
                    int markNumber2 = r.nextInt(rowLength);
                    int markNumber3 = r.nextInt(rowLength);
                    while(markNumber == markNumber2) markNumber2 = r.nextInt(rowLength);
                    while(markNumber3 == markNumber2 || markNumber == markNumber3) markNumber3 = r.nextInt(rowLength);
                    result[markNumber] = r.nextInt(4);
                    result[markNumber2] = r.nextInt(4);
                    result[markNumber3] = r.nextInt(4);
                            for(int i=0; i<rowLength; i++)
                            if(i!=markNumber && i!=markNumber2 && i!=markNumber3)
                            if(returnConstant)
                            result[i] = r.nextInt(4);
                            else
                            result[i] = r.nextInt(11);
                        return result;
                    };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                //kam nav atzīmes zemākas par 9
                case 4:
                case 9:
                mk = (rowLength, r, returnConstant)->{
                    return intGen
                    .generate(() -> r.nextInt(2) + 9, rowLength);
                };
                shouldFulFillLastNumberRequirementsRunner(mk);
                return;
                default:
                return;
            }
            default:
            return;
        }
    }

    void shouldFulFillLastNumberRequirementsRunner(markRowMaker mk) throws Exception{
        Number[][] defaultArray = tu.getArrSource(-1);
        Random r = new Random(1500);
        int mrkCnt = r.nextInt(defaultArray.length)+1;
        ArrayList<Integer> checkRows = new ArrayList<Integer>();
        int cRow = r.nextInt(defaultArray.length);
        for(int n =0; n<mrkCnt; n++){
            while(checkRows.contains(cRow)) cRow = r.nextInt(defaultArray.length);
            checkRows.add(cRow);
            if(n==0) defaultArray[cRow] = mk.makeMarkRow(defaultArray[0].length, r, true);
            else defaultArray[cRow] = mk.makeMarkRow(defaultArray[0].length, r, false);
        }
            
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<defaultArray.length; i++){
        sb = TestUtils.join(sb, " ", defaultArray[i]);
        sb.append(System.getProperty("line.separator"));
        }
        String[] output = this.method.process(1 +System.getProperty("line.separator")+ sb.toString()).split(System.getProperty("line.separator"));

        
        Pattern numbersPattern = Pattern.compile("^"+NumbersMessageStart+"\\s*?(([1-9][0-9]*?\\s*?)+)$");
        Pattern countPattern = Pattern.compile("^"+CountMessageStart+"\\s*?(\\d+)\\s*?$");

        if (output.length > 2){
            Matcher numbersMatcher = numbersPattern.matcher(output[output.length - 2]);
            Matcher countMatcher = countPattern.matcher(output[output.length - 1]);
            assertTrue(numbersMatcher.matches(), "One line before the last line should start with '"+NumbersMessageStart+"'"+ 
            System.getProperty("line.separator")+"and contain ROW numbers. "+
            System.getProperty("line.separator")+"In other words, match regular expression:"+
            System.getProperty("line.separator")+numbersPattern+
            System.getProperty("line.separator")+"output was:"+
            System.getProperty("line.separator")+output[output.length - 2]);

            assertTrue(countMatcher.matches(), "The last line should start with '"+CountMessageStart+"' and contain one number, signifying the count"+
            System.getProperty("line.separator")+"output was:"+
            System.getProperty("line.separator")+output[output.length - 1]);

            assertTrue( checkRows.size() <= Integer.parseInt(countMatcher.group(1)), "The count should be at least '"+checkRows.size()+
            System.getProperty("line.separator")+"output was:"+
            System.getProperty("line.separator")+output[output.length - 1]);
            String[] splited = numbersMatcher.group(1).split("\\s+");

            int flag = 1;
            //inputArr index 0 = 1 nat count
            for(int t : checkRows){
                //to test against repeated numbers, can not optimize here!
                //if (flag==8) break;
                for(int i = 0; i< splited.length; i++ )
                if(t+1 == Integer.parseInt(splited[i])){
                flag = (flag << 1);
                break;
                }
            }
            assertEquals(1<<checkRows.size(), flag, "Should output all matching record numbers! "+
            System.getProperty("line.separator")+"Expected:"+
            System.getProperty("line.separator")+checkRows.stream()
            .map(Object::toString)
            .collect(Collectors.joining("\t"))+
            System.getProperty("line.separator")+"output was:"+
            System.getProperty("line.separator")+output[output.length - 2]+
            System.getProperty("line.separator"));
        }else
            assertTrue(false, "the program should output at least two lines! Output had lines: " + output.length+
            System.getProperty("line.separator")+"output was:"+
            System.getProperty("line.separator")+String.join(System.getProperty("line.separator"), output));
   
    }
}
