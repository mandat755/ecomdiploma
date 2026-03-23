package com.example.ecomdiploma.data.shopfrag

import com.example.ecomdiploma.data.productdatabase.SimpleProductEntity
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel

object CartProductMapper {
    fun SimpleProductEntity.toModel(): SimpleProductModel {
        return SimpleProductModel(
            id= this.id,
            name = this.name,
            price = this.price,
            size = this.size,
            imageResId = this.imageResId,
        )
    }

    fun SimpleProductModel.toEntity(): SimpleProductEntity {
        return SimpleProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            size = this.size,
            imageResId = this.imageResId,
        )
    }
}