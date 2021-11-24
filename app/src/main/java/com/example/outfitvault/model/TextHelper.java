package com.example.outfitvault.model;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class TextHelper {

    public static SpannableString stringColorToWhite(CharSequence string) {
        SpannableString s = new SpannableString(string);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        return s;
    }
}
