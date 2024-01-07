package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.http.request.DiaryUpdateRequest;
import com.example.nutritionapp.http.response.DiaryUpdateResponse;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
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
    public GeneralListResponse<DiaryDTO> getListDiary(@PathVariable("userId") UUID userId) {
        return diaryService.getListDiary(userId);
    }


    @GetMapping("/{userId}/detail")
    public GeneralResponse<DiaryUpdateResponse> getDiaryDetail(@PathVariable("userId") UUID userId, @RequestParam("date") String dateString) {
        return diaryService.getDiaryDetail(userId, dateString);
    }

    @GetMapping("detail")
    public GeneralResponse<DiaryUpdateResponse> getDiaryDetailById(@RequestParam("diaryId") UUID diaryId) {
        return diaryService.getDiaryDetailById(diaryId);
    }

    @PutMapping("/{userId}")
    public void updateDiary(@PathVariable("userId") UUID userId, @RequestBody DiaryUpdateRequest diaryUpdatedRequest) {
        diaryService.updateDiary(userId, diaryUpdatedRequest);
    }

//    @PostMapping("/{userId}")
//    public GeneralResponse<DiaryDTO> createDiary(@PathVariable("userId") UUID userId, @RequestBody DiaryDTO diaryDTO) {
//        return diaryService.createDiary(userId, diaryDTO);
//    }
}
