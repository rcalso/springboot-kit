package com.sngular.formacion.products.dto;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepository extends MongoRepository<Product, String> {

}
