package com.aminbenarieb.yandextask.Extensions;

import java.util.HashMap;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class LanguagesMap extends HashMap<String, String> {
    public String getKeyByValue(String value) {
        for (Entry<String, String> entry : this.entrySet()) {
            if (value.equals(entry.getValue()) ) {
                return entry.getKey();
            }
        }
        return null;
    }
}

