package org.tarnavsky.trie;

import java.util.List;

/**
 * @author jkee
 */

public interface Funnel<T, P> {
    P getPrefix(T object, int level);
    T create(List<P> path);
}
