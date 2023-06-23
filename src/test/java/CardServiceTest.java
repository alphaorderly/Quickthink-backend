import com.example.QuickThink.Card.Dto.*;
import com.example.QuickThink.Card.Entity.CardEntity;
import com.example.QuickThink.Card.Repository.CardRepository;
import com.example.QuickThink.Card.Service.CardService;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.example.QuickThink.Google.Service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    CardService cardService;
    @Mock
    CardRepository cardRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    LoginService loginService;

    @Test
    void postCard() {
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

        CardWriteRequestDto card = new CardWriteRequestDto(
                "example Title",
                "example Content",
                new ArrayList<String>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                0L
        );

        when(cardRepository.save(Mockito.any(CardEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        CardWriteResponseDto response = cardService.postCard("token", card);

        assertNotNull(response);
    }

    @Test
    void editCard() {
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

        CardEntity exampleCard = CardEntity
                        .builder()
                        .title("Example Title")
                        .content("Example Content")
                        .hashTags(new HashSet<String>())
                        .writtenDate(LocalDateTime.now())
                        .latestReviewDate(LocalDateTime.now())
                        .reviewCount(0L)
                        .user(exampleUser)
                        .build();

        when(cardRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(exampleCard));

        cardService.editCard("token", 1L, new CardEditRequestDto("Example", "Content", new ArrayList<String>(), LocalDateTime.now()));

        assertNotNull(exampleCard);
        assertEquals("Content", exampleCard.getContent());
        assertEquals("Example", exampleCard.getTitle());
    }

    @Test
    void getCards() {
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

        UserEntity logUser =
                new UserEntity(
                        2L,
                        "name_",
                        "id_",
                        "picture_",
                        "text_",
                        new HashMap<String, Long>(),
                        "token_", new ArrayList<>()
                );

        lenient().doReturn(exampleUser).when(loginService).getUserByGoogleId("id");
        lenient().doReturn(logUser).when(loginService).getUserByGoogleId("id_");

        CardEntity exampleCard = CardEntity
                .builder()
                .title("Example Title")
                .content("Example Content")
                .hashTags(new HashSet<String>())
                .writtenDate(LocalDateTime.now())
                .latestReviewDate(LocalDateTime.now())
                .reviewCount(0L)
                .user(exampleUser)
                .build();

        when(cardRepository.findAllByUser(any(UserEntity.class))).thenReturn(new ArrayList<CardEntity>(Collections.singletonList(exampleCard)));

        Object obj = cardService.getCards("token", "id", new HashtagsDto(List.of()));

        assertTrue(obj instanceof MyCardListResponseDto);

        obj = cardService.getCards("token", "id_", new HashtagsDto(List.of()));

        assertTrue(obj instanceof OtherCardListResponseDto);
    }

    @Test
    void getCard() {
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

        CardEntity exampleCard = CardEntity
                .builder()
                .title("Example Title")
                .content("Example Content")
                .hashTags(new HashSet<String>())
                .writtenDate(LocalDateTime.now())
                .latestReviewDate(LocalDateTime.now())
                .reviewCount(0L)
                .user(exampleUser)
                .build();

        when(loginService.getUserByToken("token")).thenReturn(exampleUser);

        when(cardRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(exampleCard));

        Object obj = cardService.getCard("token", 0L);

        assertTrue(obj instanceof MyCardResponseDto);
    }

    @Test
    void reviewCard() {
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

        CardEntity exampleCard = CardEntity
                .builder()
                .title("Example Title")
                .content("Example Content")
                .hashTags(new HashSet<String>())
                .writtenDate(LocalDateTime.now())
                .latestReviewDate(LocalDateTime.now())
                .reviewCount(0L)
                .user(exampleUser)
                .build();

        when(loginService.getUserByToken("token")).thenReturn(exampleUser);

        when(cardRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(exampleCard));

        cardService.reviewCard("token", 0L);

        assert exampleCard != null;
        assertEquals(1L, exampleCard.getReviewCount());
    }

}
