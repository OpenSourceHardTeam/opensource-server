package org.opensource.userchatroom.repository;

import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.user.entity.UserEntity;
import org.opensource.userchatroom.entity.UserChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatroomJpaRepository extends JpaRepository<UserChatroomEntity, Long> {

    @Query("select uc from UserChatroomEntity uc" +
            " join fetch uc.user" +
            " where uc.user = :user")
    List<UserChatroomEntity> findByUserWithFetch(@Param("user") UserEntity user);

    @Query("select uc from UserChatroomEntity uc" +
            " join fetch uc.chatroom" +
            " where uc.chatroom = :chatroom")
    List<UserChatroomEntity> findByChatroomWithFetch(@Param("chatroom") ChatroomEntity chatroom);

    @Query("select uc from UserChatroomEntity uc" +
            " join fetch uc.user" +
            " join fetch uc.chatroom" +
            " where uc.user = :user and uc.chatroom = :chatroom")
    Optional<UserChatroomEntity> findByUserAndChatroomFetch(
            @Param("user") UserEntity user,
            @Param("chatroom") ChatroomEntity chatroom);

    @Query("select case when count(uc) > 0 then true else false end" +
            " from UserChatroomEntity uc" +
            " where uc.user = :user and uc.chatroom = :chatRoom")
    boolean existsByUserAndChatRoom(@Param("user") UserEntity user, @Param("chatRoom") ChatroomEntity chatRoom);
}
