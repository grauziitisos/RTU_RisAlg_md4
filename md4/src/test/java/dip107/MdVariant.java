package dip107;
public class MdVariant {
    int preLastNumber;
    public int getPreLastNumber(){
        return this.preLastNumber;
    }
    public String getArrayOutputPattern(){
        int numCols = this.getDefaultArray()[0].length;
        switch (preLastNumber){
            case 0:
            case 1:
            return "(\\d+\\s+){"+(numCols-1)+"}(\\d+)";
            case 2:
            case 3:
            return "(0|1|0.5)\\s+{"+(numCols-1)+"}(0|1|0.5)";
            case 4:
            case 5:
            return "(-1|1?[0-9]|20\\s+){"+(numCols-1)+"}(-1|1?[0-9]|20)";
            case 6:
            case 7:
            return "(10|[1-4]?[2-9][0-9]|[1-4][0-2][0-9]|500\\s+){"+(numCols-1)+"}(10|[1-4]?[2-9][0-9]|[1-4][0-2][0-9]|500)";
            case 8:
            case 9:
            return "(10|[0-9]\\s+){"+(numCols-1)+"}(10|[0-9])";
            default:
            return "";
        }
    }

    public double[][] getDefaultArray(){
            switch (preLastNumber){
                case 0:
                case 1:
                return new double[][]{
                {3, 16, 1, 5, 1, 2, 3},
                {2, 3, 2, 3, 2, 3, 2},
                {12, 20, 25, 11, 30, 14, 15},
                {5, 3, 5, 2, 3, 2, 3},
                {1, 2, 3, 1, 2, 3, 1},
                {14, 25, 17, 15, 11, 18, 16}
            };
                case 2:
                case 3:
                return new double[][]{
                    {0.5, 0.5, 0.5, 0.5, 0.5},
                    {0, 1, 0, 1, 1},
                    {0.5, 1, 0.5, 0.5, 0},
                    {0, 0.5, 0, 0.5, 0},
                    {1, 1, 1, 1, 1},
                    {0, 0, 0, 0.5, 0.5},
                    {0, 0.5, 0, 0, 1}
                };
                case 4:
                case 5:
                return new double[][]{
                {0, 1, 2, 0, 2},
                {4, 4, 4, 4, 4},
                {0, -1, 8, 10, -1},
                {0, 3, -1, 2, 1},
                {4, 8, 4, 8, 12},
                {-1, -1, 2, 0, 1},
                {1, 8, 2, 4, -1},
                {8, 16, -1, 4, 0}
                };
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
                case 8:
                case 9:
                return new double[][]{
                {4, 10, 10, 9, 3},
                {10, 9, 10, 9, 10},
                {5, 3, 2, 6, 3},
                {7, 2, 1, 8, 3},
                {9, 9, 9, 9, 9},
                {5, 6, 5, 6, 5},
                {6, 10, 8, 4, 7}
                };
                default:
                return new double[][]{
                };
            }
    };
    public MdVariant(String ObjectUnderTestName){
        if(ObjectUnderTestName.indexOf("_", 0)>0)
        preLastNumber = Integer.parseInt(ObjectUnderTestName.split("_")[1].split("(\\d{3})(\\S{3})(\\d)(\\d)(\\d)")[3]);
    }
}
