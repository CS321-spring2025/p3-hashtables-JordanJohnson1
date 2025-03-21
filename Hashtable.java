import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

abstract class Hashtable<T> {
    protected int size;
    protected HashObject<T>[] table;
    protected int count;

    @SuppressWarnings("unchecked")
    public Hashtable(int capacity) {
        this.size = capacity;
        this.table = new HashObject[capacity];
        this.count = 0;
    }

    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) quotient += divisor;
        return quotient;
    }

    public abstract int insert(T key);
    public abstract int search(T key);

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

    // Default no-op for subclasses to override
    public void setLastProbeCountForDuplicate(Object key) {
        // overridden in subclasses
    }
}
