package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.*;
import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.dto.DiaryDTO;
import com.example.nutritionapp.dto.FoodDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.request.DiaryUpdateRequest;
import com.example.nutritionapp.http.response.*;
import com.example.nutritionapp.mapper.ActivityMapper;
import com.example.nutritionapp.mapper.DiaryMapper;
import com.example.nutritionapp.mapper.FoodMapper;
import com.example.nutritionapp.repository.*;
import com.example.nutritionapp.utils.DateTransform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryFoodRepository diaryFoodRepository;
    private final DiaryActivityRepository diaryActivityRepository;
    private final ActualStatisticsRepository actualStatisticsRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ActivityRepository activityRepository;
    private final DiaryMapper diaryMapper;
    private final FoodMapper foodMapper;
    private final ActivityMapper activityMapper;

    public GeneralListResponse<DiaryDTO> getListDiary(UUID userId) {
        List<Diary> diaryList = diaryRepository.findAllByUserId(userId);

        return GeneralListResponse.success(diaryMapper.toDtoList(diaryList));
    }

    public GeneralResponse<DiaryUpdateResponse> getDiaryDetail(UUID userId, String dateString) {
        Date date = DateTransform.toDate(dateString);
        Optional<Diary> diary = diaryRepository.findFirstByUserIdAndDate(userId, date);
        DiaryUpdateResponse diaryUpdateResponse = new DiaryUpdateResponse();

        if (diary.isEmpty()) return GeneralResponse.error("Can not find diary", diaryUpdateResponse);

        else {
            Optional<ActualStatistics> statisticsOptional = actualStatisticsRepository.findFirstByDiaryId(diary.get().getId());

            if (statisticsOptional.isEmpty()) return GeneralResponse.error("No statistics", new DiaryUpdateResponse());
            diaryUpdateResponse.setStatistics(statisticsOptional.get());

            setFoodResponse(diaryUpdateResponse, diary);
            setActivityResponse(diaryUpdateResponse, diary);

            return GeneralResponse.success(diaryUpdateResponse);
        }
    }

    private void setFoodResponse(DiaryUpdateResponse diaryUpdateResponse, Optional<Diary> diary) {
        List<Food> foodList = foodRepository.findAll();
        List<FoodDTO> foodDTOList = foodMapper.toDtoList(foodList);
        Map<UUID, FoodDTO> foodMap = foodDTOList.stream().collect(Collectors.toMap(FoodDTO::getId, Function.identity()));
        List<DiaryFood> diaryFoodList = diaryFoodRepository.findAllByDiaryId(diary.get().getId());
        List<FoodResponse> foodResponse = new ArrayList<>();

        diaryFoodList.forEach(item -> {
            FoodDTO foodDTO = foodMap.get(item.getFoodId());

            FoodResponse response = FoodResponse.builder()
                    .id(foodDTO.getId())
                    .image(foodDTO.getImage())
                    .foodName(foodDTO.getFoodName())
                    .fat(foodDTO.getFat())
                    .protein(foodDTO.getProtein())
                    .carbs(foodDTO.getCarbs())
                    .calories(foodDTO.getCalories())
                    .status(foodDTO.getStatus())
                    .unitType(foodDTO.getUnitType())
                    .userId(foodDTO.getUserId())
                    .amount(item.getAmount())
                    .build();

            foodResponse.add(response);
        });

        diaryUpdateResponse.setFoodList(foodResponse);
    }

    private void setActivityResponse(DiaryUpdateResponse diaryUpdateResponse, Optional<Diary> diary) {
        List<Activity> activityList = activityRepository.findAll();
        List<ActivityDTO> activityDTOList = activityMapper.toDtoList(activityList);

        Map<UUID, ActivityDTO> activityMap = activityDTOList.stream().collect(Collectors.toMap(ActivityDTO::getId, Function.identity()));
        List<DiaryActivity> diaryActivityList = diaryActivityRepository.findAllByDiaryId(diary.get().getId());
        double totalConsumes = 0.0;
        List<ActivityResponse> activityResponse = new ArrayList<>();

        for (DiaryActivity diaryActivity : diaryActivityList) {
            ActivityDTO activity = activityMap.get(diaryActivity.getActivityId());

            double activityConsume = (diaryActivity.getMinutes() / 30) * activity.getCaloriesConsume();

            totalConsumes += activityConsume;
            ActivityResponse response = ActivityResponse.builder()
                    .id(activity.getId())
                    .name(activity.getName())
                    .caloriesConsume(activity.getCaloriesConsume())
                    .userId(activity.getUserId())
                    .status(activity.getStatus())
                    .minutes(diaryActivity.getMinutes())
                    .totalConsumes(activityConsume)
                    .build();

            activityResponse.add(response);

        }

        diaryUpdateResponse.setActivityList(activityResponse);
        diaryUpdateResponse.setTotalConsumes(totalConsumes);
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

        if (diaryUpdatedRequest.getActivityId() != null) {
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

            myDiaryFood.setAmount(diaryUpdatedRequest.getFoodAmount() + myDiaryFood.getAmount());
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

            myDiaryActivity.setMinutes(diaryUpdatedRequest.getActivityUnit() + myDiaryActivity.getMinutes());
            diaryActivityRepository.save(myDiaryActivity);
        }
    }

    private void updateActualStatistics(UUID diaryId, DiaryUpdateRequest diaryUpdateRequest) {
        Optional<ActualStatistics> actualStatistics = actualStatisticsRepository.findFirstByDiaryId(diaryId);
        List<Food> foodList = foodRepository.findAll();

        Map<UUID, Food> foodMap = foodList.stream().collect(Collectors.toMap(Food::getId, Function.identity()));
        List<DiaryFood> diaryFoods = diaryFoodRepository.findAllByDiaryId(diaryId);

        double realCarbs = 0.0;
        double realFat = 0.0;
        double realProtein = 0.0;
        double realTdee = 0.0;

        for (DiaryFood diaryFood : diaryFoods) {
            Food food = foodMap.get(diaryFood.getFoodId());
            Double scale = diaryFood.getAmount() / 100;
            realCarbs += scale * food.getCarbs();
            realFat += scale * food.getFat();
            realProtein += scale * food.getProtein();
            realTdee += scale * food.getCalories();
        }

        if (actualStatistics.isEmpty()) {
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

            Double water = 0.0;
            if (diaryUpdateRequest.getActivityId() != null || diaryUpdateRequest.getFoodId() != null) {
                water = statistics.getRealWater();
            } else if (diaryUpdateRequest.getWater() != null) {
                water = 1.0 * diaryUpdateRequest.getWater();
            }

            statistics.setRealWater(water);
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
