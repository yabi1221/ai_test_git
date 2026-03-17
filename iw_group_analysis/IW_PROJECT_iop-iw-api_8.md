# iop-iw-api 기능영역별 API 정리

## 목적

- `iop-iw-api` 엔드포인트를 기능영역 기준으로 다시 묶고, 각 API가 수행하는 기능을 짧게 요약한다.
- 전수 기준은 `IW_PROJECT_iop-iw-api_7.md`와 동일한 소스 재추출 결과다.
- 기능 요약은 경로와 메서드명을 기준으로 안정적으로 재작성했다.

## 기능영역 요약 표

| 기능영역 | 컨트롤러 | 엔드포인트 수 | 요약 |
| --- | --- | ---: | --- |
| assignment | AssignmentController, ExternalAssignmentController | 11 | 업무공간/과제 조회, 생성, 수정, 인수인계 |
| document | DocumentCommentController, DocumentManagementController, DocumentShareController, DocumentSnapshotController, DocumentTemplateController | 37 | 문서 본문, 스냅샷, 댓글, 공유, 템플릿 |
| attachment | AttachmentController, ExternalFileShareController, FileTransferController, TusdCallbackController | 19 | 첨부파일 조회, 외부 공유, 파일 전송, TUS 후처리 |
| auth | ApiAuthTestController, AuthController, AuthorityManagementController, BusinessAuthorityController, CryptoTestController, IopSsoController, KeycloakController, SaasAuthController | 46 | 로그인, 토큰, 권한, SSO, 공개 문서 권한 |
| admin | AdminAssignmentController, AdminUserController, AsmtTkovController, AuditLogController, BannerController, EmailController, EventProduceController, ExternalUserController, InternalUserController, NoticeController, RoleController, UserActvController, WorkspaceController | 162 | 관리자 운영, 감사, 공지, 배너, 역할, 사용자 운영 |
| api | HealthController, UnifiedHistoryController | 15 | 공통 상태 점검과 통합 이력 조회 |
| brm | BrmController, BrmRelationController | 19 | BRM 분류체계와 과제-분류 연계 |
| ihb | MemberController, MypageController, SaasController, ShareController | 18 | 대시보드, 회원, 마이페이지, 공통 공유 성격 API |
| mail | WorksMailController | 2 | Works 메일/메시지 발송 |
| rag | RagJobController | 6 | RAG 작업 요청, 상태 조회, 외부 콜백 |
| schedule | BatchController | 1 | 배치 수동 실행 |
| share | ExternalShareController | 3 | 외부 공유 링크 생성/회수/공개 접근 |
| todo | TodoController | 5 | 할 일 관리 |
| uscm | UscmSearchController | 2 | 사용자/기관 검색 |
| user | OrganizationController, UserController | 14 | 사용자/조직 조회와 개인 DN 관리 |
| voc | VocController | 10 | VOC 등록, 상태 변경, 댓글 처리 |

## 기능영역별 상세

### assignment

- 업무공간/과제 조회, 생성, 수정, 인수인계

#### AssignmentController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/assignments` | 과제/업무공간 조회 API |
| GET | `/api/v1/assignments/my-participated` | 과제/업무공간 조회 API |
| GET | `/api/v1/assignments/{asmtId}` | 과제/업무공간 조회 API |
| POST | `/api/v1/assignments` | 과제/업무공간 등록/실행 API |
| PUT | `/api/v1/assignments/{asmtId}` | 과제/업무공간 수정 API |
| DELETE | `/api/v1/assignments/{asmtId}` | 과제/업무공간 삭제 API |
| POST | `/api/v1/assignments/{asmtId}/approve` | 과제 종료 처리 API |
| POST | `/api/v1/assignments/{asmtId}/reject` | 과제 상태 초기화 API |
| POST | `/api/v1/assignments/transfer-owner` | 과제 담당자 일괄 이관 API |

#### ExternalAssignmentController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/external/assignments/{accountId}` | 과제/업무공간 조회 API |
| GET | `/api/v1/external/assignments/{accountId}/{asmtId}` | 과제/업무공간 조회 API |

### document

- 문서 본문, 스냅샷, 댓글, 공유, 템플릿

#### DocumentCommentController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/assignments/{asmtId}/documents/comments` | 문서 댓글 등록/실행 API |
| GET | `/api/v1/assignments/{asmtId}/comments` | 문서 댓글 조회 API |
| GET | `/api/v1/assignments/{asmtId}/documents/{docId}/comments` | 문서 댓글 조회 API |
| GET | `/api/v1/assignments/{asmtId}/documents/comments/{commentId}/replies` | 문서 댓글 조회 API |
| PUT | `/api/v1/assignments/{asmtId}/documents/comments/{commentId}` | 문서 댓글 수정 API |
| DELETE | `/api/v1/assignments/{asmtId}/documents/comments/{commentId}` | 문서 댓글 삭제 API |
| POST | `/api/v1/assignments/{asmtId}/documents/comments/{commentId}/like` | 문서 댓글 좋아요 등록 API |

