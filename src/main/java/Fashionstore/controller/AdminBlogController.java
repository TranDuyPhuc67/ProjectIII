package Fashionstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Fashionstore.entities.Blog;
import Fashionstore.repositories.BlogRepository;

@Controller
@RequestMapping("/admin/blogs")
public class AdminBlogController {

    @Autowired
    private BlogRepository blogRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("blogs", blogRepo.findAll());
        return "admin/blog";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("blog", blogRepo.findById(id).orElseThrow());
        return "admin/blog_detail";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Integer id) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        blog.setApproved(true);
        blogRepo.save(blog);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/toggle/{id}")
    public String toggle(@PathVariable Integer id) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        blog.setStatus(!Boolean.TRUE.equals(blog.getStatus()));
        blogRepo.save(blog);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        blogRepo.deleteById(id);
        return "redirect:/admin/blogs";
    }
}
