package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@Positive @PathVariable Long userId,
                          @RequestBody UserDto dto) {
        return service.update(userId, dto);
    }

    @GetMapping("/{userId}")
    public UserDto getById(@Positive @PathVariable Long userId) {
        return service.getById(userId);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{userId}")
    public void delete(@Positive @PathVariable Long userId) {
        service.delete(userId);
    }
}
