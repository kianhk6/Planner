package com.example.Planner.Model;

public class Label {
    String dept;
    String catalogNum;
    String componentCode;
    long deptId;
    long courseId;
    long courseOfferingId;

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Label(String subject, String catalogNum, String componentCode) {
        this.dept = subject;
        this.catalogNum = catalogNum;
        this.componentCode = componentCode;
        this.deptId = 0; //when there are no id generated yet
    }

    public String getDept() {
        return dept;
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

    public void setDept(String dept) {
        this.dept = dept;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }
}
