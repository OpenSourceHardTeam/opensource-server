package org.opensource.vote.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.opensource.user.domain.User;

@Getter
@Setter
@Builder
public class VoteUser {
    private Long voteUserId;
    private Boolean answered;
    private User user;
    private Vote vote;
}
