//package org.opensource;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.opensource.chatroom.domain.Chatroom;
//import org.opensource.user.domain.User;
//import org.opensource.userchatroom.domain.UserChatroom;
//import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;
//import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
//import org.opensource.userchatroom.service.UserChatroomService;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserChatroomServiceTest {
//
//    @Mock
//    private UserChatroomPersistencePort userChatroomPersistencePort;
//
//    @InjectMocks
//    private UserChatroomService userChatroomService;
//
//    @Captor
//    private ArgumentCaptor<UserChatroom> userChatroomCaptor;
//
//    @Nested
//    @DisplayName("채팅방 참여 테스트")
//    class JoinUserInChatroomTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자가 채팅방에 처음 참여하는 경우")
//        void shouldCreateNewUserChatroomWhenUserJoinsForTheFirstTime() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//            JoinUserInChatroomCommand command = new JoinUserInChatroomCommand(user, chatroom, true);
//
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.empty());
//            when(userChatroomPersistencePort.save(any(UserChatroom.class)))
//                    .thenReturn(1001L);
//
//            // When
//            Long result = userChatroomService.joinUserInChatroom(command);
//
//            // Then
//            assertThat(result).isEqualTo(1001L);
//
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//            verify(userChatroomPersistencePort).save(userChatroomCaptor.capture());
//
//            UserChatroom savedUserChatroom = userChatroomCaptor.getValue();
//            assertThat(savedUserChatroom.getUser().getId()).isEqualTo(user.getId());
//            assertThat(savedUserChatroom.getChatroom().getId()).isEqualTo(chatroom.getId());
//            assertThat(savedUserChatroom.getIsOnline()).isTrue();
//        }
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자가 이미 채팅방에 참여한 경우 기존 ID 반환")
//        void shouldReturnExistingIdWhenUserAlreadyJoined() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//            UserChatroom existingUserChatroom = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user)
//                    .chatroom(chatroom)
//                    .isOnline(false)  // 기존 설정은 오프라인
//                    .build();
//            JoinUserInChatroomCommand command = new JoinUserInChatroomCommand(user, chatroom, true);
//
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.of(existingUserChatroom));
//
//            // When
//            Long result = userChatroomService.joinUserInChatroom(command);
//
//            // Then
//            assertThat(result).isEqualTo(1001L);
//
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//            verifyNoMoreInteractions(userChatroomPersistencePort);
//        }
//
//        @Test
//        @DisplayName("예외 시나리오: 영속성 계층에서 예외 발생 시 전파")
//        void shouldPropagateExceptionWhenPersistenceLayerFails() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//            JoinUserInChatroomCommand command = new JoinUserInChatroomCommand(user, chatroom, true);
//
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.empty());
//            when(userChatroomPersistencePort.save(any(UserChatroom.class)))
//                    .thenThrow(new RuntimeException("데이터베이스 오류"));
//
//            // When & Then
//            assertThatThrownBy(() -> userChatroomService.joinUserInChatroom(command))
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage("데이터베이스 오류");
//        }
//    }
//
//    @Nested
//    @DisplayName("사용자 채팅방 삭제 테스트")
//    class DeleteUserChatroomTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자를 채팅방에서 삭제")
//        void shouldDeleteUserFromChatroom() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//            UserChatroom userChatroom = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user)
//                    .chatroom(chatroom)
//                    .isOnline(true)
//                    .build();
//
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.of(userChatroom));
//
//            // When
//            userChatroomService.deleteUserAtChatRoomByChatRoomId(1L, 101L);
//
//            // Then
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//            verify(userChatroomPersistencePort).deleteById(1001L);
//        }
//
//        @Test
//        @DisplayName("예외 시나리오: 존재하지 않는 사용자-채팅방 연결")
//        void shouldThrowExceptionWhenUserChatroomNotFound() {
//            // Given
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.empty());
//
//            // When & Then
//            assertThatExceptionOfType(NoSuchElementException.class)
//                    .isThrownBy(() -> userChatroomService.deleteUserAtChatRoomByChatRoomId(1L, 101L));
//
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//            verify(userChatroomPersistencePort, never()).deleteById(anyLong());
//        }
//    }
//
//    @Nested
//    @DisplayName("채팅방 삭제 시 모든 사용자 삭제 테스트")
//    class DeleteAllUsersTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 채팅방 삭제 시 모든 사용자 연결 삭제")
//        void shouldDeleteAllUsersWhenChatroomIsDeleted() {
//            // Given
//            User user1 = User.builder().id(1L).name("사용자1").build();
//            User user2 = User.builder().id(2L).name("사용자2").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//
//            UserChatroom userChatroom1 = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user1)
//                    .chatroom(chatroom)
//                    .build();
//
//            UserChatroom userChatroom2 = UserChatroom.builder()
//                    .id(1002L)
//                    .user(user2)
//                    .chatroom(chatroom)
//                    .build();
//
//            when(userChatroomPersistencePort.findUserListByChatRoomId(101L))
//                    .thenReturn(Arrays.asList(userChatroom1, userChatroom2));
//
//            // When
//            userChatroomService.deleteAllUsersWhenChatRoomDeleted(101L);
//
//            // Then
//            verify(userChatroomPersistencePort).findUserListByChatRoomId(101L);
//            verify(userChatroomPersistencePort).deleteById(1001L);
//            verify(userChatroomPersistencePort).deleteById(1002L);
//        }
//
//        @Test
//        @DisplayName("엣지 케이스: 채팅방에 사용자가 없는 경우")
//        void shouldHandleEmptyChatroom() {
//            // Given
//            when(userChatroomPersistencePort.findUserListByChatRoomId(101L))
//                    .thenReturn(Collections.emptyList());
//
//            // When
//            userChatroomService.deleteAllUsersWhenChatRoomDeleted(101L);
//
//            // Then
//            verify(userChatroomPersistencePort).findUserListByChatRoomId(101L);
//            verify(userChatroomPersistencePort, never()).deleteById(anyLong());
//        }
//    }
//
//    @Nested
//    @DisplayName("사용자 삭제 시 모든 채팅방 연결 삭제 테스트")
//    class DeleteAllChatroomsTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자 삭제 시 모든 채팅방 연결 삭제")
//        void shouldDeleteAllChatroomsWhenUserIsDeleted() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom1 = Chatroom.builder().id(101L).topic("채팅방1").build();
//            Chatroom chatroom2 = Chatroom.builder().id(102L).topic("채팅방2").build();
//
//            UserChatroom userChatroom1 = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user)
//                    .chatroom(chatroom1)
//                    .build();
//
//            UserChatroom userChatroom2 = UserChatroom.builder()
//                    .id(1002L)
//                    .user(user)
//                    .chatroom(chatroom2)
//                    .build();
//
//            when(userChatroomPersistencePort.findChatroomListByUserId(1L))
//                    .thenReturn(Arrays.asList(userChatroom1, userChatroom2));
//
//            // When
//            userChatroomService.deleteAllChatroomsWhenUserDeleted(1L);
//
//            // Then
//            verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
//            verify(userChatroomPersistencePort).deleteById(1001L);
//            verify(userChatroomPersistencePort).deleteById(1002L);
//        }
//
//        @Test
//        @DisplayName("엣지 케이스: 사용자가 채팅방에 참여하지 않은 경우")
//        void shouldHandleUserWithNoChatrooms() {
//            // Given
//            when(userChatroomPersistencePort.findChatroomListByUserId(1L))
//                    .thenReturn(Collections.emptyList());
//
//            // When
//            userChatroomService.deleteAllChatroomsWhenUserDeleted(1L);
//
//            // Then
//            verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
//            verify(userChatroomPersistencePort, never()).deleteById(anyLong());
//        }
//    }
//
//    @Nested
//    @DisplayName("사용자 채팅방 조회 테스트")
//    class FindUserChatroomTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 채팅방에서 사용자 찾기")
//        void shouldFindUserInChatroom() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//            UserChatroom userChatroom = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user)
//                    .chatroom(chatroom)
//                    .isOnline(true)
//                    .build();
//
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.of(userChatroom));
//
//            // When
//            UserChatroom result = userChatroomService.findUserInChatRoom(1L, 101L);
//
//            // Then
//            assertThat(result).isNotNull();
//            assertThat(result.getId()).isEqualTo(1001L);
//            assertThat(result.getUser().getId()).isEqualTo(1L);
//            assertThat(result.getChatroom().getId()).isEqualTo(101L);
//            assertThat(result.getIsOnline()).isTrue();
//
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//        }
//
//        @Test
//        @DisplayName("예외 시나리오: 사용자가 채팅방에 없을 때 예외 발생")
//        void shouldThrowExceptionWhenUserNotInChatroom() {
//            // Given
//            when(userChatroomPersistencePort.findByUserIdAndChatroomId(1L, 101L))
//                    .thenReturn(Optional.empty());
//
//            // When & Then
//            assertThatExceptionOfType(NoSuchElementException.class)
//                    .isThrownBy(() -> userChatroomService.findUserInChatRoom(1L, 101L));
//
//            verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 101L);
//        }
//    }
//
//    @Nested
//    @DisplayName("채팅방 목록 조회 테스트")
//    class FindChatroomListTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자가 참여한 채팅방 목록 조회")
//        void shouldFindChatroomsForUser() {
//            // Given
//            User user = User.builder().id(1L).name("사용자1").build();
//
//            Chatroom chatroom1 = Chatroom.builder().id(101L).topic("채팅방1").build();
//            Chatroom chatroom2 = Chatroom.builder().id(102L).topic("채팅방2").build();
//
//            UserChatroom userChatroom1 = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user)
//                    .chatroom(chatroom1)
//                    .isOnline(true)
//                    .build();
//
//            UserChatroom userChatroom2 = UserChatroom.builder()
//                    .id(1002L)
//                    .user(user)
//                    .chatroom(chatroom2)
//                    .isOnline(false)
//                    .build();
//
//            when(userChatroomPersistencePort.findChatroomListByUserId(1L))
//                    .thenReturn(Arrays.asList(userChatroom1, userChatroom2));
//
//            // When
//            List<UserChatroom> results = userChatroomService.findChatRoomsUserParticipatesIn(1L);
//
//            // Then
//            assertThat(results).isNotNull().hasSize(2);
//            assertThat(results.get(0).getId()).isEqualTo(1001L);
//            assertThat(results.get(0).getChatroom().getId()).isEqualTo(101L);
//            assertThat(results.get(1).getId()).isEqualTo(1002L);
//            assertThat(results.get(1).getChatroom().getId()).isEqualTo(102L);
//
//            verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
//        }
//
//        @Test
//        @DisplayName("엣지 케이스: 사용자가 참여한 채팅방이 없는 경우")
//        void shouldReturnEmptyListWhenUserHasNoChatrooms() {
//            // Given
//            when(userChatroomPersistencePort.findChatroomListByUserId(1L))
//                    .thenReturn(Collections.emptyList());
//
//            // When
//            List<UserChatroom> results = userChatroomService.findChatRoomsUserParticipatesIn(1L);
//
//            // Then
//            assertThat(results).isNotNull().isEmpty();
//            verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
//        }
//    }
//
//    @Nested
//    @DisplayName("채팅방 참여자 목록 조회 테스트")
//    class FindParticipantsTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 채팅방 참여자 목록 조회")
//        void shouldFindParticipantsInChatroom() {
//            // Given
//            Chatroom chatroom = Chatroom.builder().id(101L).topic("테스트 채팅방").build();
//
//            User user1 = User.builder().id(1L).name("사용자1").build();
//            User user2 = User.builder().id(2L).name("사용자2").build();
//
//            UserChatroom userChatroom1 = UserChatroom.builder()
//                    .id(1001L)
//                    .user(user1)
//                    .chatroom(chatroom)
//                    .isOnline(true)
//                    .build();
//
//            UserChatroom userChatroom2 = UserChatroom.builder()
//                    .id(1002L)
//                    .user(user2)
//                    .chatroom(chatroom)
//                    .isOnline(false)
//                    .build();
//
//            when(userChatroomPersistencePort.findUserListByChatRoomId(101L))
//                    .thenReturn(Arrays.asList(userChatroom1, userChatroom2));
//
//            // When
//            List<UserChatroom> results = userChatroomService.findParticipantsInChatRoom(101L);
//
//            // Then
//            assertThat(results).isNotNull().hasSize(2);
//            assertThat(results.get(0).getId()).isEqualTo(1001L);
//            assertThat(results.get(0).getUser().getId()).isEqualTo(1L);
//            assertThat(results.get(0).getIsOnline()).isTrue();
//            assertThat(results.get(1).getId()).isEqualTo(1002L);
//            assertThat(results.get(1).getUser().getId()).isEqualTo(2L);
//            assertThat(results.get(1).getIsOnline()).isFalse();
//
//            verify(userChatroomPersistencePort).findUserListByChatRoomId(101L);
//        }
//
//        @Test
//        @DisplayName("엣지 케이스: 참여자가 없는 채팅방")
//        void shouldReturnEmptyListWhenNoParticipants() {
//            // Given
//            when(userChatroomPersistencePort.findUserListByChatRoomId(101L))
//                    .thenReturn(Collections.emptyList());
//
//            // When
//            List<UserChatroom> results = userChatroomService.findParticipantsInChatRoom(101L);
//
//            // Then
//            assertThat(results).isNotNull().isEmpty();
//            verify(userChatroomPersistencePort).findUserListByChatRoomId(101L);
//        }
//    }
//
//    @Nested
//    @DisplayName("사용자 채팅방 존재 확인 테스트")
//    class DoesUserExistTests {
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자가 채팅방에 존재하는 경우")
//        void shouldReturnTrueWhenUserExistsInChatroom() {
//            // Given
//            when(userChatroomPersistencePort.existsByUserIdAndChatRoomId(1L, 101L))
//                    .thenReturn(true);
//
//            // When
//            Boolean result = userChatroomService.doesUserExistInChatRoom(1L, 101L);
//
//            // Then
//            assertThat(result).isTrue();
//            verify(userChatroomPersistencePort).existsByUserIdAndChatRoomId(1L, 101L);
//        }
//
//        @Test
//        @DisplayName("성공 시나리오: 사용자가 채팅방에 존재하지 않는 경우")
//        void shouldReturnFalseWhenUserDoesNotExistInChatroom() {
//            // Given
//            when(userChatroomPersistencePort.existsByUserIdAndChatRoomId(1L, 101L))
//                    .thenReturn(false);
//
//            // When
//            Boolean result = userChatroomService.doesUserExistInChatRoom(1L, 101L);
//
//            // Then
//            assertThat(result).isFalse();
//            verify(userChatroomPersistencePort).existsByUserIdAndChatRoomId(1L, 101L);
//        }
//
//        @ParameterizedTest
//        @MethodSource("validIdCombinations")
//        @DisplayName("다양한 ID 조합에 대한 테스트")
//        void shouldHandleVariousIdCombinations(Long userId, Long chatroomId, boolean expected) {
//            // Given
//            when(userChatroomPersistencePort.existsByUserIdAndChatRoomId(userId, chatroomId))
//                    .thenReturn(expected);
//
//            // When
//            Boolean result = userChatroomService.doesUserExistInChatRoom(userId, chatroomId);
//
//            // Then
//            assertThat(result).isEqualTo(expected);
//            verify(userChatroomPersistencePort).existsByUserIdAndChatRoomId(userId, chatroomId);
//        }
//
//        static Stream<Arguments> validIdCombinations() {
//            return Stream.of(
//                    Arguments.of(1L, 101L, true),    // 존재하는 사용자-채팅방
//                    Arguments.of(2L, 101L, false),   // 존재하지 않는 사용자-채팅방
//                    Arguments.of(1L, 102L, false),   // 다른 채팅방
//                    Arguments.of(999L, 999L, false)  // 완전히 다른 ID
//            );
//        }
//    }
//}