package org.opensource.vote.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

@Getter
@Builder
public class Vote {
    private Long id;
    private Book book;
    private String title;
    private String content;
    private Integer agreeCount;
    private Integer disagreeCount;
    private Integer voteCount;

    public void increaseAgreeCount() {
        agreeCount++;
        voteCount++;
    }

    public void increaseDisagreeCount() {
        disagreeCount++;
        voteCount++;
    }

    public void changeAgreeToDisagree() {
        agreeCount--;
        disagreeCount++;
    }

    public void changeDisagreeToAgree() {
        disagreeCount--;
        agreeCount++;
    }
}
