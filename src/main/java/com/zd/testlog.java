package com.zd;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangdong on 16/5/31.
 */
public class testlog {
    public static void main(String[] args) {
        String data = "2016-05-31 18:03:10,556 [http-nio-8080-exec-1] INFO Log - 111111111111111111";
        if (data != null && data.length() > 0) {
            String ragex = "(.*?)\\[(.*?)\\]\\[(.*?)\\]\\[(.*?)\\] - (.*)";
            Pattern p = Pattern.compile(ragex);
            Matcher m = p.matcher(data);
            if (m.matches()) {
                String date = m.group(1);
                String level = m.group(2);
                String className = m.group(3);
                String threadId = m.group(4);
                String message = m.group(5);
                /*if (level.equals("ERROR")) {
                    errorDo(date, level, className, threadId, message);
                }
                if (level.equals("INFO ")) {
                    infoDo(date, level, className, threadId, message);
                }*/
            }
        }
    }


}
