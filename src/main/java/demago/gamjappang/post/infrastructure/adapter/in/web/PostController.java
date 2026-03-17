package demago.gamjappang.post.infrastructure.adapter.in.web;

import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.post.applicationcore.port.in.CreatePostUseCase;
import demago.gamjappang.post.applicationcore.port.in.UpdatePostUseCase;
import demago.gamjappang.post.applicationcore.port.in.command.CreatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.command.UpdatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.request.CreatePostRequest;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.request.UpdatePostRequset;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.CreatePostResponse;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.UpdatePostResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;

    public PostController(
            CreatePostUseCase createPostUseCase,
            UpdatePostUseCase updatePostUseCase) {
        this.createPostUseCase = createPostUseCase;
        this.updatePostUseCase = updatePostUseCase;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> savePost(@Valid @RequestBody CreatePostRequest request, @AuthenticationPrincipal UserPrincipal user) {
        CreatePostCommand command = request.toCreatePostCommand(user.getId());
        CreatePostResult result = createPostUseCase.createPost(command);
        return ResponseEntity.ok(CreatePostResponse.from(result));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@Valid @RequestBody UpdatePostRequset requset, @AuthenticationPrincipal UserPrincipal user, @PathVariable Long postId) {
        UpdatePostCommand command = requset.toUpdateCommand(user.getId(), postId);
        UpdatePostResult result = updatePostUseCase.updatePost(command);
        return ResponseEntity.ok(UpdatePostResponse.from(result));
    }

}