#### DocumentManagementController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/assignments/{asmtId}/documents` | 문서 등록/실행 API |
| GET | `/api/v1/documents/{docId}` | 문서 조회 API |
| GET | `/api/v1/documents/{docId}` | 문서 조회 API |
| GET | `/api/v1/documents/{docId}/content` | 문서 조회 API |
| PUT | `/api/v1/documents/{docId}` | 문서 수정 API |
| DELETE | `/api/v1/documents/{docId}` | 문서 삭제 API |
| GET | `/api/v1/assignments/{asmtId}/documents` | 문서 조회 API |
| GET | `/api/v1/assignments/{asmtId}/documents/recent` | 최근 수정 문서 목록 조회 API |
| POST | `/api/v1/documents/{docId}/lock` | 문서 편집 잠금 API |
| POST | `/api/v1/documents/{docId}/unlock` | 문서 편집 잠금 해제 API |
| GET | `/api/v1/assignments/{asmtId}/documents/tree` | 문서 트리 구조 조회 API |
| POST | `/api/v1/documents/{docId}/restore` | 휴지통 문서 복구 API |
| GET | `/api/v1/assignments/{asmtId}/trash` | 휴지통 문서 목록 조회 API |
| DELETE | `/api/v1/documents/{docId}/permanent` | 문서 영구 삭제 API |
| DELETE | `/api/v1/assignments/{asmtId}/trash` | 휴지통 전체 비우기 API |
| GET | `/api/v1/assignments/{asmtId}/trash/count` | 휴지통 문서 개수 조회 API |
| PUT | `/api/v1/documents/{docId}/position` | 문서 위치/순서 변경 API |
| PUT | `/api/v1/assignments/{asmtId}/documents/{docId}/attachments` | 문서 첨부파일 연결 API |
| POST | `/api/v1/documents/{docId}/archive` | 문서 보관 API |

#### DocumentShareController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/documents/{docId}/share` | 문서 공유 생성 API |

#### DocumentSnapshotController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/documents/{docId}/snapshots` | 문서 스냅샷 등록/실행 API |
| GET | `/api/v1/documents/{docId}/snapshots` | 문서 스냅샷 조회 API |
| GET | `/api/v1/documents/{docId}/snapshots/{version}` | 문서 스냅샷 조회 API |
| POST | `/api/v1/documents/{docId}/snapshots/{version}:restore` | 문서 스냅샷 복원 API |
| DELETE | `/api/v1/snapshots/{snapshotId}` | 문서 스냅샷 삭제 API |

#### DocumentTemplateController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/document-templates` | 문서 템플릿 조회 API |
| GET | `/api/v1/document-templates/{templateId}` | 문서 템플릿 조회 API |
| POST | `/api/v1/document-templates` | 문서 템플릿 등록/실행 API |
| PUT | `/api/v1/document-templates/{templateId}` | 문서 템플릿 수정 API |
| DELETE | `/api/v1/document-templates/{templateId}` | 문서 템플릿 삭제 API |

### attachment

- 첨부파일 조회, 외부 공유, 파일 전송, TUS 후처리

#### AttachmentController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/assignments/{asmtId}/attachments` | 첨부파일 조회 API |
| GET | `/api/v1/assignments/{asmtId}/attachments/{fileId}` | 첨부파일 조회 API |
| GET | `/api/v1/attachments/{fileId}` | 첨부파일 조회 API |
| GET | `/api/v1/attachments/search` | 첨부파일 조회 API |
| GET | `/api/v1/attachments` | 첨부파일 조회 API |
| GET | `/api/v1/documents/{docId}/attachments` | 첨부파일 조회 API |
| GET | `/api/v1/assignments/{asmtId}/documents/{docId}/attachments` | 첨부파일 조회 API |
| GET | `/api/v1/assignments/{asmtId}/attachments:filterByType` | 유형별 첨부파일 조회 API |
| GET | `/api/v1/files/{fileId}` | 첨부파일 조회 API |
| PUT | `/api/v1/attachments/{fileId}/asmt-id` | 첨부파일 과제 연결 변경 API |

#### ExternalFileShareController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/files/{fileId}/share` | 외부 파일 공유 링크 생성 API |
| GET | `/api/v1/external/files/download` | 외부 공유 파일 다운로드 API |

#### FileTransferController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/files/transfer-from-naver` | 네이버 원본 파일 이관 API |
| POST | `/api/v1/files/transfer-from-naver-stream` | 네이버 스트림 파일 이관 API |
| GET | `/api/v1/files/transfer/status/{tusId}` | 파일 이관 상태 조회 API |

