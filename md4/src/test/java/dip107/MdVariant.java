package dip107;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
interface genRand {
    public Num getArr(int max, int shift);
}

public class MdVariant {
    int lastNumber;
    int preLastNumber;
    //default: package-private ! public only needed to access outside the package..
    public int getPreLastNumber(){
        return this.preLastNumber;
    }
    public String getArrayOutputPattern(){
        int numCols = (preLastNumber == 2 || preLastNumber == 3) ? this.getDefaultArray().d[0].length : this.getDefaultArray().i[0].length;
        switch (preLastNumber){
            case 0:
            case 1:
            return "(\\d+\\s+){"+(numCols-1)+"}(\\d+)";
            case 2:
            case 3:
            return "(0|1|0.5|0,5)\\s+{"+(numCols-1)+"}(0|1|0.5|0,5)";
            case 4:
            case 5:
            return "(-1|1?[0-9]|20\\s+){"+(numCols-1)+"}(-1|1?[0-9]|20)";
            case 6:
            case 7:
            return "((500|[1-4]0[0-9]|[1-4]?[1-9][0-9])\\s+){"+(numCols-1)+"}(500|[1-4]0[0-9]|[1-4]?[1-9][0-9])";
            case 8:
            case 9:
            return "(10|[0-9]\\s+){"+(numCols-1)+"}(10|[0-9])";
            default:
            return "";
        }
    }

    public Num getDefaultArray(){
            switch (preLastNumber){
                case 0:
                case 1:
                return new Num() {{ i=  new int[][]{
                {3, 16, 1, 5, 1, 2, 3},
                {2, 3, 2, 3, 2, 3, 2},
                {12, 20, 25, 11, 30, 14, 15},
                {5, 3, 5, 2, 3, 2, 3},
                {1, 2, 3, 1, 2, 3, 1},
                {14, 25, 17, 15, 11, 18, 16}
            };}};
                case 2:
                case 3:
                return new Num() {{ d=  new double[][]{
                    {0.5, 0.5, 0.5, 0.5, 0.5},
                    {0, 1, 0, 1, 1},
                    {0.5, 1, 0.5, 0.5, 0},
                    {0, 0.5, 0, 0.5, 0},
                    {1, 1, 1, 1, 1},
                    {0, 0, 0, 0.5, 0.5},
                    {0, 0.5, 0, 0, 1}
                };}};
                case 4:
                case 5:
                return new Num() {{ i=  new int[][]{
                {0, 1, 2, 0, 2},
                {4, 4, 4, 4, 4},
                {0, -1, 8, 10, -1},
                {0, 3, -1, 2, 1},
                {4, 8, 4, 8, 12},
                {-1, -1, 2, 0, 1},
                {1, 8, 2, 4, -1},
                {8, 16, -1, 4, 0}
            };}};
                case 6:
                case 7:
                return new Num() {{ i=  new int[][]{
                {250, 300, 250, 250, 250},
                {100, 50, 60, 70, 200},
                {250, 100, 70, 150, 200},
                {300, 400, 200, 250, 300},
                {400, 320, 250, 220, 270},
                {200, 200, 200, 200, 200},
                {250, 260, 250, 250, 250},
                {250, 200, 100, 250, 80}
            };}};
                case 8:
                case 9:
                return new Num() {{ i=  new int[][]{
                {4, 10, 10, 9, 3},
                {10, 9, 10, 9, 10},
                {5, 3, 2, 6, 3},
                {7, 2, 1, 8, 3},
                {9, 9, 9, 9, 9},
                {5, 6, 5, 6, 5},
                {6, 10, 8, 4, 7}
            };}};
                default:
                return new Num();
            }
    };

    public Number g(Random r){
        switch (preLastNumber){
            case 0:
            case 1:
            return gi(r, 30, 1);
            case 2:
            case 3:
            return gd(r, 3, 0);
            case 4:
            case 5:
            return gi(r, 22, -1);
            case 6:
            case 7:
            return gi(r, 491, 10);
            case 8:
            case 9:
            return gi(r, 11, 0);
            default:
            return 0;
        }     
    }

    int gi(Random r, int max, int shift) {
        return r.nextInt(max) + shift;
    }

    double gd(Random r, int max, int shift) {
        return r.nextInt(3)/2.;
    }

    Num genRandomArray(){
        Num t = getDefaultArray();
        Random r = new Random();
        int numCols = (preLastNumber == 2 || preLastNumber == 3) ? t.d[0].length : t.i[0].length;
        int numRows = (preLastNumber == 2 || preLastNumber == 3) ? t.d.length : t.i.length;
        genRand GenRand = (max, shift) -> {
            Num result = new Num();
            if(preLastNumber == 2 || preLastNumber == 3){
                result.d = new double[numRows][numCols];
                for(int i=0; i<result.d.length; i++)
                for(int j=0; j<result.d[0].length; j++)
                result.d[i][j] = gd(r, max, shift);
                return result;
            }else{
                result.i = new int[numRows][numCols];
                for(int i=0; i<result.i.length; i++)
                for(int j=0; j<result.i[0].length; j++)
                result.i[i][j] = gi(r, max, shift);
                return result;
            }
        };

        switch (preLastNumber){
            case 0:
            case 1:
            return GenRand.getArr(30, 1);
            case 2:
            case 3:
            return GenRand.getArr(3, 0);
            case 4:
            case 5:
            return GenRand.getArr(22, -1);
            case 6:
            case 7:
            return GenRand.getArr(491, 10);
            case 8:
            case 9:
            return GenRand.getArr(11, 0);
            default:
            return new Num() {{ d= new double[][]{
            };}};
        }
    }

/*    @ParameterizedTest
@CsvSource({ "{ 1, 2, 3 }", "{ 1, 2, 4 }", "'baz, qux', 3" })
void testWithCsvSource(int[] first) {
    assertNotNull(first);
    //assertNotEquals("2", second);
}*/
public Num getInputArrSource(int mode){
    switch(mode){
        case 1:
        Num t = genRandomArray();
    switch (preLastNumber){
        case 0:
        case 1:
        t.i[0]= new int[]{1, 20, 25, 11, 30, 14, 15};
        t.i[1]= new int[]{14, 25, 17, 15, 11, 18, 10};
        return t;
        case 2:
        case 3:
        t.d[0]= new double[]{0.5, 1, 0.5, 0.5, 0};
        t.d[1]= new double[]{0, 0.5, 0, 0, 1};
        return t;
        case 4:
        case 5:
        t.i[0]= new int[]{0, 5, 2, 0, 2};
        t.i[1]= new int[]{10, -1, 8, 20, -1};
        return t;
        case 6:
        case 7:
        t.i[0]= new int[]{ 10, 11, 12, 500, 499 };
        t.i[1]= new int[]{ 11, 22, 25, 250, 490 }; 
        return t;
        case 8:
        case 9:
        t.i[0]= new int[]{0, 10, 5, 9, 3};
        t.i[1]= new int[]{10, 9, 10, 9, 10};
        return t;

        default:
        return new Num() {{ d= new double[][]{
        };}};
    }
        
        default:
    return genRandomArray();
    }
}

    public MdVariant(String ObjectUnderTestName){
        if(ObjectUnderTestName.indexOf("_", 0)>0){
            Pattern p = Pattern.compile("(\\d{3})(\\S{3})(\\d)(\\d)(\\d)");
Matcher m = p.matcher(ObjectUnderTestName.split("_")[1]);
m.find();
        preLastNumber = Integer.parseInt(m.group(4));
        lastNumber = Integer.parseInt(m.group(5));
        }
    }
}
