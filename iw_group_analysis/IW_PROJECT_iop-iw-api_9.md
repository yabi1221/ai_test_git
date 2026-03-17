# iop-iw-api 화면 기준 API 목록

## 목적

- `iop-iw-ui` 화면 관점에서 실제로 어떤 API 묶음을 주로 호출하는지 역매핑한다.
- 화면에서 문제가 났을 때 어떤 API 군과 컨트롤러부터 봐야 하는지 빠르게 찾는 용도다.
- 전수 목록은 `IW_PROJECT_iop-iw-api_7.md`, 기능 요약은 `IW_PROJECT_iop-iw-api_8.md`를 기준으로 한다.

## 화면군 요약 표

| 화면군 | 주요 기능 | 대표 API prefix | 우선 확인 컨트롤러 |
| --- | --- | --- | --- |
| 로그인/인증 | Keycloak 로그인, callback, 토큰 재발급, 사용자 정보 | `/api/v1/auth/keycloak/*` | `AuthController`, `KeycloakController`, `IopSsoController` |
| 메인 업무공간/과제 | 과제 목록, 상세, 생성, 수정, 종료, 담당자 이관 | `/api/v1/assignments*` | `AssignmentController`, `AdminAssignmentController` |
| 문서/에디터 | 문서 생성, 본문 조회, 잠금, 복구, 트리, 첨부 연결 | `/api/v1/documents*`, `/api/v1/assignments/{asmtId}/documents*` | `DocumentManagementController`, `DocumentCommentController`, `DocumentSnapshotController` |
| 첨부/파일 업로드 | 첨부 조회, 외부 공유, TUS 업로드 후처리, 외부 파일 이관 | `/api/v1/attachments*`, `/api/v1/files*`, `/internal/tusd*` | `AttachmentController`, `ExternalFileShareController`, `FileTransferController`, `TusdCallbackController` |
| 공유/공개 문서 | 외부 공유 링크, 공개 문서 접근, 공유 포털 공지/스코프 | `/api/v1/external-shares*`, `/public/share/*`, `/api/share/*`, `/api/v1/business-authority/*` | `ExternalShareController`, `ShareController`, `BusinessAuthorityController` |
| 관리자 | 과제 관리, 사용자 관리, 역할/권한, 공지, 배너, 감사로그 | `/api/admin/*` | `AdminAssignmentController`, `RoleController`, `ExternalUserController`, `InternalUserController`, `NoticeController`, `BannerController`, `AuditLogController` |
| 대시보드/마이페이지 | 프로필, 수신설정, SaaS 대시보드 값, 회원 정보 | `/api/dashboard/*`, `/api/member/*` | `MypageController`, `SaasController`, `MemberController` |
| 검색/조직/사용자 | 사용자 검색, 조직 트리, 사용자 정보, DN 조회/수정 | `/api/v1/uscm/*`, `/api/v1/users*`, `/api/v1/organizations*` | `UscmSearchController`, `UserController`, `OrganizationController` |
| RAG/AI | RAG 작업 생성, 상태 조회, 통계, 외부 콜백 | `/api/v1/assignments/{asmtId}/rag-jobs*`, `/api/v1/external/rag-callbacks` | `RagJobController` |
| VOC/기타 운영 | VOC 등록/상태변경/댓글, 메일/메시지 발송, 배치 실행 | `/api/v1/voc*`, `/api/v1/works/*`, `/api/batch/*` | `VocController`, `WorksMailController`, `BatchController` |

## 화면별 상세

### 1. 로그인/인증 화면

