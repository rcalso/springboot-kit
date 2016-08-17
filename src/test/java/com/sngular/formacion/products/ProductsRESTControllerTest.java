package com.sngular.formacion.products;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sngular.formacion.products.dto.Product;
import com.sngular.formacion.products.dto.ProductsRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductsRESTController.class)
public class ProductsRESTControllerTest {

	private static final String PRODUCT_JSON = "{ \"name\": \"Play Station 4\", \"desc\": \"Super cool console\", \"price\": 399.0 }";
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private ProductsRepository repository;
	
	@Test
	public void whenNewProductIsSent_ItWillBeCreatedWithUniqueId() throws Exception {
		
		when(repository.save(any(Product.class))).thenReturn(new Product().setId("1234").setName("Play Station 4"));
		
		mvc.perform(	
				post("/v1/products")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(PRODUCT_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").isNotEmpty())
		.andExpect(jsonPath("$.name").value("Play Station 4"));
	}
	
	@Test
	public void whenAnExistingProductIsRequested_ItWillBeReturned() throws Exception {
		
		when(repository.findOne(any(String.class))).thenReturn(new Product().setId("1234"));
		
		mvc.perform(
				get("/v1/products/1234")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value("1234"));
	}

}
