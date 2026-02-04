package Fashionstore.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class Comment {
	
		@Id 
		@GeneratedValue(strategy = GenerationType.IDENTITY) 
		@Column(name="commentid") 
		private Integer commentId; 
		@Column(name="content", length=500, nullable=false) 
		private String content; 
		@Column(name="createdate") 
		private LocalDateTime createDate; 
		@ManyToOne 
		@JoinColumn(name="blogid", referencedColumnName="blogid", nullable=false) 
		private Blog blog; 
		@ManyToOne @JoinColumn(name="accountid", nullable=false) 
		private Account account;
	
	

    // Getters & Setters
    public Integer getCommentId() { return commentId; }
    public void setCommentId(Integer commentId) { this.commentId = commentId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public Blog getBlog() { return blog; }
    public void setBlog(Blog blog) { this.blog = blog; }   // <-- thêm setter này

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}
