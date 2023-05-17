package com.kt.controllers.admin;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.kt.dtos.CategoryDto;
import com.kt.mapper.CategoryMapper;
import com.kt.services.CloudinaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kt.entities.Category;
import com.kt.models.CategoryModel;
import com.kt.services.CategoryService;
import com.kt.services.ImageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/category")
public class CrudCategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("")
    public CategoryDto createCategory(@Valid @ModelAttribute("category") CategoryModel categoryModel) throws IOException {
        String filename = null;
        MultipartFile file = categoryModel.getImageFile();
        if(file != null) {
            if(!file.isEmpty()) {
//                filename = imageService.save(file);
                filename = cloudinaryService.uploadImage(file);
            }
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryModel, category);
        category.setImage(filename);
        category.setStatus(1);
        return CategoryMapper.getInstance().toDto(categoryService.save(category));
    }

    @GetMapping("")
    public List<CategoryDto> getAllCategory() {
        return CategoryMapper.getInstance().toListDto(categoryService.findAll());
    }

    @GetMapping("{id}")
    public CategoryDto getCategory(@PathVariable Integer id) {
        return CategoryMapper.getInstance().toDto(categoryService.findById(id));
    }


    @PutMapping("")
    public CategoryDto updateCategory(@Valid @ModelAttribute("category") CategoryModel categoryModel) throws IOException {
        Category oldCategory = categoryService.findById(categoryModel.getId());
        String oldImage = oldCategory.getImage();
        Category newCategory = new Category();
        BeanUtils.copyProperties(categoryModel, newCategory);
        //Nếu ảnh không đổi thì lấy ảnh củ, nếu đổi ảnh thì xóa ảnh củ và lấy ảnh mới
        MultipartFile file = categoryModel.getImageFile();
        if(file == null || file.isEmpty()) {
            newCategory.setImage(oldImage);
        }
        else {
//            imageService.delete(oldImage);
//            String newImage = imageService.save(categoryModel.getImageFile());
            cloudinaryService.deleteImage(oldImage);
            String newImage = cloudinaryService.uploadImage(file);
            newCategory.setImage(newImage);
        }
        return CategoryMapper.getInstance().toDto(categoryService.save(newCategory));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(required = false) Integer id) throws Exception {
        if(id == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService.findById(id);
        if(category == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        else {
//            imageService.delete(category.getImages());
            cloudinaryService.deleteImage(category.getImage());
            categoryService.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
    }

}
