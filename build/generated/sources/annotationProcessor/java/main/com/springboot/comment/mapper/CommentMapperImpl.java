package com.springboot.comment.mapper;

import com.springboot.board.entity.Board;
import com.springboot.comment.dto.CommentDto;
import com.springboot.comment.entity.Comment;
import com.springboot.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T17:04:42+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setMember( postToMember( requestBody ) );
        comment.setBoard( postToBoard( requestBody ) );
        comment.setContent( requestBody.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(CommentDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( requestBody.getCommentId() );
        comment.setContent( requestBody.getContent() );

        return comment;
    }

    @Override
    public CommentDto.Response commentToCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response response = new CommentDto.Response();

        response.setMemberId( commentMemberMemberId( comment ) );
        response.setBoardId( commentBoardBoardId( comment ) );
        if ( comment.getCommentId() != null ) {
            response.setCommentId( comment.getCommentId() );
        }
        response.setContent( comment.getContent() );

        return response;
    }

    @Override
    public List<CommentDto.Response> commentsToCommentResponses(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto.Response> list = new ArrayList<CommentDto.Response>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentResponse( comment ) );
        }

        return list;
    }

    protected Member postToMember(CommentDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( post.getMemberId() );

        return member;
    }

    protected Board postToBoard(CommentDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( post.getBoardId() );

        return board;
    }

    private long commentMemberMemberId(Comment comment) {
        if ( comment == null ) {
            return 0L;
        }
        Member member = comment.getMember();
        if ( member == null ) {
            return 0L;
        }
        long memberId = member.getMemberId();
        return memberId;
    }

    private long commentBoardBoardId(Comment comment) {
        if ( comment == null ) {
            return 0L;
        }
        Board board = comment.getBoard();
        if ( board == null ) {
            return 0L;
        }
        long boardId = board.getBoardId();
        return boardId;
    }
}
