package market;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V>  {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private List<LinkedList<Entry>> table;
    private int size;

    public HashTable() {
        this.table = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            table.add(new LinkedList<>());
        }
        this.size = 0;
    }

    public boolean contains(String key) {
        int index = calculateIndex(key);
        LinkedList<Entry> bucket = table.get(index);
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Transaction> get(String key) {
        int index = calculateIndex(key);
        LinkedList<Entry> bucket = table.get(index);
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                return new LinkedList<>(entry.getValues());
            }
        }
        return new LinkedList<>();
    }

    public void put(String key, List<Transaction> values) {
        int index = calculateIndex(key);
        LinkedList<Entry> bucket = table.get(index);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValues(values);
                return;
            }
        }

        Entry newEntry = new Entry(key, values);
        bucket.add(newEntry);
        size++;

        if ((double) size / table.size() > LOAD_FACTOR) {
            resize();
        }
    }

    public void remove(String key) {
        int index = calculateIndex(key);
        LinkedList<Entry> bucket = table.get(index);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                bucket.remove(entry);
                size--;
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (LinkedList<Entry> bucket : table) {
            bucket.clear();
        }
        size = 0;
    }

    private void resize() {
        List<LinkedList<Entry>> newTable = new ArrayList<>(table.size() * 2);

        for (int i = 0; i < table.size() * 2; i++) {
            newTable.add(new LinkedList<>());
        }

        for (LinkedList<Entry> bucket : table) {
            for (Entry entry : bucket) {
                int index = calculateIndex(entry.getKey(), newTable.size());
                newTable.get(index).add(entry);
            }
        }

        table = newTable;
    }

    private int calculateIndex(String key) {
        return Math.abs(key.hashCode()) % table.size();
    }

    private int calculateIndex(String key, int newSize) {
        return Math.abs(key.hashCode()) % newSize;
    }

    private static class Entry {
        private String key;
        private List<Transaction> values;

        public Entry(String key, List<Transaction> values) {
            this.key = key;
            this.values = values;
        }

        public String getKey() {
            return key;
        }

        public List<Transaction> getValues() {
            return values;
        }

        public void setValues(List<Transaction> values) {
            this.values = values;
        }
    }

    public boolean containsKey(String key) {
        int index = calculateIndex(key);
        LinkedList<Entry> bucket = table.get(index);
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public java.util.Map.Entry<String, LinkedList<Transaction>>[] entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    public void putAll(HashTable<String, LinkedList<Transaction>> otherHashTable) {
        for (java.util.Map.Entry<String, LinkedList<Transaction>> entry : otherHashTable.entrySet()) {
            String key = entry.getKey();
            LinkedList<Transaction> values = entry.getValue();

            if (this.containsKey(key)) {
                // Eğer key zaten varsa, mevcut listeye ekleyin
                this.get(key).addAll(values);
            } else {
                // Eğer key yoksa, yeni bir liste oluşturun
                this.put(key, new LinkedList<>(values));
            }
        }
    }


}
class Entry<K, V> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
