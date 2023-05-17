package com.kt.controllers.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kt.dtos.FeedbackDto;
import com.kt.entities.Feedback;
import com.kt.entities.Product;
import com.kt.mapper.FeedbackMapper;
import com.kt.models.FeedbackModel;
import com.kt.services.CloudinaryService;
import com.kt.services.FeedbackService;
import com.kt.services.ProductService;
import com.kt.utils.Constant;

@RestController
@RequestMapping("account/feedback")
public class FeedbackController {
    @Autowired
    ProductService productService;
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping
    public FeedbackDto saveFeedback(@Valid @ModelAttribute FeedbackModel feedbackModel) throws IOException {
        Product product = productService.findById(feedbackModel.getProductId());
        if(product == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackModel, feedback);
        feedback.setUser(Constant.getUserLogin());
        feedback.setProduct(product);
        feedback.setTime(new Date());
        MultipartFile imageFile = feedbackModel.getImageFile();
        if(imageFile != null && !imageFile.isEmpty()) {
            String filename = cloudinaryService.uploadImage(feedbackModel.getImageFile());
            feedback.setImage(filename);
        }
        return FeedbackMapper.getInstance().toDto(feedbackService.save(feedback));
    }

    @GetMapping
    public List<FeedbackDto> getFeedbackByProduct(@RequestParam(defaultValue = "0") int productId) {
        Product product = productService.findById(productId);
        if(product == null) {
            return null;
        }
        return FeedbackMapper.getInstance().toListDto(feedbackService.findByProduct(product));
    }
}
