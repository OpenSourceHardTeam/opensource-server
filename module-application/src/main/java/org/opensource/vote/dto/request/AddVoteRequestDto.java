package org.opensource.vote.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddVoteRequestDto {
    @NotNull
    private Long bookId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
