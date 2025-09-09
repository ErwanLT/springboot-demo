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
package fr.eletutour.controller;

import fr.eletutour.dto.CreateStockRequest;
import fr.eletutour.dto.RevisionDto;
import fr.eletutour.dto.StockDto;
import fr.eletutour.dto.UpdateStockQuantityRequest;
import fr.eletutour.service.RevisionService;
import fr.eletutour.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    private final RevisionService revisionService;

    public StockController(StockService stockService, RevisionService revisionService) {
        this.stockService = stockService;
        this.revisionService = revisionService;
    }

    @PostMapping
    public ResponseEntity<StockDto> createStock(@RequestBody CreateStockRequest request) {
        StockDto createdStock = stockService.createStock(request);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<StockDto> updateStockQuantity(@PathVariable Long id, @RequestBody UpdateStockQuantityRequest request) {
        StockDto updatedStock = stockService.updateStockQuantity(id, request);
        return ResponseEntity.ok(updatedStock);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<RevisionDto>> getStockHistory(@PathVariable Long id) {
        List<RevisionDto> history = revisionService.getStockHistory(id);
        return ResponseEntity.ok(history);
    }
}
