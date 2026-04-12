package demago.gamjappang.post.infrastructure.adapter.in.web;

import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.post.applicationcore.port.in.*;
import demago.gamjappang.post.applicationcore.port.in.command.*;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.GetPostResult;
import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.request.CreatePostRequest;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.request.UpdatePostRequest;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.CreatePostResponse;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.GetPostResponse;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.PostPageResponse;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.UpdatePostResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final PostPageUseCase postPageUseCase;
    private final GetPostUseCase getPostUseCase;

    public PostController(
            CreatePostUseCase createPostUseCase,
            UpdatePostUseCase updatePostUseCase,
            DeletePostUseCase deletePostUseCase,
            PostPageUseCase postPageUseCase,
            GetPostUseCase getPostUseCase) {
        this.createPostUseCase = createPostUseCase;
        this.updatePostUseCase = updatePostUseCase;
        this.deletePostUseCase = deletePostUseCase;
        this.postPageUseCase = postPageUseCase;
        this.getPostUseCase = getPostUseCase;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> savePost(
            @Valid @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        CreatePostCommand command = request.toCreatePostCommand(user.getId());
        CreatePostResult result = createPostUseCase.createPost(command);
        return ResponseEntity.ok(CreatePostResponse.from(result));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(
            @Valid @RequestBody UpdatePostRequest request,
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId
    ) {
        UpdatePostCommand command = request.toUpdateCommand(postId, user.getId());
        UpdatePostResult result = updatePostUseCase.updatePost(command);
        return ResponseEntity.ok(UpdatePostResponse.from(result));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId
    ) {
        DeletePostCommand command = new DeletePostCommand(postId, user.getId());
        deletePostUseCase.deletePost(command);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<PostPageResponse> getPostList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tag,
            Pageable pageable
    ) {
        Sort.Order order = pageable.getSort().isSorted()
                ? pageable.getSort().iterator().next()
                : Sort.Order.desc("createdAt");

                String sortBy = order.getProperty();
                String direction = order.getDirection().name();

        PostPageCommand command = new PostPageCommand(
                keyword,
                tag,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortBy,
                direction
        );

        PostPageResult result = postPageUseCase.getPostPage(command);

        return ResponseEntity.ok(PostPageResponse.from(result));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable Long postId) {
        GetPostCommand command = new GetPostCommand(postId);
        GetPostResult result = getPostUseCase.getPost(command);

        return ResponseEntity.ok(GetPostResponse.from(result));
    }
}
