package com.kongpf8848.dmail.login.oauth.hotmail;

/**
 {
     "id": "70dfe6f35a324887",
     "name": "xxx",
     "first_name": "xx",
     "last_name": "x",
     "link": "https://profile.live.com/",
     "gender": null,
     "emails": {
     "preferred": "xxx@outlook.com",
     "account": "xxx@outlook.com",
     "personal": null,
     "business": null
     },
     "locale": "zh_CN",
     "updated_time": null
 }
 */
public class HotmailProfile {
    private String id;
    private String name;
    private String first_name;
    private String last_name;
    private String link;
    private Object gender;

    private Emails emails;
    private String locale;
    private Object updated_time;

    public Emails getEmails() {
        return emails;
    }


    public static class Emails {
        private String preferred;
        private String account;
        private Object personal;
        private Object business;

        public String getAccount() {
            return account;
        }
    }
}
