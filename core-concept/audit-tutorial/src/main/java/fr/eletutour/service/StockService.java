package fr.eletutour.service;

import fr.eletutour.dto.CreateStockRequest;
import fr.eletutour.dto.StockDto;
import fr.eletutour.dto.UpdateStockQuantityRequest;
import fr.eletutour.entity.Stock;
import fr.eletutour.repository.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public StockDto createStock(CreateStockRequest request) {
        Stock stock = new Stock();
        stock.setProductName(request.getProductName());
        stock.setQuantity(request.getQuantity());
        Stock savedStock = stockRepository.save(stock);
        return toDto(savedStock);
    }

    @Transactional
    public StockDto updateStockQuantity(Long id, UpdateStockQuantityRequest request) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
        stock.setQuantity(request.getNewQuantity());
        Stock updatedStock = stockRepository.save(stock);
        return toDto(updatedStock);
    }

    private StockDto toDto(Stock stock) {
        StockDto dto = new StockDto();
        BeanUtils.copyProperties(stock, dto);
        return dto;
    }
}
