package dip107;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 60 4. variants - Pieccīņas sacensību apstrādes sistēma (priekšpēdējais
 * studenta apliecības numura cipars 6 vai 7) 1. Aprakstīt divdimensiju masīvu,
 * paredzētu pieccīņas rezultātu glabāšanai. Pieņemsim, ka turnīrā piedalās 8
 * sportisti, tādējādi masīvā ir jābūt 8 rindām un 5 kolonām. Visiem masīva
 * elementiem piešķirt sekojošas sākumvērtības masīva aprakstīšanas laikā: 250
 * 300 250 250 250 100 50 60 70 200 250 100 70 150 200 300 400 200 250 300 400
 * 320 250 220 270 200 200 200 200 200 250 260 250 250 250 250 200 100 250 80 2.
 * Uzdot lietotājam jautājumu, kā viņš vēlas aizpildīt masīvu: ievadīt no
 * tastatūras (lietotājs ievadīja 1), ar patvaļīgām vērtībām (lietotājs ievadīja
 * 2), vai izmantot sākumvērtības, norādītas masīva aprakstīšanas laika
 * (lietotājs ievadīja skaitli 3). Ja lietotājs ievadīja cipars 3, tad piešķirt
 * masīva elementiem patvaļīgas vērtības no diapazona [10; 500] 3. Atkarībā no
 * pēdējā studenta apliecības numura cipara izpildīt vienu no norādītām
 * darbībām. Izvadot sportistu numurus, pieņemt ka sportisti ir sanumurēti no 1
 * (izvadīt numuru, nevis indeksu masīvā).
 * 
 * Pēdējais studenta apliecības numura cipars 0 vai 5 Izvadīt ekrānā to
 * sportistu numurus un skaitu (daudzumu), kas visās sacensības ieguva minimums
 * 250 punktus. Piemēram, ja lietotājs ievadīja cipars 3, tad programmai aiz
 * paziņojuma "result:" ir jāizvadā: result: numbers: 1 7 count: 2
 *
 * 
 */
public class Md4_061rmc160 {
    public static void main(String[] args) {
        testableMain(System.in, System.out);
    }

    // public static String errorInputMessage = "Nepareizs ievads! Jāievada {0}";
    public static String errorInputMessage = "input-output error";

    public static void testableMain(InputStream inputStream, PrintStream outputStream) {
        Scanner sc = new Scanner(inputStream);
        int[][] dbArr;
        // String outputFormatString = "%1$.1f";
        String outputFormatString = "%3d";
        outputStream.println("061RMC160 Oskars Grauzis 4");
        outputStream.println("Kā aizpildīt masīvu (1, 2 vai 3)?");
        if (!sc.hasNext("1|2|3")) {
            outputStream.println(String.format(errorInputMessage, "izvēli vienu no trim iespējām : 1 vai 2 vai 3"));
            sc.close();
            return;
        }
        dbArr = getArray(Byte.parseByte(sc.next("1|2|3")), sc, outputStream);
        if (dbArr.length == 0)
            return;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int hadFlag = 0, maxFlag = 0;
        for (byte i = 0; i < dbArr[0].length; i++)
            maxFlag = maxFlag | (1 << i);
        // region masiva izvade un rezultatu izlase
        for (byte i = 0; i < dbArr.length; i++) {
            hadFlag = 0;
            for (byte j = 0; j < dbArr[i].length; j++) {
                if (dbArr[i][j] >= 250)
                    hadFlag = hadFlag | (1 << j);
                if ((j % dbArr[i].length) == dbArr[i].length - 1) {
                    outputStream.print(
                            String.format(outputFormatString, dbArr[i][j]) + System.getProperty("line.separator"));
                } else {
                    outputStream.print(String.format(outputFormatString, dbArr[i][j]) + "\t");
                }
            }
            if (hadFlag == maxFlag)
                numbers.add(i + 1);
        }
        // endregion
        // region 3. rezultātu izvade
        outputStream.println("result:");
        outputStream.println("numbers: " + numbers.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        outputStream.println("count: " + numbers.size());
        // endregion
        // region 4. izvade

        // endregion
        // Trešais no beigām studenta apliecības numura cipars 1 vai 6: while/ do while/
        // for /for
        sc.close();
    }

    static int[][] getArray(byte mode, Scanner sc, PrintStream outputStream) {
        if (mode == 3)
            return new int[][] { 
            { 250, 300, 250, 250, 250 }, 
            { 100, 50, 60, 70, 200 }, 
            { 250, 100, 70, 150, 200 },
            { 300, 400, 200, 250, 300 }, 
            { 400, 320, 250, 220, 270 }, 
            { 200, 200, 200, 200, 200 },
            { 250, 260, 250, 250, 250 }, 
            { 250, 200, 100, 250, 80 } 
            };
        Random r = new Random();
        if (mode == 2)
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
        if (mode == 1) {
            int[][] result = new int[8][5];
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 5; j++) {
                    outputStream.println(String.format("Ievadiet veselu skaitli 10-250 : %1$s"+
                    ".rinda un %2$s. kolona", i+1, j+1));
                    if (sc.hasNext("(10|[1-4]?[2-9][0-9]|[1-2][0-9]|[1-4][0-2][0-9]|500)"))
                        result[i][j] = sc.nextInt();
                    else {
                        outputStream.println(errorInputMessage);
                        sc.close();
                        return new int[][] {};
                    }
                }
            return result;
        }
        return new int[][] {};
    }

    static int g(Random r) {
        return r.nextInt(491) + 10;
    }
}
