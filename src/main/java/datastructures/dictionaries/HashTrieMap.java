package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }
        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode) this.root;
        V val = null;
        for(A element : key){
            if(!curr.pointers.containsKey(element)){
                curr.pointers.put(element, new HashTrieNode());
            }
            curr = curr.pointers.get(element);
        }
        val = curr.value;
        curr.value = value;
        if (val == null){
            size++;
        }
        return val;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for (A element: key){
            curr = curr.pointers.get(element);
            if (curr == null){
                return null;
            }
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for (A element: key){
            curr = curr.pointers.get(element);
            if(curr == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode lastNode = (HashTrieNode)this.root;
        A lastElement = null;
        for (A element : key) {
            lastElement = element;
        }
        HashTrieNode curr = (HashTrieNode)this.root;

        for (A element: key) {
            if (curr == null) {
                return;
            }
            if (curr.value != null || curr.pointers.size() > 1 ) {
                lastNode = curr;
                lastElement = element;
            }
            curr = curr.pointers.get(element);
        }
        if (curr != null && curr.value != null) {
            if (!curr.pointers.isEmpty()) {
                curr.value = null;
            } else if (lastNode != null) {
                lastNode.pointers.remove(lastElement);
            }
            this.size--;
        }
    }

    @Override
    public void clear() {
        this.root = null;
    }
}
