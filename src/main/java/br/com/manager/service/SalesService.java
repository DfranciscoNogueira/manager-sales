package br.com.manager.service;

import br.com.manager.dto.SaleItemDTO;
import br.com.manager.dto.SalesDTO;
import br.com.manager.exception.BusinessException;
import br.com.manager.model.Client;
import br.com.manager.model.Product;
import br.com.manager.model.SaleItem;
import br.com.manager.model.Sales;
import br.com.manager.repository.ClientRepository;
import br.com.manager.repository.ProductRepository;
import br.com.manager.repository.SaleRepository;
import br.com.manager.util.Constants;
import br.com.manager.util.TextManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesService {

    private final SaleRepository salesRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SalesService() {
        this.salesRepository = new SaleRepository();
        this.clientRepository = new ClientRepository();
        this.productRepository = new ProductRepository();
    }

    public SalesDTO registerSale(Long clientId, List<SaleItemDTO> items) {

        Client client = this.clientRepository.findById(clientId).orElseThrow(() -> new BusinessException(TextManager.getLabel("client.not.found")));

        validateCreditLimit(client, items);

        Sales sales = new Sales(client, LocalDate.now());

        sales.setTotal(items.stream().map(SaleItemDTO::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add));

        for (SaleItemDTO item : items) {

            if (sales.getItems().stream().anyMatch(i -> i.getProduct().getId().equals(item.getProduct().getId()))) {
                throw new BusinessException(TextManager.getLabel("product.already.added"));
            }

            Product product = this.productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new BusinessException(TextManager.getLabel("product.not.found")));

            SaleItem saleItem = new SaleItem(product, item.getAmount());
            saleItem.setSales(sales);
            sales.addItem(saleItem);

        }

        Sales newSales = this.salesRepository.save(sales);

        return newSales.toDTO();
    }

    private void validateCreditLimit(Client cliente, List<SaleItemDTO> itens) {

        BigDecimal totalShopping = itens.stream().map(SaleItemDTO::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate lastClosing = calculateLastClosing(cliente);
        BigDecimal limit = cliente.getLimitSales().subtract(totalShopping);

        if (limit.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(TextManager.getLabel("credit.limit.exceeded1") + Constants.SPACE_BLANK + limit + TextManager.getLabel("credit.limit.exceeded2") + Constants.SPACE_BLANK + lastClosing);
        }

    }

    private LocalDate calculateLastClosing(Client client) {
        LocalDate now = LocalDate.now();
        if (now.getDayOfMonth() >= client.getDayClosingInvoice()) {
            return now.withDayOfMonth(client.getDayClosingInvoice()).plusMonths(1);
        } else {
            return now.withDayOfMonth(client.getDayClosingInvoice());
        }
    }

    public Optional<SalesDTO> findById(Long id) {

        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(TextManager.getLabel("client.null.id"));
        }

        return this.salesRepository.findById(id).map(Sales::toDTO);
    }

    public List<SalesDTO> findByClient(Long clientId) {
        return this.salesRepository.findByClientId(clientId).stream().map(Sales::toDTO).collect(Collectors.toList());
    }

    public List<SalesDTO> findByPeriod(LocalDate startDate, LocalDate endDate) {
        return this.salesRepository.findByDateBetween(startDate, endDate).stream().map(Sales::toDTO).collect(Collectors.toList());
    }

    public List<SalesDTO> findAll() {
        return this.salesRepository.findAll().stream().map(Sales::toDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        this.salesRepository.delete(id);
    }

}