package org.example;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<String, String> customHashMap = new CustomHashMap<>();
        System.out.println(customHashMap.put("1", "1"));
        System.out.println(customHashMap.put("1", null));
        System.out.println(customHashMap.put(null, "8"));
        System.out.println(customHashMap.put(null, "9"));
        System.out.println(customHashMap.put("2", "2"));
        System.out.println(customHashMap.put("3", "3"));
        System.out.println(customHashMap.get("2"));
        System.out.println(customHashMap.remove("2"));
        System.out.println(customHashMap.put(null, null));
        System.out.println(customHashMap.get(null));
        System.out.println(customHashMap.remove(null));
        System.out.println(customHashMap.get("5"));

    }
}