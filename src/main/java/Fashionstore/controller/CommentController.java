package Fashionstore.controller;

import Fashionstore.entities.Comment;
import Fashionstore.entities.Blog;
import Fashionstore.entities.Account;
import Fashionstore.repositories.CommentRepository;
import Fashionstore.repositories.BlogRepository;
import Fashionstore.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private AccountRepository accountRepo;

    @PostMapping("/comments/add/{blogId}")
    public String addComment(@PathVariable("blogId") Integer blogId,
                             @ModelAttribute Comment comment,
                             Authentication authentication) {

        comment.setCreateDate(LocalDateTime.now());

        String username = authentication.getName();
        Account acc = accountRepo.findByUsername(username)
                                 .orElseThrow(() -> new RuntimeException("Account not found"));
        comment.setAccount(acc);

        Blog blog = blogRepo.findById(blogId)
                            .orElseThrow(() -> new RuntimeException("Blog not found"));
        comment.setBlog(blog);

        commentRepo.save(comment);

        return "redirect:/blogs/" + blogId;
    }
}
