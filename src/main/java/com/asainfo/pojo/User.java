package com.asainfo.pojo;

public class User
{

    public User()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPremission()
    {
        return premission;
    }

    public void setPremission(String premission)
    {
        this.premission = premission;
    }

    public String toString()
    {
        return (new StringBuilder("User [id=")).append(id).append(", name=").append(name).append(", password=").append(password).append(", premission=").append(premission).append("]").toString();
    }

    private Integer id;
    private String name;
    private String password;
    private String premission;
    
    
}