- 화면 성격: 로그인 버튼, callback 처리, 세션 복구, 토큰 갱신, 로그아웃
- UI 기준 파일 축: `auth.service`, `api.ts`

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| Keycloak 로그인 | `POST /api/v1/auth/keycloak/login` | Keycloak 로그인 수행 | `AuthController` |
| Authorization Code 교환 | `POST /api/v1/auth/keycloak/token` | 인가코드를 액세스 토큰으로 교환 | `AuthController` |
| 토큰 갱신 | `POST /api/v1/auth/keycloak/refresh` | refresh token 기반 재발급 | `AuthController` |
| 토큰 폐기 | `POST /api/v1/auth/keycloak/revoke` | 로그인 세션/토큰 무효화 | `AuthController` |
| 사용자 정보 조회 | `GET /api/v1/auth/keycloak/userinfo` | 로그인 사용자 정보 확인 | `AuthController` |
| 로그인 URL 생성 | `GET /api/v1/auth/keycloak/authorize` | Keycloak redirect URL 생성 | `AuthController` |
| OAuth callback 처리 | `POST /api/v1/auth/keycloak/callback` | callback 결과 처리 | `AuthController` |
| 로그아웃 URL 생성 | `GET /api/v1/auth/keycloak/logout` | Keycloak logout URL 생성 | `AuthController` |
| SSO 토큰 처리 | `POST /sso/handleToken` | 별도 SSO 토큰 처리 | `IopSsoController` |
| ID 기반 로그인 | `POST /api/keycloak/userIdLogin` | 사용자 ID 기반 Keycloak 로그인 | `KeycloakController` |

### 2. 메인 업무공간/과제 화면

- 화면 성격: 과제 목록, 참여 과제, 상세, 생성/수정/삭제, 종료, 인수인계
- UI 기준 핵심 축: `assignment`

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| 과제 목록 | `GET /api/v1/assignments` | 기본 과제 목록 조회 | `AssignmentController` |
| 내가 참여한 과제 | `GET /api/v1/assignments/my-participated` | 참여 과제 목록 조회 | `AssignmentController` |
| 과제 상세 | `GET /api/v1/assignments/{asmtId}` | 단일 과제 상세 조회 | `AssignmentController` |
| 과제 생성 | `POST /api/v1/assignments` | 업무공간/과제 생성 | `AssignmentController` |
| 과제 수정 | `PUT /api/v1/assignments/{asmtId}` | 과제 정보 수정 | `AssignmentController` |
| 과제 삭제 | `DELETE /api/v1/assignments/{asmtId}` | 과제 삭제 | `AssignmentController` |
| 과제 종료 | `POST /api/v1/assignments/{asmtId}/approve` | 과제 상태 종료 처리 | `AssignmentController` |
| 과제 초기화 | `POST /api/v1/assignments/{asmtId}/reject` | 종료된 과제 상태 초기화 | `AssignmentController` |
| 담당자 이관 | `POST /api/v1/assignments/transfer-owner` | 과제 owner 일괄 변경 | `AssignmentController` |
| 외부 사용자 과제 목록 | `GET /api/v1/external/assignments/{accountId}` | 외부 사용자 기준 조회 | `ExternalAssignmentController` |
| 외부 사용자 과제 상세 | `GET /api/v1/external/assignments/{accountId}/{asmtId}` | 외부 사용자 상세 조회 | `ExternalAssignmentController` |

### 3. 문서/에디터 화면

