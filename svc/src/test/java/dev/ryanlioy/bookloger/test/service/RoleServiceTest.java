package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.entity.RoleEntity;
import dev.ryanlioy.bookloger.repository.RoleRepository;
import dev.ryanlioy.bookloger.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void getRoleByName() {
        RoleEntity expected =  new RoleEntity(1L, "ADMIN");
        when(roleRepository.findByName(any())).thenReturn(Optional.of(expected));
        RoleEntity actual = roleService.getRoleByName("ADMIN");

        assertEquals(expected, actual);
    }
}
