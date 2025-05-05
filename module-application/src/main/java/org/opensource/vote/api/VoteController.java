package org.opensource.vote.api;

import dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.opensource.user.annotation.LoginUserId;
import org.opensource.vote.dto.request.AddVoteRequestDto;
import org.opensource.vote.dto.request.VoteRequestDto;
import org.opensource.vote.dto.response.GetVoteResponseDto;
import org.opensource.vote.facade.VoteFacade;
import org.springframework.web.bind.annotation.*;
import type.user.VoteSuccessType;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController implements VoteApi {
    private final VoteFacade voteFacade;

    @Override
    @PostMapping("/add-vote")
    public ApiResponse addVote(
            @RequestBody @Valid AddVoteRequestDto addVoteRequestDto) {
        voteFacade.addVote(addVoteRequestDto);
        return ApiResponse.success(VoteSuccessType.ADD_VOTE_SUCCESS);
    }

    @Override
    @PostMapping("/vote")
    public ApiResponse vote(
            @LoginUserId Long userId,
            @RequestBody @Valid VoteRequestDto voteRequestDto) {
        voteFacade.vote(userId, voteRequestDto);
        return ApiResponse.success(VoteSuccessType.VOTE_SUCCESS);
    }

    @Override
    @GetMapping("/all-votes")
    public ApiResponse<List<GetVoteResponseDto>> getAllVotes(
            @RequestParam Long bookId) {
        List<GetVoteResponseDto> response = voteFacade.getAllVotes(bookId)
                .stream()
                .map(GetVoteResponseDto::of)
                .toList();
        return ApiResponse.success(VoteSuccessType.GET_ALL_BOOK_VOTE_SUCCESS, response);
    }

    @Override
    @GetMapping("/user-answered")
    public ApiResponse<Boolean> getVoteAnswered(
            @LoginUserId Long userId,
            @RequestParam Long voteId) {
        return ApiResponse.success(VoteSuccessType.GET_VOTE_ANSWERED_SUCCESS, voteFacade.getVoteAnswered(userId, voteId));
    }
}
