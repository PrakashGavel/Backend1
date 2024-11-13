package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserProjectDetails {
    private Long id;
    private String proName;
    private String proDesc;
    private String proGbl;
    private boolean isActive;
}
