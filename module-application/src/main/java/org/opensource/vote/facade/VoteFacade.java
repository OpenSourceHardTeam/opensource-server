package org.opensource.vote.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.vote.domain.Vote;
import org.opensource.vote.dto.request.AddVoteRequestDto;
import org.opensource.vote.dto.request.VoteRequestDto;
import org.opensource.vote.mapper.VoteMapper;
import org.opensource.vote.port.in.command.VoteCommand;
import org.opensource.vote.port.in.usecase.VoteUsecase;
import org.opensource.vote.port.in.usecase.VoteUserUsecase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteFacade {
    private final VoteUsecase voteUsecase;
    private final VoteUserUsecase voteUserUsecase;

    public void addVote(AddVoteRequestDto requestDto) {
        voteUsecase.addVote(VoteMapper.toAddVoteCommand(requestDto.getBookId(), requestDto.getTitle(), requestDto.getContent()));
    }

    public void vote(Long userId, VoteRequestDto requestDto) {
        VoteCommand voteCommand = VoteMapper.toVoteCommand(requestDto.getVoteId(), userId, requestDto.getAnswered());
        voteUsecase.vote(voteCommand);
        voteUserUsecase.voteUser(voteCommand);
    }

    public List<Vote> getAllVotes(Long bookId) {
        return voteUsecase.findAllVoteByBookId(bookId);
    }

    public Boolean getVoteAnswered(Long userId, Long voteId) {
        return voteUserUsecase.userAnswered(userId, voteId);
    }
}
