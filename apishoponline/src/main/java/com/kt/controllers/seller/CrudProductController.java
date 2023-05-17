package com.kt.controllers.seller;

import com.kt.dtos.ProductDto;
import com.kt.entities.CartItem;
import com.kt.entities.Category;
import com.kt.entities.Product;
import com.kt.entities.User;
import com.kt.mapper.ProductMapper;
import com.kt.models.CustomUserDetail;
import com.kt.models.ProductModel;
import com.kt.services.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("seller/product")
public class CrudProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    CartItemService cartItemService;
    @PostMapping("")
    public ProductDto createProduct(@Valid @ModelAttribute ProductModel productModel) throws IOException {
        Product product = new Product();
        BeanUtils.copyProperties(productModel, product);
        Category category = categoryService.findById(productModel.getCategoryId());
//        User user = userService.findById(productModel.getUserId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();
        if(user == null) {
            return null;
        }
        product.setCategory(category);
        product.setUser(user);
        MultipartFile file = productModel.getImageFile();
        if(file != null) {
            if(!file.isEmpty()) {
//                String filename = imageService.save(file);
                String filename = cloudinaryService.uploadImage(file);
                product.setImage(filename);
            }
        }
        product.setCreateDate(new Date());
        product.setStatus(1);
        return ProductMapper.getInstance().toDto(productService.save(product));
    }
    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable int id) {
        return ProductMapper.getInstance().toDto(productService.findById(id));
    }
    @GetMapping("")
    public List<ProductDto> getAllProduct() {
        return ProductMapper.getInstance().toListDto(productService.findAll());
    }
    @PutMapping("")
    public ProductDto updateProduct(@Valid @ModelAttribute ProductModel productModel) throws IOException {
        User user = userService.findById(productModel.getUserId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User userLogin = userDetails.getUser();
        if(user.getId() != userLogin.getId()) {
            return null;
        }
        Product newProduct = new Product();
        Product oldProduct = productService.findById(productModel.getId());
        BeanUtils.copyProperties(productModel, newProduct);
        Category category = categoryService.findById(productModel.getCategoryId());
        newProduct.setCategory(category);
        newProduct.setUser(user);
        MultipartFile file = productModel.getImageFile();
        String oldImage = oldProduct.getImage();
        if(file == null || file.isEmpty()) {
            newProduct.setImage(oldImage);
        }
        else {
//            String newImage = imageService.save(file);
//            imageService.delete(oldImage);
            cloudinaryService.deleteImage(oldImage);
            String newImage = cloudinaryService.uploadImage(file);
            newProduct.setImage(newImage);
        }
        newProduct.setCreateDate(oldProduct.getCreateDate());
        float oldPrice = oldProduct.getPrice();
        Product result = productService.save(newProduct);
        //Change unitPrice
        if(result != null)
        {
            if(oldPrice != result.getPrice())
            {
                float newPrice = result.getPrice();
                List<CartItem> cartItems = cartItemService.findCartItemNoCheckOutByProduct(productModel.getId());
                for(CartItem c : cartItems) {
                    c.setUnitPrice(newPrice);
                    cartItemService.save(c);
                }
            }
        }
        return ProductMapper.getInstance().toDto(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws IOException {
        if(id == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        Product product = productService.findById(id);
        if(product == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        if(product.getUser().getId() != userDetails.getUser().getId()) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        else {
//            imageService.delete(product.getImages());
            // cloudinaryService.deleteImage(product.getImage());
            // productService.deleteById(id);
            product.setStatus(0);
            productService.save(product);
            return new ResponseEntity<>("Xóa thành công product có id: " + id.toString(), HttpStatus.OK);
        }
    }

}
