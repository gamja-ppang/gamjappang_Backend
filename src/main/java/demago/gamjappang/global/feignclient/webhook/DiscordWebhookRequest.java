package demago.gamjappang.global.feignclient.webhook;

import demago.gamjappang.global.feignclient.webhook.dto.Embed;

import java.util.List;

public record DiscordWebhookRequest(String content, List<Embed> embeds) {
}
