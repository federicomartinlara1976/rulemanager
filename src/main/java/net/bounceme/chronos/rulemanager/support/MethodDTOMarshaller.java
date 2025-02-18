package net.bounceme.chronos.rulemanager.support;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

import net.bounceme.chronos.rulemanager.dto.MethodDTO;

public class MethodDTOMarshaller implements MessageMarshaller<MethodDTO> {

    @Override
    public Class<? extends MethodDTO> getJavaClass() {
        return MethodDTO.class;
    }

    @Override
    public String getTypeName() {
        return "net.bounceme.chronos.rulemanager.MethodDTO";
    }

    @Override
    public MethodDTO readFrom(ProtoStreamReader reader) throws IOException {
        Long id = reader.readLong("id");
        String name = reader.readString("name");
        return new MethodDTO(id, name);
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, MethodDTO accessDTO) throws IOException {
        writer.writeLong("id", accessDTO.getId());
        writer.writeString("name", accessDTO.getName());
    }
}