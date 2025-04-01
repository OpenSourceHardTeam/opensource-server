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

    @Override
    public Long save(UserChatroom userChatroom) {
        return userChatroomJpaRepository.save(UserChatroomEntity.from(userChatroom)).getId();
    }


    @Override
    public Optional<UserChatroom> findByUserIdAndChatroomId(User user, Chatroom chatroom) {
        return userChatroomJpaRepository.findByUserAndChatroomFetch(UserEntity.from(user), ChatroomEntity.from(chatroom))
                .map(UserChatroomEntity::toModel);
    }

    @Override
    public List<UserChatroom> findChatroomListByUserId(User user) {
        return userChatroomJpaRepository.findByUserWithFetch(UserEntity.from(user))
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public List<UserChatroom> findUserListByChatRoomId(Chatroom chatroom) {
        return userChatroomJpaRepository.findByChatroomWithFetch(ChatroomEntity.from(chatroom))
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndChatRoomId(User user, Chatroom chatroom) {
       return userChatroomJpaRepository.existsByUserAndChatRoom(UserEntity.from(user), ChatroomEntity.from(chatroom));
    }

    @Override
    public void deleteById(Long id) {
        userChatroomJpaRepository.deleteById(id);
    }
}
