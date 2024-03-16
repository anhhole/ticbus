package com.ticbus.backend.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author AnhLH
 */
public class JSONUtils {

  public static final ObjectMapper mapper = new ObjectMapper()
      .setAnnotationIntrospector(new JacksonAnnotationIntrospector());




  public static <T> T DeSerialize(String value, Class<T> typeOfT) throws IOException {
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    T result = mapper.readValue(value, typeOfT);
    return result;
  }

  public static <T> List<T> parseJsonArray(String json,
      Class<T> classOnWhichArrayIsDefined)
      throws IOException, ClassNotFoundException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    Class<T[]> arrayClass = (Class<T[]>) Class
        .forName("[L" + classOnWhichArrayIsDefined.getName() + ";");
    T[] objects = mapper.readValue(json, arrayClass);
    return Arrays.asList(objects);
  }

  public static String Serialize(Object value) throws IOException {
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    return mapper.writeValueAsString(value);
  }
}
