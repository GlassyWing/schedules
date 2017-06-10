package org.manlier.customs.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.quartz.CronExpression;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by manlier on 2017/6/10.
 */
public class CronDeserializer extends StdDeserializer<CronExpression> {

    protected CronDeserializer() {
        this(null);
    }

    protected CronDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CronExpression deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String cronExpression = jsonParser.getText();
        try {
            return new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
