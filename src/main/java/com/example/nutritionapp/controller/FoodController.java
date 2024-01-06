package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.FoodDTO;
import com.example.nutritionapp.http.request.FoodApproveRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food")
@CrossOrigin
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("{userId}")
    public GeneralListResponse<FoodDTO> getFoodList(@PathVariable String userId) {
        return foodService.getFoodList(userId);
    }

    @GetMapping("/detail/{foodId}")
    public GeneralResponse<FoodDTO> getFoodDetail(@PathVariable("foodId") UUID foodId) {
        return foodService.getFoodDetail(foodId);
    }

    @GetMapping("query")
    public GeneralListResponse<FoodDTO> queryFood(@RequestParam(name = "q") String query) {
        return foodService.getFoodListBySearch(query);
    }

    @PostMapping
    public GeneralResponse<FoodDTO> createFood(@RequestBody FoodDTO foodDTO) {
        return foodService.createFood(foodDTO);
    }

    @PutMapping("{foodId}")
    public GeneralResponse<FoodDTO> publishedFood(@PathVariable("foodId") UUID foodId, @RequestBody FoodApproveRequest request) {
        return foodService.publishedFood(foodId, request);
    }

    @PutMapping("approve/{foodId}")
    public GeneralResponse<FoodDTO> approveFood(@PathVariable("foodId") UUID foodId, @RequestBody FoodApproveRequest request) {
        return foodService.approveFood(foodId, request);
    }

    @DeleteMapping("{foodId}")
    public void deleteFood(@PathVariable("foodId") UUID foodId) {
        foodService.deleteFood(foodId);
    }

    @GetMapping("public")
    public GeneralListResponse<FoodDTO> getPendingListFood() {
        return foodService.getPendingListFood();
    }

    @PostMapping("{foodId}")
    public GeneralResponse<FoodDTO> updatedFood(@PathVariable("foodId") UUID foodId, @RequestBody FoodDTO foodDTO) {
        return foodService.updatedFood(foodId, foodDTO);
    }
}