#### TusdCallbackController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/internal/tusd` | TUS 업로드 후처리 콜백 처리 API |
| POST | `/internal/tusd/finish` | TUS 업로드 후처리 콜백 처리 API |
| POST | `/internal/tusd/failed` | TUS 업로드 후처리 콜백 처리 API |
| POST | `/internal/tusd/deleted` | TUS 업로드 후처리 콜백 처리 API |

### auth

- 로그인, 토큰, 권한, SSO, 공개 문서 권한

#### ApiAuthTestController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/test/api-auth/token` | API 인증 테스트 조회 API |
| GET | `/api/v1/test/api-auth/health` | API 인증 테스트 검증/확인 API |

#### AuthController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/auth/keycloak/login` | Keycloak 로그인 API |
| POST | `/api/v1/auth/keycloak/token` | Authorization Code 토큰 교환 API |
| POST | `/api/v1/auth/keycloak/refresh` | Keycloak 인증 토큰 갱신 API |
| POST | `/api/v1/auth/keycloak/revoke` | Keycloak 토큰 폐기 API |
| GET | `/api/v1/auth/keycloak/userinfo` | Keycloak 사용자 정보 조회 API |
| GET | `/api/v1/auth/keycloak/authorize` | Keycloak 로그인 URL 생성 API |
| POST | `/api/v1/auth/keycloak/callback` | Keycloak 인증 콜백 처리 API |
| GET | `/api/v1/auth/keycloak/logout` | Keycloak 로그아웃 URL 생성 API |

#### AuthorityManagementController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/authorities` | 권한 그룹/권한 조회 API |
| GET | `/api/v1/authorities/groups` | 권한 그룹/권한 조회 API |
| GET | `/api/v1/authorities/groups/{authrtGroupId}` | 권한 그룹/권한 조회 API |
| POST | `/api/v1/authorities/groups` | 권한 그룹/권한 등록/실행 API |
| PUT | `/api/v1/authorities/groups/{authrtGroupId}` | 권한 그룹/권한 수정 API |
| DELETE | `/api/v1/authorities/groups/{authrtGroupId}` | 권한 그룹/권한 삭제 API |
| POST | `/api/v1/authorities` | 권한 그룹/권한 등록/실행 API |
| POST | `/api/v1/authorities/assign` | 사용자 권한 할당 API |
| DELETE | `/api/v1/authorities/assign/{accountId}` | 권한 그룹/권한 삭제 API |
| GET | `/api/v1/authorities/users/{accountId}` | 권한 그룹/권한 조회 API |
| GET | `/api/v1/authorities/validate/{accountId}/{authorityId}` | 사용자 권한 유효성 확인 API |

#### BusinessAuthorityController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/business-authority/assignments/{asmtId}/permissions` | 업무 권한 조회 API |
| POST | `/api/v1/business-authority/assignments/{asmtId}/permissions/user` | 과제 사용자 권한 부여 API |
| POST | `/api/v1/business-authority/assignments/{asmtId}/permissions/users` | 과제 사용자 권한 일괄 부여 API |
| POST | `/api/v1/business-authority/assignments/{asmtId}/permissions/department` | 과제 부서 권한 부여 API |
| POST | `/api/v1/business-authority/assignments/{asmtId}/permissions/departments` | 과제 부서 권한 일괄 부여 API |
| PUT | `/api/v1/business-authority/assignments/{asmtId}/permissions` | 업무 권한 수정 API |
| DELETE | `/api/v1/business-authority/assignments/{asmtId}/permissions` | 업무 권한 삭제 API |
| DELETE | `/api/v1/business-authority/assignments/{asmtId}/permissions/department` | 업무 권한 삭제 API |
| DELETE | `/api/v1/business-authority/assignments/{asmtId}/permissions/user` | 업무 권한 삭제 API |
| GET | `/api/v1/business-authority/my-assignments` | 업무 권한 조회 API |
| GET | `/api/v1/business-authority/department/{deptCode}/assignments` | 업무 권한 조회 API |
| POST | `/api/v1/business-authority/documents/{docId}/public` | 문서 공개 링크 생성 API |
| GET | `/api/v1/business-authority/public/{docPublicId}` | 공개 문서 접근 API |
| GET | `/api/v1/business-authority/my-public-documents` | 내가 공개한 문서 목록 조회 API |
| DELETE | `/api/v1/business-authority/documents/public/{docPublicId}` | 업무 권한 삭제 API |

#### CryptoTestController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/admin/crypto/basic` | 암호화 기본 동작 테스트 API |
| POST | `/admin/crypto/encrypt` | 암호화 테스트 API |
| POST | `/admin/crypto/decrypt` | 복호화 테스트 API |
| POST | `/admin/crypto/verify-password` | 비밀번호 검증 테스트 API |

#### IopSsoController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/sso/handleToken` | SSO 콜백 처리 API |
| GET | `/sso/enPass` | SSO 조회 API |
| GET | `/sso/enNormal` | SSO 조회 API |
| GET | `/sso/deNormal` | SSO 조회 API |

