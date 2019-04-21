package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cartItems")
public class CartItemController {

//    @Autowired
//    private CartItemRepository cartItemRepository;

//    @GetMapping("/{username}/all")
//    public @ResponseBody
//    List<CartItem> getByUser_Username(@PathVariable("username") String user,
//                                      HttpSession session) {
//        Object sessionCart = session.getAttribute("cart");
//
//        for (CartItem c : cartItemRepository.findByUser_Username(user)
//        ) {
//            JSONObject jsonObject = new JSONObject("{'orderId':" + c.getCartItemId() + "," + "}");
//
//        }
//        return cartItemRepository.findByUser_Username(user);
//    }
    @GetMapping("/all")
    public @ResponseBody List<CartItem> getAllCartItems(HttpSession session){
//        System.out.println(session.getId());
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("cart")==null){
            Cart cart = new Cart();
            session.setAttribute("cart",cart);
        }
        return ((Cart)(session.getAttribute("cart"))).getCartItemList();
    }

    @PostMapping("/add")
    public @ResponseBody Integer addCartItem(@RequestBody String data, HttpSession session){
//        System.out.println(session.getId());
        User user = (User)session.getAttribute("user");
        JSONObject jsonObject = new JSONObject(data);
        if(session.getAttribute("cart")==null){
            Cart cart = new Cart();
            session.setAttribute("cart",cart);
        }

        Cart cart = (Cart)session.getAttribute("cart");
        CartItem cartItem = new CartItem();
        cartItem.setBookname(jsonObject.getString("bookname"));
        cartItem.setAmount(jsonObject.getInt("amount"));
        cartItem.setPrice(jsonObject.getInt("price"));
        cart.add(cartItem);

        return 200;
    }

}
