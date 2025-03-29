package org.opensource.userchatroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.user.domain.User;
import org.opensource.user.entity.UserEntity;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.entity.UserChatroomEntity;
import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserChatroomRepository implements UserChatroomPersistencePort {

    @PersistenceContext
    private EntityManager em;

    private final UserChatroomJpaRepository userChatroomJpaRepository;

    @Override
    public Long save(UserChatroom userChatRoom) {
        return userChatroomJpaRepository.save(UserChatroomEntity.from(userChatRoom)).getId();
    }

    @Override
    public Optional<UserChatroom> findByUserAndChatRoom(User user, Chatroom chatRoom) {
        UserEntity userEntity = UserEntity.from(user);
        ChatroomEntity chatroomEntity = ChatroomEntity.from(chatRoom);

        return userChatroomJpaRepository.findByUserAndChatroomFetch(userEntity, chatroomEntity)
                .map(UserChatroomEntity::toModel);
    }

    @Override
    public List<UserChatroom> findByUser(User user) {
        UserEntity userEntity = UserEntity.from(user);

        return userChatroomJpaRepository.findByUserWithFetch(userEntity)
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public List<UserChatroom> findByChatRoom(Chatroom chatRoom) {
        ChatroomEntity chatroomEntity = ChatroomEntity.from(chatRoom);

        return userChatroomJpaRepository.findByChatroomWithFetch(chatroomEntity)
                .stream()
                .map(UserChatroomEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByUserAndChatRoom(User user, Chatroom chatRoom) {
        UserEntity userEntity = UserEntity.from(user);
        ChatroomEntity chatroomEntity = ChatroomEntity.from(chatRoom);

        return userChatroomJpaRepository.existsByUserAndChatRoom(userEntity, chatroomEntity);
    }

    @Override
    public void deleteById(Long id) {
        userChatroomJpaRepository.deleteById(id);
    }
}
