package net.bounceme.chronos.rulemanager.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamReader;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter;

import net.bounceme.chronos.rulemanager.dto.AccessDTO;
import net.bounceme.chronos.rulemanager.dto.MethodDTO;

public class MethodDTOListMarshaller implements MessageMarshaller<List<MethodDTO>> {

    @Override
    public Class<? extends List<MethodDTO>> getJavaClass() {
        return (Class) List.class;
    }

    @Override
    public String getTypeName() {
        return "net.bounceme.chronos.rulemanager.MethodDTOList";
    }

    @Override
    public List<MethodDTO> readFrom(ProtoStreamReader reader) throws IOException {
        int size = reader.readInt("size");
        List<MethodDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readObject("element" + i, MethodDTO.class));
        }
        return list;
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, List<MethodDTO> list) throws IOException {
        writer.writeInt("size", list.size());
        for (int i = 0; i < list.size(); i++) {
            writer.writeObject("element" + i, list.get(i), MethodDTO.class);
        }
    }
}
