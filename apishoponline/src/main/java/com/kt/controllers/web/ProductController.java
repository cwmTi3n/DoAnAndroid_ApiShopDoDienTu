package com.kt.controllers.web;

import com.kt.dtos.CartItemDto;
import com.kt.dtos.PageDto;
import com.kt.dtos.ProductDto;
import com.kt.entities.Cart;
import com.kt.entities.CartItem;
import com.kt.entities.Product;
import com.kt.entities.User;
import com.kt.mapper.CartItemMapper;
import com.kt.mapper.ProductMapper;
import com.kt.models.CustomUserDetail;
import com.kt.services.CartItemService;
import com.kt.services.CartService;
import com.kt.services.ProductService;
import com.kt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserService userService;
    @GetMapping("find-product")
    public PageDto<ProductDto> findProduct(@RequestParam(defaultValue = "0") int categoryId
    , @RequestParam(defaultValue = "") String keyword
    , @RequestParam(defaultValue = "name") String orderby
    , @RequestParam(defaultValue = "0") int page) {
        PageDto<Product> productPageDto = null;
        if(categoryId == 0) {
            if(keyword.equals(""))
                productPageDto = productService.findAll(orderby, page);
            else
                productPageDto = productService.searchAll(keyword, orderby, page);
        }
        else {
            if(keyword.equals(""))
                productPageDto = productService.findByCategoryId(categoryId, orderby, page);
            else
                productPageDto = productService.searchAllByCategoryId(categoryId, keyword, orderby, page);

        }
        PageDto<ProductDto> pDtoPageDto = new PageDto<>();
        pDtoPageDto.setTotal(productPageDto.getTotal());
        pDtoPageDto.setIndex(productPageDto.getIndex());
        pDtoPageDto.setContent(ProductMapper.getInstance().toListDto(productPageDto.getContent()));
        return pDtoPageDto;
    }
    @PostMapping("account/add-product-to-cart")
    public CartItemDto addProductToCart(@RequestParam() int productId
    , @RequestParam(defaultValue = "1") int quantity) {
        Product product = productService.findById(productId);
        if(product == null) {
            return null;
        }
        if(product.getStatus() == 0) {
            return null;
        }
        if(product.getStock() < quantity) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();
        int userId = user.getId();
        Cart cart = cartService.findCartNoCheckOut(userId);
        if(cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setStatus(0);
            cart = cartService.save(cart);
        }
        CartItem cartItem = cartItemService.findByCartIdAndProductId(cart.getId(), productId);
        if(cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
        }
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(product.getPrice());
        return CartItemMapper.getInstance().toDto(cartItemService.save(cartItem));
    }

    @DeleteMapping("account/delete-product-from-cart/{id}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Integer id) {
        if(id == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        CartItem cartItem = cartItemService.findById(id);
        if(cartItem == null) {
            return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
        if(cartItem.getCart().getStatus() == 0) {
            cartItemService.deleteById(id);
            return new ResponseEntity<>("Xóa thành công product khỏi cart", HttpStatus.OK);
        }
        return new ResponseEntity<>("Xóa không thành công", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("find-product-by-seller")
    public PageDto<ProductDto> findProductBySeller(@RequestParam(defaultValue = "0") int sellerId,
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "desccreateDate") String orderby,
    @RequestParam(defaultValue = "0") int categoryId,
    @RequestParam(defaultValue = "") String keyword) {
        if(sellerId == 0) {
            return null;
        }
        PageDto<Product> productPageDto =  productService.searchProductBySeller(sellerId, categoryId, keyword, orderby, page);
        PageDto<ProductDto> pDtoPageDto = new PageDto<>();
        pDtoPageDto.setTotal(productPageDto.getTotal());
        pDtoPageDto.setIndex(productPageDto.getIndex());
        pDtoPageDto.setContent(ProductMapper.getInstance().toListDto(productPageDto.getContent()));
        return pDtoPageDto;
    }

}
