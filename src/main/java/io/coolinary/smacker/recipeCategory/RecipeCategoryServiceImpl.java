package io.coolinary.smacker.recipeCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    public Optional<RecipeCategoryEntity> getByPublicId(UUID publicId) {
        return recipeCategoryRepository.findByPublicId(publicId);
    }

    public List<RecipeCategoryEntity> getAll() {
        return recipeCategoryRepository.findAll().stream()
                .sorted(Comparator.comparingLong(RecipeCategoryEntity::getId))
                .collect(Collectors.toList());
    }

    public Page<RecipeCategoryEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy,
            String sortDirection,
            String filter) {
        int pIdx = pageNo != null ? pageNo : 0;
        int pSize = pageSize != null ? pageSize : 10;
        Sort sort = (sortBy != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();
        PageRequest pageable = PageRequest.of(pIdx, pSize, sort);

        if (filter != null && !filter.isBlank()) {
            System.out.println(
                    "pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
                            + sortDirection + ", filter: " + filter);
            return recipeCategoryRepository.findByCategoryNameContainingIgnoreCase(filter, pageable);
        } else {
            System.out.println(
                    "pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
                            + sortDirection);
            return recipeCategoryRepository.findAll(pageable);
        }

    }

    public RecipeCategoryEntity createCategory(RecipeCategoryCreateAPI createAPI) {
        RecipeCategoryAPI recipeCategoryAPI = new RecipeCategoryAPI(UUID.randomUUID(), createAPI.name());
        RecipeCategoryEntity recipeCategoryEntity = toRecipeCategoryEntity(recipeCategoryAPI);
        return recipeCategoryRepository.save(recipeCategoryEntity);
    }

    public RecipeCategoryEntity updateCategory(RecipeCategoryEntity recipeCategoryEntity,
            RecipeCategoryAPI recipeCategoryAPI) {
        recipeCategoryEntity.setCategoryName(recipeCategoryAPI.name());
        return recipeCategoryRepository.save(recipeCategoryEntity);
    }

    @Transactional
    public Boolean deleteCategory(UUID publicId) {
        try {
            recipeCategoryRepository.deleteByPublicId(publicId);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    public Boolean deleteAllCategories() {
        recipeCategoryRepository.deleteAll();
        return true;
    }

    public static RecipeCategoryEntity toRecipeCategoryEntity(RecipeCategoryAPI categoryAPI) {
        RecipeCategoryEntity.RecipeCategoryEntityBuilder<?, ?> categoryBuilder = RecipeCategoryEntity.builder();
        categoryBuilder.categoryName(categoryAPI.name());
        return categoryBuilder.build();
    }

    public static RecipeCategoryAPI toRecipeCategoryAPI(RecipeCategoryEntity categoryEntity) {
        RecipeCategoryAPI categoryAPI = new RecipeCategoryAPI(
                categoryEntity.getPublicId(),
                categoryEntity.getCategoryName());
        return categoryAPI;
    }
}
