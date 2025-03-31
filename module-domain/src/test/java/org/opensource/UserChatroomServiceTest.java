package org.opensource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;
import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
import org.opensource.userchatroom.service.UserChatroomService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserChatroomServiceTest {

    @Mock
    private UserChatroomPersistencePort userChatroomPersistencePort;

    @InjectMocks
    private UserChatroomService userChatroomService;

    private User testUser;
    private Chatroom testChatroom;
    private UserChatroom testUserChatroom;
    private JoinUserInChatroomCommand testCommand;

    @BeforeEach
    void setUp() {
        // 테스트용 사용자 생성
        testUser = User.builder()
                .id(1L)
                .name("테스트 사용자")
                .build();

        // 테스트용 채팅방 생성
        testChatroom = Chatroom.builder()
                .id(1L)
                .topic("테스트 채팅방")
                .build();

        // 테스트용 사용자채팅방 연결 객체 생성
        testUserChatroom = UserChatroom.builder()
                .id(1L)
                .user(testUser)
                .chatroom(testChatroom)
                .isOnline(true)
                .build();

        // 테스트용 명령 객체 생성
        testCommand = new JoinUserInChatroomCommand(testUser, testChatroom, true);
    }

    @Test
    @DisplayName("채팅방 참여 - 사용자가 채팅방에 처음 참여하는 경우")
    void joinUserInChatroom_FirstTime_Success() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());
        when(userChatroomPersistencePort.save(any(UserChatroom.class)))
                .thenReturn(1L);

        // when
        Long result = userChatroomService.joinUserInChatroom(testCommand);

        // then
        assertEquals(1L, result);
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
        verify(userChatroomPersistencePort).save(any(UserChatroom.class));
    }

    @Test
    @DisplayName("채팅방 참여 - 사용자가 이미 채팅방에 참여한 경우")
    void joinUserInChatroom_AlreadyJoined_ReturnsExistingId() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.of(testUserChatroom));

        // when
        Long result = userChatroomService.joinUserInChatroom(testCommand);

        // then
        assertEquals(1L, result);
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
        verify(userChatroomPersistencePort, never()).save(any(UserChatroom.class));
    }

    @Test
    @DisplayName("채팅방에서 사용자 삭제")
    void deleteUserAtChatRoomByChatRoomId_Success() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.of(testUserChatroom));

        // when
        userChatroomService.deleteUserAtChatRoomByChatRoomId(1L, 1L);

        // then
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
        verify(userChatroomPersistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("채팅방에서 사용자 삭제 - 존재하지 않는 경우 예외 발생")
    void deleteUserAtChatRoomByChatRoomId_NotFound_ThrowsException() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> {
            userChatroomService.deleteUserAtChatRoomByChatRoomId(1L, 1L);
        });
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
        verify(userChatroomPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("채팅방 삭제 시 모든 사용자 삭제")
    void deleteAllUsersWhenChatRoomDeleted_Success() {
        // given
        UserChatroom user1 = UserChatroom.builder().id(1L).build();
        UserChatroom user2 = UserChatroom.builder().id(2L).build();
        List<UserChatroom> userList = Arrays.asList(user1, user2);

        when(userChatroomPersistencePort.findUserListByChatRoomId(anyLong()))
                .thenReturn(userList);

        // when
        userChatroomService.deleteAllUsersWhenChatRoomDeleted(1L);

        // then
        verify(userChatroomPersistencePort).findUserListByChatRoomId(1L);
        verify(userChatroomPersistencePort).deleteById(1L);
        verify(userChatroomPersistencePort).deleteById(2L);
    }

    @Test
    @DisplayName("사용자 삭제 시 모든 채팅방 연결 삭제")
    void deleteAllChatroomsWhenUserDeleted_Success() {
        // given
        UserChatroom chatroom1 = UserChatroom.builder().id(1L).build();
        UserChatroom chatroom2 = UserChatroom.builder().id(2L).build();
        List<UserChatroom> chatroomList = Arrays.asList(chatroom1, chatroom2);

        when(userChatroomPersistencePort.findChatroomListByUserId(anyLong()))
                .thenReturn(chatroomList);

        // when
        userChatroomService.deleteAllChatroomsWhenUserDeleted(1L);

        // then
        verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
        verify(userChatroomPersistencePort).deleteById(1L);
        verify(userChatroomPersistencePort).deleteById(2L);
    }

    @Test
    @DisplayName("채팅방에서 사용자 찾기")
    void findUserInChatRoom_Success() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.of(testUserChatroom));

        // when
        UserChatroom result = userChatroomService.findUserInChatRoom(1L, 1L);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
    }

    @Test
    @DisplayName("채팅방에서 사용자 찾기 - 존재하지 않는 경우 예외 발생")
    void findUserInChatRoom_NotFound_ThrowsException() {
        // given
        when(userChatroomPersistencePort.findByUserIdAndChatroomId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> {
            userChatroomService.findUserInChatRoom(1L, 1L);
        });
        verify(userChatroomPersistencePort).findByUserIdAndChatroomId(1L, 1L);
    }

    @Test
    @DisplayName("사용자가 참여한 채팅방 목록 조회")
    void findChatRoomsUserParticipatesIn_Success() {
        // given
        UserChatroom chatroom1 = UserChatroom.builder().id(1L).build();
        UserChatroom chatroom2 = UserChatroom.builder().id(2L).build();
        List<UserChatroom> chatroomList = Arrays.asList(chatroom1, chatroom2);

        when(userChatroomPersistencePort.findChatroomListByUserId(anyLong()))
                .thenReturn(chatroomList);

        // when
        List<UserChatroom> result = userChatroomService.findChatRoomsUserParticipatesIn(1L);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userChatroomPersistencePort).findChatroomListByUserId(1L);
    }

    @Test
    @DisplayName("채팅방 참여자 목록 조회")
    void findParticipantsInChatRoom_Success() {
        // given
        UserChatroom user1 = UserChatroom.builder().id(1L).build();
        UserChatroom user2 = UserChatroom.builder().id(2L).build();
        List<UserChatroom> userList = Arrays.asList(user1, user2);

        when(userChatroomPersistencePort.findUserListByChatRoomId(anyLong()))
                .thenReturn(userList);

        // when
        List<UserChatroom> result = userChatroomService.findParticipantsInChatRoom(1L);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userChatroomPersistencePort).findUserListByChatRoomId(1L);
    }

    @Test
    @DisplayName("사용자가 채팅방에 존재하는지 확인 - 존재함")
    void doesUserExistInChatRoom_Exists_ReturnsTrue() {
        // given
        when(userChatroomPersistencePort.existsByUserIdAndChatRoomId(anyLong(), anyLong()))
                .thenReturn(true);

        // when
        Boolean result = userChatroomService.doesUserExistInChatRoom(1L, 1L);

        // then
        assertTrue(result);
        verify(userChatroomPersistencePort).existsByUserIdAndChatRoomId(1L, 1L);
    }

    @Test
    @DisplayName("사용자가 채팅방에 존재하는지 확인 - 존재하지 않음")
    void doesUserExistInChatRoom_DoesNotExist_ReturnsFalse() {
        // given
        when(userChatroomPersistencePort.existsByUserIdAndChatRoomId(anyLong(), anyLong()))
                .thenReturn(false);

        // when
        Boolean result = userChatroomService.doesUserExistInChatRoom(1L, 1L);

        // then
        assertFalse(result);
        verify(userChatroomPersistencePort).existsByUserIdAndChatRoomId(1L, 1L);
    }
}