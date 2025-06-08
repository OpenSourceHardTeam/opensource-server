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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteFacade {
    private final VoteUsecase voteUsecase;
    private final VoteUserUsecase voteUserUsecase;

    public void addVote(Long userId, AddVoteRequestDto requestDto) {
        voteUsecase.addVote(VoteMapper.toAddVoteCommand(requestDto.getBookId(), userId, requestDto.getTitle(), requestDto.getContent()));
    }

    public void vote(Long userId, VoteRequestDto requestDto) {
        VoteCommand voteCommand = VoteMapper.toVoteCommand(requestDto.getVoteId(), userId, requestDto.getAnswered());
        voteUsecase.vote(voteCommand);
        voteUserUsecase.voteUser(voteCommand);
    }

    public List<Vote> findAllVotes(String sortBy) {
        if(Objects.equals(sortBy, "createAt")) {
            return voteUsecase.findAllByCreatedAt();
        } else {
            return voteUsecase.findAllByVoteCount();
        }
    }
    public List<Vote> findByBookId(Long bookId, String sortBy) {
        if(Objects.equals(sortBy, "createAt")) {
            return voteUsecase.findByBookIdOrderByCreateAt(bookId);
        } else {
            return voteUsecase.findByBookIdOrderByVoteCount(bookId);
        }
    }

    public Boolean getVoteAnswered(Long userId, Long voteId) {
        return voteUserUsecase.userAnswered(userId, voteId);
    }

    public void deleteVote(Long userId, Long voteId) {
        voteUserUsecase.deleteVoteUserByVoteId(voteId);
        voteUsecase.deleteVoteByVoteId(userId, voteId);
    }
}
