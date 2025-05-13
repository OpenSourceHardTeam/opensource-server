package org.opensource.vote.api;

import dto.response.ApiResponse;
import org.opensource.vote.dto.request.AddVoteRequestDto;
import org.opensource.vote.dto.request.VoteRequestDto;
import org.opensource.vote.dto.response.GetVoteResponseDto;

import java.util.List;

public interface VoteApi {
    ApiResponse addVote(AddVoteRequestDto addVoteRequestDto);

    ApiResponse vote(Long userId, VoteRequestDto voteRequestDto);

    ApiResponse<List<GetVoteResponseDto>> getAllVotes(String sortBy);

    ApiResponse<List<GetVoteResponseDto>> getAllByBookId(Long bookId, String sortBy);

    ApiResponse<Boolean> getVoteAnswered(Long userId, Long voteId);
}
