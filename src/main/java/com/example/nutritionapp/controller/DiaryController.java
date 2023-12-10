package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/{userId}")
    public List<DiaryDTO> getListDiary(@PathVariable("userId") UUID userId) {
        return diaryService.getListDiary(userId);
    }


    @GetMapping("/{userId}/detail")
    public DiaryDTO getDiaryDetail(@PathVariable("userId") UUID userId, @RequestParam("diaryId") UUID diaryId) {
        return diaryService.getDiaryDetail(userId, diaryId);
    }

    @PutMapping
    public DiaryDTO updateDiary(@RequestBody DiaryDTO diaryUpdatedRequest) {
//        TODO: Need document
        return null;
    }
}
