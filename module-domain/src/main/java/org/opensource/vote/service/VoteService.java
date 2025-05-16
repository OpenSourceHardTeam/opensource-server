package org.opensource.vote.service;

import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.port.out.persistence.BookPersistencePort;
import org.opensource.vote.domain.Vote;
import org.opensource.vote.domain.VoteUser;
import org.opensource.vote.port.in.command.AddVoteCommand;
import org.opensource.vote.port.in.command.VoteCommand;
import org.opensource.vote.port.in.usecase.VoteUsecase;
import org.opensource.vote.port.out.persistence.VotePersistencePort;
import org.opensource.vote.port.out.persistence.VoteUserPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import type.user.BookErrorType;
import type.user.VoteErrorType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService implements VoteUsecase {
    private final VotePersistencePort votePersistencePort;
    private final VoteUserPersistencePort voteUserPersistencePort;
    private final BookPersistencePort bookPersistencePort;

    @Override
    @Transactional
    public void vote(VoteCommand command) {
        Vote vote = votePersistencePort.findByVoteId(command.voteId())
                .orElseThrow(() -> new BadRequestException(VoteErrorType.VOTE_NOT_EXIST));

        if(voteUserPersistencePort.isUserVotedByUserIdAndVoteId(command.userId(), command.voteId())) {
            VoteUser voteUser = voteUserPersistencePort.findUserVotedByUserIdAndVoteId(command.userId(), command.voteId())
                    .orElseThrow(() -> new BadRequestException(VoteErrorType.USER_NOT_VOTED));
            if(voteUser.getAnswered() == command.answered()) {
                throw new BadRequestException(VoteErrorType.VOTE_SAME_ANSWERED);
            }
            if(command.answered()) {
                vote.changeDisagreeToAgree();
            } else {
                vote.changeAgreeToDisagree();
            }
        } else {
            if(command.answered()) {
                vote.increaseAgreeCount();
            } else {
                vote.increaseDisagreeCount();
            }
        }

        votePersistencePort.vote(vote);
    }

    @Override
    @Transactional
    public void addVote(AddVoteCommand command) {
        Book book = bookPersistencePort.getBookById(command.bookId())
                .orElseThrow(() -> new BadRequestException(BookErrorType.GET_BOOK_INFO_ERROR));

        Vote vote = Vote.builder()
                .book(book)
                .title(command.title())
                .content(command.content())
                .agreeCount(0)
                .disagreeCount(0)
                .voteCount(0)
                .build();

        votePersistencePort.addVote(vote);
    }

    @Override
    public List<Vote> findByBookIdOrderByCreateAt(Long bookId) {
        return votePersistencePort.findByBookIdOrderByCreateAt(bookId);
    }

    @Override
    public List<Vote> findByBookIdOrderByVoteCount(Long bookId) {
        return votePersistencePort.findByBookIdOrderByVoteCount(bookId);
    }

    @Override
    public List<Vote> findAllByCreatedAt() {
        return votePersistencePort.findAllByCreatedAt();
    }

    @Override
    public List<Vote> findAllByVoteCount() {
        return votePersistencePort.findAllByVoteCount();
    }
}
