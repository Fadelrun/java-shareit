package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto create(UserDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists: " + dto.getEmail());
        }

        User user = UserMapper.toUser(dto);
        User saved = repository.save(user);

        return UserMapper.toUserDto(saved);
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            if (!dto.getEmail().equalsIgnoreCase(existing.getEmail())
                    && repository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException("Email already exists: " + dto.getEmail());
            }

            existing.setEmail(dto.getEmail());
        }

        return UserMapper.toUserDto(repository.update(existing));
    }

    @Override
    public UserDto getById(Long id) {
        return UserMapper.toUserDto(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found: " + id))
        );
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        repository.deleteById(id);
    }
}
