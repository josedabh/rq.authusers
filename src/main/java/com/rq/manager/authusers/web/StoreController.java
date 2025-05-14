package com.rq.manager.authusers.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.RewardRequest;
import com.rq.manager.authusers.bean.RewardResponse;
import com.rq.manager.authusers.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

/**
 * The Class StoreController.
 */
@RestController
@RequestMapping("/api/v1/store")
@AllArgsConstructor
@ApiResponses(value = { 
		@ApiResponse(responseCode = "400", description = "BAD REQUEST") 
})
public class StoreController {
	
	/** The store service. */
	private StoreService storeService;
	
	/**
	 * Creates the product.
	 *
	 * @param rewardRequest the reward request
	 * @return the reward response
	 */
	@Operation(summary = "Crea un nuevo producto", 
			description = "Crea un nuevo producto en la tienda")
	@ApiResponse(responseCode = "200", description = "Producto creado",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
		schema = @Schema(implementation = RewardResponse.class)))
	@GetMapping("/create-product")
	public RewardResponse createProduct(RewardRequest rewardRequest) {
		return storeService.createReward(rewardRequest);
	}

	/**
	 * List products.
	 *
	 * @return the list of reward response
	 */
	@Operation(summary = "Lista los productos", 
			description = "Lista los productos en la tienda")
	@ApiResponse(responseCode = "200", description = "Lista de productos",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
		schema = @Schema(implementation = RewardResponse.class)))
	@GetMapping("/list-products")
	public List<RewardResponse> listProducts() {
        return storeService.listRewards();
    }
	
	@Operation(summary = "Elimina un producto", 
			description = "Elimina un producto de la tienda")
	@ApiResponse(responseCode = "200", description = "Producto eliminado",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
		schema = @Schema(implementation = Void.class)))
	@DeleteMapping("/delete-product")
	public void deleteProduct(long id) {
		storeService.deleteReward(id);
	}
	
	/**
	 * Gets the reward by id.
	 *
	 * @param id the id
	 * @return the reward by id
	 */
	@Operation(summary = "Actualiza un producto", description = "Actualiza un producto en la tienda")
	@ApiResponse(responseCode = "200", description = "Producto actualizado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RewardResponse.class)))
	public RewardResponse getRewardById(long id) {
		return storeService.getRewardById(id);
	}
	
	/**
	 * Buy reward.
	 *
	 * @param id the id
	 * @return the reward response
	 */
	@Operation(summary = "Compra un producto", description = "Compra un producto de la tienda")
	@ApiResponse(responseCode = "200", description = "Producto comprado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RewardResponse.class)))
	public RewardResponse buyReward(long id) {
        return storeService.buyReward(id);
    }
	
	
}