#### KeycloakController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/keycloak/userIdLogin` | 사용자 ID 기반 Keycloak 로그인 API |

#### SaasAuthController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/saas/auth-token` | SaaS 연동용 인증 토큰 조회 API |

### admin

- 관리자 운영, 감사, 공지, 배너, 역할, 사용자 운영

#### AdminAssignmentController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/assignments` | 과제/업무공간 조회 API |
| POST | `/api/admin/assignments/transfer-owner` | 관리자 과제 담당자 이관 API |
| PUT | `/api/admin/workspaces/{workspaceId}` | 과제/업무공간 수정 API |
| GET | `/api/admin/assignments/{asmtId}` | 과제/업무공간 조회 API |
| GET | `/api/admin/assignments/{asmtId}/attachments` | 관리자 과제 첨부파일 조회 API |
| POST | `/api/admin/assignments/{asmtId}/restore` | 관리자 삭제 과제 복구 API |

#### AdminUserController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/users/{accountId}/iop-dn` | 관리자 사용자 DN 조회 API |
| PUT | `/api/admin/users/{accountId}/iop-dn` | 관리자 사용자 DN 수정 API |

#### AsmtTkovController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/asmt-takeovers` | 과제 인수인계 목록 조회 API |
| GET | `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}` | 과제 인수인계 상세 조회 API |
| POST | `/api/admin/asmt-takeovers` | 과제 인수인계 등록 API |
| PUT | `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}` | 과제 인수인계 수정 API |
| DELETE | `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}` | 과제 인수인계 삭제 API |
| GET | `/api/admin/asmt-takeovers/by-assessment/{asmtId}` | 과제별 인수인계 조회 API |
| GET | `/api/admin/asmt-takeovers/by-handover-person/{hnovPicAcntId}` | 인계자 기준 인수인계 조회 API |
| GET | `/api/admin/asmt-takeovers/by-acceptor/{acptnPicAcntId}` | 인수자 기준 인수인계 조회 API |
| GET | `/api/admin/asmt-takeovers/ongoing-count/{accountId}` | 진행중 인수인계 건수 조회 API |

#### AuditLogController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/audit-logs` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/{logId}` | 감사 로그 조회 API |
| POST | `/api/admin/audit-logs` | 감사 로그 수동 등록 API |
| GET | `/api/admin/audit-logs/type/{logType}` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/user` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/date-range` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/ip/{ipAddress}` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/severity/{severity}` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/resource` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/category` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/errors` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/login` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/files` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/workspaces` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/roles` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/recent` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/session/{sessionId}` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/time-range` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/resource-history` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/anomalous-access` | 감사 로그 조회 API |
| GET | `/api/admin/audit-logs/stats/daily` | 감사 로그 통계 조회 API |
| GET | `/api/admin/audit-logs/stats/users` | 감사 로그 통계 조회 API |
| GET | `/api/admin/audit-logs/stats/log-types` | 감사 로그 통계 조회 API |
| GET | `/api/admin/audit-logs/stats/severity` | 감사 로그 통계 조회 API |
| GET | `/api/admin/audit-logs/export/excel` | 감사 로그 다운로드 API |
| DELETE | `/api/admin/audit-logs/cleanup` | 감사 로그 삭제 API |
| POST | `/api/admin/audit-logs/archive` | 감사 로그 보관 API |
| POST | `/api/admin/audit-logs/test-log` | 감사 로그 테스트 생성 API |

#### BannerController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/banners` | 배너 조회 API |
| GET | `/api/admin/banners/{bnrId}` | 배너 조회 API |
| POST | `/api/admin/banners/register` | 배너 등록 API |
| PUT | `/api/admin/banners/{bnrId}` | 배너 수정 API |
| DELETE | `/api/admin/banners/{bnrId}` | 배너 삭제 API |
| GET | `/api/admin/banners/active` | 배너 조회 API |
| PUT | `/api/admin/banners/{bnrId}/activation` | 배너 활성화 상태 변경 API |
| DELETE | `/api/admin/banners/batch` | 배너 일괄 삭제 API |
| PATCH | `/api/admin/banners/batch/status` | 배너 상태 일괄 변경 API |
| GET | `/api/admin/banners/author/{authorId}` | 배너 조회 API |
| GET | `/api/admin/banners/daterange` | 배너 조회 API |
| PATCH | `/api/admin/banners/{bnrId}/links` | 배너 링크 수정 API |
| PUT | `/api/admin/banners/{bnrId}/order` | 배너 노출 순서 변경 API |
| POST | `/api/admin/banners/reorder` | 배너 순서 재정렬 API |

