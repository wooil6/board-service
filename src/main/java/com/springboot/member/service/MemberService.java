package com.springboot.member.service;

import com.springboot.board.entity.Board;
import com.springboot.board.service.BoardService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private BoardService boardService;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    @Lazy
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

//    @PostConstruct
//    public void init() {
//        this.boardService = boardService;
//    }

    // 멤버 등록
    public Member createMember(Member member) {
        // 존재하는 멤버인지 확인
        verifyExistsEmail(member.getEmail());

        Member saveMember = memberRepository.save(member);

        return saveMember;
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }


    // 멤버 탈퇴
    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
       // boardService.deactivateBoardsByMember(memberId);
        findMember.getBoards().stream()
                        .forEach(board -> {
                            board.setQuestionStatus(Board.QuestionStatus.QUESTION_DEACTIVED);
                            board.setModifiedAt(LocalDateTime.now());
                        });
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findMember;
    }
}