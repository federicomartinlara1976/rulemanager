package net.bounceme.chronos.rulemanager.support;

import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.commons.io.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.interceptor.SimpleKey;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
class RuleManagerHelperTest {

    @Test
    void asDate_ReturnsDate_WhenLocalDateTimeIsProvided() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        Date date = RuleManagerHelper.asDate(localDateTime);

        Assertions.assertNotNull(date);
        Assertions.assertEquals(2023, date.getYear() + 1900);
        Assertions.assertEquals(10, date.getMonth() + 1);
        Assertions.assertEquals(1, date.getDate());
        Assertions.assertEquals(12, date.getHours());
        Assertions.assertEquals(0, date.getMinutes());
    }

    @Test
    void asDate_ReturnsDateWithSystemDefaultZone_WhenLocalDateTimeIsProvided() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        Date date = RuleManagerHelper.asDate(localDateTime);

        Assertions.assertNotNull(date);
        Assertions.assertEquals(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), date.getTime());
    }

    @Test
    void asDate_ThrowsException_WhenLocalDateTimeIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> RuleManagerHelper.asDate(null));
    }
}
