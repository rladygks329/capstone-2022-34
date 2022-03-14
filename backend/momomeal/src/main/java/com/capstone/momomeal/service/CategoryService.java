package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 저장 메서드
     * @param category  저장할 카테고리 object
     * @return  저장한 카테고리 id값
     */
    public Long save(Category category){
        categoryRepository.save(category);
        return category.getId();
    }
}
