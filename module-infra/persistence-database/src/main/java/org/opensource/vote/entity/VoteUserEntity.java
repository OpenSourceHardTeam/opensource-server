package org.opensource.vote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.basetime.BaseTimeEntity;
import org.opensource.user.entity.UserEntity;
import org.opensource.vote.domain.VoteUser;

@Entity
@Getter
@NoArgsConstructor
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_id"})})
public class VoteUserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_user_id")
    private Long voteUserId;

    @Column(name = "answered")
    private Boolean answered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    private VoteEntity vote;

    @Builder
    public VoteUserEntity(
            Long voteUserId,
            Boolean answered,
            UserEntity user,
            VoteEntity vote) {
        this.voteUserId = voteUserId;
        this.answered = answered;
        this.user = user;
        this.vote = vote;
    }

    public static VoteUserEntity from(VoteUser voteUser) {
        return builder()
                .voteUserId(voteUser.getVoteUserId())
                .answered(voteUser.getAnswered())
                .user(UserEntity.from(voteUser.getUser()))
                .vote(VoteEntity.from(voteUser.getVote()))
                .build();
    }

    public VoteUser toModel() {
        return VoteUser.builder()
                .voteUserId(this.voteUserId)
                .answered(this.answered)
                .user(this.user.toModel())
                .vote(this.vote.toModel())
                .build();
    }
}
