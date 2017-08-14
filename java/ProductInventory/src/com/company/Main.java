package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    //현재 java파일의 위치를 저장
    public static final String currentDir = System.getProperty("user.dir");
    //Manager들의 정보를 저장 (id는 유일하므로 Map에 저장)
    public static Manager manager;

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("                   Inventory Service                   ");
        System.out.println("=======================================================");

        while(true){
            Scanner in = new Scanner(System.in);
            String id = "", password = "";
            String res;
            boolean start = false;
            try {
                //로그인
                while (true) {
                    System.out.println("사용자 정보를 입력해주세요");
                    System.out.print("ID: ");
                    id = in.nextLine();
                    System.out.print("PASSWORD: ");
                    password = in.nextLine();
                    if((start = login(id,password))){
                        System.out.println("LOGIN SUCCEED!");
                        break;
                    }else{
                        System.out.println("LOGIN FAILED");
                        System.out.println("DO YOU WANT TO QUIT? (Y to yes)");
                        res = in.nextLine();
                        if(res.equals("Y")|| res.equals("y")){
                            System.out.println("GOOD BYE~~~");
                            break;
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            if(!start){
                //프로그램 종료
                System.out.println("EXIT");
                System.exit(0);
            }

            //로그인된 Manager의 stock을 읽어온다.
            int select;
            boolean logout = false;
            while(!logout){
                System.out.println("====================================================================");
                System.out.println("================================메뉴================================");
                System.out.println("1.  재고 목록 확인");
                System.out.println("2.  재고 물건 추가");
                System.out.println("3.  재고 내용 변경");
                System.out.println("4.  로그아웃");
                System.out.println(">> ");
                select = Integer.parseInt(in.nextLine());

                switch(select){
                    case 1:
                        manager.listItems();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        logout = true;
                        break;
                    default:
                        System.out.println("잘못된 입력입니다.");
                }
            }
            System.out.println("Do you want to terminate program? (Y to yes)");
            res = in.nextLine();
            if(res.equals("Y") || res.equals("y")){
                System.out.println("GOOD BYE");
                System.exit(0);
            }
        }
    }

    //정상적인 로그인 확인
    public static boolean login(String id, String password) {
        //id가 존재한다면 db폴더 내부에 id이름과 동일한 폴더가존재한다.
        String path = currentDir + "\\" + "db";
        File file = new File(path);

        boolean idCheck = false;

        //id 존재 여부 확인
        for(File dir : file.listFiles()){
            if(dir.getName().equals(id)){
                idCheck = true;
            }
        }

        if(!idCheck) return false;

        System.out.println("ID CHECK PASS");
        //id폴더 내부에는 password.txt파일이 존재하여 비밀번호를 담고있다.
        path += "\\" + id + "\\password.txt";
        String origin = "";             //원본 비밀번호.
        try {
            FileInputStream fis = new FileInputStream(path);
            int data;
            while((data = fis.read()) != -1){
                origin += (char)data;
            }
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        if(origin.equals(password)){
            //이 id의 stock.txt를 읽어서 초기화 시킨다.
            //생성자 내부에서 초기화 함수 호출
            System.out.println("init manager");
            manager = new Manager(id);
            return true;
        }else{
            return false;
        }
    }
}
