package Fashionstore.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accountid", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

    private int quantity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() {
        return product.getPrice();
    }

    public int getTotal() {
        return getPrice() * quantity;
    }
}
