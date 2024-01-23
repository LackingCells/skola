
/**
 * This class represents a symbol table, or hash table, with a very
 * simple hash function. Observe that you are not supposed to change
 * hash function. Ensure that you use linear probing as your method of
 * collision handling.
 *
 * @author Magnus Nielsen, Tommy Färnqvist ...
 */
public class SymbolTable {
    private static final int INIT_CAPACITY = 7;

    /* Number of key-value pairs in the symbol table */
    private int size;
    /* Size of linear probing table */
    private int maxSize;
    /* The keys */
    private String[] keys;
    /* The values */
    private Character[] vals;

    /**
     * Create an empty hash table - use 7 as default size
     */
    public SymbolTable() {
        this(INIT_CAPACITY);
    }

    /**
     * Create linear probing hash table of given capacity
     */
    public SymbolTable(int capacity) {
        size = 0;
        maxSize = capacity;
        keys = new String[maxSize];
        vals = new Character[maxSize];
    }

    /**
     * Return the number of key-value pairs in the symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Is the symbol table empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does a key-value pair with the given key exist in the symbol table?
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Hash function for keys - returns value between 0 and maxSize-1
     */
    public int hash(String key) {
        int i;
        int v = 0;

        for (i = 0; i < key.length(); i++) {
            v += key.charAt(i);
        }

        return v % maxSize;
    }

    /**
     * Insert the key-value pair into the symbol table.
     * TODO: implement the put method.
     */
    public void put(String key, Character val) {

        // Call to put with val = null and key = key should give the same result as delete(key)
        if(val == null){
            delete(key);
            return;
        }

        int index = hash(key); // Variable to store current index

        // Iterate though the keys array if slot at index isn't empty
        while (keys[index] != null) {
            // Key already exists in the table, update its value
            if (keys[index].equals(key)) {
                vals[index] = val;
                return;
            }

            index = (index + 1) % maxSize; // Linear probing
        }

        // Found an empty slot for the new key-value pair
        keys[index] = key;
        vals[index] = val;
        size++;
    }

    private void resize(int newCapacity) {
        SymbolTable newTable = new SymbolTable(newCapacity);

        for (int i = 0; i < maxSize; i++) {
            if (keys[i] != null) {
                newTable.put(keys[i], vals[i]); // Rehash the keys into the new table
            }
        }

        keys = newTable.keys;
        vals = newTable.vals;
        maxSize = newTable.maxSize;
    }


    /**
     * Return the value associated with the given key, null if no such
     * value.
     * TODO: implement the get method.
     */
    public Character get(String key) {
        int index = hash(key);

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return vals[index]; // Return the value if the key is found
            }
            index = (index + 1) % maxSize; // Linear probing
        }

        return null; // Return null if the key is not found
    }


    /**
     * Delete the key (and associated value) from the symbol table.
     * TODO: implement the delete method.
     */
    public void delete(String key) {
        int index = hash(key);

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                // Key found, mark the slot as deleted
                keys[index] = null;
                vals[index] = null;
                size--;

                // Rehash and re-insert subsequent key-value pairs
                index = (index + 1) % maxSize;
                while (keys[index] != null) {
                    String rehashKey = keys[index];
                    Character rehashVal = vals[index];
                    keys[index] = null;
                    vals[index] = null;
                    size--;
                    put(rehashKey, rehashVal); // Re-insert the pair
                    index = (index + 1) % maxSize;
                }
                return; // Exit the method
            }
            index = (index + 1) % maxSize; // Linear probing
        }
    }


    /**
     * Print the contents of the symbol table.
     */
    public void dump() {
        String str = "";

        for (int i = 0; i < maxSize; i++) {
            str = str + i + ". " + vals[i];
            if (keys[i] != null) {
                str = str + " " + keys[i] + " (";
                str = str + hash(keys[i]) + ")";
            } else {
                str = str + " -";
            }
            System.out.println(str);
            str = "";
        }
    }
}
