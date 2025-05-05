package org.opensource.vote.service;

import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.User;
import org.opensource.user.port.out.persistence.UserPersistencePort;
import org.opensource.vote.domain.Vote;
import org.opensource.vote.domain.VoteUser;
import org.opensource.vote.port.in.command.VoteCommand;
import org.opensource.vote.port.in.usecase.VoteUserUsecase;
import org.opensource.vote.port.out.persistence.VotePersistencePort;
import org.opensource.vote.port.out.persistence.VoteUserPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import type.user.UserErrorType;
import type.user.VoteErrorType;

@Service
@RequiredArgsConstructor
public class VoteUserService implements VoteUserUsecase {
    private final UserPersistencePort userPersistencePort;
    private final VotePersistencePort votePersistencePort;
    private final VoteUserPersistencePort voteUserPersistencePort;

    @Override
    @Transactional
    public void voteUser(VoteCommand command) {
        User user = userPersistencePort.findById(command.userId())
                .orElseThrow(() -> new BadRequestException(UserErrorType.USER_NOT_EXIST));

        Vote vote = votePersistencePort.findByVoteId(command.voteId())
                .orElseThrow(() -> new BadRequestException(VoteErrorType.VOTE_NOT_EXIST));

        if (voteUserPersistencePort.isUserVotedByUserIdAndVoteId(user.getId(), command.voteId())) {
            VoteUser voteUser = voteUserPersistencePort.findUserVotedByUserIdAndVoteId(command.userId(), command.voteId())
                    .orElseThrow(() -> new BadRequestException(VoteErrorType.USER_NOT_VOTED));

            voteUser.setAnswered(command.answered());
            voteUserPersistencePort.changeAnswer(voteUser);
        } else {
            VoteUser voteUser = VoteUser.builder()
                    .answered(command.answered())
                    .vote(vote)
                    .user(user)
                    .build();

            voteUserPersistencePort.addVoteUser(voteUser);
        }
    }

    @Override
    @Transactional
    public Boolean userAnswered(Long userId, Long voteId) {
        VoteUser voteUser = voteUserPersistencePort.findUserVotedByUserIdAndVoteId(userId, voteId)
                .orElseThrow(() -> new BadRequestException(VoteErrorType.USER_NOT_VOTED));
        return voteUser.getAnswered();
    }
}
