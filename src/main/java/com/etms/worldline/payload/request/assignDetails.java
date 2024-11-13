package com.etms.worldline.payload.request;


import java.util.Set;

public class assignDetails {
    private String emp_group;

    private String pro_name;

    private Set<Long>  skills;

    public Set<Long> getSkills() {
        return skills;
    }

    public void setSkills(Set<Long> skills) {
        this.skills = skills;
    }

    public String getEmp_group() {
        return emp_group;
    }

    public void setEmp_group(String emp_group) {
        this.emp_group = emp_group;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

}
