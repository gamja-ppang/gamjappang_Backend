package demago.gamjappang.post.applicationcore.port.in.command;

import org.springframework.data.domain.Pageable;

public record PostPageCommand(
        String keyword,
        String tag,
        Pageable pageable
) {
}
