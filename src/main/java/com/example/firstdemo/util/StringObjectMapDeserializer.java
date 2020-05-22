package com.example.firstdemo.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;

import java.lang.reflect.Type;
import java.util.Map;

public final class StringObjectMapDeserializer extends MapDeserializer {
    public static final StringObjectMapDeserializer instance = new StringObjectMapDeserializer();

    public StringObjectMapDeserializer() {
    }

    protected Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map) {

        Map value = parseMap(parser, map, Object.class, fieldName);
        try {
            ReflectionUtils.setFieldValue(value, "type", type);
        } catch (Exception var7) {

        }

        return value;
    }
}
