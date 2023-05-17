package com.kt.controllers.web;

import com.kt.dtos.CartDto;
import com.kt.dtos.CartItemDto;
import com.kt.dtos.PageDto;
import com.kt.entities.Cart;
import com.kt.entities.User;
import com.kt.mapper.CartItemMapper;
import com.kt.mapper.CartMapper;
import com.kt.models.CartModel;
import com.kt.models.CustomUserDetail;
import com.kt.services.CartItemService;
import com.kt.services.CartService;
import com.kt.services.UserService;
import com.kt.utils.Constant;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("account")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserService userService;
    @PostMapping("cart")
    public CartDto createCart(@Valid @ModelAttribute CartModel cartModel) {
        return saveCart(cartModel);
    }
    @GetMapping("cart/{id}")
    public CartDto findCartById(@PathVariable int id) {
        return CartMapper.getInstance().toDto(cartService.findById(id));
    }
    @GetMapping("cart")
    public List<CartDto> findAll() {
        return CartMapper.getInstance().toListDto(cartService.findAll());
    }
    @PutMapping("cart")
    public CartDto updateCart(@Valid @ModelAttribute CartModel cartModel) {
        return saveCart(cartModel);
    }
    private CartDto saveCart(CartModel cartModel) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartModel, cart);
        User user = userService.findById(cartModel.getUserId());
        cart.setUser(user);
        return CartMapper.getInstance().toDto(cartService.save(cart));
    }
    @GetMapping("cart/{id}/items-in-cart")
    public ResponseEntity<List<CartItemDto>> findAllItemInCart(@PathVariable Integer id) {
        if(id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<CartItemDto> cartItemDtos = CartItemMapper.getInstance().toListDto(cartItemService.findByCartId(id));
        return new ResponseEntity<>(cartItemDtos, HttpStatus.OK);
    }

    @GetMapping("cart/items-in-cart-no-checkout")
    public ResponseEntity<List<CartItemDto>> findCartNoCheckout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();
        List<CartItemDto> cartItemDtos = CartItemMapper.getInstance().toListDto(cartService.findCartNoCheckOut(user.getId()).getCartItems());
        return new ResponseEntity<>(cartItemDtos, HttpStatus.OK);
    }

    @GetMapping("cart-by-user-and-status")
    public PageDto<CartDto> getCartsByUserAndStatus(@RequestParam(defaultValue = "1") int status, @RequestParam(defaultValue = "0") int page) {
        User user = Constant.getUserLogin();
        Page<Cart> pCart = cartService.findByUserAndStatus(user.getId(), status, page);
        PageDto<CartDto> pDto = new PageDto<>();
        pDto.setContent(CartMapper.getInstance().toListDto(pCart.getContent()));
        pDto.setIndex(pCart.getNumber());
        pDto.setTotal(pCart.getTotalPages());
        return pDto;
    }

    @DeleteMapping("cancel-order/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable int id) {
        cartService.deleteById(id);
        return ResponseEntity.status(200).body("Cancel order");
    }

}
