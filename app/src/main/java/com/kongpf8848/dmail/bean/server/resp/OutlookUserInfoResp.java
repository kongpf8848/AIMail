package com.kongpf8848.dmail.bean.server.resp;

import com.kongpf8848.dmail.bean.server.BaseEntity;

public class OutlookUserInfoResp extends BaseEntity {

    public String id;
    public String name;
    public String first_name;
    public String last_name;
    public String link;
    public Object gender;
    public Emails emails;
    public String locale;
    public Object updated_time;

    public class Emails {
        public String preferred;
        public String account;
        public Object personal;
        public Object business;
    }
}
