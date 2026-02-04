package Fashionstore.entities;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name="products")
@Entity
public class Product {
	@Id
	@Column(name="productid",length = 10)
	private String productId;
	@Column(name="productname",length = 200)
	private String productName;
	@Column(name="priceold")
	private int priceOld;
	private int price;
	@Column(name="colors",length = 200)
	private String colors;
	@Column(name="sizes",length = 200)
	private String sizes;
	@Column(name="pictures",length = 500)
	private String pictures;
	@Column(name="brief",length = 1000)
	private String brief;
	@Column(name="description",columnDefinition = "nvarchar(max)")
	private String description;
	private boolean status = true;
		
	@Transient 
	public String getMainPicture() { 
		if (pictures == null || pictures.isEmpty()) return ""; 
		return pictures.split(",")[0].trim();
	}
	
	@OneToMany(mappedBy = "product")
	private Set<OrderDetail> details=new HashSet<OrderDetail>();
	
	public Set<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
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

	public int getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(int priceOld) {
		this.priceOld = priceOld;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String[] getListPictures()
	{
		if (this.pictures!=null)
				return pictures.split(",");
		else
			return new String[0];
	}
	
}