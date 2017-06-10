package org.manlier.customs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.manlier.contracts.ScheduleStatus;

import java.io.IOException;

/**
 * Created by manlier on 2017/6/8.
 */
public class ScheduleStatusSerializer extends StdSerializer<ScheduleStatus> {

    protected ScheduleStatusSerializer() {
        this(null);
    }

    protected ScheduleStatusSerializer(Class<ScheduleStatus> t) {
        super(t);
    }

    @Override
    public void serialize(ScheduleStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.ordinal());
    }
}
