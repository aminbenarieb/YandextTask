package com.aminbenarieb.yandextask.Services.JSONParser;


import com.aminbenarieb.yandextask.Extensions.LanguagesMap;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public interface JSONParser {

    public LanguagesMap getLangsMap(String json) throws Exception;

}
