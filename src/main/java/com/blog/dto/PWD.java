package com.blog.dto;

import lombok.Data;

@Data
public class PWD {
    private Integer id;
    private String oldPwd;
    private String newPwd;
}