- 화면 성격: 문서 트리, 본문 편집, 잠금, 댓글, 버전, 휴지통, 공유
- UI 기준 핵심 축: `document`, `attachment`, `share`, `rag`

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| 문서 생성 | `POST /api/v1/assignments/{asmtId}/documents` | 새 문서 생성 | `DocumentManagementController` |
| 문서 정보 조회 | `GET /api/v1/documents/{docId}` | 문서 메타/스냅샷 조회 | `DocumentManagementController` |
| 문서 본문 조회 | `GET /api/v1/documents/{docId}/content` | 에디터 본문 조회 | `DocumentManagementController` |
| 문서 수정 | `PUT /api/v1/documents/{docId}` | 문서 스냅샷 저장 | `DocumentManagementController` |
| 문서 트리 | `GET /api/v1/assignments/{asmtId}/documents/tree` | 문서 계층 구조 조회 | `DocumentManagementController` |
| 최근 문서 | `GET /api/v1/assignments/{asmtId}/documents/recent` | 최근 수정 문서 조회 | `DocumentManagementController` |
| 문서 잠금 | `POST /api/v1/documents/{docId}/lock` | 동시 편집 방지 잠금 | `DocumentManagementController` |
| 문서 잠금 해제 | `POST /api/v1/documents/{docId}/unlock` | 잠금 해제 | `DocumentManagementController` |
| 문서 복구 | `POST /api/v1/documents/{docId}/restore` | 휴지통 문서 복구 | `DocumentManagementController` |
| 문서 보관 | `POST /api/v1/documents/{docId}/archive` | 읽기전용 보관 처리 | `DocumentManagementController` |
| 문서 위치 이동 | `PUT /api/v1/documents/{docId}/position` | 문서 순서/부모 변경 | `DocumentManagementController` |
| 문서 첨부 연결 | `PUT /api/v1/assignments/{asmtId}/documents/{docId}/attachments` | 첨부파일 연결 | `DocumentManagementController` |
| 댓글 등록 | `POST /api/v1/assignments/{asmtId}/documents/comments` | 문서 댓글 작성 | `DocumentCommentController` |
| 댓글 목록 | `GET /api/v1/assignments/{asmtId}/documents/{docId}/comments` | 문서 댓글 조회 | `DocumentCommentController` |
| 댓글 답글 | `GET /api/v1/assignments/{asmtId}/documents/comments/{commentId}/replies` | 답글 조회 | `DocumentCommentController` |
| 댓글 좋아요 | `POST /api/v1/assignments/{asmtId}/documents/comments/{commentId}/like` | 댓글 반응 추가 | `DocumentCommentController` |
| 스냅샷 생성 | `POST /api/v1/documents/{docId}/snapshots` | 문서 버전 저장 | `DocumentSnapshotController` |
| 스냅샷 목록 | `GET /api/v1/documents/{docId}/snapshots` | 버전 목록 조회 | `DocumentSnapshotController` |
| 스냅샷 복원 | `POST /api/v1/documents/{docId}/snapshots/{version}:restore` | 특정 버전 복원 | `DocumentSnapshotController` |
| 문서 공유 생성 | `POST /api/v1/documents/{docId}/share` | 문서 공유 링크/권한 생성 | `DocumentShareController` |
| 템플릿 목록 | `GET /api/v1/document-templates` | 문서 템플릿 조회 | `DocumentTemplateController` |

### 4. 첨부/파일 업로드 화면

- 화면 성격: 첨부 조회, 업로드, 외부 다운로드, TUS 완료 후 처리
- UI 기준 핵심 축: `attachment`, `/iw/files`

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| 과제 첨부 목록 | `GET /api/v1/assignments/{asmtId}/attachments` | 과제 첨부 조회 | `AttachmentController` |
| 첨부 상세 | `GET /api/v1/attachments/{fileId}` | 첨부 메타 조회 | `AttachmentController` |
| 문서 첨부 목록 | `GET /api/v1/documents/{docId}/attachments` | 문서 연결 첨부 조회 | `AttachmentController` |
| 유형별 첨부 조회 | `GET /api/v1/assignments/{asmtId}/attachments:filterByType` | 유형 필터 조회 | `AttachmentController` |
| 파일 다운로드 메타 | `GET /api/v1/files/{fileId}` | 파일 조회 | `AttachmentController` |
| 파일 공유 링크 생성 | `POST /api/v1/files/{fileId}/share` | 외부 파일 공유 | `ExternalFileShareController` |
| 외부 공유 다운로드 | `GET /api/v1/external/files/download` | 공유 파일 다운로드 | `ExternalFileShareController` |
| 네이버 파일 이관 | `POST /api/v1/files/transfer-from-naver` | 외부 파일을 내부 저장소로 이관 | `FileTransferController` |
| 스트림 이관 | `POST /api/v1/files/transfer-from-naver-stream` | 스트리밍 방식 파일 이관 | `FileTransferController` |
| 이관 상태 조회 | `GET /api/v1/files/transfer/status/{tusId}` | TUS 기반 이관 상태 확인 | `FileTransferController` |
| TUS 콜백 | `POST /internal/tusd` | 업로드 완료/시작 콜백 | `TusdCallbackController` |
| TUS finish/failed/deleted | `POST /internal/tusd/*` | 업로드 후처리 세부 이벤트 | `TusdCallbackController` |

### 5. 공유/공개 문서 화면

