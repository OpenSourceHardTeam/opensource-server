package org.opensource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensource.book.domain.Book;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.opensource.chatroom.service.ChatroomService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatroomServiceTest {

    @Mock
    private ChatroomPersistencePort chatroomPersistencePort;

    @InjectMocks
    private ChatroomService chatroomService;

    @Captor
    private ArgumentCaptor<Chatroom> chatroomCaptor;

    @Nested
    @DisplayName("채팅방 조회 테스트")
    class FindChatroomTests {

        @Test
        @DisplayName("성공 시나리오: ID로 채팅방 조회")
        void shouldFindChatroomById() {
            // Given
            Long chatroomId = 1L;
            Book book = Book.builder().bookId(101L).build();
            Chatroom chatroom = Chatroom.builder()
                    .id(chatroomId)
                    .topic("테스트 채팅방")
                    .book(book)
                    .build();

            when(chatroomPersistencePort.findById(chatroomId))
                    .thenReturn(Optional.of(chatroom));

            // When
            Chatroom result = chatroomService.findById(chatroomId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(chatroomId);
            assertThat(result.getTopic()).isEqualTo("테스트 채팅방");
            assertThat(result.getBook().getBookId()).isEqualTo(101L);

            verify(chatroomPersistencePort).findById(chatroomId);
        }

        @Test
        @DisplayName("예외 시나리오: 존재하지 않는 채팅방 ID로 조회")
        void shouldThrowExceptionWhenChatroomNotFound() {
            // Given
            Long chatroomId = 999L;
            when(chatroomPersistencePort.findById(chatroomId))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThatExceptionOfType(NoSuchElementException.class)
                    .isThrownBy(() -> chatroomService.findById(chatroomId))
                    .withMessageContaining("Chatroom with id " + chatroomId + " not found");

            verify(chatroomPersistencePort).findById(chatroomId);
        }
    }

    @Nested
    @DisplayName("채팅방 생성 테스트")
    class CreateChatroomTests {

        @Test
        @DisplayName("성공 시나리오: 새 채팅방 생성")
        void shouldCreateNewChatroom() {
            // Given
            Long bookId = 101L;
            Long bookArgumentId = 201L;
            String topic = "새 채팅방 주제";
            CreateChatroomCommand command = new CreateChatroomCommand(topic, bookId, bookArgumentId);

            when(chatroomPersistencePort.save(any(Chatroom.class)))
                    .thenReturn(1L);

            // When
            Long result = chatroomService.createBy(command);

            // Then
            assertThat(result).isEqualTo(1L);

            verify(chatroomPersistencePort).save(chatroomCaptor.capture());

            Chatroom savedChatroom = chatroomCaptor.getValue();
            assertThat(savedChatroom.getTopic()).isEqualTo(topic);
            assertThat(savedChatroom.getBook().getBookId()).isEqualTo(bookId);
            // 여기서 bookArgumentId는 서비스 내부 구현에 따라 검증 방식이 달라질 수 있음
            // 현재 서비스 코드에는 bookArgumentId 활용 로직이 보이지 않음
        }

        @Test
        @DisplayName("예외 시나리오: 저장 중 오류 발생")
        void shouldPropagateExceptionWhenSaveFails() {
            // Given
            CreateChatroomCommand command = new CreateChatroomCommand("주제", 101L, 201L);

            when(chatroomPersistencePort.save(any(Chatroom.class)))
                    .thenThrow(new RuntimeException("저장 실패"));

            // When & Then
            assertThatThrownBy(() -> chatroomService.createBy(command))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("저장 실패");

            verify(chatroomPersistencePort).save(any(Chatroom.class));
        }

        @Test
        @DisplayName("경계 테스트: 제목이 긴 채팅방 생성")
        void shouldCreateChatroomWithLongTopic() {
            // Given
            String longTopic = "A".repeat(255); // 최대 길이 가정
            CreateChatroomCommand command = new CreateChatroomCommand(longTopic, 101L, 201L);

            when(chatroomPersistencePort.save(any(Chatroom.class)))
                    .thenReturn(1L);

            // When
            Long result = chatroomService.createBy(command);

            // Then
            assertThat(result).isEqualTo(1L);

            verify(chatroomPersistencePort).save(chatroomCaptor.capture());

            Chatroom savedChatroom = chatroomCaptor.getValue();
            assertThat(savedChatroom.getTopic()).isEqualTo(longTopic);
        }

        @Test
        @DisplayName("경계 테스트: bookArgumentId가 null인 경우")
        void shouldCreateChatroomWithNullBookArgumentId() {
            // Given
            CreateChatroomCommand command = new CreateChatroomCommand("주제", 101L, null);

            when(chatroomPersistencePort.save(any(Chatroom.class)))
                    .thenReturn(1L);

            // When
            Long result = chatroomService.createBy(command);

            // Then
            assertThat(result).isEqualTo(1L);

            verify(chatroomPersistencePort).save(any(Chatroom.class));
        }
    }

    @Nested
    @DisplayName("채팅방 삭제 테스트")
    class DeleteChatroomTests {

        @Test
        @DisplayName("성공 시나리오: 채팅방 삭제")
        void shouldDeleteChatroom() {
            // Given
            Long chatroomId = 1L;
            doNothing().when(chatroomPersistencePort).deleteById(chatroomId);

            // When
            chatroomService.deleteById(chatroomId);

            // Then
            verify(chatroomPersistencePort).deleteById(chatroomId);
        }

        @Test
        @DisplayName("예외 시나리오: 삭제 중 오류 발생")
        void shouldPropagateExceptionWhenDeleteFails() {
            // Given
            Long chatroomId = 1L;
            doThrow(new RuntimeException("삭제 실패")).when(chatroomPersistencePort).deleteById(chatroomId);

            // When & Then
            assertThatThrownBy(() -> chatroomService.deleteById(chatroomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("삭제 실패");

            verify(chatroomPersistencePort).deleteById(chatroomId);
        }
    }
}