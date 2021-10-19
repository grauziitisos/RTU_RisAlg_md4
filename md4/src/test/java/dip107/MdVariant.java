package dip107;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdVariant {
    int preLastNumber;
    public int getPreLastNumber(){
        return this.preLastNumber;
    }
    public String getArrayOutputPattern(){
        int numCols = this.getDefaultArray()[0].length;
        switch (preLastNumber){
            case 6:
            case 7:
            return "((500|[1-4]0[0-9]|[1-4]?[1-9][0-9])\\s+){"+(numCols-1)+"}(500|[1-4]0[0-9]|[1-4]?[1-9][0-9])";
            default:
            return "";
        }
    }

    public double[][] getDefaultArray(){
            switch (preLastNumber){
                case 6:
                case 7:
                return new double[][]{
                {250, 300, 250, 250, 250},
                {100, 50, 60, 70, 200},
                {250, 100, 70, 150, 200},
                {300, 400, 200, 250, 300},
                {400, 320, 250, 220, 270},
                {200, 200, 200, 200, 200},
                {250, 260, 250, 250, 250},
                {250, 200, 100, 250, 80}
                };
                default:
                return new double[][]{
                };
            }
    };
    public MdVariant(String ObjectUnderTestName){
        if(ObjectUnderTestName.indexOf("_", 0)>0){
            Pattern p = Pattern.compile("(\\d{3})(\\S{3})(\\d)(\\d)(\\d)");
Matcher m = p.matcher(ObjectUnderTestName.split("_")[1]);
m.find();
        preLastNumber = Integer.parseInt(m.group(4));
        }
    }
}
