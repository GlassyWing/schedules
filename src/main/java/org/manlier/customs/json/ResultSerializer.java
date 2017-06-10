package org.manlier.customs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.manlier.dto.base.BaseResult;

import java.io.IOException;

/**
 * Created by manlier on 2017/6/8.
 */
public class ResultSerializer extends StdSerializer<BaseResult.Result> {

    protected ResultSerializer() {
        this(null);
    }

    protected ResultSerializer(Class<BaseResult.Result> t) {
        super(t);
    }

    @Override
    public void serialize(BaseResult.Result value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeBoolean(value == BaseResult.Result.SUCCESS);
    }

}
