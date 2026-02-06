package Fashionstore.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import Fashionstore.entities.Account;
import Fashionstore.entities.Blog;
import Fashionstore.entities.Comment;
import Fashionstore.repositories.AccountRepository;
import Fashionstore.repositories.BlogRepository;
import Fashionstore.repositories.CommentRepository;

@Controller
public class BlogController {

	@Autowired 
	private BlogRepository blogRepo; 
	@Autowired 
	private CommentRepository commentRepo; 
	@Autowired 
	private AccountRepository accountRepo; 
	
	@GetMapping("/blogs")
	public String listBlogs(@RequestParam(value = "q", required = false) String keyword,
	                        @RequestParam(value = "page", defaultValue = "0") int page,
	                        Model model) {

	    Page<Blog> blogs;

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        blogs = blogRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
	                    keyword, keyword, PageRequest.of(page, 10, Sort.by("createDate").descending()));
	    } else {
	    	

	    	blogs = blogRepo.findByStatusTrue(
	                PageRequest.of(page, 10, Sort.by("createDate").descending())
	            );
	    }

	    model.addAttribute("blogs", blogs.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", blogs.getTotalPages());

	    model.addAttribute("recentBlogs",
	            blogRepo.findByStatusTrueOrderByCreateDateDesc()
	                    .stream()
	                    .limit(5)
	                    .toList());

	    model.addAttribute("recentComments", commentRepo.findTop5ByOrderByCreateDateDesc());

	    return "home/blog";
	}

	@PostMapping("/blogs/add") 
	public String addBlog(@RequestParam String title,
						  @RequestParam("content") String content, 
						  @RequestParam("imageFile") MultipartFile imageFile, Principal principal) throws IOException { 
		Blog blog = new Blog(); 
		blog.setTitle(title); 
		blog.setContent(content); 
		blog.setCreateDate(LocalDateTime.now()); 
		 blog.setStatus(true);
		String username = principal.getName(); 
		Account acc = accountRepo.findByUsername(username) .orElseThrow(() -> new RuntimeException("Account not found")); 
		blog.setAccount(acc);
		if (!imageFile.isEmpty()) { 
			String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename(); 
			String uploadDir = "target/classes/static/images/"; // ghi trực tiếp vào thư mục build 
			Files.createDirectories(Paths.get(uploadDir)); 
			Files.write(Paths.get(uploadDir + fileName),
			imageFile.getBytes()); 
			blog.setImage("/images/" + fileName); }
		blogRepo.save(blog); 
		return "redirect:/blogs"; 
		
	}
    @GetMapping("/blogs/{id}")
    public String viewBlog(@PathVariable Integer id, Model model) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        List<Comment> comments = commentRepo.findByBlog_BlogIdOrderByCreateDateDesc(id);

        model.addAttribute("blog", blog);
        model.addAttribute("comments", comments);

        return "home/blog_detail";
        
    }

    @PostMapping("/blogs/{id}/comment")
    public String addComment(@PathVariable Integer id, @RequestParam String content) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        Comment c = new Comment();
        c.setBlog(blog);
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        commentRepo.save(c);
        return "redirect:/blogs/" + id;
    }

}