#### EmailController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/email/templates` | 이메일 템플릿 목록 조회 API |
| GET | `/api/admin/email/templates/{templateId}` | 이메일 템플릿 상세 조회 API |
| POST | `/api/admin/email/templates` | 이메일 템플릿 등록 API |
| PUT | `/api/admin/email/templates/{templateId}` | 이메일 템플릿 수정 API |
| DELETE | `/api/admin/email/templates/{templateId}` | 이메일 템플릿 삭제 API |
| PUT | `/api/admin/email/templates/{templateId}/status` | 이메일 템플릿 활성화 상태 변경 API |
| GET | `/api/admin/email/templates/active` | 활성 이메일 템플릿 조회 API |
| GET | `/api/admin/email/templates/type/{templateType}` | 유형별 이메일 템플릿 조회 API |
| POST | `/api/admin/email/templates/{templateId}/preview` | 이메일 템플릿 미리보기 API |
| POST | `/api/admin/email/send/template` | 템플릿 기반 이메일 발송 API |
| POST | `/api/admin/email/send/direct` | 직접 작성 이메일 발송 API |
| POST | `/api/admin/email/send/bulk` | 대량 이메일 발송 API |
| GET | `/api/admin/email/logs` | 이메일 발송 로그 목록 조회 API |
| GET | `/api/admin/email/logs/{emailLogId}` | 이메일 발송 로그 상세 조회 API |
| POST | `/api/admin/email/logs/retry` | 실패 이메일 재발송 API |
| GET | `/api/admin/email/logs/user/{recipientUserId}` | 사용자별 이메일 로그 조회 API |
| GET | `/api/admin/email/logs/recent` | 최근 이메일 로그 조회 API |
| GET | `/api/admin/email/logs/pending` | 발송 대기 이메일 조회 API |
| GET | `/api/admin/email/statistics/period` | 기간별 이메일 통계 조회 API |
| GET | `/api/admin/email/statistics/type` | 유형별 이메일 통계 조회 API |
| GET | `/api/admin/email/statistics/template` | 템플릿별 이메일 통계 조회 API |
| GET | `/api/admin/email/statistics/summary` | 이메일 통계 요약 조회 API |
| POST | `/api/admin/email/validate-email` | 이메일 주소 유효성 검증 API |
| POST | `/api/admin/email/extract-variables` | 템플릿 변수 추출 API |
| POST | `/api/admin/email/substitute-variables` | 템플릿 변수 치환 API |

#### EventProduceController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/kafka/send` | Kafka 테스트 이벤트 발행 API |

#### ExternalUserController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/external-users` | 외부 사용자 조회 API |
| GET | `/api/admin/external-users/{accountId}` | 외부 사용자 조회 API |
| POST | `/api/admin/external-users` | 외부 사용자 등록 API |
| PUT | `/api/admin/external-users/{accountId}` | 외부 사용자 수정 API |
| POST | `/api/admin/external-users/removeUser` | 외부 사용자 삭제 처리 API |
| GET | `/api/admin/external-users/institutions` | 외부 사용자 기관 목록 조회 API |
| GET | `/api/admin/external-users/institutions/{institutionCode}/departments` | 기관별 부서 목록 조회 API |
| POST | `/api/admin/external-users/{accountId}/send-invitation` | 외부 사용자 초대 메일 발송 API |
| GET | `/api/admin/external-users/generate-userId` | 외부 사용자 ID 생성 API |
| POST | `/api/admin/external-users/checkInitPassword` | 외부 사용자 검증/확인 API |
| POST | `/api/admin/external-users/modifyInitPassword` | 외부 사용자 초기 비밀번호 변경 API |

#### InternalUserController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/internal-users` | 내부 사용자 조회 API |
| GET | `/api/admin/internal-users/{accountId}` | 내부 사용자 조회 API |
| POST | `/api/admin/internal-users` | 내부 사용자 등록 API |
| PUT | `/api/admin/internal-users/{accountId}` | 내부 사용자 수정 API |
| DELETE | `/api/admin/internal-users/{accountId}` | 내부 사용자 삭제 API |
| GET | `/api/admin/internal-users/institutions` | 내부 사용자 기관 목록 조회 API |
| GET | `/api/admin/internal-users/institutions/{institutionCode}/departments` | 기관별 부서 목록 조회 API |
| PATCH | `/api/admin/internal-users/{accountId}/last-access` | 내부 사용자 최종 접속일 갱신 API |
| PATCH | `/api/admin/internal-users/{accountId}/dn` | 내부 사용자 DN 갱신 API |

#### NoticeController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/notices` | 공지사항 조회 API |
| GET | `/api/admin/notices/{ntcMttrId}` | 공지사항 조회 API |
| GET | `/api/admin/notices/byNo/{ntcMttrNo}` | 공지사항 조회 API |
| DELETE | `/api/admin/notices/{ntcMttrId}` | 공지사항 삭제 API |
| POST | `/api/admin/notices` | 공지사항 등록/실행 API |
| PUT | `/api/admin/notices/{ntcMttrId}` | 공지사항 수정 API |
| GET | `/api/admin/notices/recent` | 최근 공지사항 조회 API |
| GET | `/api/admin/notices/has-new` | 신규 공지 존재 여부 확인 API |

