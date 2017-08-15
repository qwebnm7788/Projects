
package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * 파일에 저장되는 정보는 항상 id, name, price, quantity 가 공백으로 구분되어 들어간다
 * Created by jaewon on 2017-08-14.
 */
public class Manager {
    private List<Item> stocks;
    private String id;
    private int totalItems = 0;

    public Manager(String id) {
        this.id = id;
        this.stocks = new LinkedList<>();
        this.stocks.add(null);              //0번 index에는 더미 노드를 하나 넣는다. (id를 1-index부터 시작하도록 하기 위해서)
        initialize();
    }

    public void initialize(){
        String path = System.getProperty("user.dir");
        path += "\\db\\" + id + "\\stocks.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String info;
            //id, name, price, quantity 가 공백으로 나누어져있음을 이용
            while((info = br.readLine()) != null){
                String[] itemInfo = info.split(" ");
                Item item = new Item(Integer.parseInt(itemInfo[0]),itemInfo[1],Double.parseDouble(itemInfo[2]),Integer.parseInt(itemInfo[3]));
                stocks.add(item);
                totalItems++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    //Item의 추가 (성공시 true 반환)
    public boolean addItem(){
        int quantity;
        double price;
        String name;

        Scanner in = new Scanner(System.in);
        System.out.println("추가할 품목 정보 입력");
        System.out.print("Name: ");
        name = in.nextLine();
        System.out.print("Price: ");
        price = in.nextDouble();
        System.out.print("Quantity: ");
        quantity = in.nextInt();

        Item addIt = new Item(++totalItems,name,price,quantity);

        if(addIt.getQuantity() < 0){
            System.out.println("Invalid quantity");
            return false;
        }
        /*
        //중복체크 -> 중복이 있다면 update로 자동으로 전환된다.
        for(Item it : stocks){
            if(it.getName().equals(addIt.getName())){
            }
        }
        */
        //중복이 아니라면 새롭게 추가
        stocks.add(addIt);

        //변경된 내용을 출력
        listItems();

        return true;
    }

    public boolean updateItem(){
        System.out.println("변경할 물건의 ID를 입력하세요");
        System.out.println("ID: ");
        Scanner in = new Scanner(System.in);
        int id = Integer.parseInt(in.nextLine());

        System.out.println("물건의 새로운 정보를 입력하세요");
        String name;
        Double price;
        int quantity;

        System.out.println("Name: ");
        name = in.nextLine();
        System.out.println("Price: ");
        price = in.nextDouble();
        System.out.println("Quantity: ");
        quantity = in.nextInt();

        Item updatedIt = new Item(id,name,price,quantity);
        stocks.remove(id);
        stocks.add(id,updatedIt);

        listItems();

        return true;
    }

    public void listItems(){
        double totalValue = 0;
        System.out.println("====================================================================");
        System.out.printf("%5s%10s%19s%12s\n","ID","상품명","가격","재고");
        for(Item item : stocks){
            if(item == null) continue;
            System.out.println("====================================================================");
            System.out.printf("   %-5d     %-15s%-5.2f          %-10d\n",item.getId(),item.getName(),item.getPrice(),item.getQuantity());
            totalValue += item.getPrice();
        }
        System.out.println("====================================================================");
        System.out.printf("TOTAL %d items (estimated %.2f$ cost) are stored for you\n",totalItems, totalValue);
        System.out.println("====================================================================");
    }


    public boolean save(){
        String path = System.getProperty("user.dir");
        path += "\\db\\" + id + "\\stocks.txt";
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            //id, name, price, quantity 가 공백으로 나누어져있음을 이용
            for(Item it : stocks){
                br.write(it.toString() + "\n");
            }
            br.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
