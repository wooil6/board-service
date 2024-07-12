package com.springboot.board.mapper;

import com.springboot.board.dto.BoardDto;
import com.springboot.board.entity.Board;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T17:38:48+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Board boardPostDtoToBoard(BoardDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Board board = new Board();

        board.setMember( postToMember( requestBody ) );
        board.setTitle( requestBody.getTitle() );
        board.setContent( requestBody.getContent() );
        board.setBoardStatus( requestBody.getBoardStatus() );

        return board;
    }

    @Override
    public Board boardPatchDtoToBoard(BoardDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( requestBody.getBoardId() );
        board.setTitle( requestBody.getTitle() );
        board.setContent( requestBody.getContent() );
        board.setQuestionStatus( requestBody.getQuestionStatus() );
        board.setBoardStatus( requestBody.getBoardStatus() );

        return board;
    }

    @Override
    public BoardDto.Response boardToBoardResponseDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.Response response = new BoardDto.Response();

        response.setMemberId( boardMemberMemberId( board ) );
        response.setComments( commentMapper.commentsToCommentResponses( board.getComments() ) );
        response.setBoardId( board.getBoardId() );
        response.setTitle( board.getTitle() );
        response.setContent( board.getContent() );
        response.setView( board.getView() );
        response.setLikeCount( board.getLikeCount() );
        response.setBoardStatus( board.getBoardStatus() );
        response.setQuestionStatus( board.getQuestionStatus() );

        return response;
    }

    @Override
    public List<BoardDto.Response> boardsToBoardResponseDtos(List<Board> boards) {
        if ( boards == null ) {
            return null;
        }

        List<BoardDto.Response> list = new ArrayList<BoardDto.Response>( boards.size() );
        for ( Board board : boards ) {
            list.add( boardToBoardResponseDto( board ) );
        }

        return list;
    }

    @Override
    public List<BoardDto.Response> boardsToBoardResponseDtosIdAsc(List<Board> boards) {
        if ( boards == null ) {
            return null;
        }

        List<BoardDto.Response> list = new ArrayList<BoardDto.Response>( boards.size() );
        for ( Board board : boards ) {
            list.add( boardToBoardResponseDto( board ) );
        }

        return list;
    }

    protected Member postToMember(BoardDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( post.getMemberId() );

        return member;
    }

    private long boardMemberMemberId(Board board) {
        if ( board == null ) {
            return 0L;
        }
        Member member = board.getMember();
        if ( member == null ) {
            return 0L;
        }
        long memberId = member.getMemberId();
        return memberId;
    }
}
