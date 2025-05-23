package com.rq.manager.authusers.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryShopping {
	
	/** The id. */
	private Long id;

	/** The user id. */
	private String userId;

	/** The product id. */
	private Long productId;

	/** The total price. */
	private Integer totalPrice;

	/** The purchase date. */
	private String purchaseDate;

}
