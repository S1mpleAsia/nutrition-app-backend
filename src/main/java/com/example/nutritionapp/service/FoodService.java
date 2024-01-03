package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Food;
import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.FoodDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.request.FoodApproveRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.FoodMapper;
import com.example.nutritionapp.repository.FoodRepository;
import com.example.nutritionapp.repository.UserRepository;
import com.example.nutritionapp.utils.type.FoodStatus;
import com.example.nutritionapp.utils.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final FoodMapper foodMapper;

    public GeneralListResponse<FoodDTO> getFoodList() {
        List<Food> foodList = foodRepository.findAll();

        return GeneralListResponse.success(foodMapper.toDtoList(foodList));
    }

    public GeneralResponse<FoodDTO> getFoodDetail(UUID foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new InstanceNotFoundException("Can not found any food"));

        return GeneralResponse.success(foodMapper.toDto(food));
    }

    public GeneralListResponse<FoodDTO> getFoodListBySearch(String query) {

        return getFoodList();
    }

    public GeneralResponse<FoodDTO> createFood(FoodDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Could not found any user"));

        Food food = foodMapper.toEntity(request);

        if(user.getRole().equals(Role.USER.name())) {
            food.setStatus(FoodStatus.UNPUBLISHED.name());
        }
        else if (user.getRole().equals(Role.ADMIN.name())) {
            food.setStatus(FoodStatus.PUBLISHED.name());
        }
        Food insertedFood = foodRepository.saveAndFlush(food);

        return GeneralResponse.success(foodMapper.toDto(insertedFood));
    }

    public GeneralResponse<FoodDTO> publishedFood(UUID foodId, FoodApproveRequest request) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        if(request.getStatus().equals(FoodStatus.PENDING.name())) {
            food.setStatus(FoodStatus.PENDING.name());
            foodRepository.save(food);
            return GeneralResponse.success(foodMapper.toDto(food));
        } else {
            throw new RuntimeException("Status is invalid");
        }

    }

    public GeneralResponse<FoodDTO> approveFood(UUID foodId, FoodApproveRequest request) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        if(request.getStatus().equals(FoodStatus.PUBLISHED.name())) {
            food.setStatus(FoodStatus.PUBLISHED.name());
            foodRepository.save(food);
        } else if(request.getStatus().equals(FoodStatus.UNPUBLISHED.name())) {
            food.setStatus(FoodStatus.UNPUBLISHED.name());
            foodRepository.save(food);
        } else {
            throw new RuntimeException("Status is invalid");
        }

        return GeneralResponse.success(foodMapper.toDto(food));
    }

    public void deleteFood(UUID foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        foodRepository.delete(food);
    }

    public GeneralListResponse<FoodDTO> getPendingListFood() {
        List<Food> foodList = foodRepository.findAllByStatus(FoodStatus.PENDING.name());

        return GeneralListResponse.success(foodMapper.toDtoList(foodList));
    }

    public GeneralResponse<FoodDTO> updatedFood(UUID foodId, FoodDTO foodDTO) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        foodRepository.save(food);

        return GeneralResponse.success(foodMapper.toDto(food));
    }
}
