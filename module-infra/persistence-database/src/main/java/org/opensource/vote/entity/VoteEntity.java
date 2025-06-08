package org.opensource.vote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.opensource.basetime.BaseTimeEntity;
import org.opensource.book.entity.BookEntity;
import org.opensource.user.entity.UserEntity;
import org.opensource.vote.domain.Vote;

@Entity
@NoArgsConstructor
public class VoteEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "agree_count")
    private int agreeCount;

    @Column(name = "disagree_count")
    private int disagreeCount;

    @Column(name = "vote_count")
    private int voteCount;

    @Builder
    public VoteEntity(
            Long voteId,
            BookEntity book,
            UserEntity user,
            String title,
            String content,
            int agreeCount,
            int disagreeCount,
            int voteCount) {
        this.voteId = voteId;
        this.book = book;
        this.user = user;
        this.title = title;
        this.content = content;
        this.agreeCount = agreeCount;
        this.disagreeCount = disagreeCount;
        this.voteCount = voteCount;
    }

    public static VoteEntity from(Vote vote) {
        return builder()
                .voteId(vote.getId())
                .book(BookEntity.from(vote.getBook()))
                .user(UserEntity.from(vote.getUser()))
                .title(vote.getTitle())
                .content(vote.getTitle())
                .content(vote.getContent())
                .agreeCount(vote.getAgreeCount())
                .disagreeCount(vote.getDisagreeCount())
                .voteCount(vote.getVoteCount())
                .build();
    }

    public Vote toModel() {
        return Vote.builder()
                .id(this.voteId)
                .book(this.book.toModel())
                .user(this.user.toModel())
                .title(this.title)
                .content(this.content)
                .agreeCount(this.agreeCount)
                .disagreeCount(this.disagreeCount)
                .voteCount(this.voteCount)
                .build();
    }
}
