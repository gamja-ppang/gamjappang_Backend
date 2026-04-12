package demago.gamjappang.post.applicationcore.port.in.command;

public record PostPageCommand(
        String keyword,
        String tag,
//        Pageable pageable
        int page, int size, String sortBy, String direction
) {
}
