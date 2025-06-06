package org.opensource.userchatroom.repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.repository.ChatroomJpaRepository;
import org.opensource.user.domain.User;
import org.opensource.user.entity.UserEntity;
import org.opensource.user.repository.UserJpaRepository;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.entity.UserChatroomEntity;
import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserChatroomRepository implements UserChatroomPersistencePort {

    private final UserChatroomJpaRepository userChatroomJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public Long save(UserChatroom userChatroom) {
        UserChatroomEntity entity = UserChatroomEntity.builder()
                .user(userJpaRepository.findById(userChatroom.getUser().getId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found")))
                .chatroom(chatroomJpaRepository.findById(userChatroom.getChatroom().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Chatroom not found")))
                .isOnline(userChatroom.getIsOnline())
                .build();
        return userChatroomJpaRepository.save(entity).getId();
    }

    @Override
    public Optional<UserChatroom> findByUserIdAndChatroomId(User user, Chatroom chatroom) {
        UserEntity userEntity = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ChatroomEntity chatroomEntity = chatroomJpaRepository.findById(chatroom.getId())
                .orElseThrow(() -> new IllegalArgumentException("Chatroom not found"));

        return userChatroomJpaRepository.findByUserAndChatroomFetch(userEntity, chatroomEntity)
                .map(UserChatroomEntity::toModel);
    }

    @Override
    public List<UserChatroom> findChatroomListByUserId(User user) {
        UserEntity userEntity = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userChatroomJpaRepository.findByUserWithFetch(userEntity)
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public List<UserChatroom> findUserListByChatRoomId(Chatroom chatroom) {
        ChatroomEntity chatroomEntity = chatroomJpaRepository.findById(chatroom.getId())
                .orElseThrow(() -> new IllegalArgumentException("Chatroom not found"));

        return userChatroomJpaRepository.findByChatroomWithFetch(chatroomEntity)
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndChatRoomId(User user, Chatroom chatroom) {
        UserEntity userEntity = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ChatroomEntity chatroomEntity = chatroomJpaRepository.findById(chatroom.getId())
                .orElseThrow(() -> new IllegalArgumentException("Chatroom not found"));

        return userChatroomJpaRepository.existsByUserAndChatRoom(userEntity, chatroomEntity);
    }

    @Override
    public void deleteById(Long id) {
        userChatroomJpaRepository.deleteById(id);
    }
}