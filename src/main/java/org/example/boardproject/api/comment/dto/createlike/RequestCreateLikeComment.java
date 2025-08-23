package org.example.boardproject.api.comment.dto.createlike;

import lombok.Getter;
import org.example.boardproject.api.comment.enums.CommentType;

@Getter
public class RequestCreateLikeComment {
    private CommentType commentType;
}
