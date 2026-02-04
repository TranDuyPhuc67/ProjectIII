package Fashionstore.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="blogs")
public class Blog {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="blogid") 
	Integer blogId; 
	@Column(name="title", length=200, nullable=false) 
	private String title;
	@Column(name="content", columnDefinition="NVARCHAR(MAX)") 
	private String content; 
	@Column(name="image", length=200) private String image; 
	@Column(name="createdate") private LocalDateTime createDate; 
	@ManyToOne 
	@JoinColumn(name="accountid", nullable=false) private Account account; 
	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL) 
	private List<Comment> comments;

    public Integer getBlogId() { return blogId; }
    public void setBlogId(Integer blogId) { this.blogId = blogId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }  // <-- thêm setter này

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
