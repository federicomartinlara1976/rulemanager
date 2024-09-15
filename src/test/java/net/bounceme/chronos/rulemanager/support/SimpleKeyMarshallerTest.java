package net.bounceme.chronos.rulemanager.support;

import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.commons.io.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.interceptor.SimpleKey;

import java.io.IOException;

@SpringBootTest
class SimpleKeyMarshallerTest {

    @Test
    void objectFromByteBuffer_ReturnsObject_WhenValidByteArrayProvided() throws IOException, ClassNotFoundException, InterruptedException {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();
        SimpleKey key = new SimpleKey("test");
        byte[] bytes = marshaller.objectToBuffer(key, 1024).getBuf();

        Object result = marshaller.objectFromByteBuffer(bytes, 0, bytes.length);

        Assertions.assertTrue(result instanceof SimpleKey);
        Assertions.assertEquals(key, result);
    }

    @Test
    void objectFromByteBuffer_ThrowsException_WhenInvalidByteArrayProvided() {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();
        byte[] invalidBytes = new byte[]{1, 2, 3, 4};

        Assertions.assertThrows(IOException.class, () -> marshaller.objectFromByteBuffer(invalidBytes, 0, invalidBytes.length));
    }

    @Test
    void isMarshallable_ReturnsTrue_WhenSimpleKeyProvided() throws Exception {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();
        SimpleKey key = new SimpleKey("test");

        boolean result = marshaller.isMarshallable(key);

        Assertions.assertTrue(result);
    }

    @Test
    void isMarshallable_ReturnsFalse_WhenNonSimpleKeyProvided() throws Exception {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();
        String nonSimpleKey = "test";

        boolean result = marshaller.isMarshallable(nonSimpleKey);

        Assertions.assertFalse(result);
    }

    @Test
    void mediaType_ReturnsApplicationObject() {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();

        MediaType result = marshaller.mediaType();

        Assertions.assertEquals(MediaType.APPLICATION_OBJECT, result);
    }

    @Test
    void objectToBuffer_ReturnsByteBuffer_WhenValidObjectProvided() throws IOException, InterruptedException {
        SimpleKeyMarshaller marshaller = new SimpleKeyMarshaller();
        SimpleKey key = new SimpleKey("test");

        ByteBuffer result = marshaller.objectToBuffer(key, 1024);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getBuf().length > 0);
    }
}
