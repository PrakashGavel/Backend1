package com.etms.worldline.payload.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class nominationsRequest {
  private List<String> skills;
  private Long user_id;
}
