package com.velocity.eShopCart.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velocity.eShopCart.exception.EmptyResultDataAccessException;
import com.velocity.eShopCart.exception.EntityNotFoundException;
import com.velocity.eShopCart.exception.ResourceNotFoundException;
import com.velocity.eShopCart.model.ProductPrice;
import com.velocity.eShopCart.repository.ProductPriceRepository;
import com.velocity.eShopCart.service.ProductPriceService;


@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Autowired
	private ProductPriceRepository productPriceRepository;
	
	@Override
	public ProductPrice addProductPrice(ProductPrice productPrice) {
		return productPriceRepository.save(productPrice);
	}

	@Override
	public Optional<ProductPrice> getById(Integer id) {
		return Optional.ofNullable(productPriceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id Not Found", null, id)));
	}
		
	@Override
	public List<ProductPrice> getAllProductPriceData() {
		return productPriceRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		if (id != null) {
			productPriceRepository.deleteById(id);
			
		} else {
			throw new EmptyResultDataAccessException("the given id is null or not present", null, id, null);
		}
		
	}

	@Override
	public ProductPrice updateProductPrice(int id, ProductPrice productPrice) {
		ProductPrice pr=this.productPriceRepository.getById(id);
		
		if(pr==null) {
			throw new EntityNotFoundException("The Given Id is Null", null, id);
		}else {
			//pr.setProduct(productPrice.g);
			pr.setRegularPrice(productPrice.getRegularPrice());
			pr.setSellPrice(productPrice.getSellPrice());
			pr.setUnit(productPrice.getUnit());
			pr.setDeleted(false);
		ProductPrice pr1=	productPriceRepository.save(pr);
		return pr1;
	}
		
	}

}
