package org.opensource.vote.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequestDto {
    @NotNull
    private Long voteId;
    @NotNull
    private Boolean answered;
}
