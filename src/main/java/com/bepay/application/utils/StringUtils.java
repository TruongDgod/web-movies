package com.bepay.application.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static void main(String[] args) {
        String a = subNameFile("sang.san.fd.fs.f.sdf.sd.f.sdf.sd.f.s.fs.df.sd.fsd.f.sd.f.sf.sd.fsd.jpg", 25);
        System.out.println(a);
    }

    public static String subNameFile(String name, int size) {
        String result = "";
        int index = name.lastIndexOf(".");
        if (index >= 30) {
            result = name.substring(0, size) + name.substring(index, name.length());
        } else result = name;
        return result ;
    }

    public static String subNameFile(String name) {
        return subNameFile(name, 25);
    }

    public static String convertUT8(String str){
        if(str == null) return null;
        byte[] sourceBytes = str.getBytes( Charset.forName("Windows-1252"));
        return new String(sourceBytes , StandardCharsets.UTF_8);
    }
    public static String convertWindows1252(String str){
        if(str == null) return null;
        byte[] sourceBytes = str.getBytes(StandardCharsets.UTF_8);
        return new String(sourceBytes , Charset.forName("Windows-1252"));
    }
}