#### RoleController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/roles` | 역할/접근범위 조회 API |
| GET | `/api/admin/roles/{roleId}` | 역할/접근범위 조회 API |
| POST | `/api/admin/roles` | 역할 등록 API |
| POST | `/api/admin/roles/modify` | 역할 수정 API |
| POST | `/api/admin/roles/delete` | 역할 삭제 API |
| GET | `/api/admin/roles/{roleId}/users` | 역할/접근범위 조회 API |
| POST | `/api/admin/roles/{roleId}/users` | 사용자 역할 부여 API |
| POST | `/api/admin/roles/removeUser` | 사용자 역할 해제 API |
| POST | `/api/admin/roles/modifyExp` | 역할 설명 수정 API |
| GET | `/api/admin/roles/getUserScopeList/{accountId}` | 사용자 스코프 목록 조회 API |
| PUT | `/api/admin/roles/users/{userId}/change-role` | 사용자 역할 변경 API |
| GET | `/api/admin/roles/users/{userId}` | 역할/접근범위 조회 API |
| GET | `/api/admin/roles/access-scopes` | 접근 범위 목록 조회 API |
| GET | `/api/admin/roles/{roleId}/access-scopes` | 역할별 접근 범위 조회 API |
| PUT | `/api/admin/roles/{roleId}/access-scopes` | 역할별 접근 범위 설정 API |
| GET | `/api/admin/roles/check-name` | 역할명 중복 확인 API |
| GET | `/api/admin/roles/by-type/{roleType}` | 역할/접근범위 조회 API |
| POST | `/api/admin/roles/{roleId}/users/bulk-assign` | 역할 사용자 일괄 할당 API |
| GET | `/api/admin/roles/{roleId}/user-count` | 역할 사용자 수 조회 API |

#### UserActvController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/admin/user-activities` | 사용자 활동 이력 조회 API |
| GET | `/api/admin/user-activities/{acntId}/{hstrySn}` | 사용자 활동 이력 조회 API |
| GET | `/api/admin/user-activities/user/{acntId}` | 사용자 활동 이력 조회 API |
| POST | `/api/admin/user-activities` | 사용자 활동 이력 등록 API |
| PUT | `/api/admin/user-activities/{acntId}/{hstrySn}` | 사용자 활동 이력 수정 API |
| DELETE | `/api/admin/user-activities/{acntId}/{hstrySn}` | 사용자 활동 이력 삭제 API |
| DELETE | `/api/admin/user-activities/user/{acntId}` | 사용자 활동 이력 삭제 API |
| GET | `/api/admin/user-activities/errors` | 오류 활동 이력 조회 API |
| GET | `/api/admin/user-activities/period` | 기간별 활동 이력 조회 API |
| POST | `/api/admin/user-activities/record` | 사용자 활동 기록 저장 API |
| GET | `/api/admin/user-activities/statistics/{acntId}` | 사용자 활동 이력 통계 조회 API |
| GET | `/api/admin/user-activities/excel` | 사용자 활동 이력 다운로드 API |

#### WorkspaceController (legacy/inactive 의심)

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/` | 레거시 업무공간 조회 API |
| GET | `/{workspaceId}` | 레거시 업무공간 조회 API |
| POST | `/` | 레거시 업무공간 등록 API |
| PUT | `/{workspaceId}` | 레거시 업무공간 수정 API |
| DELETE | `/{workspaceId}` | 레거시 업무공간 삭제 API |
| GET | `/handover/{handoverUserId}` | 사용자별 인수인계 대상 업무공간 조회 API |
| GET | `/user/{userId}` | 레거시 업무공간 조회 API |
| GET | `/department/{deptId}` | 레거시 업무공간 조회 API |
| GET | `/check-project-code` | 프로젝트 코드 중복 확인 API |
| GET | `/handovers` | 인수인계 목록 조회 API |
| GET | `/handovers/{handoverId}` | 인수인계 상세 조회 API |
| POST | `/handovers` | 인수인계 등록 API |
| PUT | `/handovers/{handoverId}` | 인수인계 수정 API |
| DELETE | `/handovers/{handoverId}` | 인수인계 삭제 API |
| PUT | `/handovers/{handoverId}/status` | 인수인계 상태 변경 API |
| POST | `/handovers/{handoverId}/complete` | 인수인계 완료 처리 API |
| GET | `/{workspaceId}/handover-history` | 업무공간 인수인계 이력 조회 API |
| GET | `/handovers/pending-count/{receiveUserId}` | 대기중 인수인계 건수 조회 API |

### api

- 공통 상태 점검과 통합 이력 조회

#### HealthController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/health` | 서비스 헬스체크 API |

