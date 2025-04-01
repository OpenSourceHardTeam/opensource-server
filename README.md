# 온라인 서재에서 나누는 책 이야기

저희 서비스는 오프라인에서만 가능했던 책에 대한 깊이 있는 대화를 온라인으로 확장합니다.
<br/>시간과 장소의 제약 없이 언제 어디서나 책을 매개로 사람들을 연결하는 것을 목표로 하고 있습니다.
<br/>베스트셀러들 읽고 해당 책의 채팅방에서 다양한 이야기를 나눌 수 있습니다.

## 핵심 기능
- 사용자 회원가입 및 인증
- 이메일 인증 시스템
- 책별 채팅방 생성 및 참여
- 채팅방 내에서 실시간 다대다 채팅 기능
- 사용자와 채팅룸 관리 기능

## 기술적 도전
### Multi Module Structure & Hexagonal Architecture
- 멀티 모듈과 헥사고날 아키텍처를 적용하여 모듈 간 의존성을 분리하고 외부로부터 도메인 모듈을 독립시켰습니다.

```
│
├── module-application       # API 게이트웨이 모듈
├── module-domain            # 도메인 모듈
├── module-common            # common 모듈
├── module-external-api      # 외부 API 인터페이스 모듈
├── module-messaging         # 실시간 소켓 메시징 모듈
└── module-infra             # 인프라 모듈
    ├── persistence-database # 데이터베이스 모듈
    ├── persistence-redis    # Redis 모듈
    └── security             
```


### Real Time Messaging
- 실시간 채팅 기능을 위해 WebSocket을 사용했습니다.
- 다중 서버 환경을 고려하여 Redis Pub/Sub Message Queue를 사용했습니다.
- 메시지 데이터는 읽고 쓰는 작업은 많지만 수정 작업이 적다는 특성을 고려하여 NoSQL인 MongoDB를 사용했습니다.

### Database Segregation
- 메시징 데이터 서버를 메인 서버와 분리했습니다.
- 메인 데이터베이스로는 MySQL을 사용하고 채팅 메시지를 저장하는 데이터베이스로는 MongoDB를 사용했습니다.
