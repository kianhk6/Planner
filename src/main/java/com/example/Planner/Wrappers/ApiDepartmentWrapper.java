package com.example.Planner.Wrappers;

public class ApiDepartmentWrapper {
    public long deptId;
    public String name;

    public ApiDepartmentWrapper(String name, long deptId) {
        this.deptId = deptId;
        this.name = name;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
