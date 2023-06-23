
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.example.QuickThink.Google.Service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    UserRepository userRepository;
//
//    @Test
//    void getUserByToken() {
//        UserEntity exampleUser =
//                new UserEntity(
//                        1L,
//                        "name",
//                        "id",
//                        "picture",
//                        "text",
//                        new HashMap<String, Long>(),
//                        "token", new ArrayList<>()
//                );
//
//        when(userRepository.findByAccessToken("token")).thenReturn(exampleUser);
//
//        UserEntity user = loginService.getUserByToken("token");
//
//        assertEquals(exampleUser, user);
//    }
//
//    @Test
//    void getUserByGoogleId() {
//        UserEntity exampleUser =
//                new UserEntity(
//                        1L,
//                        "name",
//                        "id",
//                        "picture",
//                        "text",
//                        new HashMap<String, Long>(),
//                        "token", new ArrayList<>()
//                );
//
//        when(userRepository.findByGoogleId("id")).thenReturn(exampleUser);
//
//        UserEntity user = loginService.getUserByGoogleId("id");
//
//        assertEquals(exampleUser, user);
//    }

}
