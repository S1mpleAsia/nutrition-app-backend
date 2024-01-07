package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.CommentDTO;
import com.example.nutritionapp.dto.PostDTO;
import com.example.nutritionapp.dto.ReportDTO;
import com.example.nutritionapp.http.request.PostReactionRequest;
import com.example.nutritionapp.http.request.ReportPostRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.http.response.PostDetailResponse;
import com.example.nutritionapp.http.response.PostResponse;
import com.example.nutritionapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public GeneralResponse<PostDTO> createPost(@RequestBody PostDTO request) {
        return postService.createPost(request);
    }

    @PutMapping("{postId}")
    public void updatePost(@PathVariable("postId") UUID postId, @RequestBody PostDTO request) {
        postService.updatePost(postId, request);
    }

    @DeleteMapping("{postId}")
    public void deletePost(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
    }

    @GetMapping
    public GeneralListResponse<PostResponse> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("{postId}")
    public GeneralResponse<PostDetailResponse> getPostDetail(@PathVariable("postId") UUID postId) {
        return postService.getPostDetail(postId);
    }

    @PutMapping("/comment/{commentId}")
    public void updateComment(@PathVariable("commentId") UUID commentId, @RequestBody CommentDTO request) {
        postService.updateComment(commentId, request);
    }

    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") UUID commentId) {
        postService.deleteComment(commentId);
    }

    @PostMapping("/comment")
    public void addComment(@RequestBody CommentDTO request) {
        postService.addComment(request);
    }

    @PutMapping("/reaction")
    public void changeReaction(@RequestBody PostReactionRequest request) {
        postService.changeReaction(request);
    }

    @PostMapping("/report")
    public void reportPost(@RequestBody ReportPostRequest request) {
        postService.reportPost(request);
    }

    @PutMapping("/report/{reportId}")
    public void approveReport(@PathVariable("reportId") UUID reportId) {
        postService.approveReport(reportId);
    }

    @GetMapping("/report")
    public GeneralListResponse<ReportDTO> getReportList() {
        return postService.getReportList();
    }
}
