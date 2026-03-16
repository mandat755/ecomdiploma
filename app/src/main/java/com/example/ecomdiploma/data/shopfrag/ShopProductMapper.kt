package com.example.ecomdiploma.data.shopfrag

import com.example.ecomdiploma.data.productdatabase.ProductEntity
import com.example.ecomdiploma.domain.shopfrag.ProductModel

object ShopProductMapper {
    fun ProductEntity.toModel(): ProductModel {
        return ProductModel(
             id= this.id,
            name = this.name,
            price = this.price,
            images = this.images,
            description = this.description
        )
    }

    fun ProductModel.toEntity(): ProductEntity {
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            images = this.images,
            description = this.description
        )
    }
}