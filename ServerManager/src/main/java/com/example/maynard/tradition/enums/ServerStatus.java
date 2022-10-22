package com.example.maynard.tradition.enums;

/**
 * @author zhangyu
 */

public enum ServerStatus {

    /**
     * 空闲的
     */
    FREE("free"),

    /**
     * 使用中
     */
    USED("used");


    private String desc;

    ServerStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
