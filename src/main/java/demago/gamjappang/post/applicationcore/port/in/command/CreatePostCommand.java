package demago.gamjappang.post.applicationcore.port.in.command;

import java.util.List;

public record CreatePostCommand(String title, String content, List<String> tags) {
}
