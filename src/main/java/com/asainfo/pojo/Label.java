package com.asainfo.pojo;


public class Label
{

    public Label()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getLabelname()
    {
        return labelname;
    }

    public void setLabelname(String labelname)
    {
        this.labelname = labelname;
    }

    private Integer id;
    private String labelname;
}
