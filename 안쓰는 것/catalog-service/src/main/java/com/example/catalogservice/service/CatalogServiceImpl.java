package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService{
    CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public CatalogEntity createCatalog(CatalogDto catalogDto) {


        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CatalogEntity catalogEntity = mapper.map(catalogDto, CatalogEntity.class);

        catalogRepository.save(catalogEntity);

        return null;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @Override
    public CatalogEntity getCatalog(String productId) {
        return catalogRepository.findByProductId(productId);
    }

    @Override
    public void deleteCatalog(String productId) {
        catalogRepository.deleteByproductId(productId);
    }

    @Override
    public Iterable<CatalogEntity> getByCatalogsBetween(String start, String end){
        return catalogRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    public Iterable<CatalogEntity> getCatalogsByProductName(String name){
        return catalogRepository.findByProductNameContains(name);
    }

    @Override
    public Iterable<CatalogEntity> getCatalogsByWriter(String name){
        return catalogRepository.findByWriterContains(name);
    }

    /* 카탈로그(상품) 수정관련 */
    @Override
    public CatalogDto getCatalogByProductId(String productId) {

        CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CatalogDto catalogDto = mapper.map(catalogEntity, CatalogDto.class);

        return catalogDto;
    }

    /* 카탈로그(상품) 수정관련 */
    @Override
    public CatalogDto updateByProductId(CatalogDto catalogDto, CatalogDto catalogDetails) {
        CatalogEntity userEntity = catalogRepository.findByProductId(catalogDto.getProductId());
        ModelMapper mapper = new ModelMapper();
        CatalogDto catalogUpdateDto = mapper.map(userEntity, CatalogDto.class);

        catalogUpdateDto.setProductName(catalogDetails.getProductName());
        catalogUpdateDto.setWriter(catalogDetails.getWriter());
        catalogUpdateDto.setStock(catalogDetails.getStock());
        catalogUpdateDto.setUnitPrice(catalogDetails.getUnitPrice());
        catalogUpdateDto.setImage(catalogDetails.getImage());
        catalogUpdateDto.setProductId(catalogDto.getProductId());

        ModelMapper catalogmapper = new ModelMapper();
        catalogmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CatalogEntity catalogUpdateEntity = catalogmapper.map(catalogUpdateDto, CatalogEntity.class);

        catalogUpdateEntity.setId(userEntity.getId());
        catalogRepository.save(catalogUpdateEntity);

        return null;
    }
}
