package com.weitest.testapcapital.model.merchant;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ProductPo {

	/** 商品名稱 */
	private String name;
	/** 庫存單位 pk*/
	private String sku;
	/** 價格 */
	private BigDecimal price;
	/** 當前商品數量 */
	private Integer quantity;
	/** 商品總數 */
	private Integer quantityAll;
	/** 商戶Id */
	private String merchantId;

}
