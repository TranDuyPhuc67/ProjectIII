package Fashionstore.entities;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id @GeneratedValue(generator = "uuid") 
	@GenericGenerator(name = "uuid", strategy = "uuid2") 
	@Column(name = "accountid", length = 36, updatable = false, nullable = false) 
	private String accountId;
	@NotBlank
	@Size(max = 64)
	@Column(name = "username", length = 64, unique = true)
	private String username;

	@NotBlank
	@Column(name = "password", length = 256)
	private String password;

	@Email
	@Column(name = "email", length = 64, unique = true)
	private String email;

	@Column(name = "phone", length = 64)
	private String phone;

	@Column(name = "fullname",length = 100)
	private String fullname;

	@Column(name = "address", length = 256)
	private String address;

	@Column(name = "picture", length = 512)
	private String picture;

	@Column(name = "enabled")
	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "accountroles", joinColumns = @JoinColumn(name = "accountid",columnDefinition = "nvarchar(36)"), inverseJoinColumns = @JoinColumn(name = "roleid"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "account")
	private Set<Order> orders=new HashSet<Order>();
	
	public Account() {

	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Account(String accountId, String username, String password,
			String email, String phone, String fullname, String address, String picture, boolean enabled) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fullname = fullname;
		this.address = address;
		this.picture = picture;
		this.enabled = enabled;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}