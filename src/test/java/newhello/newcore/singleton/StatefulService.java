package newhello.newcore.singleton;

public class StatefulService {
    private int price; //상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.print("name = " + name);
        System.out.println(", price = " + price);
        this.price = price; //여기에 문제거리를 심었음
    }

    public int getPrice() {
        return price;
    }
}
