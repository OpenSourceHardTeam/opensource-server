package org.opensource.vote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opensource.vote.domain.Vote;

@Getter
@AllArgsConstructor
public class GetVoteResponseDto {
    private Long voteId;
    private Long bookId;
    private Long userId;
    private String bookImageUrl;
    private String title;
    private String content;
    private Integer agreeCount;
    private Integer disagreeCount;
    private Integer voteCount;

    public static GetVoteResponseDto of(Vote vote) {
        return new GetVoteResponseDto(
                vote.getId(),
                vote.getBook().getBookId(),
                vote.getUser().getId(),
                vote.getBook().getBookImageUrl(),
                vote.getTitle(),
                vote.getContent(),
                vote.getAgreeCount(),
                vote.getDisagreeCount(),
                vote.getVoteCount());
    }
}
