package org.opensource.vote.api;

import dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
@Tag(name = "Vote", description = "투표 API Document")
public class VoteController implements VoteApi {
    private final VoteFacade voteFacade;

    @Override
    @PostMapping("/add-vote")
    @Operation(summary = "투표 등록 API", description = "사용자가 투표를 등록합니다.")
    public ApiResponse addVote(
            @RequestBody @Valid AddVoteRequestDto addVoteRequestDto) {
        voteFacade.addVote(addVoteRequestDto);
        return ApiResponse.success(VoteSuccessType.ADD_VOTE_SUCCESS);
    }

    @Override
    @PostMapping("/vote")
    @Operation(summary = "투표 API", description = "사용자가 투표합니다.")
    public ApiResponse vote(
            @LoginUserId Long userId,
            @RequestBody @Valid VoteRequestDto voteRequestDto) {
        voteFacade.vote(userId, voteRequestDto);
        return ApiResponse.success(VoteSuccessType.VOTE_SUCCESS);
    }

    @Override
    @GetMapping("/votes")
    @Operation(summary = "투표 리스트 API", description = "등록된 투표를 전부 불러옵니다.")
    public ApiResponse<List<GetVoteResponseDto>> getAllVotes(
            @Parameter(description = "정렬 기준: 'createAt' 또는 'voteCount'")
            @RequestParam String sortBy) {
        List<GetVoteResponseDto> response = voteFacade.findAllVotes(sortBy)
                .stream()
                .map(GetVoteResponseDto::of)
                .toList();
        return ApiResponse.success(VoteSuccessType.GET_ALL_VOTE_SUCCESS, response);
    }

    @Override
    @GetMapping("votes/book")
    @Operation(summary = "책의 투표 리스트 API", description = "책에 등록된 투표를 전부 불러옵니다.")
    public ApiResponse<List<GetVoteResponseDto>> getAllByBookId(
            @RequestParam Long bookId,
            @Parameter(description = "정렬 기준: 'createAt' 또는 'voteCount'") @RequestParam String sortBy) {
        List<GetVoteResponseDto> response = voteFacade.findByBookId(bookId, sortBy)
                .stream()
                .map(GetVoteResponseDto::of)
                .toList();
        return ApiResponse.success(VoteSuccessType.GET_ALL_BOOK_VOTE_SUCCESS, response);
    }

    @Override
    @GetMapping("/user-answered")
    @Operation(summary = "사용자 투표 확인 API", description = "사용자가 찬성인지 반대인지를 보여줍니다.")
    public ApiResponse<Boolean> getVoteAnswered(
            @LoginUserId Long userId,
            @RequestParam Long voteId) {
        return ApiResponse.success(VoteSuccessType.GET_VOTE_ANSWERED_SUCCESS, voteFacade.getVoteAnswered(userId, voteId));
    }
}
