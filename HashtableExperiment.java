import java.io.*;
import java.util.*;

public class HashtableExperiment {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
            return;
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = (args.length == 3) ? Integer.parseInt(args[2]) : 0;

        int tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        int n = (int) Math.ceil(loadFactor * tableSize);

        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + tableSize);

        Hashtable<Object> linearTable = new LinearProbing<>(tableSize);
        Hashtable<Object> doubleTable = new DoubleHashing<>(tableSize);

        System.out.println("HashtableExperiment: Input: " + getDataSourceName(dataSource) + " Loadfactor: " + loadFactor);

        runExperiment("Linear Probing", linearTable, dataSource, n, debugLevel, "linear-dump.txt");
        runExperiment("Double Hashing", doubleTable, dataSource, n, debugLevel, "double-dump.txt");
    }

    private static String getDataSourceName(int dataSource) {
        return switch (dataSource) {
            case 1 -> "Random Numbers";
            case 2 -> "Date Values";
            case 3 -> "Word List";
            default -> "Unknown";
        };
    }

    private static void runExperiment(String name, Hashtable<Object> table, int dataSource, int n, int debugLevel, String dumpFileName) {
        System.out.println("\nUsing " + name);

        int insertions = 0;
        int duplicates = 0;
        int totalProbes = 0;

        try {
            switch (dataSource) {
                case 1 -> {
                    Random rand = new Random();
                    while (insertions < n) {
                        Object key = rand.nextInt();
                        int probes = table.insert(key);
                        if (probes == -1) {
                            // still count probe for duplicate for dump consistency
                            // table.setLastProbeCountForDuplicate(key);
                            duplicates++;
                        } else {
                            insertions++;
                            totalProbes += probes;
                        }
                    }
                }
                case 2 -> {
                    long current = System.currentTimeMillis();
                    while (insertions < n) {
                        Object key = new Date(current);
                        int probes = table.insert(key);
                        if (probes == -1) {
                            // table.setLastProbeCountForDuplicate(key);
                            duplicates++;
                        } else {
                            insertions++;
                            totalProbes += probes;
                        }
                        current += 1000;
                    }
                }
                case 3 -> {
                    try (BufferedReader br = new BufferedReader(new FileReader("word-list.txt"))) {
                        String word;
                        while ((word = br.readLine()) != null && insertions < n) {
                            int probes = table.insert(word);
                            if (probes == -1) {
                                // Handle duplicate case appropriately
                                duplicates++;
                                duplicates++;
                            } else {
                                insertions++;
                                totalProbes += probes;
                            }
                        }
                    }
                }
                default -> throw new IllegalArgumentException("Invalid data source");
            }
        } catch (IOException e) {
            System.err.println("Error reading input data: " + e.getMessage());
            return;
        }

        double avgProbes = insertions > 0 ? (double) totalProbes / insertions : 0;

        System.out.printf("Inserted %d elements, of which %d were duplicates\n", insertions, duplicates);
        System.out.printf("Avg. no. of probes = %.2f\n", avgProbes);

        if (debugLevel == 1) {
            table.dumpToFile(dumpFileName);
            System.out.println("HashtableExperiment: Saved dump of hash table to " + dumpFileName);
        }
    }
}