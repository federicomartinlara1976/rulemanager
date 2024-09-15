package net.bounceme.chronos.rulemanager.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.commons.io.ByteBuffer;
import org.infinispan.commons.io.ByteBufferImpl;
import org.infinispan.commons.marshall.AbstractMarshaller;
import org.springframework.cache.interceptor.SimpleKey;

public class SimpleKeyMarshaller extends AbstractMarshaller {

	@Override
	public Object objectFromByteBuffer(byte[] buf, int offset, int length) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(buf, offset, length);
				ObjectInputStream ois = new ObjectInputStream(bais)) {
			return ois.readObject(); // Deserializar el objeto
		}
	}

	@Override
	public boolean isMarshallable(Object o) throws Exception {
		return o instanceof SimpleKey || o instanceof SimpleKey[];
	}

	@Override
	public MediaType mediaType() {
		return MediaType.APPLICATION_OBJECT;
	}

	@Override
	protected ByteBuffer objectToBuffer(Object o, int estimatedSize) throws IOException, InterruptedException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(estimatedSize);
				ObjectOutputStream oos = new ObjectOutputStream(baos)) {

			oos.writeObject(o); // Serializar el objeto
			byte[] bytes = baos.toByteArray();
			return ByteBufferImpl.create(bytes);
		}
	}

}
