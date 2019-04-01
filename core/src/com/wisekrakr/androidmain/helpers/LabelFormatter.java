package com.wisekrakr.androidmain.helpers;

public class LabelFormatter {
    private final int textLength;
    private final String[] data;
    private final StringBuilder textBuilder;

    public LabelFormatter(String text) {
        this.textBuilder = new StringBuilder();
        this.data = text.split("\n");
        int temp = 0;
        for (int i = 0 ; i < data.length; i++) {
            temp += data[i].length();
        }
        textLength = temp;
    }

    public String getText(float percent) {
        textBuilder.delete(0, textBuilder.length());
        int current = Math.round(percent * textLength);

        for (final String row : data) {
            current -= row.length();
            if (current >= 0) {
                textBuilder.append(row);
                if (current != 0) {
                    textBuilder.append('\n');
                }
            } else {
                textBuilder.append(row, 0, row.length() + current);

                // Back fill spaces for partial line
                for (int i = 0; i < -current; i++) {
                    textBuilder.append(' ');
                }
            }

            if (current <= 0) {
                break;
            }
        }

        return textBuilder.toString();
    }
}
