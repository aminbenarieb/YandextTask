package com.aminbenarieb.yandextask.Services.Storage;

import java.io.IOException;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public interface Storage {
    public void saveFile(String params, String mJsonResponse) throws IOException;
    public String getFile(String params) throws IOException;
}
