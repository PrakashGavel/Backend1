package com.etms.worldline.payload.response;

import com.etms.worldline.payload.request.projectskilldetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Projectdetails {
    private Long pro_id;
    private String pro_name;
    private String proGbl;
    private String proDesc;
    private List<projectskilldetails> projectskilldetails;
    private List<String> docUrls;

    public Projectdetails(Long pro_id,String pro_name,String proDesc){
        this.pro_id=pro_id;
        this.pro_name=pro_name;
        this.proDesc=proDesc;
    }
    public Projectdetails(Long pro_id,String pro_name,String proGbl,String proDesc){
        this.pro_id=pro_id;
        this.pro_name=pro_name;
        this.proGbl=proGbl;
        this.proDesc=proDesc;
    }
}
