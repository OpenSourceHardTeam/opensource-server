package org.opensource.userchatroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
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
        return userChatroomJpaRepository.save(UserChatroomEntity.from(userChatRoom))
                .toModel().getId();
    }

    @Override
    public Optional<UserChatroom> findByUserAndChatRoom(User user, Chatroom chatRoom) {
        try {
            UserChatroomEntity userChatRoom = (UserChatroomEntity) em.createQuery(
                            "select uc from UserChatroomEntity uc" +
                                    " join fetch uc.user" +
                                    " join fetch uc.chatroom" +
                                    " where uc.user = :user and uc.chatroom = :chatroom")
                    .setParameter("user", user)
                    .setParameter("chatroom", chatRoom)
                    .getSingleResult();

            return Optional.of(userChatRoom);
        } catch (NoResultException e) {
            return Optional.empty(); // 결과가 없을 때는 Optional.empty() 반환
        }
    }

    @Override
    public List<UserChatroom> findByUser(User user) {
        return List.of();
    }

    @Override
    public List<UserChatroom> findByChatRoom(Chatroom chatRoom) {
        return List.of();
    }

    @Override
    public boolean existsByUserAndChatRoom(User user, Chatroom chatRoom) {
        return false;
    }

    @Override
    public void delete(UserChatroom userChatRoom) {

    }
}
