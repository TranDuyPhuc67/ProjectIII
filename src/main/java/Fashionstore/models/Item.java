package Fashionstore.models;
import Fashionstore.entities.Product;

public class Item {
	private String productId;
	private String productName;
	private String picture;
	private String color;
	private String size;
	private float price;
	private int quantity;
	private float total;
	public Item() {
	}
	
	public Item(Product p) {
		this.productId = p.getProductId();
		this.productName = p.getProductName();
		this.picture = p.getPictures().split(",")[0];
		this.color = p.getColors().split(",")[0];
		this.size = p.getSizes().split(",")[0];
		this.price = p.getPrice();
		this.quantity = 1;
		this.total = p.getPrice()*1;
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}