package com.sxau.homework.common;

import java.util.ArrayList;
import java.util.List;

class Solution {
    private Integer maxRadius = -1;
    private Integer maxCenter = null;

    /**
     * #a#b#c#d#c#b#a#
     */
    public String longestPalindrome(String s) {
        if(s.isEmpty()){
            return "";
        }
        s = encode(s);
        Integer[] radiusArray = new Integer[100];
        List<List<Integer>> toBeDeterminedIndexs = new ArrayList<>();
        preProcess(toBeDeterminedIndexs, s.length());
        Integer keyCenterIndex = null;
        Integer keyRadiusLength = null;

        for (int i = 0; i < s.length(); i++) {
            char keyCenterChar = s.charAt(i);
            if (toBeDeterminedIndexs.get(i) != null) {
                List<Integer> indexList = toBeDeterminedIndexs.get(i);
                for (int j = 0; j < indexList.size(); j++) {
                    Integer toBeDeterminedIndex = indexList.get(j);
                    if ((toBeDeterminedIndex * 2 - i) < 0 || keyCenterChar != s.charAt(toBeDeterminedIndex * 2 - i) || i == (s.length() - 1)) {
                        if (i == (s.length() - 1) && keyCenterChar == s.charAt(toBeDeterminedIndex * 2 - i)) {
                            radiusArray[toBeDeterminedIndex] = i - toBeDeterminedIndex;
                            afterProcess(toBeDeterminedIndex, radiusArray);
                        } else {
                            radiusArray[toBeDeterminedIndex] = i - toBeDeterminedIndex - 1;
                            afterProcess(toBeDeterminedIndex, radiusArray);
                            keyCenterIndex = toBeDeterminedIndex;
                            keyRadiusLength = radiusArray[toBeDeterminedIndex];
                        }
                    } else {
                        setToBeDeterminedArray(toBeDeterminedIndexs, toBeDeterminedIndex, i + 1);
                    }
                }
            }
            if (i == 0) {
                radiusArray[i] = 0;
                afterProcess(i, radiusArray);
                keyCenterIndex = i;
                keyRadiusLength = radiusArray[i];
                continue;
            }
            if (i == (s.length() - 1)) {
                radiusArray[i] = 0;
                afterProcess(i, radiusArray);
            } else if (i <= (keyCenterIndex + keyRadiusLength)) {
                int mirrorIndex = keyCenterIndex * 2 - i;
                int mirrorLeftBoundary = mirrorIndex - radiusArray[mirrorIndex];
                int keyLeftBoundary = keyCenterIndex - keyRadiusLength;
                if (mirrorLeftBoundary > keyLeftBoundary) {
                    radiusArray[i] = radiusArray[mirrorIndex];
                    afterProcess(i, radiusArray);
                } else if (mirrorLeftBoundary < keyLeftBoundary) {
                    radiusArray[i] = mirrorIndex - keyLeftBoundary;
                    afterProcess(i, radiusArray);
                } else {
                    radiusArray[i] = -1;
                }

            } else {
                setToBeDeterminedArray(toBeDeterminedIndexs, i, i + 1);
                radiusArray[i] = -1;
            }
        }
        System.out.println(maxCenter);
        System.out.println(maxRadius);
        System.out.println(s.substring(maxCenter - maxRadius, maxCenter + maxRadius + 1));
        return decode(s.substring(maxCenter - maxRadius, maxCenter + maxRadius + 1));
    }

    /**
     * 把半径待定的下标记录进需读取信息的下标所连的串
     *
     * @param toBeDeterminedIndexs 待定下标关系数组
     * @param from                 半径待定的下标
     * @param to                   需读取信息的下标
     */
    private void setToBeDeterminedArray(List<List<Integer>> toBeDeterminedIndexs, int from, int to) {
        if (toBeDeterminedIndexs.get(to) == null) {
            List<Integer> temp = new ArrayList<>();
            temp.add(from);
            toBeDeterminedIndexs.set(to, temp);
        } else {
            toBeDeterminedIndexs.get(to).add(from);
        }
    }

    private void preProcess(List<List<Integer>> toBeList, int length) {
        for (int i = 0; i < length + 1; i++) {
            toBeList.add(null);
        }
    }

    /**
     * 更新最大半径的下标
     *
     * @param newIndex    新下标
     * @param radiusArray 半径数组
     * @return 最大半径的中心下标
     */
    private void afterProcess(Integer newIndex, Integer[] radiusArray) {
        maxCenter = radiusArray[newIndex] > maxRadius ? newIndex : maxCenter;
        maxRadius = radiusArray[newIndex] > maxRadius ? radiusArray[newIndex] : maxRadius;
    }

    /**
     * 编码（加#）
     *
     * @param origin 原码
     * @return 编码后的码
     */
    private static String encode(String origin) {
        StringBuilder sb = new StringBuilder();
        if (origin.length() == 0) {
            return origin;
        }
        sb.append('#');
        for (int i = 0; i < origin.length(); i++) {
            sb.append(origin.charAt(i)).append('#');
        }
        return sb.toString();
    }

    /**
     * 解码（去#）
     *
     * @param target 目标
     * @return 原码
     */
    private static String decode(String target) {
        StringBuilder sb = new StringBuilder(target);
        if (target.length() == 0) {
            return target;
        }
        int m=sb.length();
        for (int i = 0; i < (m+1)/2; i ++){
            sb.deleteCharAt(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s1 = "aaaa";
        System.out.println(encode(s1));
        System.out.println(decode(encode(s1)));
    }
}
