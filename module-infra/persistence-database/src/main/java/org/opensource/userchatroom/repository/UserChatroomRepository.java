package org.opensource.userchatroom.repository;

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
    public Long save(UserChatroom userChatRoom) {
        return userChatroomJpaRepository.save(UserChatroomEntity.from(userChatRoom)).getId();
    }

    @Override
    public Optional<UserChatroom> findByUserIdAndChatroomId(Long userId, Long chatroomId) {
        return userChatroomJpaRepository.findByUserAndChatroomFetch(findUserEntityById(userId), findChatroomEntityById(chatroomId))
                .map(UserChatroomEntity::toModel);
    }

    @Override
    public List<UserChatroom> findChatroomListByUserId(Long userId) {
        return userChatroomJpaRepository.findByUserWithFetch(findUserEntityById(userId))
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public List<UserChatroom> findUserListByChatRoomId(Long chatroomId) {
        return userChatroomJpaRepository.findByChatroomWithFetch(findChatroomEntityById(chatroomId))
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndChatRoomId(Long userId, Long chatroomId) {
       return userChatroomJpaRepository.existsByUserAndChatRoom(findUserEntityById(userId), findChatroomEntityById(chatroomId));
    }

    @Override
    public void deleteById(Long id) {
        userChatroomJpaRepository.deleteById(id);
    }

    private UserEntity findUserEntityById(Long id) {
        return userJpaRepository.findById(id).orElse(null);
    }

    private ChatroomEntity findChatroomEntityById(Long id) {
        return chatroomJpaRepository.findById(id).orElse(null);
    }
}
