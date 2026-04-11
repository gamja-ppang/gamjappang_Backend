package demago.gamjappang.global.feignclient.webhook.dto;

public record Field(
        String name,
        String value,
        boolean inline
) {
}