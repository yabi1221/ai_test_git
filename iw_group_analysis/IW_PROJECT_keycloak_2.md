# keycloak 실무 가이드

## 목적

이 문서는 기존 Keycloak 파생 문서들을 한 번에 묶은 통합 실무 가이드다.

## 1. 이 저장소의 정체

`keycloak` 프로젝트는 Keycloak 본체가 아니라 `Keycloak 26`에 올리는 커스텀 Provider 저장소다.

역할:

- 사용자 저장소 확장
- 로그인 인증 단계 확장
- 세션 정책 확장

## 2. 확인된 주요 Provider

- `JdbcUserStorageProviderFactory`
- `GpkiAuthenticatorFactory`
- `MetaMaskAuthenticatorFactory`
- `SessionLimitEventListenerFactory`

## 3. 이 포털에서의 위치

이 프로젝트에서 인증은 `Keycloak 단독`이 아니라 아래 구조다.

`UI -> Keycloak -> API auth -> 내부 사용자 판별`

즉 Keycloak은 로그인 중심축이지만, API 내부 인증 보강 로직과 결합돼 있다.

## 4. 처음 보는 개발자가 알아야 할 최소 개념

- `realm`
- `client`
- `access token`
- `refresh token`
- `authentication flow`

## 5. 실제 인증 시퀀스

1. UI가 Keycloak 로그인 URL 생성
2. 사용자가 Keycloak 로그인
3. UI callback으로 code 수신
4. UI가 API `/api/v1/auth/keycloak/*` 호출
5. API가 Keycloak + 내부 사용자 판별 수행
6. UI가 토큰 저장 후 업무 API 사용
7. 만료 임박 시 refresh 수행

## 6. 운영 시 반드시 보는 항목

- UI: Keycloak base URL, realm, client id, redirect URI
- Keycloak: realm/client, redirect URI 허용, provider 배포
- API: auth 응답, 내부 사용자 매핑, 로그인 실패/차단
- 사용자 저장소: JDBC provider 설정, DB 연결 정보

## 7. 장애 대응 기본 순서

1. UI 환경값과 callback 흐름 확인
2. API auth 응답 확인
3. 내부 사용자 매핑 확인
4. Keycloak realm/client 확인
5. provider 배포 여부 확인
6. 사용자 DB 연결 확인

## 8. 코드 추적 시작점

1. `iop-iw-ui/src/services/auth.service.ts`
2. `iop-iw-ui/src/config/api.ts`
3. `AuthController.java`
4. `auth.service.KeycloakService`
5. `ihb.keycloak.svc.KeycloakService`
6. `LoginAttemptService`
7. 필요 시 `JdbcUserStorageProviderFactory`, `GpkiAuthenticatorFactory`

## 9. 실무 판단

현재 분석 기준으로는 `keycloak` 저장소가 실제 운영형 provider 저장소일 가능성이 높다.

반면 `user-storage-jpa-master`는 실험/레퍼런스 성격이 더 강하다.
