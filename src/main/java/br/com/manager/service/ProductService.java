package br.com.manager.service;

import br.com.manager.dto.ProductDTO;
import br.com.manager.exception.BusinessException;
import br.com.manager.model.Product;
import br.com.manager.repository.ProductRepository;
import br.com.manager.util.TextManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public ProductDTO save(ProductDTO dto) {

        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException(TextManager.getLabel("product.null"));
        }

        if (Objects.isNull(dto.getDescription()) || dto.getDescription().isEmpty()) {
            throw new BusinessException(TextManager.getLabel("product.null.description"));
        }
        if (Objects.isNull(dto.getPrice()) || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(TextManager.getLabel("product.price.zero"));
        }

        Product product = this.productRepository.save(dto.toModel());

        return product.toDTO();
    }

    public Optional<ProductDTO> findById(Long id) {
        return this.productRepository.findById(id).map(Product::toDTO);
    }

    public List<ProductDTO> findAll() {
        return this.productRepository.findAll().stream().map(Product::toDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        this.productRepository.delete(id);
    }

}
