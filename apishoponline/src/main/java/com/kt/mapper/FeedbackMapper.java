package com.kt.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kt.dtos.FeedbackDto;
import com.kt.entities.Feedback;
import com.kt.entities.User;
import com.kt.utils.Constant;

public class FeedbackMapper {
    private static FeedbackMapper instance;
    private FeedbackMapper(){};
    public static FeedbackMapper getInstance() {
        if(instance == null) {
            instance = new FeedbackMapper();
        }
        return instance;
    }

    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setContent(feedback.getContent());
        feedbackDto.setId(feedback.getId());
        feedbackDto.setImage(feedback.getImage());
        feedbackDto.setStar(feedback.getStar());
        feedbackDto.setTime(feedback.getTime());
        User user = feedback.getUser();
        if(user.getAvatar() == null) {
            feedbackDto.setAvatar(Constant.placeholderAvatar);
        }
        else {
            feedbackDto.setAvatar(user.getAvatar());
        }
        feedbackDto.setUsername(user.getUsername());
        return feedbackDto;
    }

    public List<FeedbackDto> toListDto(List<Feedback> feedbacks) {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        for(Feedback f : feedbacks) {
            feedbackDtos.add(toDto(f));
        }
        return feedbackDtos;
    }
}
