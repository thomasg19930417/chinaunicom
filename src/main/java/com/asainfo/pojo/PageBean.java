package com.asainfo.pojo;

import java.util.List;

public class PageBean
{

    public PageBean()
    {
    }

    public int getBegin()
    {
        return begin;
    }

    public void setBegin(int begin)
    {
        this.begin = begin;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    public List getData()
    {
        return data;
    }

    public void setData(List task)
    {
        data = task;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String toString()
    {
        return (new StringBuilder("PageBean [currentPage=")).append(currentPage).append(", begin=").append(begin).append(", pageNum=").append(pageNum).append(", data=").append(data).append(", count=").append(count).append(", condition=").append(condition).append("]").toString();
    }

    private int currentPage;
    private int begin;
    private int pageNum;
    private List data;
    private int count;
    private String condition;
}
