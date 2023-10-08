package mazegame;

import mazegame.entity.Item;

public class Shop extends Item {
    private int price;
    private int stock;
    private String description;

    public Shop(String name, String type, int price, int stock, String description) {
        super(name, type);
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