#### UnifiedHistoryController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/history/audit/events` | 감사 이벤트 이력 저장 API |
| GET | `/api/v1/history/audit/events` | 통합 이력 조회 API |
| GET | `/api/v1/history/entity/{tableName}/{recordId}` | 통합 이력 조회 API |
| GET | `/api/v1/history/assignments/{asmtId}` | 통합 이력 조회 API |
| GET | `/api/v1/history/assignments/{asmtId}/pic` | 통합 이력 조회 API |
| GET | `/api/v1/history/user/{accountId}` | 통합 이력 조회 API |
| GET | `/api/v1/history/user/{accountId}/statistics` | 사용자 이력 통계 조회 API |
| POST | `/api/v1/history/complex-change` | 복합 변경 이력 저장 API |
| GET | `/api/v1/history/complex-change/{entityType}/{entityId}` | 통합 이력 조회 API |
| GET | `/api/v1/history/recent` | 최근 통합 이력 조회 API |
| GET | `/api/v1/history/statistics` | 통합 이력 통계 조회 API |
| GET | `/api/v1/history/anomalies` | 이상 징후 이력 조회 API |
| GET | `/api/v1/history/todo/{todoId}` | 할 일 이력 조회 API |
| GET | `/api/v1/history/permission/{targetId}` | 권한 변경 이력 조회 API |

### brm

- BRM 분류체계와 과제-분류 연계

#### BrmController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/brm/purpose` | BRM 목적 분류 목록 조회 API |
| GET | `/api/v1/brm/purpose/{clsfSystId}` | BRM 목적 분류 상세 조회 API |
| GET | `/api/v1/brm/purpose/{parentId}/children` | BRM 목적 하위 분류 조회 API |
| GET | `/api/v1/brm/purpose/tree` | BRM 목적 트리 조회 API |
| GET | `/api/v1/brm/function` | BRM 기능 분류 목록 조회 API |
| GET | `/api/v1/brm/function/{clsfSystId}` | BRM 기능 분류 상세 조회 API |
| GET | `/api/v1/brm/function/{parentId}/children` | BRM 기능 하위 분류 조회 API |
| GET | `/api/v1/brm/function/tree` | BRM 기능 트리 조회 API |
| GET | `/api/v1/brm/stats` | BRM 분류 통계 조회 API |

#### BrmRelationController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/brm-relations/assignments/{asmtId}/purposes` | 과제-목적 BRM 연계 조회 API |
| POST | `/api/v1/brm-relations/assignments/purposes` | 과제-목적 BRM 연계 등록 API |
| DELETE | `/api/v1/brm-relations/assignments/purposes/{asmtPurposeId}` | BRM 연계 삭제 API |
| GET | `/api/v1/brm-relations/assignments/{asmtId}/functions` | 과제-기능 BRM 연계 조회 API |
| POST | `/api/v1/brm-relations/assignments/functions` | 과제-기능 BRM 연계 등록 API |
| DELETE | `/api/v1/brm-relations/assignments/functions/{asmtFunctionId}` | BRM 연계 삭제 API |
| GET | `/api/v1/brm-relations/assignments/{asmtId}/organizations` | 과제-조직 BRM 연계 조회 API |
| POST | `/api/v1/brm-relations/assignments/organizations` | 과제-조직 BRM 연계 등록 API |
| DELETE | `/api/v1/brm-relations/assignments/organizations/{asmtOgnzId}` | BRM 연계 삭제 API |
| POST | `/api/v1/brm-relations/assignments/bulk` | BRM 연계 일괄 등록 API |

### ihb

- 대시보드, 회원, 마이페이지, 공통 공유 성격 API

#### MemberController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/member/iopJoin` | 회원 가입 API |
| POST | `/api/member/iopModify` | 회원 정보 수정 API |

#### MypageController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/dashboard/mypage/userImgUpload` | 마이페이지 프로필 이미지 업로드 API |
| GET | `/api/dashboard/mypage/{accountId}` | 마이페이지 조회 API |
| POST | `/api/dashboard/mypage/receiveSettingRegist` | 마이페이지 수신 설정 저장 API |

#### SaasController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/dashboard/saas/checkIopCount/{iopId}` | IOP 사용 건수 확인 API |
| GET | `/api/dashboard/saas/checkIopIdByAccountId/{accountId}` | 계정 기준 IOP ID 확인 API |
| POST | `/api/dashboard/saas/updateCommonIopId` | 공통 IOP ID 변경 API |
| POST | `/api/dashboard/saas/updateMainIopId` | 메인 IOP ID 변경 API |
| GET | `/api/dashboard/saas/getViewData/{accountId}` | SaaS 대시보드 조회 API |

