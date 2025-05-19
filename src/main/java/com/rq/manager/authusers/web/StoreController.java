package com.rq.manager.authusers.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.admin.RewardRequest;
import com.rq.manager.authusers.bean.admin.RewardResponse;
import com.rq.manager.authusers.constants.ApiConstants;
import com.rq.manager.authusers.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * The Class StoreController.
 */
@RestController
@RequestMapping("/api/v1/store")
@AllArgsConstructor
@Tag(name = "Store Controller", description = "Controlador de la tienda")
@ApiResponses(value = { 
		@ApiResponse(responseCode = "400", 
				description = ApiConstants.BAD_REQUEST)
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
	@PostMapping("/create-product")
	public RewardResponse createProduct(@Valid @RequestBody RewardRequest rewardRequest) {
		return storeService.createReward(rewardRequest);
	}

	/**
	 * Update reward.
	 *
	 * @param id the id
	 * @param rewardRequest the reward request
	 * @return the reward response
	 */
	@Operation(summary = "Actualiza un producto", 
			description = "Actualiza un producto en la tienda")
	@ApiResponse(responseCode = "200", description = "Producto actualizado",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
		schema = @Schema(implementation = RewardResponse.class)))
	@PostMapping("/update-product/{id}")
	public RewardResponse updateReward(@PathVariable long id, @Valid @RequestBody RewardRequest rewardRequest) {
		return storeService.updateReward(id, rewardRequest);
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
	
	/**
	 * Delete product.
	 *
	 * @param id the id
	 */
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
    @Operation(
            summary = "Actualiza un producto",
            description = "Actualiza un producto en la tienda")
    @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RewardResponse.class)))
    @GetMapping("/reward/{id}")
    public RewardResponse getRewardById(
            @PathVariable @Parameter(description = "the id product") long id) {
        return storeService.getRewardById(id);
    }
	
	/**
	 * Buy reward.
	 *
	 * @param id the id
	 * @return the reward response
	 */
    @Operation(
            summary = "Compra un producto",
            description = "Compra un producto de la tienda")
    @ApiResponse(
            responseCode = "200",
            description = "Producto comprado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RewardResponse.class)))
    @PostMapping("/buy/{id}")
    public RewardResponse buyReward(@PathVariable long id) {
        return storeService.buyReward(id);
    }
	
    /**
     * Can see user reward.
     *
     * @param id the id
     * @return the reward response
     */
    @Operation(
            summary = "El usuario lo ve o no",
            description = "El administrador activa o deactiva la visiblidad del producto")
    @ApiResponse(
            responseCode = "200",
            description = "Visibilidad cambiada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RewardResponse.class)))
	@PatchMapping("/change-visibility/{id}")
	public RewardResponse canSeeUserReward(@PathVariable long id) {
        return storeService.toggleRewardVisibility(id);
    }
}
