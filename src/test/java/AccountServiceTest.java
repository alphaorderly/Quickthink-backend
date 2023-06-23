import com.example.QuickThink.Card.Dto.HashtagsDto;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.example.QuickThink.Google.Service.AccountService;
import com.example.QuickThink.Google.Service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService accountService;

    @Mock
    UserRepository userRepository;

    @Mock
    LoginService loginService;

    @Test
    void getProfileText() {
        UserEntity exampleUser =
                new UserEntity(
                        1L,
                        "name",
                        "id",
                        "picture",
                        "text",
                        new HashMap<String, Long>(),
                        "token", new ArrayList<>()
                );

        when(loginService.getUserByToken("token")).thenReturn(exampleUser);

        when(userRepository.findByGoogleId(any(String.class))).thenReturn(exampleUser);

        String profileText = accountService.getProfileText("token", "id");

        assertEquals("text", profileText);
    }

    @Test
    void changeProfileText() {
        UserEntity exampleUser =
                new UserEntity(
                        1L,
                        "name",
                        "id",
                        "picture",
                        "text",
                        new HashMap<String, Long>(),
                        "token", new ArrayList<>()
                );

        when(loginService.getUserByToken("token")).thenReturn(exampleUser);

        when(loginService.getUserByGoogleId(any(String.class))).thenReturn(exampleUser);

        accountService.changeProfileText("token", "id", "change");

        assertEquals("change", exampleUser.getProfileText());
    }

    @Test
    void getUserHashtags() {
        UserEntity exampleUser =
                new UserEntity(
                        1L,
                        "name",
                        "id",
                        "picture",
                        "text",
                        new HashMap<String, Long>() {{
                            put("name", 2L);
                            put("nickname", 1L);
                        }},
                        "token", new ArrayList<>()
                );

        when(loginService.getUserByToken("token")).thenReturn(exampleUser);
        when(loginService.getUserByGoogleId(any(String.class))).thenReturn(exampleUser);

        HashtagsDto hashs = accountService.getUserHashTags("token", "id");

        assertEquals(2L, hashs.getHashTags().size());
    }
}
