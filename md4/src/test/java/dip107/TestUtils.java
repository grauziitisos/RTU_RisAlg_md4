package dip107;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class TestUtils {
    public TestUtils(MdVariant varr) {
        this.var = varr;
    }

    public MdVariant var;

    public Number[][] getArrSource(int mode) {
        if (var.preLastNumber == 2 || var.preLastNumber == 3) {
            return Arrays.stream(var.getInputArrSource(mode).d)
                .map(array -> DoubleStream.of(array)
                .boxed()
                .toArray(Number[]::new)).toArray(Number[][]::new);
        } else {
            return Arrays.stream(var.getInputArrSource(mode).i)
                .map(array -> IntStream.of(array)
                .boxed()
                .toArray(Number[]::new)).toArray(Number[][]::new);
        }
    }

    // maybe with <T> ??
    public Number[][] getArrSource(Num markerNum) {
        Num result = new Num();
        if (var.preLastNumber == 2 || var.preLastNumber == 3) {
            double[][] markerArray = markerNum.d;
            double[][] templateArr = var.getInputArrSource(-1).d;
            int maxI = templateArr.length < markerArray.length ? templateArr.length : markerArray.length;
            int maxJ = templateArr[0].length < markerArray[0].length ? templateArr[0].length : markerArray[0].length;
            for (int i = 0; i < maxI; i++)
                for (int j = 0; j < maxJ; j++)
                    if (markerArray[i][j] != 0)
                        templateArr[i][j] = markerArray[i][j];
            result.d = templateArr;
        } else {
            int[][] markerArray = markerNum.i;
            int[][] templateArr = var.getInputArrSource(-1).i;
            int maxI = templateArr.length < markerArray.length ? templateArr.length : markerArray.length;
            int maxJ = templateArr[0].length < markerArray[0].length ? templateArr[0].length : markerArray[0].length;
            for (int i = 0; i < maxI; i++)
                for (int j = 0; j < maxJ; j++)
                    if (markerArray[i][j] != 0)
                        templateArr[i][j] = markerArray[i][j];
            result.i = templateArr;
        }
        if (var.preLastNumber == 2 || var.preLastNumber == 3) {
            return Arrays.stream(result.d)
            .map(array -> DoubleStream.of(array)
            .boxed()
            .toArray(Number[]::new)).toArray(Number[][]::new);
        } else {
            return Arrays.stream(result.i)
            .map(array -> IntStream.of(array)
            .boxed()
            .toArray(Number[]::new)).toArray(Number[][]::new);
        }
    }

    public String[] makePatternsFromArray(Number[][] inpt) {
        ArrayList<String> o = new ArrayList<String>();
        String sbuilder = "";
        for (int i = 0; i < inpt.length; i++) {
            sbuilder = "\\s*";
            for (int j = 0; j < inpt[i].length - 1; j++)
                sbuilder += inpt[i][j] + "\\s+";
            sbuilder += inpt[i][inpt[i].length - 1] + "\\s*";
            o.add(sbuilder);
        }
        return o.toArray(String[]::new);
    }

    // IntStream.of()....
    public static StringBuilder join(StringBuilder sb, CharSequence delimiter, Number... arr) {
        if (null == delimiter || null == arr)
            throw new NullPointerException();

        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            sb.append(delimiter).append(arr[i]);

        return sb;
    }
}
