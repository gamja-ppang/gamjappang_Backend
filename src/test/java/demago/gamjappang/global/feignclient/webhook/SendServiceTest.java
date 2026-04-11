package demago.gamjappang.global.feignclient.webhook;

import demago.gamjappang.global.feignclient.webhook.dto.Embed;
import demago.gamjappang.global.feignclient.webhook.dto.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class SendServiceTest {

    @Autowired
    private SendService sendService;

    @MockitoBean
    private DiscordWebhookClient discordWebhookClient;

    @Test
    void sendDiscordAlert_success() throws InterruptedException {
        sendService.sendDiscordAlert(new Exception("테스트 예외"), "GET", "/test");

        Thread.sleep(500);

        verify(discordWebhookClient).send(any(DiscordWebhookRequest.class));
    }

    @Test
    void buildMessage() {
        Exception e = new RuntimeException("이것은 테스트 에러 메시지입니다. ".repeat(100));

        DiscordWebhookRequest request = sendService.buildMessage(e, "POST", "/api/test");

        assertThat(request).isNotNull();
        assertThat(request.embeds()).hasSize(1);

        Embed embed = request.embeds().get(0);
        assertThat(embed.title()).contains("Internal Server Error");
        assertThat(embed.fields()).isNotNull();

        List<Field> fields = embed.fields();

        assertThat(fields).extracting(Field::name)
                .contains("Method", "URI", "Exception", "Message");

        assertThat(fields).extracting(Field::value)
                .anyMatch(value -> value.contains("POST"))
                .anyMatch(value -> value.contains("/api/test"));
    }

    @Test
    void buildMessage_shouldTruncate_whenExceedsLimit() {
        String longErrorMessage = "A".repeat(3000);
        Exception e = new RuntimeException(longErrorMessage);

        DiscordWebhookRequest request = sendService.buildMessage(e, "POST", "/api/test-limit");

        assertThat(request).isNotNull();
        assertThat(request.embeds()).hasSize(1);

        Embed embed = request.embeds().get(0);
        List<Field> fields = embed.fields();

        Field messageField = fields.stream()
                .filter(field -> field.name().equals("Message"))
                .findFirst()
                .orElseThrow();

        Field methodField = fields.stream()
                .filter(field -> field.name().equals("Method"))
                .findFirst()
                .orElseThrow();

        Field uriField = fields.stream()
                .filter(field -> field.name().equals("URI"))
                .findFirst()
                .orElseThrow();

        assertThat(methodField.value()).contains("POST");
        assertThat(uriField.value()).contains("/api/test-limit");
        assertThat(messageField.value()).contains("... (중략)");

        assertDoesNotThrow(() -> {
            sendService.sendDiscordAlert(e, "POST", "/api/test-limit");
        });
    }

    @Test
    void asyncTest() {
        Exception e = new RuntimeException("비동기 테스트 에러");

        sendService.sendDiscordAlert(e, "GET", "/api/async-test");

        verify(discordWebhookClient, timeout(2000)).send(any(DiscordWebhookRequest.class));
    }
}