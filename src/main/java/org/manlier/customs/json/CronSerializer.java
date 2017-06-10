package org.manlier.customs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.quartz.CronExpression;

import java.io.IOException;

/**
 * Created by manlier on 2017/6/10.
 */
public class CronSerializer extends StdSerializer<CronExpression> {

    protected CronSerializer() {
        this(null);
    }

    protected CronSerializer(Class<CronExpression> t) {
        super(t);
    }

    @Override
    public void serialize(CronExpression cronExpression, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(cronExpression.getCronExpression());
    }
}
