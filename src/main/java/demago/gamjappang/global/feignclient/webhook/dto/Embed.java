package demago.gamjappang.global.feignclient.webhook.dto;

import java.util.List;

public record Embed(
        String title,
        Integer color,
        List<Field> fields,
        Footer footer
) {
}
