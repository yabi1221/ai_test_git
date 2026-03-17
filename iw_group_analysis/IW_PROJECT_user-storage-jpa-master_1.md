# user-storage-jpa-master 분석

## 역할

`user-storage-jpa-master`는 Keycloak 사용자 저장소/인증 확장 프로젝트다. 현재 `keycloak` 프로젝트와 역할이 상당 부분 겹친다.

## 기술 스택

- Maven
- Java 17
- Keycloak 26.0.7
- Docker / Docker Compose
- MariaDB 사용자 저장소 연동

## 확인된 구현 클래스

- `JdbcUserStorageProvider`
- `ImprovedJdbcUserStorageProvider`
- `JdbcUserStorageProviderFactory`
- `GpkiAuthenticator`
- `ImprovedGpkiAuthenticator`
- `MobileQrAuthenticator`
- `MetaMaskAuthenticator`
- `SessionLimitEventListener`

## README 기준 기능

- JDBC 사용자 저장소 연동
- 세션 수 제한
- GPKI/QR/MetaMask 관련 인증 흐름
- Docker 기반 테스트/실행 가이드 제공

## docker-compose 기준 연동

### Keycloak 자체 DB
- 별도 MariaDB `keycloak` DB 사용

### 사용자 저장소 DB
- `usiw` MariaDB 사용
- 즉, API 프로젝트의 메인 업무 DB와 맞물릴 가능성이 높다.

## keycloak 프로젝트와의 관계

두 저장소 모두 다음 역할을 가진다.
- JDBC 사용자 저장소
- GPKI 계열 인증기
- 세션 제한 이벤트

따라서 다음 중 하나로 해석된다.

1. `user-storage-jpa-master`가 원본/실험 저장소
2. `keycloak`이 현재 운영용 축약 버전
3. 기관별/버전별로 병행 관리 중

현재 상태만 놓고 보면 `keycloak`보다 문서가 많고 실험 흔적도 많아서, 이전 기준 또는 확장판 저장소일 가능성이 높다.

## 파생 문서

- `IW_PROJECT_user-storage-jpa-master_2.md`: 저장소 성격 재평가
