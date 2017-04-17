package com.aminbenarieb.yandextask.Services.JSONParser;

import com.aminbenarieb.yandextask.Extensions.LanguagesMap;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class ABJSONParser implements JSONParser {

    @Override
    public LanguagesMap getLangsMap(String json) throws Exception {
        JSONObject mainObject = new JSONObject(json);
        JSONObject langs =  mainObject.getJSONObject("langs");
        LanguagesMap langsMap = new LanguagesMap();
        Iterator<String> iterator = mainObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = langs.getString(key);
            langsMap.put(key, value);
        }

        return langsMap;
    }
}
