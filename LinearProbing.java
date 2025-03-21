import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
@SuppressWarnings("unchecked")
public class LinearProbing<T> extends Hashtable<T> {
    public LinearProbing(int capacity) {
        super(capacity);
    }

    @Override
    public int insert(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                table[hash].incrementFrequency();
                table[hash].setProbeCount(probes);
                return -1;
            }
            hash = (hash + 1) % size;
            probes++;
        }

        table[hash] = new HashObject<>(key);
        table[hash].setProbeCount(probes);
        count++;
        return probes;
    }

    @Override
    public int search(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) return probes;
            hash = (hash + 1) % size;
            probes++;
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
    public void setLastProbeCountForDuplicate(Object keyObj) {
        T key = (T) keyObj;
        int hash = positiveMod(key.hashCode(), size);
        int probes = 0;

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                table[hash].setProbeCount(probes);
                return;
            }
            hash = (hash + 1) % size;
            probes++;
        }
    }

    @Override
    public String toString() {
        return "LinearProbing";
    }
}
