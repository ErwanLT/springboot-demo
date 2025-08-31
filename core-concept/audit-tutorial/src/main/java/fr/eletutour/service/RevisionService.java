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
