package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Diary;
import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.DiaryMapper;
import com.example.nutritionapp.repository.DiaryRepository;
import com.example.nutritionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final DiaryMapper diaryMapper;

    public GeneralListResponse<DiaryDTO> getListDiary(UUID userId) {
        List<Diary> diaryList = diaryRepository.findAllByUserId(userId);

        return GeneralListResponse.success(diaryMapper.toDtoList(diaryList));
    }

    public GeneralResponse<DiaryDTO> getDiaryDetail(UUID userId, UUID diaryId) {
        userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException("Can not found user"));

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new InstanceNotFoundException("Can not found diary"));

        return GeneralResponse.success(diaryMapper.toDto(diary));
    }

    public GeneralResponse<DiaryDTO> updateDiary(UUID diaryId, DiaryDTO diaryUpdatedRequest) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RuntimeException("Could not found any diary"));

        diary.setNote(diaryUpdatedRequest.getNote());
        diaryRepository.save(diary);

        return GeneralResponse.success(diaryMapper.toDto(diary));
    }

    public GeneralResponse<DiaryDTO> createDiary(UUID userId, DiaryDTO diaryDTO) {
        userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException("Could not found any user"));

        Diary diary = Diary.builder()
                .date(new Date())
                .note(diaryDTO.getNote())
                .userId(userId)
                .build();

        Diary savedDiary = diaryRepository.save(diary);

        return GeneralResponse.success(diaryMapper.toDto(savedDiary));
    }
}
