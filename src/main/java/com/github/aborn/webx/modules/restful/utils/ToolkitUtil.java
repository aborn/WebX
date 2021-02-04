package com.github.aborn.webx.modules.restful.utils;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author aborn
 * @date 2021/01/29 5:01 PM
 */
public class ToolkitUtil {

    public static String formatHtmlImage(URL url) {
        return "<img src=\"" + url + "\"> ";
    }

    @NotNull
    public static String removeRedundancyMarkup(String pattern) {

        String localhostRegex = "(http(s?)://)?(localhost)(:\\d+)?";
        String hostAndPortRegex = "(http(s?)://)?" +
                "( " +
                "([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6} |" +  // domain
                "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)" + // ip address
                ")" ;

        String localhost = "localhost";
        if (pattern.contains(localhost)) {
            pattern = pattern.replaceFirst(localhostRegex,"");
        }

        if (pattern.contains("http:") || pattern.contains("https:") ) { // quick test if reg exp should be used
            pattern  = pattern.replaceFirst(hostAndPortRegex, "");
        }

        //TODO : resolve RequestMapping(params="method=someMethod")
        // 包含参数
        if (pattern.contains("?")) {
            pattern = pattern.substring(0, pattern.indexOf("?"));
        }
        return pattern;
    }

    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }

        return collection.toArray(new String[collection.size()]);
    }

}
