package org.example.pioner_pixel.PhoneService;

import com.redis.testcontainers.RedisContainer;
import org.example.pioner_pixel.domain.entity.Account;
import org.example.pioner_pixel.domain.entity.EmailData;
import org.example.pioner_pixel.domain.entity.User;
import org.example.pioner_pixel.dto.UserDto;
import org.example.pioner_pixel.repository.EmailRepository;
import org.example.pioner_pixel.repository.PhoneRepository;
import org.example.pioner_pixel.repository.UserRepository;
import org.example.pioner_pixel.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
public class PhoneServiceIntegrationTest {
    private static final int PORT = 6379;
    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(PORT);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private EmailRepository emailRepository;


    @Autowired
    private CacheManager cacheManager;

    private User testUser;

    @DynamicPropertySource
    private static void registeredRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(PORT).toString());
    }

    @BeforeEach
    void setUp() {
        phoneRepository.deleteAll();
        emailRepository.deleteAll();
        userRepository.deleteAll();

        testUser = User.builder()
                .name("Test User")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .password("password")
                .emailData(new ArrayList<>())
                .phoneData(new ArrayList<>())
                .build();

        EmailData email = EmailData.builder()
                .email("test1@example.com")
                .user(testUser)
                .build();
        testUser.getEmailData().add(email);

        Account account = Account.builder()
                .user(testUser)
                .balance(BigDecimal.ZERO)
                .build();
        testUser.setAccount(account);

        userRepository.save(testUser);

        Optional.ofNullable(cacheManager.getCache("users")).ifPresent(Cache::clear);
    }

    @Test
    void givenRedisContainerConfiguredWithDynamicProperties_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertTrue(REDIS_CONTAINER.isRunning());
    }

    @Test
    @WithMockUser(username = "test1@example.com")
    void addPhone_shouldCacheUserInRedis() {

        String newPhone = "+79591112234";

        User currentUser = userRepository.findByEmailData_Email("test1@example.com")
                .orElseThrow(() -> new IllegalStateException("Test user not found"));
        Long userId = currentUser.getId();

        UserDto serviceResult = phoneService.addPhone(newPhone);

        assertNotNull(serviceResult);

        Cache usersCache = cacheManager.getCache("users");

        assertNotNull(usersCache, "Cache 'users' should exist");

        UserDto cachedUser = usersCache.get(userId, UserDto.class);

        assertNotNull(cachedUser, "User with ID " + userId + " should be cached");
        assertEquals(1, cachedUser.getPhoneData().size());
        assertEquals(newPhone, cachedUser.getPhoneData().get(0).getPhone());
    }
}
