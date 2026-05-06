package demago.gamjappang.domain.comment.infrastructure.adepter.in.web;

import demago.gamjappang.domain.comment.applicationcore.port.in.CommentListUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.CreateCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CommentListCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.result.CommentListResult;
import demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.request.CreateCommentRequest;
import demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.response.CommentListResponse;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/comment")
public class CommentController {

    private final CreateCommentUseCase createCommentUseCase;
    private final CommentListUseCase commentListUseCase;

    public CommentController(
            CreateCommentUseCase createCommentUseCase,
            CommentListUseCase commentListUseCase
    ) {
        this.createCommentUseCase = createCommentUseCase;
        this.commentListUseCase = commentListUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        CreateCommentCommand command = request.toCreateCommentCommand(user.getId());

        createCommentUseCase.createComment(command);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommentListResponse> getCommentList(@PathVariable Long postId) {
        CommentListResult result = commentListUseCase.getCommentList(new CommentListCommand(postId));

        return ResponseEntity.ok(CommentListResponse.from(result));
    }
}
