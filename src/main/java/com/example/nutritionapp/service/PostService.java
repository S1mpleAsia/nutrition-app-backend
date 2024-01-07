package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.*;
import com.example.nutritionapp.dto.CommentDTO;
import com.example.nutritionapp.dto.PostDTO;
import com.example.nutritionapp.dto.ReportDTO;
import com.example.nutritionapp.http.request.PostReactionRequest;
import com.example.nutritionapp.http.request.ReportPostRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.http.response.PostDetailResponse;
import com.example.nutritionapp.http.response.PostResponse;
import com.example.nutritionapp.mapper.CommentMapper;
import com.example.nutritionapp.mapper.PostInteractionMapper;
import com.example.nutritionapp.mapper.PostMapper;
import com.example.nutritionapp.mapper.ReportMapper;
import com.example.nutritionapp.repository.*;
import com.example.nutritionapp.utils.type.ReportStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostInteractionRepository postInteractionRepository;
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final PostMapper postMapper;
    private final PostInteractionMapper postInteractionMapper;
    private final CommentMapper commentMapper;
    private final ReportMapper reportMapper;

    public GeneralResponse<PostDTO> createPost(PostDTO request) {
        Optional<Diary> diaryOptional = diaryRepository.findById(request.getDiaryId());
        if(diaryOptional.isEmpty()) return GeneralResponse.error("No diary", null);

        Post insertedPost = postRepository.save(postMapper.toEntity(request));

        return GeneralResponse.success(postMapper.toDto(insertedPost));
    }

    public void updatePost(UUID postId, PostDTO request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No post"));

        if(request.getContent() != null) post.setContent(request.getContent());
        if(request.getImage() != null) post.setImage(request.getImage());

        postRepository.save(post);
    }

    public GeneralListResponse<PostResponse> getPostList() {
        List<User> userList = userRepository.findAll();
        List<Diary> diaryList = diaryRepository.findAll();
        Map<UUID, Diary> diaryMap = diaryList.stream().collect(Collectors.toMap(Diary::getId, Function.identity()));
        Map<UUID, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        List<Post> postList = postRepository.findAll();
        List<PostDTO> dtoList = postMapper.toDtoList(postList);

        List<PostResponse> responses = dtoList.stream().map(item -> {
            UUID userId = diaryMap.get(item.getDiaryId()).getUserId();
            String username = userMap.get(userId).getUsername();

            return PostResponse.builder()
                    .id(item.getId())
                    .content(item.getContent())
                    .image(item.getImage())
                    .diaryId(item.getDiaryId())
                    .userId(userId)
                    .username(username)
                    .build();
        }).toList();

        return GeneralListResponse.success(responses);
    }

    public GeneralResponse<PostDetailResponse> getPostDetail(UUID postId) {
        List<User> userList = userRepository.findAll();
        List<Diary> diaryList = diaryRepository.findAll();
        Map<UUID, Diary> diaryMap = diaryList.stream().collect(Collectors.toMap(Diary::getId, Function.identity()));
        Map<UUID, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No post"));
        List<PostInteraction> postInteraction = postInteractionRepository.findAllByPostId(postId);
        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        UUID userId = diaryMap.get(post.getDiaryId()).getUserId();
        String username = userMap.get(userId).getUsername();

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .image(post.getImage())
                .diaryId(post.getDiaryId())
                .userId(userId)
                .username(username)
                .build();

        PostDetailResponse response = PostDetailResponse.builder()
                .post(postResponse)
                .interaction(postInteractionMapper.toDtoList(postInteraction))
                .comment(commentMapper.toDtoList(commentList))
                .build();

        return GeneralResponse.success(response);
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No post"));

        postInteractionRepository.deleteAllByPostId(postId);
        commentRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    public void deleteComment(UUID commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No comment"));

        commentRepository.deleteById(commentId);
    }

    public void addComment(CommentDTO request) {
        postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("No Post"));

        commentRepository.save(commentMapper.toEntity(request));
    }

    public void changeReaction(PostReactionRequest request) {
        userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("No user found"));
        postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("No post found"));

        Optional<PostInteraction> postInteraction = postInteractionRepository.findFirstByFromUserIdAndPostId(
                request.getUserId(), request.getPostId());

        if(postInteraction.isEmpty()) {
            PostInteraction interaction = PostInteraction.builder()
                    .fromUserId(request.getUserId())
                    .postId(request.getPostId())
                    .isLiked(true)
                    .build();

            postInteractionRepository.save(interaction);
        } else {
            postInteractionRepository.deleteById(postInteraction.get().getId());
        }
    }

    public void updateComment(UUID commentId, CommentDTO request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No Comment"));

        if(request.getContent() != null) comment.setContent(request.getContent());

        commentRepository.save(comment);
    }

    public void reportPost(ReportPostRequest request) {
        userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("No user found"));
        postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("No post found"));

        Report report = Report.builder()
                .postId(request.getPostId())
                .userReportId(request.getUserId())
                .reason(request.getReason())
                .status(ReportStatus.PENDING.name())
                .build();

        reportRepository.save(report);
    }

    public void approveReport(UUID reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("No report found"));

        report.setStatus(ReportStatus.APPROVE.name());

        reportRepository.save(report);
    }

    public GeneralListResponse<ReportDTO> getReportList() {
        List<Report> reportList = reportRepository.findAll();

        return GeneralListResponse.success(reportMapper.toDtoList(reportList));
    }
}
