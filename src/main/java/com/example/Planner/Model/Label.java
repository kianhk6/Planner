package com.example.Planner.Model;

public class Label {
    String subject;
    String catalogNum;
    String componentCode;

    public Label(String subject, String catalogNum, String componentCode) {
        this.subject = subject;
        this.catalogNum = catalogNum;
        this.componentCode = componentCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }
}
