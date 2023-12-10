package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Diary;
import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.mapper.DiaryMapper;
import com.example.nutritionapp.repository.DiaryRepository;
import com.example.nutritionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final DiaryMapper diaryMapper;

    public List<DiaryDTO> getListDiary(UUID userId) {
        List<Diary> diaryList = diaryRepository.findAllByUserId(userId);

        return diaryMapper.toDtoList(diaryList);
    }

    public DiaryDTO getDiaryDetail(UUID userId, UUID diaryId) {
        userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException("Can not found user"));

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new InstanceNotFoundException("Can not found diary"));

        return diaryMapper.toDto(diary);}
}
