package Fashionstore.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="orderid", length = 36)
	private String orderId;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "orderdate")
    private LocalDateTime orderDate;   

    @Column(name="receivename", length = 100)
    private String receiveName;

    @Column(name="receiveaddress", length = 200)
    private String receiveAddress;

    @Column(name="receivephone", length = 50)
    private String receivePhone;

    @Column(name = "receivedate")
    private LocalDateTime receivedate;

    @Column(name="status")
    private int status;

    @Column(name="note", length = 1000)
    private String note;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "accountid", nullable = false)
    private Account account = new Account();

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> details = new HashSet<>();

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getReceivedate() { return receivedate; }
    public void setReceivedate(LocalDateTime receivedate) { this.receivedate = receivedate; }

    public String getReceiveName() { return receiveName; }
    public void setReceiveName(String receiveName) { this.receiveName = receiveName; }

    public String getReceiveAddress() { return receiveAddress; }
    public void setReceiveAddress(String receiveAddress) { this.receiveAddress = receiveAddress; }

    public String getReceivePhone() { return receivePhone; }
    public void setReceivePhone(String receivePhone) { this.receivePhone = receivePhone; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Set<OrderDetail> getDetails() { return details; }
    public void setDetails(Set<OrderDetail> details) { this.details = details; }
}
