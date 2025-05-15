package com.todoapp.user.application;
import com.todoapp.user.port.in.UserUseCase;
import com.todoapp.user.port.out.UserRepository;
import com.todoapp.user.domain.User;
import com.todoapp.user.dto.UserRequestDTO;
import com.todoapp.user.dto.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserUseCase {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) {
        if (repo.existsByEmail(dto.email()) || repo.existsByUsername(dto.username())) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        User user = new User(null, dto.username(), dto.name(), dto.email(), hashedPassword);
        User saved = repo.save(user);
        return new UserResponseDTO(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        User user = repo.findById(id);
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}