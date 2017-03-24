package com.asainfo.pojo;


public class LabelDatacode
{

    public LabelDatacode()
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

    public String getDatacode()
    {
        return datacode;
    }

    public void setDatacode(String datacode)
    {
        this.datacode = datacode;
    }

    public Integer getLabelId()
    {
        return labelId;
    }

    public void setLabelId(Integer labelId)
    {
        this.labelId = labelId;
    }

    public String toString()
    {
        return (new StringBuilder("LabelDatacode [id=")).append(id).append(", datacode=").append(datacode).append(", labelId=").append(labelId).append("]").toString();
    }

    private Integer id;
    private String datacode;
    private Integer labelId;
}
