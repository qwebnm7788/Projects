
package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

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
    public boolean addItem(Item item){
        if(item.getQuantity() < 0){
            System.out.println("Invalid quantity");
            return false;
        }
        //중복체크 -> 중복이 있다면 update로 자동으로 전환된다.
        for(Item it : stocks){
            if(it.getName().equals(item.getName())){
                //updateItem
                return true;
            }
        }

        //중복이 아니라면 새롭게 추가
        stocks.add(item);
        return true;
    }

    public boolean updateItem(Item item){
        Item prev = null;
        for(Item found : stocks){
            if(item.getName().equals(found.getName())){
                prev = found;
                break;
            }
        }

        if(prev == null){
            System.out.println(item.getName() +  "은 존재하지 않는 항목입니다.");
            return false;
        }

        return false;
    }

    public void listItems(){
        double totalValue = 0;
        System.out.println("====================================================================");
        System.out.printf("%5s%10s%19s%12s\n","ID","상품명","가격","재고");
        for(Item item : stocks){
            System.out.println("====================================================================");
            System.out.printf("   %-5d     %-15s%-5.2f          %-10d\n",item.getId(),item.getName(),item.getPrice(),item.getQuantity());
            totalValue += item.getPrice();
        }
        System.out.println("====================================================================");
        System.out.printf("TOTAL %d items (estimated %.2f$ cost) are stored for you\n",totalItems, totalValue);
        System.out.println("====================================================================");
    }
}
