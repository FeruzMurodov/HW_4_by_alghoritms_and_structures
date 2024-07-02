import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;

public class Hashmap<K, V> implements Iterable<Hashmap.Entity> {

    @Override
    public Iterator<Hashmap.Entity> iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator<Hashmap.Entity>{
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Entity next() {
            LinkedList<Entity> list = new LinkedList<>();
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    Bucket.Node node = buckets[i].head;
                    while (node != null) {
                        list.add(node.value);
                        node = node.next;
                    }
                }
            }
            return list.get(index++);
        }
    }

    @Override
    public String toString() {
        String result;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                Bucket.Node node = buckets[i].head;
                while (node != null) {
                    stringBuilder.append(node.value).append("\n");
                    node = node.next;
                }
            }
        }
        result = String.valueOf(stringBuilder);
        return result;
    }

    public V put(K key, V value){
        if (buckets.length * LOAD_FACTOR <= size){
            recalculate();
        }
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = (V)bucket.add(entity);
        if (buf == null){
            size++;
        }
        return buf;
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V) bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        V buf = (V) bucket.remove(key);
        if (buf != null){
            size--;
        }
        return buf;
    }

    private void recalculate(){
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            Bucket<K, V> bucket = old[i];
            if (bucket == null)
                continue;
            Bucket.Node node = bucket.head;
            while (node != null){
                put((K) node.value.key, (V) node.value.value);
                node = node.next;
            }
        }
    }

    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }
    public Hashmap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public Hashmap(int capacity){
        buckets = new Bucket[capacity];
    }

    private Bucket[] buckets;
    private int size;

    class Entity{
        K key;
        V value;

        @Override
        public String toString() {
            return "key=" + key +
                    ", value=" + value;
        }
    }

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;

    class Bucket<K,V>{
        public Node head;
        class Node{
            Node next;
            Entity value;
        }

        public V add(Entity entity){

            Node node = new Node();
            node.value = entity;
            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V) currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else{
                    currentNode.next = node;
                    return null;
                }

            }

        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return (V) node.value.value;
                node = node.next;
            }
            return null;
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else {
                Node node = head;
                while (node.next != null){
                    if (node.next.value.equals(key)){
                        V buf = (V) node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

    }
}
