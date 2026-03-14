package demago.gamjappang.post.infrastructure.adapter.in.web;

import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.post.applicationcore.port.in.CreatePostUseCase;
import demago.gamjappang.post.applicationcore.port.in.command.CreatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.request.CreatePostRequest;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.CreatePostResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final CreatePostUseCase createPostUseCase;

    public PostController(
            CreatePostUseCase createPostUseCase
    ) {
        this.createPostUseCase = createPostUseCase;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> save(@Valid @RequestBody CreatePostRequest request, @AuthenticationPrincipal UserPrincipal user) {
        CreatePostCommand command = request.toCreatePostCommand(user.getId());
        CreatePostResult result = createPostUseCase.createPost(command);
        return ResponseEntity.ok(CreatePostResponse.from(result));
    }

}
