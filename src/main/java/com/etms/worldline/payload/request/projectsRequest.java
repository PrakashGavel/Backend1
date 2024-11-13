package com.etms.worldline.payload.request;

import java.util.List;
import java.util.Set;

public class projectsRequest {


    private String proName;
    private String gbl;


    private String proDesc;
    private Long man_id;
    private List<String> prodocPath;
    private List<projectskilldetails> projectskilldetails;

    public String getGbl() {
        return gbl;
    }

    public void setGbl(String gbl) {
        this.gbl = gbl;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public List<com.etms.worldline.payload.request.projectskilldetails> getProjectskilldetails() {
        return projectskilldetails;
    }

    public void setProjectskilldetails(List<com.etms.worldline.payload.request.projectskilldetails> projectskilldetails) {
        this.projectskilldetails = projectskilldetails;
    }
    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }
    public Long getMan_id() {
        return man_id;
    }

    public void setMan_id(Long man_id) {
        this.man_id = man_id;
    }

    public List<String> getProdocPath() {
        return prodocPath;
    }

    public void setProdocPath(List<String> prodocPath) {
        this.prodocPath = prodocPath;
    }
}
