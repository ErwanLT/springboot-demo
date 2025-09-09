/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of audit-tutorial.
 *
 * audit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * audit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with audit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
