package com.achobeta.domain.render.adapter.port;

import com.achobeta.domain.render.model.entity.ProductEntity;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
public interface IProductPort {

    ProductEntity queryProductByProductId(String productId);

}
