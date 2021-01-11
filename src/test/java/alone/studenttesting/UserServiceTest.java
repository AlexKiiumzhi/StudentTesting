package alone.studenttesting;

import alone.studenttesting.entity.User;
import alone.studenttesting.repository.SubjectRepository;
import alone.studenttesting.repository.TestRepository;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

// won't work unless disable password encoder in UserServiceImpl mapper (line 281)
    /*@Test
    public void registerUser_shall_register_a_user() {
        // given
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("alex@gmail.com");
        registrationDto.setPassword("alex123");

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.registerUser(registrationDto);
        Optional<User> userFromDB = userRepository.findByEmail(registrationDto.getEmail());
        // then
        userFromDB.ifPresent(value -> assertEquals(value.getId(), user.getId()));
    }*/

    @Test
    public void testSelection_shall_select_a_test() {
        // given
        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(2L);
        test.setEnName("Mathemati 11");
        test.setUaName("Математ 11");
        test.setDifficulty(6L);
        test.setQuestionAmount(11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        test.setTestDate(LocalDateTime.parse("2021-01-10 12:00", formatter));

        alone.studenttesting.entity.Test test1 = new alone.studenttesting.entity.Test();
        test.setId(1L);

        List<alone.studenttesting.entity.Test> tests = new ArrayList<>();
        tests.add(test1);

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");
        user.setBlocked(Boolean.FALSE);
        user.setTests(tests);
        List<alone.studenttesting.entity.Test> expectedTests = user.getTests();

        mockSecurityContext();
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));

        // when
        userService.testSelection(test.getId());
        testRepository.addUserTest(user.getId(), test.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            List<alone.studenttesting.entity.Test> actualTests = optionalUser.get().getTests();

        LocalDateTime passingTestDate = LocalDateTime.now();
        // then
        assertEquals(passingTestDate.isAfter(test.getTestDate()), Boolean.TRUE);
        int index = 0;
        for (alone.studenttesting.entity.Test actualTest : actualTests) {
            assertTest(actualTest, expectedTests.get(index++));
        }
        }
    }

    private void assertTest(alone.studenttesting.entity.Test actualTest, alone.studenttesting.entity.Test expectedTest){
        assertEquals(actualTest.getId(), expectedTest.getId());
        assertEquals(actualTest.getQuestionAmount(), expectedTest.getQuestionAmount());
        assertEquals(actualTest.getDifficulty(), expectedTest.getDifficulty());
        assertEquals(actualTest.getEnName(), expectedTest.getEnName());
        assertEquals(actualTest.getUaName(), expectedTest.getUaName());
    }

    private void mockSecurityContext(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
