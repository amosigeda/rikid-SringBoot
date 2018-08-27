package com.rokid.skill.kit4j.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyukai
 * @date 2018/7/22
 */
@Slf4j
public class JacksonUtil {

    private static ObjectMapper mObjectMapper;

    /**
     * Creates an {@link ObjectMapper} for mapping json objects. Mapper can be configured here
     *
     * @return created {@link ObjectMapper}
     */
    private static ObjectMapper getMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
        }
        return mObjectMapper;
    }

    /**
     * Maps json string to specified class
     *
     * @param json string to parse
     * @param tClass class of object in which json will be parsed
     * @param <T> generic parameter for tClass
     * @return mapped T class instance
     */
    public static <T> T toEntity(String json, Class<T> tClass) {
        try {
            return getMapper().readValue(json, tClass);
        } catch (IOException e) {
            log.error("convert to object error : {}", e);
            return null;
        }
    }

    /**
     * Maps json string to {@link ArrayList} of specified class object instances
     *
     * @param json string to parse
     * @param tClass class of object in which json will be parsed
     * @param <T> generic parameter for tClass
     * @return mapped T class instance
     */
    public static <T> ArrayList<T> arrayList(String json, Class<T> tClass) throws IOException {
        TypeFactory typeFactory = getMapper().getTypeFactory();
        JavaType type = typeFactory.constructCollectionType(ArrayList.class, tClass);
        return getMapper().readValue(json, type);
    }

    /**
     * Writes specified object as string
     *
     * @param object object to write
     * @return result json
     */
    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("convert to json error : {}", e);
            return null;
        }
    }


}
