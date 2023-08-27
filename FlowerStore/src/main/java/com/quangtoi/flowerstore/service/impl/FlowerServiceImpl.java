package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.dto.FlowerResponse;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Category;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Supplier;
import com.quangtoi.flowerstore.repository.CategoryRepository;
import com.quangtoi.flowerstore.repository.FlowerRepository;
import com.quangtoi.flowerstore.repository.SupplierRepository;
import com.quangtoi.flowerstore.service.FlowerService;
import com.quangtoi.flowerstore.service.ImageService;
import com.quangtoi.flowerstore.service.PreviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlowerServiceImpl implements FlowerService {
    private FlowerRepository flowerRepository;
    private CategoryRepository categoryRepository;
    private SupplierRepository supplierRepository;
    private PreviewService previewService;
    private ImageService imageService;
    private ModelMapper mapper;

    @Override
    public List<FlowerDto> getAllFlowers() {
        return flowerRepository.findAll()
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowerDto getFlowerById(Long id) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));

        return mapper.map(flower, FlowerDto.class);
    }

    @Override
    public List<FlowerDto> getFlowerByCategoryId(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", cateId));

        List<Flower> flowers = flowerRepository.findByCategory(category);

        return flowers
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerResponse> getFlowerBestSeller() {
        List<Object[]> flowers = flowerRepository.findByBestSeller();
        List<FlowerResponse> flowerResponses = new ArrayList<>();
        for (Object[] result : flowers) {
            FlowerResponse flowerResponse = new FlowerResponse();
            flowerResponse.setId((Long) result[0]);
            flowerResponse.setName((String) result[1]);
            flowerResponse.setDescription((String) result[2]);
            flowerResponse.setCategoryId((Long) result[3]);
            Timestamp timestamp = (Timestamp) result[4];
            long timestampMillis = timestamp.getTime();
            Instant instant = Instant.ofEpochMilli(timestampMillis);
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            flowerResponse.setCreatedAt(localDateTime);
            flowerResponse.setStockQuantity((Integer) result[5]);
            flowerResponse.setUnitPrice((Double) result[6]);
            flowerResponse.setUrlImage((String) result[7]);
            Timestamp timestamp1 = (Timestamp) result[8];
            long timestampMillis1 = timestamp1.getTime();
            Instant instant1 = Instant.ofEpochMilli(timestampMillis1);
            LocalDateTime updatedAt = instant1.atZone(ZoneId.systemDefault()).toLocalDateTime();
            flowerResponse.setUpdatedAt(updatedAt);
            flowerResponse.setSaleVolume(((BigDecimal) result[9]).intValue());

            flowerResponses.add(flowerResponse);
        }
        return flowerResponses;
    }

    @Override
    public List<FlowerDto> getFlowerFavorites() {
        return flowerRepository.findByFavorites()
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerDto> getFlowerBySupplierId(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "supplier id", supplierId));

        List<Flower> flowers = flowerRepository.getAllBySuppliers(supplier.getId());

        return flowers
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerDto> getFlowersByKeyword(String kw) {
        return flowerRepository.getFlowersByKeyword(kw)
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowerDto saveFlower(FlowerDto flowerDto, MultipartFile imageFlower) {
        Flower flower = new Flower();
        if (imageFlower == null){
            flower.setUrlImage(null);
        }else {
            String urlImage = imageService.uploadImage(imageFlower);
            flower.setUrlImage(urlImage);
        }
        flower.setStockQuantity(0);
        flower.setDescription(flowerDto.getDescription());
        flower.setName(flowerDto.getName());
        flower.setCreatedAt(LocalDateTime.now());
        flower.setUnitPrice(flowerDto.getUnitPrice());
        flower.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository
                .findById(flowerDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id",
                                flowerDto.getCategoryId()));
        flower.setCategory(category);
        FlowerDto flowerDtoSaved = mapper.map(flowerRepository.save(flower), FlowerDto.class);
        flowerDto.setStockQuantity(0);
        flowerDto.setAvgScore(0.0);
        flowerDto.setTotalPreviews(0);
        return flowerDtoSaved;
    }

    @Override
    public FlowerDto updateFlowerById(FlowerDto flowerDto, Long id, MultipartFile imageFlower) {
        Flower flowerSaved = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));
        Category category = categoryRepository.findById(flowerDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", flowerDto.getCategoryId()));

        if (imageFlower != null){
            String urlImage = imageService.uploadImage(imageFlower);
            flowerSaved.setUrlImage(urlImage);
        }

        flowerSaved.setCategory(category);
        flowerSaved.setName(flowerDto.getName());
        flowerSaved.setDescription(flowerDto.getDescription());
        flowerSaved.setStockQuantity(flowerDto.getStockQuantity());
        flowerSaved.setUnitPrice(flowerDto.getUnitPrice());
        flowerSaved.setUpdatedAt(LocalDateTime.now());
        Flower flowerUpdated = flowerRepository.save(flowerSaved);
        FlowerDto flowerDtoResponse = previewService.countPreviewsById(flowerUpdated.getId());
        return flowerDtoResponse;
    }

    @Override
    public void deleteFlowerById(Long id) {
        Flower flowerSaved = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));
        flowerRepository.delete(flowerSaved);
    }

    @Override
    public Integer getAmountOfSoldFlowers(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", flowerId));
        return flowerRepository.getSoldAmountOfFlowers(flowerId);
    }
}
