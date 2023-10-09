package com.qxbase.blog.common.constant;

import java.util.regex.Pattern;

public class PatternConstant {

    private PatternConstant() {}

    public static final Pattern PHONE_NUMBER_PATTERN = java.util.regex.Pattern.compile("^1[3-9]\\d{9}$");

    public static final Pattern EMAIL_NUMBER_PATTERN = java.util.regex.Pattern.compile("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");
}
