package com.idea.opengles.help;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: jijie
 * Description:资源读取器
 * Email: qinjijie@unisound.com
 * Date: Create at 14:57 2019/7/17 0017
 * Modified:
 */
public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open resource ：" + resourceId, e);
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException("resource not found ：" + resourceId, e);
        }

        return body.toString();
    }

}
