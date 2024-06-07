package com.gfk.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


/**
 * BigDecimal Json格式化序列器
 *
 * @author wzl
 * @version 1.0 2024/1/13
 */
public class BigDecimalFor3ScaleSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
        } else {
            if (value.scale() > 3) {
                value = value.setScale(3, RoundingMode.HALF_UP);
            }
            gen.writeNumber(value.toPlainString());
        }
    }
}
