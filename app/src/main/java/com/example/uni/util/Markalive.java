package com.example.uni.util;

import org.litepal.crud.LitePalSupport;

public class Markalive  extends LitePalSupport {
    private String stu_number;

    public void setStu_number(String stu_number){this.stu_number = stu_number;}
    public String getStu_number() {return stu_number;}
}
