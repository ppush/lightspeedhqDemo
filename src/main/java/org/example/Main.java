package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.logging.Logger;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    static long totalCounter = 0L;

    public static void main(String[] args) throws IOException {

        String path = getFilePath(args);

        InputStream inputStream = getInputStream(path);

        var stTime = System.nanoTime();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            calculateByBitSet(br);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        var endTime = System.nanoTime();
        System.out.printf("Time taken is %d min%n", (endTime - stTime) / 1000000 / 1000 / 60);

    }

    private static void calculateByBitSet(BufferedReader br) throws IOException {
        String line;
        BitSet lowerBits = new BitSet();
        BitSet upperBits = new BitSet();

        while ((line = br.readLine()) != null) {
                try {
                    int intIp = IpStringUtils.parseIp(line);
                    if (intIp >= 0) {
                        lowerBits.set(intIp);
                    } else {
                        // Treat as unsigned, map to 0-based index in upperBits
                        int unsignedIndex = intIp - Integer.MIN_VALUE;
                        upperBits.set(unsignedIndex);
                    }

                    totalCounter++;
                    if (totalCounter % 100000000 == 0) {
                        System.out.printf("^^^^^^Processed %d IPs%n", totalCounter);//output processed ips count so that it wouldn't be boring to wait
                    }
                } catch (NullPointerException | IllegalArgumentException e) {
                    //Ignored logged and continues
                    logger.info(e.getMessage());
                }
        }
        System.out.printf("Unique ips is %d%n", (long) lowerBits.cardinality() + upperBits.cardinality());//output unique ips count
        System.out.printf("Total ips is %d%n", totalCounter);//output total ips count
    }


    private static InputStream getInputStream(String path) throws FileNotFoundException {
        InputStream inputStream;
        if (path != null) {
            inputStream = new FileInputStream(path);
        } else {
            ClassLoader classLoader = Main.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("demo.txt")).getFile());
            inputStream = new FileInputStream(file);
        }
        return inputStream;
    }

    private static String getFilePath(String[] args) {
        String path = null;

        var pathMarkerIndexOf = Arrays.asList(args).indexOf("-p");

        if (args.length > pathMarkerIndexOf + 1) {
            path = args[pathMarkerIndexOf + 1];
        }
        return path;
    }

}