- 화면 성격: 외부 공유 링크, 공개 문서, 공유 포털 공지와 외부사용자 진입

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| 외부 공유 링크 생성 | `POST /api/v1/external-shares` | 외부 공유 링크 발급 | `ExternalShareController` |
| 외부 공유 링크 삭제 | `DELETE /api/v1/external-shares/{shareId}` | 공유 종료 | `ExternalShareController` |
| 외부 공유 접근 | `GET /public/share/{shareId}` | 공개 링크 접근 | `ExternalShareController` |
| 공개 문서 링크 생성 | `POST /api/v1/business-authority/documents/{docId}/public` | 공개 문서 URL 생성 | `BusinessAuthorityController` |
| 공개 문서 접근 | `GET /api/v1/business-authority/public/{docPublicId}` | 공개 문서 조회 | `BusinessAuthorityController` |
| 내가 공개한 문서 | `GET /api/v1/business-authority/my-public-documents` | 내 공개 문서 조회 | `BusinessAuthorityController` |
| 공유 포털 공지 목록 | `GET /api/share/notices` | 공유 포털 공지 조회 | `ShareController` |
| 공유 포털 공지 상세 | `GET /api/share/notices/{ntcMttrId}` | 공지 상세 | `ShareController` |
| 신규 공지 여부 | `GET /api/share/notices/has-new` | 신규 공지 확인 | `ShareController` |
| 외부사용자 초기 비밀번호 확인 | `POST /api/share/external-users/checkInitPassword` | 초기 비밀번호 상태 확인 | `ShareController` |
| 외부사용자 초기 비밀번호 변경 | `POST /api/share/external-users/modifyInitPassword` | 초기 비밀번호 변경 | `ShareController` |

### 6. 관리자 화면

- 화면 성격: 관리 콘솔 전반
- UI 기준 핵심 축: `admin.api.ts`

| 하위 화면 | 대표 API | 기능 요약 | 우선 확인 컨트롤러 |
| --- | --- | --- | --- |
| 관리자 과제 목록/상세 | `GET /api/admin/assignments`, `GET /api/admin/assignments/{asmtId}` | 관리자 기준 과제 조회 | `AdminAssignmentController` |
| 관리자 과제 복구/이관 | `POST /api/admin/assignments/{asmtId}/restore`, `POST /api/admin/assignments/transfer-owner` | 삭제 과제 복구, 담당자 이관 | `AdminAssignmentController` |
| 외부 사용자 관리 | `/api/admin/external-users*` | 조회, 등록, 초대, 초기 비밀번호 처리 | `ExternalUserController` |
| 내부 사용자 관리 | `/api/admin/internal-users*` | 조회, 등록, 기관/부서 조회, DN/접속일 갱신 | `InternalUserController` |
| 역할/접근범위 | `/api/admin/roles*` | 역할 CRUD, 사용자 역할 부여, 접근범위 설정 | `RoleController` |
| 공지사항 | `/api/admin/notices*` | 공지 CRUD, 최근 공지, 신규 공지 확인 | `NoticeController` |
| 배너 | `/api/admin/banners*` | 배너 CRUD, 활성화, 순서, 일괄 상태 변경 | `BannerController` |
| 감사로그 | `/api/admin/audit-logs*` | 로그 조회, 통계, excel export, cleanup | `AuditLogController` |
| 이메일 관리 | `/api/admin/email/*` | 템플릿, 발송, 재발송, 통계 | `EmailController` |
| 사용자 활동 이력 | `/api/admin/user-activities*` | 활동 로그 조회/기록/다운로드 | `UserActvController` |
| Kafka 운영 테스트 | `GET /api/admin/kafka/send` | 테스트 이벤트 발행 | `EventProduceController` |

