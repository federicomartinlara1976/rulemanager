package net.bounceme.chronos.rulemanager.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.infinispan.spring.remote.provider.SpringRemoteCacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CacheConfig.class)
public class CacheConfigTest {

    @Autowired
    private SpringRemoteCacheManager cacheManager;

    @Test
    public void testCacheManager() {
        assertNotNull(cacheManager);
        Cache cache = cacheManager.getCache("myCache");
        assertNotNull(cache);

        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1").get());
    }
}
