# iop-iw-api 개발 핵심 경로 추적

## 목적

이 문서는 UI 실사용 API 기준으로 `컨트롤러 -> 서비스 -> DAO`, 그리고 대표 메서드 매핑까지 한 번에 볼 수 있도록 묶은 통합 추적 문서다.

## 1. 작업공간 축

주요 API:

- `/api/v1/assignments`
- `/api/v1/assignments/my-participated`
- `/api/v1/assignments/{asmtId}`
- `/api/v1/assignments/transfer-owner`

핵심 진입점:

- `AssignmentController`
- `AssignmentService`
- `AssignmentDao`
- `AssignmentHistoryDao`

## 2. 문서 축

주요 API:

- `/api/v1/assignments/{asmtId}/documents`
- `/api/v1/documents/{docId}`
- `/api/v1/documents/{docId}/lock`
- `/api/v1/documents/{docId}/unlock`

핵심 진입점:

- `DocumentManagementController`
- `DocumentService`
- `DocumentDao`

## 3. 관리자/인증 축

핵심 진입점:

- `AuthController`
- `auth.service.KeycloakService`
- `AdminAssignmentController`
- `AdminAssignmentService`

## 4. 메서드 매핑 해석

실무적으로는 URL 하나를 보면 아래처럼 좁히면 된다.

- 작업공간 목록/상세/생성/수정/삭제 -> `AssignmentController`
- 문서 CRUD/잠금/복원/트리 -> `DocumentManagementController`
- 댓글/공유/버전 -> 문서 하위 컨트롤러/서비스
- 로그인/토큰/사용자정보 -> `AuthController`

## 5. 공통 보강 계층

같이 걸리는 축:

- `BusinessAuthorityService`
- `UnifiedHistoryService`
- 내부 사용자/권한 관련 DAO

## 결론

기능 단위 수정이나 디버깅을 시작할 때, 이 문서 기준으로 먼저 컨트롤러와 서비스 축을 좁히는 것이 가장 효율적이다.
