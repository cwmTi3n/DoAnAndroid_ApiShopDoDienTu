package com.kt.controllers.web;

import java.util.Date;
import java.util.List;

import com.kt.services.CartService;
import com.kt.services.ProductService;
import com.kt.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kt.dtos.CartItemDto;
import com.kt.entities.Cart;
import com.kt.entities.CartItem;
import com.kt.entities.Product;
import com.kt.mapper.CartItemMapper;
import com.kt.models.CheckoutModel;
import com.kt.services.CartItemService;

@RestController
public class CartItemController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @PutMapping("account/cart-item/update-quantity")
    public CartItemDto updateCartQuantity(@RequestParam("id") int id, @RequestParam("quantity") int quantity) {
        CartItem cartItem = cartItemService.findById(id);
        if(cartItem == null) {
            return null;
        }
        cartItem.setQuantity(quantity);
        return CartItemMapper.getInstance().toDto(cartItemService.save(cartItem));
    }

    @PutMapping("account/delete-item-from-cart")
    public ResponseEntity<String> processData(@RequestBody List<Integer> data) {
        for(Integer id : data) {
            cartItemService.deleteById(id);
        }
        return ResponseEntity.ok().body("Xóa thành công");
    }

    @PostMapping("account/checkout")
    public ResponseEntity<String> checkOut(@RequestBody CheckoutModel checkoutModel) {
        Cart cart = new Cart();
        cart.setAddress(checkoutModel.getAddress());
        cart.setBuyDate(new Date());
        cart.setUser(Constant.getUserLogin());
        cart.setStatus(1);
        Cart cartSave = cartService.save(cart);
        List<Integer> data = checkoutModel.getData();
        for(Integer id : data) {
            CartItem cartItem = cartItemService.findById(id);
            cartItem.setStatus(1);
            cartItem.setCart(cartSave);
            cartItemService.save(cartItem);
        }
        return ResponseEntity.ok("Đơn hàng đã được đặt");
    }

    @PostMapping("account/buy-now")
    public ResponseEntity<String> buyNow(@RequestParam(defaultValue = "0") int productId, @RequestParam(defaultValue = "1") int quantity, @RequestParam String address) {
        Product product = productService.findById(productId);
        if(product == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setAddress(address);
        cart.setBuyDate(new Date());
        cart.setStatus(1);
        cart.setUser(Constant.getUserLogin());
        cartService.save(cart);
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setStatus(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItemService.save(cartItem);
        return ResponseEntity.ok("Thành công");
    }
}
