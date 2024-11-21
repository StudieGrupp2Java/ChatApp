package org.example.filemanager;

import org.example.logininfo.LoginInfo;

import java.io.*;

public class FileManager {
    private static LoginInfo login = null;
    private static final File file = new File("loginInfo.txt");

    public FileManager(LoginInfo login){
        FileManager.login = login;
    }
    public void load(){
        BufferedReader in = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            in = new BufferedReader(new FileReader(file));
            String loginInfo;
            String[] info = {};
            while((loginInfo = in.readLine()) != null){
                info = loginInfo.split("=");
            }
            if (info.length == 0){
                return;
            }
            login.addLoginInfo(info[0], info[1]);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void save(){
        BufferedWriter out = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            out = new BufferedWriter(new PrintWriter(file));
            out.write(login.getLoginInfo().getFirst());

        } catch (Exception e){
            System.out.println("Something went wrong while saving.");
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}