#### ShareController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/share/notices/has-new` | 공유 포털 신규 공지 존재 여부 확인 API |
| GET | `/api/share/notices` | 공유 포털 공지 목록 조회 API |
| GET | `/api/share/notices/{ntcMttrId}` | 공유 포털 공지 상세 조회 API |
| POST | `/api/share/external-users/checkInitPassword` | 공유 포털 외부사용자 초기 비밀번호 확인 API |
| PATCH | `/api/share/internal-users/{accountId}/last-access` | 공유 포털 내부사용자 최종 접속일 갱신 API |
| GET | `/api/share/roles/getUserScopeList/{accountId}` | 공유 포털 사용자 스코프 조회 API |
| POST | `/api/share/external-users/modifyInitPassword` | 공유 포털 외부사용자 초기 비밀번호 변경 API |
| GET | `/api/share/assignments/{asmtId}` | 공유 포털 과제 상세 조회 API |

### mail

- Works 메일/메시지 발송

#### WorksMailController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/works/mail-send` | Works 메일/메시지 발송 API |
| POST | `/api/v1/works/message-send` | Works 메일/메시지 발송 API |

### rag

- RAG 작업 요청, 상태 조회, 외부 콜백

#### RagJobController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/assignments/{asmtId}/rag-jobs` | RAG 작업 생성 API |
| GET | `/api/v1/assignments/{asmtId}/rag-jobs/{id}` | RAG 작업 조회 API |
| GET | `/api/v1/assignments/{asmtId}/rag-jobs` | RAG 작업 조회 API |
| GET | `/api/v1/assignments/{asmtId}/rag-jobs:stats` | RAG 작업 통계 조회 API |
| POST | `/api/v1/assignments/{asmtId}/rag-jobs/status` | RAG 작업 상태 변경 API |
| POST | `/api/v1/external/rag-callbacks` | RAG 작업 콜백 처리 API |

### schedule

- 배치 수동 실행

#### BatchController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/batch/memberSync` | 회원 동기화 배치 수동 실행 API |

### share

- 외부 공유 링크 생성/회수/공개 접근

#### ExternalShareController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| POST | `/api/v1/external-shares` | 외부 공유 링크 생성 API |
| DELETE | `/api/v1/external-shares/{shareId}` | 외부 공유 링크 삭제 API |
| GET | `/public/share/{shareId}` | 외부 공유 링크 공개 접근 API |

### todo

- 할 일 관리

#### TodoController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/assignments/{asmtId}/todos` | 할 일 조회 API |
| GET | `/api/v1/todos/{todoId}` | 할 일 조회 API |
| POST | `/api/v1/assignments/{asmtId}/todos` | 할 일 등록 API |
| PUT | `/api/v1/todos/{todoId}` | 할 일 수정 API |
| DELETE | `/api/v1/todos/{todoId}` | 할 일 삭제 API |

### uscm

- 사용자/기관 검색

#### UscmSearchController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/uscm/users/search` | USCM 사용자 검색 API |
| GET | `/api/v1/uscm/orgs/search` | USCM 기관 검색 API |

### user

- 사용자/조직 조회와 개인 DN 관리

#### OrganizationController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/organizations` | 조직 조회 API |
| GET | `/api/v1/organizations/{oucode}` | 조직 조회 API |
| GET | `/api/v1/organizations/search` | 조직 조회 API |
| GET | `/api/v1/organizations/parent/{parentOucode}` | 조직 조회 API |
| GET | `/api/v1/organizations/level/{level}` | 조직 조회 API |
| GET | `/api/v1/organizations/root` | 조직 조회 API |
| GET | `/api/v1/organizations/tree/{rootOucode}` | 조직 조회 API |

#### UserController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/users` | 사용자 조회 API |
| GET | `/api/v1/users/{accountId}` | 사용자 조회 API |
| GET | `/api/v1/users/search` | 사용자 조회 API |
| GET | `/api/v1/users/status` | 사용자 조회 API |
| PUT | `/api/v1/users/{accountId}/frst-crt-dt` | 사용자 최초 생성일 수정 API |
| GET | `/api/v1/users/me/iop-dn` | 내 IOP DN 조회 API |
| PUT | `/api/v1/users/me/iop-dn` | 내 IOP DN 수정 API |

### voc

- VOC 등록, 상태 변경, 댓글 처리

#### VocController

| Method | Path | 기능 요약 |
| --- | --- | --- |
| GET | `/api/v1/voc` | VOC 조회 API |
| GET | `/api/v1/voc/{vocId}` | VOC 조회 API |
| POST | `/api/v1/voc` | VOC 등록 API |
| PUT | `/api/v1/voc/{vocId}` | VOC 수정 API |
| DELETE | `/api/v1/voc/{vocId}` | VOC 삭제 API |
| PATCH | `/api/v1/voc/{vocId}/status` | VOC 상태 변경 API |
| POST | `/api/v1/voc/{vocId}/comments` | VOC 댓글 등록 API |
| PUT | `/api/v1/voc/{vocId}/comments/{cmntId}` | VOC 수정 API |
| DELETE | `/api/v1/voc/{vocId}/comments/{cmntId}` | VOC 삭제 API |
| GET | `/api/v1/voc/types` | VOC 조회 API |

