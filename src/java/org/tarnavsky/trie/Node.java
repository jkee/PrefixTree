package org.tarnavsky.trie;

import org.tarnavsky.trie.callback.TrieMapCallbackFull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jkee
 */

public class Node<T, P, V> {

    private V value;
    private Map<P, Node<T, P, V>> children;

    public Node() {
        value = null;
    }

    public Node<T, P, V> getChild(P prefix) {
        return children.get(prefix);
    }

    public V put(T object, V newValue, Funnel<T, P> funnel, int level) {
        P prefix = funnel.getPrefix(object, level);
        if (prefix == null) {//current level is object level
            V old = value;
            value = newValue;
            return old;
        }
        if (children == null) children = new HashMap<P, Node<T, P, V>>();
        Node<T, P, V> child = children.get(prefix);
        if (child == null) { //there are no such way yet, creating
            Node<T, P, V> newNode = new Node<T, P, V>();
            children.put(prefix, newNode);
            newNode.put(object, newValue, funnel, level + 1);
            return null;
        } else {
            return child.put(object, newValue, funnel, level + 1);
        }
    }

    public V get(T object, Funnel<T, P> funnel, int level) {
        P prefix = funnel.getPrefix(object, level);
        if (prefix == null) return value;
        if (children == null) return null;
        Node<T, P, V> child = children.get(prefix);
        if (child == null) return null;
        else return child.get(object, funnel, level + 1);
    }

    public boolean contains(T object, Funnel<T, P> funnel, int level) {
        P prefix = funnel.getPrefix(object, level);
        if (prefix == null) return value != null;
        if (children == null) return false;
        Node<T, P, V> child = children.get(prefix);
        if (child == null) return false;
        else return child.contains(object, funnel, level + 1);
    }

    public void preOrderDepthForEach(List<P> path, Funnel<T, P> funnel, TrieMapCallbackFull<T, V> callback, int level) {
        if (value != null) {
            T object = funnel.create(path);
            callback.onNode(object, value, level);
        }
        if (children == null) return;
        for (Map.Entry<P, Node<T, P, V>> pNodeEntry : children.entrySet()) {
            path.add(pNodeEntry.getKey());
            callback.onMoveDown(level + 1);
            pNodeEntry.getValue().preOrderDepthForEach(path, funnel, callback, level + 1);
            callback.onMoveUp(level);
            path.remove(path.size() - 1);
        }
    }
}
