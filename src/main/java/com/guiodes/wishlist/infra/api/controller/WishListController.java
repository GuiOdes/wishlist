package com.guiodes.wishlist.infra.api.controller;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.usecase.AddProductToWishlistUseCase;
import com.guiodes.wishlist.application.usecase.DeleteProductFromWishListUseCase;
import com.guiodes.wishlist.application.usecase.ExistsProductByWishListUseCase;
import com.guiodes.wishlist.application.usecase.FindWishListUseCase;
import com.guiodes.wishlist.infra.api.request.AddProductRequest;
import com.guiodes.wishlist.infra.api.response.ContainsProductInWishListResponse;
import com.guiodes.wishlist.infra.api.response.WishListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {

    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final FindWishListUseCase findWishListUseCase;
    private final ExistsProductByWishListUseCase existsProductByWishListUseCase;
    private final DeleteProductFromWishListUseCase deleteProductFromWishListUseCase;

    public WishListController(
            AddProductToWishlistUseCase addProductToWishlistUseCase,
            FindWishListUseCase findWishListUseCase,
            ExistsProductByWishListUseCase existsProductByWishListUseCase,
            DeleteProductFromWishListUseCase deleteProductFromWishListUseCase
    ) {
        this.addProductToWishlistUseCase = addProductToWishlistUseCase;
        this.findWishListUseCase = findWishListUseCase;
        this.existsProductByWishListUseCase = existsProductByWishListUseCase;
        this.deleteProductFromWishListUseCase = deleteProductFromWishListUseCase;
    }

    @PostMapping
    public ResponseEntity<WishListResponse> addItemToWishList(@RequestBody AddProductRequest addProductRequest) {
        AddProductToWishlistCommand addProductToWishlistCommand = new AddProductToWishlistCommand(
                addProductRequest.userId(),
                addProductRequest.productId()
        );

        WishListResponse response = new WishListResponse(addProductToWishlistUseCase.execute(addProductToWishlistCommand));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WishListResponse> findWishListByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(new WishListResponse(findWishListUseCase.execute(userId)));
    }

    @GetMapping("/{userId}/product/{productId}/exists")
    public ContainsProductInWishListResponse containsProductInWishList(
            @PathVariable UUID userId,
            @PathVariable UUID productId
    ) {
        return new ContainsProductInWishListResponse(existsProductByWishListUseCase.execute(userId, productId));
    }

    @DeleteMapping("/{userId}/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromWishList(
            @PathVariable UUID productId,
            @PathVariable UUID userId
    ) {
        deleteProductFromWishListUseCase.execute(userId, productId);
    }
}
