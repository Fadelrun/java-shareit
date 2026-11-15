package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService service;

    @PostMapping
    public ItemDto create(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody ItemDto dto) {
        return service.create(dto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                          @Positive @PathVariable Long itemId,
                          @RequestBody ItemDto dto) {
        return service.update(itemId, dto, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@Positive @PathVariable Long itemId) {
        return service.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getByOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getByOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return service.search(text);
    }
}