### 7. 대시보드/마이페이지 화면

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| 회원 가입 | `POST /api/member/iopJoin` | 신규 회원 등록 | `MemberController` |
| 회원 정보 수정 | `POST /api/member/iopModify` | 회원 기본정보 수정 | `MemberController` |
| 프로필 조회 | `GET /api/dashboard/mypage/{accountId}` | 마이페이지 조회 | `MypageController` |
| 프로필 이미지 업로드 | `POST /api/dashboard/mypage/userImgUpload` | 사용자 이미지 업로드 | `MypageController` |
| 수신설정 저장 | `POST /api/dashboard/mypage/receiveSettingRegist` | 알림/수신 설정 저장 | `MypageController` |
| IOP 사용 건수 확인 | `GET /api/dashboard/saas/checkIopCount/{iopId}` | SaaS 연동값 확인 | `SaasController` |
| 계정 기준 IOP ID 확인 | `GET /api/dashboard/saas/checkIopIdByAccountId/{accountId}` | 계정별 매핑 확인 | `SaasController` |
| 공통/메인 IOP ID 변경 | `POST /api/dashboard/saas/updateCommonIopId`, `POST /api/dashboard/saas/updateMainIopId` | SaaS 설정값 변경 | `SaasController` |
| 대시보드 View 데이터 | `GET /api/dashboard/saas/getViewData/{accountId}` | 화면 렌더링 데이터 조회 | `SaasController` |

### 8. 검색/조직/사용자 화면

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| USCM 사용자 검색 | `GET /api/v1/uscm/users/search` | 사용자 검색 | `UscmSearchController` |
| USCM 기관 검색 | `GET /api/v1/uscm/orgs/search` | 기관 검색 | `UscmSearchController` |
| 사용자 목록/상세 | `GET /api/v1/users`, `GET /api/v1/users/{accountId}` | 사용자 조회 | `UserController` |
| 사용자 상태 조회 | `GET /api/v1/users/status` | 상태 기반 조회 | `UserController` |
| 내 IOP DN 조회/수정 | `GET/PUT /api/v1/users/me/iop-dn` | 개인 인증 DN 관리 | `UserController` |
| 조직 목록/트리 | `GET /api/v1/organizations*` | 조직/트리 조회 | `OrganizationController` |

### 9. RAG/AI 화면

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| RAG 작업 생성 | `POST /api/v1/assignments/{asmtId}/rag-jobs` | 과제 기준 RAG 작업 생성 | `RagJobController` |
| RAG 작업 목록/상세 | `GET /api/v1/assignments/{asmtId}/rag-jobs*` | 작업 상태 조회 | `RagJobController` |
| RAG 통계 | `GET /api/v1/assignments/{asmtId}/rag-jobs:stats` | 작업 통계 | `RagJobController` |
| RAG 상태 변경 | `POST /api/v1/assignments/{asmtId}/rag-jobs/status` | 작업 상태 갱신 | `RagJobController` |
| 외부 RAG callback | `POST /api/v1/external/rag-callbacks` | 외부 처리 결과 반영 | `RagJobController` |

### 10. VOC/기타 운영 화면

| 기능 | 대표 API | 기능 요약 | 백엔드 진입점 |
| --- | --- | --- | --- |
| VOC 목록/상세 | `GET /api/v1/voc`, `GET /api/v1/voc/{vocId}` | VOC 조회 | `VocController` |
| VOC 등록/수정/삭제 | `POST/PUT/DELETE /api/v1/voc/{vocId}` | VOC 기본 처리 | `VocController` |
| VOC 상태 변경 | `PATCH /api/v1/voc/{vocId}/status` | 진행 상태 변경 | `VocController` |
| VOC 댓글 | `POST/PUT/DELETE /api/v1/voc/{vocId}/comments*` | 댓글 처리 | `VocController` |
| Works 메일/메시지 | `POST /api/v1/works/mail-send`, `POST /api/v1/works/message-send` | 알림 발송 | `WorksMailController` |
| 배치 수동 실행 | `GET /api/batch/memberSync` | 회원 동기화 배치 실행 | `BatchController` |

## 디버깅 시작점

- 로그인 문제: `AuthController` -> `KeycloakService` 계열 -> UI `auth.service`
- 과제 목록/상세 문제: `AssignmentController` -> `AssignmentService`
- 에디터 저장/잠금 문제: `DocumentManagementController` -> `DocumentService`
- 업로드 완료 후 반영 문제: `TusdCallbackController` -> 첨부/문서 후처리
- 관리자 화면 문제: `admin.api.ts`가 호출하는 `/api/admin/*` 컨트롤러부터 확인
