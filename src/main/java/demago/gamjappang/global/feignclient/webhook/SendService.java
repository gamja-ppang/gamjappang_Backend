package demago.gamjappang.global.feignclient.webhook;

import demago.gamjappang.global.feignclient.webhook.dto.Embed;
import demago.gamjappang.global.feignclient.webhook.dto.Field;
import demago.gamjappang.global.feignclient.webhook.dto.Footer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendService {

    private final DiscordWebhookClient discordWebhookClient;

    @Async("discordAsyncExecutor")
    public void sendDiscordAlert(Exception e, String method, String uri) {
        try {
            DiscordWebhookRequest request = buildMessage(e, method, uri);
            discordWebhookClient.send(request);
        } catch (Exception ex) {
            log.error("Failed to send discord webhook", ex);
        }
    }

    public DiscordWebhookRequest buildMessage(Exception e, String method, String uri) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String exceptionName = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage() != null ? e.getMessage() : "(no message)";

        if (exceptionMessage.length() > 1000) {
            exceptionMessage = exceptionMessage.substring(0, 990) + "\n... (중략)";
        }

        Embed embed = new Embed(
                "🚨 Internal Server Error",
                15158332,
                List.of(
                        new Field("Method", "`" + method + "`", true),
                        new Field("URI", "`" + uri + "`", true),
                        new Field("Exception", "`" + exceptionName + "`", false),
                        new Field("Message", "```text\n" + exceptionMessage + "\n```", false)
                ),
                new Footer(timestamp)
        );

        return new DiscordWebhookRequest(null, List.of(embed));
    }
}