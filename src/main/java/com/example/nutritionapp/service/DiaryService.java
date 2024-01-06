package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.*;
import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.request.DiaryUpdateRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.DiaryMapper;
import com.example.nutritionapp.repository.*;
import com.example.nutritionapp.utils.DateTransform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryFoodRepository diaryFoodRepository;
    private final DiaryActivityRepository diaryActivityRepository;
    private final ActualStatisticsRepository actualStatisticsRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ActivityRepository activityRepository;
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

    public void updateDiary(UUID userId, DiaryUpdateRequest diaryUpdatedRequest) {
        String dateString = diaryUpdatedRequest.getDate();

        if (dateString == null) throw new RuntimeException("No input date");

        Date date = DateTransform.toDate(dateString);
        Optional<Diary> diary = diaryRepository.findFirstByUserIdAndDate(userId, date);
        UUID diaryId = createDiaryIfNull(userId, date, diary);

        if (diaryUpdatedRequest.getFoodId() != null) {
            updateDiaryFood(diaryId, diaryUpdatedRequest);
        }

        if(diaryUpdatedRequest.getActivityId() != null) {
            updateDiaryActivity(diaryId, diaryUpdatedRequest);
        }

        updateActualStatistics(diaryId, diaryUpdatedRequest);

    }

    private UUID createDiaryIfNull(UUID userId, Date date, Optional<Diary> diary) {
        if (diary.isEmpty()) {
            Diary insertedDiary = Diary.builder()
                    .date(date)
                    .userId(userId)
                    .build();

            Diary persistedDiary = diaryRepository.save(insertedDiary);
            return persistedDiary.getId();
        } else {
            return diary.get().getId();
        }
    }

    private void updateDiaryFood(UUID diaryId, DiaryUpdateRequest diaryUpdatedRequest) {
        Optional<DiaryFood> diaryFood = diaryFoodRepository.findFirstByDiaryIdAndFoodId(
                diaryId,
                diaryUpdatedRequest.getFoodId());

        if (diaryFood.isEmpty()) {
            DiaryFood insertedDiaryFood = DiaryFood.builder()
                    .diaryId(diaryId)
                    .amount(diaryUpdatedRequest.getFoodAmount())
                    .foodId(diaryUpdatedRequest.getFoodId())
                    .build();
            diaryFoodRepository.save(insertedDiaryFood);
        } else {
            DiaryFood myDiaryFood = diaryFood.get();

            myDiaryFood.setAmount(diaryUpdatedRequest.getFoodAmount());
            diaryFoodRepository.save(myDiaryFood);
        }
    }

    private void updateDiaryActivity(UUID diaryId, DiaryUpdateRequest diaryUpdatedRequest) {
        Optional<DiaryActivity> diaryActivity = diaryActivityRepository.findFirstByDiaryIdAndActivityId(
                diaryId,
                diaryUpdatedRequest.getActivityId());

        if (diaryActivity.isEmpty()) {
            DiaryActivity insertedDiaryActivity = DiaryActivity.builder()
                    .diaryId(diaryId)
                    .minutes(diaryUpdatedRequest.getActivityUnit())
                    .activityId(diaryUpdatedRequest.getActivityId())
                    .build();

            diaryActivityRepository.save(insertedDiaryActivity);
        } else {
            DiaryActivity myDiaryActivity = diaryActivity.get();

            myDiaryActivity.setMinutes(diaryUpdatedRequest.getActivityUnit());
            diaryActivityRepository.save(myDiaryActivity);
        }
    }

    private void updateActualStatistics(UUID diaryId, DiaryUpdateRequest diaryUpdateRequest) {
        Optional<ActualStatistics> actualStatistics = actualStatisticsRepository.findFirstByDiaryId(diaryId);
        List<Food> foodList = foodRepository.findAll();
        List<Activity> activityList = activityRepository.findAll();

        Map<UUID, Food> foodMap = foodList.stream().collect(Collectors.toMap(Food::getId, Function.identity()));
        Map<UUID, Activity> activityMap = activityList.stream().collect(Collectors.toMap(Activity::getId, Function.identity()));

        List<DiaryActivity> diaryActivities = diaryActivityRepository.findAllByDiaryId(diaryId);
        List<DiaryFood> diaryFoods = diaryFoodRepository.findAllByDiaryId(diaryId);

        double realCarbs = 0.0;
        double realFat = 0.0;
        double realProtein = 0.0;
        double realTdee = 0.0;

        for(DiaryFood diaryFood : diaryFoods) {
            Food food = foodMap.get(diaryFood.getFoodId());
            Double scale = diaryFood.getAmount() / 100;
            realCarbs += scale * food.getCarbs();
            realFat += scale * food.getFat();
            realProtein += scale * food.getProtein();
            realTdee += scale * food.getCalories();
        }

        if(actualStatistics.isEmpty()) {
            ActualStatistics statistics = ActualStatistics.builder()
                    .diaryId(diaryId)
                    .realWater(diaryUpdateRequest.getWater() == null ? 0 : 1.0 * diaryUpdateRequest.getWater())
                    .realCarbs(realCarbs)
                    .realFat(realFat)
                    .realProtein(realProtein)
                    .realTdee(realTdee)
                    .build();

            actualStatisticsRepository.save(statistics);
        } else {
            ActualStatistics statistics = actualStatistics.get();

            statistics.setRealWater(diaryUpdateRequest.getWater() == null ? 0 : 1.0 * diaryUpdateRequest.getWater());
            statistics.setRealCarbs(realCarbs);
            statistics.setRealFat(realFat);
            statistics.setRealProtein(realProtein);
            statistics.setRealTdee(realTdee);

            actualStatisticsRepository.save(statistics);
        }
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
