import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DoubleHashing<T> extends Hashtable<T> {
    public DoubleHashing(int capacity) {
        super(capacity);
    }

    private int hash2(T key) {
        return 1 + positiveMod(key.hashCode(), size - 2); // Secondary hash function
    }

    @Override
    public int insert(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int stepSize = hash2(key);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                table[hash].incrementFrequency();
                table[hash].setProbeCount(probes);
                return -1; // Duplicate found
            }

            hash = (hash + stepSize) % size;
            probes++;

            if (probes >= size) {
                System.err.println("Error: Hashtable is full, cannot insert " + key);
                return -1;
            }
        }

        table[hash] = new HashObject<>(key);
        table[hash].setProbeCount(probes);
        count++;
        return probes;
    }

    @Override
    public int search(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int stepSize = hash2(key);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) return probes;
            hash = (hash + stepSize) % size;
            probes++;

            if (probes >= size) break;
        }
        return -1;
    }

    @Override
    public void dumpToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    writer.println("table[" + i + "]: " + table[i]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setLastProbeCountForDuplicate(Object keyObj) {
        T key = (T) keyObj;
        int hash = positiveMod(key.hashCode(), size);
        int stepSize = hash2(key);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                table[hash].setProbeCount(probes);
                return;
            }
            hash = (hash + stepSize) % size;
            probes++;
        }
    }

    @Override
    public String toString() {
        return "DoubleHashing";
    }
}
