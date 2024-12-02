package org.example.chatclient.server;

import java.util.ArrayList;
import java.util.List;

public class LoginInfo {
    private final List<String> loginInfo = new ArrayList<>();

    public void addLoginInfo(String username, String password){
        loginInfo.add(username + "=" + password);
    }

    public void removeInfo(){
        loginInfo.clear();
    }

    public String checkLogin(){
        return loginInfo.getFirst();
    }

    public List<String> getLoginInfo(){
        return loginInfo;
    }
}
