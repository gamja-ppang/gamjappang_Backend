package demago.gamjappang.domain.comment.infrastructure.adepter.in.web;

import demago.gamjappang.domain.comment.applicationcore.port.in.CreateCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.service.CommentApplicationService;
import demago.gamjappang.domain.comment.domain.model.Comment;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/comment")
public class CommentController {

}
