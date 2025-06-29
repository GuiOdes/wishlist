package com.guiodes.wishlist.infra.api.controller;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.usecase.AddProductToWishlistUseCase;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.api.request.AddProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {

    @Autowired
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @PostMapping
    public ResponseEntity<WishListModel> addItemToWishList(@RequestBody AddProductRequest addProductRequest) {
        AddProductToWishlistCommand addProductToWishlistCommand = new AddProductToWishlistCommand(
                addProductRequest.userId(),
                addProductRequest.productId()
        );

        WishListModel wishList =  addProductToWishlistUseCase.execute(addProductToWishlistCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(wishList);
    }

    @GetMapping("/{userId}")
    public String getWishListByUserId(@PathVariable String userId) {
        return "Wishlist for user: " + userId;
    }

    @GetMapping("/{userId}/all")
    public String getAllWishLists(@PathVariable String userId) {
        return "All wishlists retrieved successfully";
    }

    @GetMapping("/{userId}/contains/{productId}")
    public String checkIfWishListContainsProduct(@PathVariable String userId, @PathVariable String productId) {
        return "Wishlist for user: " + userId + " contains product: " + productId;
    }

    @DeleteMapping("/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String removeItemFromWishList(@RequestParam String userId, @RequestParam String productId) {
        return "Item with ID: " + productId + " removed from wishlist for user: " + userId;
    }
}
