package demago.gamjappang.domain.comment.infrastructure.adepter.in.web;

import demago.gamjappang.domain.comment.applicationcore.port.in.CreateCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.service.CommentApplicationService;
import demago.gamjappang.domain.comment.domain.model.Comment;
import demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.request.CreateCommentRequest;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import feign.Body;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/comment")
public class CommentController {

    private final CreateCommentUseCase createCommentUseCase;

    public CommentController(CreateCommentUseCase createCommentUseCase) {
        this.createCommentUseCase = createCommentUseCase;
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
}
