package com.hotelier.service;

import com.hotelier.dto.MenuItemDTO;
import com.hotelier.model.MenuItem;
import com.hotelier.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public MenuItemDTO createMenuItem(MenuItemDTO request) {

        if (menuItemRepository.existsByName(request.getName())) {
            throw new RuntimeException("Menu item with this name already exists");
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setVegetarian(request.getVegetarian());

        MenuItem saved = menuItemRepository.save(menuItem);

        return toResponse(saved);
    }

    private MenuItemDTO toResponse(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory(),
                menuItem.getAvailable(),
                menuItem.getVegetarian()
        );
    }
}

