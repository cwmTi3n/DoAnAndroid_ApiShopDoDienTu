package com.kt.controllers.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dtos.CartItemDto;
import com.kt.dtos.PageDto;
import com.kt.entities.Cart;
import com.kt.entities.CartItem;
import com.kt.entities.Product;
import com.kt.entities.User;
import com.kt.mapper.CartItemMapper;
import com.kt.services.CartItemService;
import com.kt.services.CartService;
import com.kt.services.ProductService;
import com.kt.utils.Constant;

@RestController
@RequestMapping("seller")
public class OrderController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @GetMapping("checking")
    public PageDto<CartItemDto> getCheckingItem(@RequestParam(defaultValue = "1") int status, @RequestParam(defaultValue = "0") int page) {
        User user = Constant.getUserLogin();
        PageDto<CartItem> pageCartItem= cartItemService.findBySellerAndStatus(user.getId(), status, page);
        PageDto<CartItemDto> pDto = new PageDto<>();
        pDto.setTotal(pageCartItem.getTotal());
        pDto.setIndex(pageCartItem.getIndex());
        pDto.setContent(CartItemMapper.getInstance().toListDto(pageCartItem.getContent()));
        return pDto;
    }

    @PostMapping("conform-cart-item")
    public CartItemDto conformCartItem(@RequestParam(defaultValue = "0") int id, @RequestParam(defaultValue = "4") int status) {
        CartItem cartItem = cartItemService.findById(id);
        if(cartItem == null) {
            return null;
        }
        if(cartItem.getCart().getStatus() != 1) {
            return null;
        }
        Product product = cartItem.getProduct();
        int stock = product.getStock();
        int amount = product.getAmount();
        if(status == 2 && (product.getStatus() == 0 || stock < 1)) {
            return null;
        }
        cartItem.setStatus(status);
        CartItem cartSave = cartItemService.save(cartItem);
        if(cartSave != null) {
            if(status == 2) {
                stock--;
                amount++;
                product.setStock(stock);
                product.setAmount(amount);
                productService.save(product);
            }
            checkConformCart(cartSave.getCart().getCartItems());
            return CartItemMapper.getInstance().toDto(cartSave);
        }
        return null;

    }

    private void checkConformCart(List<CartItem> cartItems) {
        int tmp = 0;
        for(CartItem c : cartItems) {
            if(c.getStatus() == 1) {
                return;
            }
            else  if(c.getStatus() == 4) {
                tmp++;
            }
        }
        Cart cart = cartItems.get(0).getCart();
        if(tmp == cartItems.size()) {
            cart.setStatus(4);
        }
        else {
            cart.setStatus(2);
        }
        cartService.save(cart);
    }
}
