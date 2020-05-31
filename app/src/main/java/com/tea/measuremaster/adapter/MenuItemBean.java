package com.tea.measuremaster.adapter;

public class MenuItemBean {
    private String title;
    private String brief;

    private Class toPage;

    public MenuItemBean(String title, String brief, Class toPage) {
        this.title = title;
        this.brief = brief;
        this.toPage = toPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Class getToPage() {
        return toPage;
    }

    public void setToPage(Class toPage) {
        this.toPage = toPage;
    }
}
