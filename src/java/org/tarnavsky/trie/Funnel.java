package org.tarnavsky.trie;

/**
 * @author jkee
 */

public interface Funnel<P> {
    public P getPrefix(int index);
}
