# iop-iw-api 관리자 인증 및 화면 진입 분석

## 목적

이 문서는 `admin/auth` 구조와 화면 기준 백엔드 진입점을 한 번에 묶은 문서다.

## 1. 인증 축

핵심 진입점:

- `AuthController`
- `auth.service.KeycloakService`
- `auth.service.KeycloakOAuth2Service`
- `LoginAttemptService`
- `ihb.keycloak.svc.KeycloakService`

해석:

- 이 프로젝트 인증은 `Keycloak 토큰 처리`와 `내부 사용자 판별`이 결합된 구조다.
- 신규 auth 패키지와 레거시 `ihb` 계열이 공존한다.

## 2. 관리자 축

핵심 진입점:

- `AdminAssignmentController`
- `AdminAssignmentService`
- `ihb.admin.*`

주요 기능:

- 사용자 관리
- 역할/권한 관리
- 공지
- 사용자 활동 이력

## 3. 화면 기준 우선 진입점

### 작업공간/목록

1순위:

- `AssignmentController`
- `AssignmentService`

### 문서/에디터

1순위:

- `DocumentManagementController`
- `DocumentService`

### 로그인/세션

1순위:

- `AuthController`
- `KeycloakService` 계열

### 관리자 화면

1순위:

- `admin.*`
- `ihb.admin.*`

## 4. 디버깅 관점

- 로그인 실패: `AuthController` -> `LoginAttemptService` -> 내부 사용자 매핑
- 권한 이상: `BusinessAuthorityService`와 관리자 역할 축
- 관리자 기능 이상: `admin.*`와 `ihb.admin.*`를 같이 확인

## 결론

이 문서는 화면에서 들어왔을 때 어떤 백엔드 파일을 먼저 열어야 하는지 빠르게 좁히는 용도의 실무 문서다.
