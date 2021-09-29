/*
 *    Copyright 2009-2014 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache.decorators;

import org.apache.ibatis.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 事务缓存装饰器，该缓存与其他缓存的不同之处在于，TransactionalCache增加了两个方法，
 * 即commit()和rollback()。当写入缓存时，只有调用commit()方法后，缓存对象
 * 才会真正添加到TransactionalCache对象中，如果调用了rollback()方法，写入操作将被回滚。
 *
 * @author Clinton Begin
 */
public class TransactionalCache implements Cache {

    private Cache delegate;
    private boolean clearOnCommit;
    private Map<Object, AddEntry> entriesToAddOnCommit;
    private Map<Object, RemoveEntry> entriesToRemoveOnCommit;

    public TransactionalCache(Cache delegate) {
        this.delegate = delegate;
        this.clearOnCommit = false;
        this.entriesToAddOnCommit = new HashMap<Object, AddEntry>();
        this.entriesToRemoveOnCommit = new HashMap<Object, RemoveEntry>();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public Object getObject(Object key) {
        if (clearOnCommit) return null; // issue #146
        return delegate.getObject(key);
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    @Override
    public void putObject(Object key, Object object) {
        entriesToRemoveOnCommit.remove(key);
        entriesToAddOnCommit.put(key, new AddEntry(delegate, key, object));
    }

    @Override
    public Object removeObject(Object key) {
        entriesToAddOnCommit.remove(key);
        entriesToRemoveOnCommit.put(key, new RemoveEntry(delegate, key));
        return delegate.getObject(key);
    }

    @Override
    public void clear() {
        reset();
        clearOnCommit = true;
    }

    public void commit() {
        if (clearOnCommit) {
            delegate.clear();
        } else {
            for (RemoveEntry entry : entriesToRemoveOnCommit.values()) {
                entry.commit();
            }
        }
        for (AddEntry entry : entriesToAddOnCommit.values()) {
            entry.commit();
        }
        reset();
    }

    public void rollback() {
        reset();
    }

    private void reset() {
        clearOnCommit = false;
        entriesToRemoveOnCommit.clear();
        entriesToAddOnCommit.clear();
    }

    private static class AddEntry {
        private Cache cache;
        private Object key;
        private Object value;

        public AddEntry(Cache cache, Object key, Object value) {
            this.cache = cache;
            this.key = key;
            this.value = value;
        }

        public void commit() {
            cache.putObject(key, value);
        }
    }

    private static class RemoveEntry {
        private Cache cache;
        private Object key;

        public RemoveEntry(Cache cache, Object key) {
            this.cache = cache;
            this.key = key;
        }

        public void commit() {
            cache.removeObject(key);
        }
    }

}
