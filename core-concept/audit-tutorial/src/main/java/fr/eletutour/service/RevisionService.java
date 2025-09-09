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

import fr.eletutour.dto.RevisionDto;
import fr.eletutour.dto.StockDto;
import fr.eletutour.entity.Stock;
import fr.eletutour.entity.StockRevisionEntity;
import fr.eletutour.repository.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionService {

    private final StockRepository stockRepository;

    public RevisionService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public List<RevisionDto> getStockHistory(Long id) {
        return stockRepository.findRevisions(id).getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RevisionDto toDto(Revision<Integer, Stock> revision) {
        Stock stockEntity = revision.getEntity();
        StockRevisionEntity revisionMetadata = (StockRevisionEntity) revision.getMetadata().getDelegate();

        return new RevisionDto(
                revision.getRevisionNumber().orElseThrow(() -> new IllegalStateException("Revision number not found")),
                new Date(revisionMetadata.getTimestamp()),
                revision.getMetadata().getRevisionType().name(),
                revisionMetadata.getUsername(),
                toDto(stockEntity)
        );
    }

    private StockDto toDto(Stock stock) {
        StockDto dto = new StockDto();
        BeanUtils.copyProperties(stock, dto);
        return dto;
    }
}
