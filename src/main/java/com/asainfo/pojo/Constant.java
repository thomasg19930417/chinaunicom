package com.asainfo.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant
{

    public Constant()
    {
    }

    private static InputStream in;
    private static Properties p;
    public static final String count;
    public static final String pythonPath;
    public static final String fensheng;
    public static final String addpartition;
    public static final String dataFilePath;
    public static final String split;
    public static final String getdata;
    public static final String STATIC_URL;
    static 
    {
        try
        {
            p = new Properties();
            in = Constant.class.getClassLoader().getResourceAsStream("constant.properties");
            p.load(in);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        count = p.getProperty("dataFilePath");
        pythonPath = p.getProperty("pythonPath");
        fensheng = p.getProperty("fensheng");
        addpartition = p.getProperty("addpartition");
        dataFilePath = p.getProperty("dataFilePath");
        split = p.getProperty("split");
        getdata = p.getProperty("getdata");
        STATIC_URL =p.getProperty("STATIC_URL");
    }
}
