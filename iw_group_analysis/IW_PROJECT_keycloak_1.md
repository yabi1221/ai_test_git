# keycloak 분석

## 역할

`keycloak` 프로젝트는 Keycloak 자체가 아니라 `Keycloak 26`에 올리는 커스텀 Provider 확장 저장소다.

## 기술 스택

- Maven
- Java 11
- Keycloak 26 SPI
- Shade Plugin

## 확인된 구현 클래스

- `JdbcUserStorageProvider`
- `JdbcUserStorageProviderFactory`
- `GpkiAuthenticator`
- `GpkiAuthenticatorFactory`
- `MetaMaskAuthenticator`
- `MetaMaskAuthenticatorFactory`
- `SessionLimitEventListener`
- `SessionLimitEventListenerFactory`

## 기능 해석

- JDBC 기반 사용자 저장소 연동
- GPKI 로그인 인증기
- MetaMask 인증기
- 세션 수 제한 이벤트 처리

## 다른 프로젝트와의 관계

### API DB와 연결될 가능성
- 유사 프로젝트인 `user-storage-jpa-master` 기준 docker-compose에서 `usiw` DB를 사용자 저장소 DB로 사용한다.
- 즉, Keycloak은 API가 쓰는 사용자 DB와 연동되는 구조일 가능성이 높다.

### UI와의 관계
- UI는 Keycloak base URL, realm, client id를 직접 사용한다.
- 즉, 최종 로그인 제공자는 이 확장이 탑재된 Keycloak 인스턴스다.

### GPKI와의 관계
- GPKI 인증기가 이 저장소 안에 있다.
- 별도 `iop-iw-gpki` 저장소와 함께 전체 GPKI 로그인 체계를 구성할 수 있다.

## 현재 상태 해석

이 저장소는 포털의 인증 확장 핵심이다. 단순 설정 저장소가 아니라 실제 로그인 방식과 사용자 연동 방식을 바꾸는 코드 저장소다.

## 파생 문서

- `IW_PROJECT_keycloak_2.md`: 확장 공급자, 입문, 인증 시퀀스, 운영, 장애 대응, 코드 추적을 합친 실무 가이드
