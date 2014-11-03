package net.pushq.soccero.convert;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by Michal on 2014-10-12.
 */
public class Mapper {
    public static <T> T mapToObject(Map<String, ? extends Object> map, T game) {
        Set<String> strings = map.keySet();
        for (String key : strings) {
            try {
                Object value = map.get(key);

                if (value instanceof String[]) {
                    value =  ((String[]) value)[0];
                }

                if (value != null) {
                    BeanUtils.setProperty(game, key, value);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return game;
    }
}
