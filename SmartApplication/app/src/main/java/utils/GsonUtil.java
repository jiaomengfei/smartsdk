package utils;

import com.google.gson.Gson;

/**
 * Created by jmf on 2017/3/20 0020.
 */

public class GsonUtil {
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static String beanToJson(Object clz) {
        Gson gson = new Gson();
        return gson.toJson(clz);
    }
}
