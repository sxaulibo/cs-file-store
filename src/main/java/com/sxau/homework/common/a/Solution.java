package com.sxau.homework.common.a;

public class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 0) {
            return "";
        } else if (numRows == 1) {
            return s;
        }
        StringBuilder[] stringBuilders = new StringBuilder[numRows];
        for (int i = 0; i < stringBuilders.length; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        int rowIndex = 0;
        boolean returnFlag = false;
        for (char c : s.toCharArray()) {
            stringBuilders[rowIndex].append(c);
            if (rowIndex == numRows - 1) {
                returnFlag = true;
            } else if (rowIndex == 0) {
                returnFlag = false;
            }
            rowIndex += returnFlag ? (-1) : 1;
        }
        return getResult(stringBuilders);
    }

    private String getResult(StringBuilder[] stringBuilders) {
        StringBuilder sb = new StringBuilder();
        for (StringBuilder temp : stringBuilders) {
            sb.append(temp);
        }
        return sb.toString();
    }
}
