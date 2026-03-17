# iop-iw-api API 전수 목록

## 목적

- `iop-iw-api` 소스 기준 전체 HTTP 엔드포인트를 컨트롤러별로 정리한다.
- 기준 경로: `workspace-egov/iw_group/iop-iw-api/src/main/java`
- 추출 기준: `*Controller.java` 내부 `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`

## 집계 결과

- 재추출 엔드포인트 수: `370`개
- 컨트롤러 수: `49`개
- 기존 요약 문서의 `약 371개`와는 1건 차이가 난다. 현재 문서는 2026-03-12 기준 소스 재추출 결과를 기준으로 한다.
- `WorkspaceController`는 파일 내 `@RestController`, `@RequestMapping`이 주석 처리된 레거시 코드로 보이며, 아래 목록에는 참고용으로 포함했다.

## 컨트롤러별 목록

### AdminAssignmentController (6)
- [GET] `/api/admin/assignments`
- [POST] `/api/admin/assignments/transfer-owner`
- [PUT] `/api/admin/workspaces/{workspaceId}`
- [GET] `/api/admin/assignments/{asmtId}`
- [GET] `/api/admin/assignments/{asmtId}/attachments`
- [POST] `/api/admin/assignments/{asmtId}/restore`

### AdminUserController (2)
- [GET] `/api/admin/users/{accountId}/iop-dn`
- [PUT] `/api/admin/users/{accountId}/iop-dn`

### ApiAuthTestController (2)
- [GET] `/api/v1/test/api-auth/token`
- [GET] `/api/v1/test/api-auth/health`

### AsmtTkovController (9)
- [GET] `/api/admin/asmt-takeovers`
- [GET] `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}`
- [POST] `/api/admin/asmt-takeovers`
- [PUT] `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}`
- [DELETE] `/api/admin/asmt-takeovers/{asmtId}/{tkovSn}`
- [GET] `/api/admin/asmt-takeovers/by-assessment/{asmtId}`
- [GET] `/api/admin/asmt-takeovers/by-handover-person/{hnovPicAcntId}`
- [GET] `/api/admin/asmt-takeovers/by-acceptor/{acptnPicAcntId}`
- [GET] `/api/admin/asmt-takeovers/ongoing-count/{accountId}`

### AssignmentController (9)
- [GET] `/api/v1/assignments`
- [GET] `/api/v1/assignments/my-participated`
- [GET] `/api/v1/assignments/{asmtId}`
- [POST] `/api/v1/assignments`
- [PUT] `/api/v1/assignments/{asmtId}`
- [DELETE] `/api/v1/assignments/{asmtId}`
- [POST] `/api/v1/assignments/{asmtId}/approve`
- [POST] `/api/v1/assignments/{asmtId}/reject`
- [POST] `/api/v1/assignments/transfer-owner`

### AttachmentController (10)
- [GET] `/api/v1/assignments/{asmtId}/attachments`
- [GET] `/api/v1/assignments/{asmtId}/attachments/{fileId}`
- [GET] `/api/v1/attachments/{fileId}`
- [GET] `/api/v1/attachments/search`
- [GET] `/api/v1/attachments`
- [GET] `/api/v1/documents/{docId}/attachments`
- [GET] `/api/v1/assignments/{asmtId}/documents/{docId}/attachments`
- [GET] `/api/v1/assignments/{asmtId}/attachments:filterByType`
- [GET] `/api/v1/files/{fileId}`
- [PUT] `/api/v1/attachments/{fileId}/asmt-id`

### AuditLogController (28)
- [GET] `/api/admin/audit-logs`
- [GET] `/api/admin/audit-logs/{logId}`
- [POST] `/api/admin/audit-logs`
- [GET] `/api/admin/audit-logs/type/{logType}`
- [GET] `/api/admin/audit-logs/user`
- [GET] `/api/admin/audit-logs/date-range`
- [GET] `/api/admin/audit-logs/ip/{ipAddress}`
- [GET] `/api/admin/audit-logs/severity/{severity}`
- [GET] `/api/admin/audit-logs/resource`
- [GET] `/api/admin/audit-logs/category`
- [GET] `/api/admin/audit-logs/errors`
- [GET] `/api/admin/audit-logs/login`
- [GET] `/api/admin/audit-logs/files`
- [GET] `/api/admin/audit-logs/workspaces`
- [GET] `/api/admin/audit-logs/roles`
- [GET] `/api/admin/audit-logs/recent`
- [GET] `/api/admin/audit-logs/session/{sessionId}`
- [GET] `/api/admin/audit-logs/time-range`
- [GET] `/api/admin/audit-logs/resource-history`
- [GET] `/api/admin/audit-logs/anomalous-access`
- [GET] `/api/admin/audit-logs/stats/daily`
- [GET] `/api/admin/audit-logs/stats/users`
- [GET] `/api/admin/audit-logs/stats/log-types`
- [GET] `/api/admin/audit-logs/stats/severity`
- [GET] `/api/admin/audit-logs/export/excel`
- [DELETE] `/api/admin/audit-logs/cleanup`
- [POST] `/api/admin/audit-logs/archive`
- [POST] `/api/admin/audit-logs/test-log`

### AuthController (8)
- [POST] `/api/v1/auth/keycloak/login`
- [POST] `/api/v1/auth/keycloak/token`
- [POST] `/api/v1/auth/keycloak/refresh`
- [POST] `/api/v1/auth/keycloak/revoke`
- [GET] `/api/v1/auth/keycloak/userinfo`
- [GET] `/api/v1/auth/keycloak/authorize`
- [POST] `/api/v1/auth/keycloak/callback`
- [GET] `/api/v1/auth/keycloak/logout`

### AuthorityManagementController (11)
- [GET] `/api/v1/authorities`
- [GET] `/api/v1/authorities/groups`
- [GET] `/api/v1/authorities/groups/{authrtGroupId}`
- [POST] `/api/v1/authorities/groups`
- [PUT] `/api/v1/authorities/groups/{authrtGroupId}`
- [DELETE] `/api/v1/authorities/groups/{authrtGroupId}`
- [POST] `/api/v1/authorities`
- [POST] `/api/v1/authorities/assign`
- [DELETE] `/api/v1/authorities/assign/{accountId}`
- [GET] `/api/v1/authorities/users/{accountId}`
- [GET] `/api/v1/authorities/validate/{accountId}/{authorityId}`

### BannerController (14)
- [GET] `/api/admin/banners`
- [GET] `/api/admin/banners/{bnrId}`
- [POST] `/api/admin/banners/register`
- [PUT] `/api/admin/banners/{bnrId}`
- [DELETE] `/api/admin/banners/{bnrId}`
- [GET] `/api/admin/banners/active`
- [PUT] `/api/admin/banners/{bnrId}/activation`
- [DELETE] `/api/admin/banners/batch`
- [PATCH] `/api/admin/banners/batch/status`
- [GET] `/api/admin/banners/author/{authorId}`
- [GET] `/api/admin/banners/daterange`
- [PATCH] `/api/admin/banners/{bnrId}/links`
- [PUT] `/api/admin/banners/{bnrId}/order`
- [POST] `/api/admin/banners/reorder`

### BatchController (1)
- [GET] `/api/batch/memberSync`

### BrmController (9)
- [GET] `/api/v1/brm/purpose`
- [GET] `/api/v1/brm/purpose/{clsfSystId}`
- [GET] `/api/v1/brm/purpose/{parentId}/children`
- [GET] `/api/v1/brm/purpose/tree`
- [GET] `/api/v1/brm/function`
- [GET] `/api/v1/brm/function/{clsfSystId}`
- [GET] `/api/v1/brm/function/{parentId}/children`
- [GET] `/api/v1/brm/function/tree`
- [GET] `/api/v1/brm/stats`

### BrmRelationController (10)
- [GET] `/api/v1/brm-relations/assignments/{asmtId}/purposes`
- [POST] `/api/v1/brm-relations/assignments/purposes`
- [DELETE] `/api/v1/brm-relations/assignments/purposes/{asmtPurposeId}`
- [GET] `/api/v1/brm-relations/assignments/{asmtId}/functions`
- [POST] `/api/v1/brm-relations/assignments/functions`
- [DELETE] `/api/v1/brm-relations/assignments/functions/{asmtFunctionId}`
- [GET] `/api/v1/brm-relations/assignments/{asmtId}/organizations`
- [POST] `/api/v1/brm-relations/assignments/organizations`
- [DELETE] `/api/v1/brm-relations/assignments/organizations/{asmtOgnzId}`
- [POST] `/api/v1/brm-relations/assignments/bulk`

### BusinessAuthorityController (15)
- [GET] `/api/v1/business-authority/assignments/{asmtId}/permissions`
- [POST] `/api/v1/business-authority/assignments/{asmtId}/permissions/user`
- [POST] `/api/v1/business-authority/assignments/{asmtId}/permissions/users`
- [POST] `/api/v1/business-authority/assignments/{asmtId}/permissions/department`
- [POST] `/api/v1/business-authority/assignments/{asmtId}/permissions/departments`
- [PUT] `/api/v1/business-authority/assignments/{asmtId}/permissions`
- [DELETE] `/api/v1/business-authority/assignments/{asmtId}/permissions`
- [DELETE] `/api/v1/business-authority/assignments/{asmtId}/permissions/department`
- [DELETE] `/api/v1/business-authority/assignments/{asmtId}/permissions/user`
- [GET] `/api/v1/business-authority/my-assignments`
- [GET] `/api/v1/business-authority/department/{deptCode}/assignments`
- [POST] `/api/v1/business-authority/documents/{docId}/public`
- [GET] `/api/v1/business-authority/public/{docPublicId}`
- [GET] `/api/v1/business-authority/my-public-documents`
- [DELETE] `/api/v1/business-authority/documents/public/{docPublicId}`

### CryptoTestController (4)
- [GET] `/admin/crypto/basic`
- [POST] `/admin/crypto/encrypt`
- [POST] `/admin/crypto/decrypt`
- [POST] `/admin/crypto/verify-password`

### DocumentCommentController (7)
- [POST] `/api/v1/assignments/{asmtId}/documents/comments`
- [GET] `/api/v1/assignments/{asmtId}/comments`
- [GET] `/api/v1/assignments/{asmtId}/documents/{docId}/comments`
- [GET] `/api/v1/assignments/{asmtId}/documents/comments/{commentId}/replies`
- [PUT] `/api/v1/assignments/{asmtId}/documents/comments/{commentId}`
- [DELETE] `/api/v1/assignments/{asmtId}/documents/comments/{commentId}`
- [POST] `/api/v1/assignments/{asmtId}/documents/comments/{commentId}/like`

### DocumentManagementController (19)
- [POST] `/api/v1/assignments/{asmtId}/documents`
- [GET] `/api/v1/documents/{docId}`
- [GET] `/api/v1/documents/{docId}`
- [GET] `/api/v1/documents/{docId}/content`
- [PUT] `/api/v1/documents/{docId}`
- [DELETE] `/api/v1/documents/{docId}`
- [GET] `/api/v1/assignments/{asmtId}/documents`
- [GET] `/api/v1/assignments/{asmtId}/documents/recent`
- [POST] `/api/v1/documents/{docId}/lock`
- [POST] `/api/v1/documents/{docId}/unlock`
- [GET] `/api/v1/assignments/{asmtId}/documents/tree`
- [POST] `/api/v1/documents/{docId}/restore`
- [GET] `/api/v1/assignments/{asmtId}/trash`
- [DELETE] `/api/v1/documents/{docId}/permanent`
- [DELETE] `/api/v1/assignments/{asmtId}/trash`
- [GET] `/api/v1/assignments/{asmtId}/trash/count`
- [PUT] `/api/v1/documents/{docId}/position`
- [PUT] `/api/v1/assignments/{asmtId}/documents/{docId}/attachments`
- [POST] `/api/v1/documents/{docId}/archive`

### DocumentShareController (1)
- [POST] `/api/v1/documents/{docId}/share`

### DocumentSnapshotController (5)
- [POST] `/api/v1/documents/{docId}/snapshots`
- [GET] `/api/v1/documents/{docId}/snapshots`
- [GET] `/api/v1/documents/{docId}/snapshots/{version}`
- [POST] `/api/v1/documents/{docId}/snapshots/{version}:restore`
- [DELETE] `/api/v1/snapshots/{snapshotId}`

### DocumentTemplateController (5)
- [GET] `/api/v1/document-templates`
- [GET] `/api/v1/document-templates/{templateId}`
- [POST] `/api/v1/document-templates`
- [PUT] `/api/v1/document-templates/{templateId}`
- [DELETE] `/api/v1/document-templates/{templateId}`

### EmailController (25)
- [GET] `/api/admin/email/templates`
- [GET] `/api/admin/email/templates/{templateId}`
- [POST] `/api/admin/email/templates`
- [PUT] `/api/admin/email/templates/{templateId}`
- [DELETE] `/api/admin/email/templates/{templateId}`
- [PUT] `/api/admin/email/templates/{templateId}/status`
- [GET] `/api/admin/email/templates/active`
- [GET] `/api/admin/email/templates/type/{templateType}`
- [POST] `/api/admin/email/templates/{templateId}/preview`
- [POST] `/api/admin/email/send/template`
- [POST] `/api/admin/email/send/direct`
- [POST] `/api/admin/email/send/bulk`
- [GET] `/api/admin/email/logs`
- [GET] `/api/admin/email/logs/{emailLogId}`
- [POST] `/api/admin/email/logs/retry`
- [GET] `/api/admin/email/logs/user/{recipientUserId}`
- [GET] `/api/admin/email/logs/recent`
- [GET] `/api/admin/email/logs/pending`
- [GET] `/api/admin/email/statistics/period`
- [GET] `/api/admin/email/statistics/type`
- [GET] `/api/admin/email/statistics/template`
- [GET] `/api/admin/email/statistics/summary`
- [POST] `/api/admin/email/validate-email`
- [POST] `/api/admin/email/extract-variables`
- [POST] `/api/admin/email/substitute-variables`

### EventProduceController (1)
- [GET] `/api/admin/kafka/send`

### ExternalAssignmentController (2)
- [GET] `/api/v1/external/assignments/{accountId}`
- [GET] `/api/v1/external/assignments/{accountId}/{asmtId}`

### ExternalFileShareController (2)
- [POST] `/api/v1/files/{fileId}/share`
- [GET] `/api/v1/external/files/download`

### ExternalShareController (3)
- [POST] `/api/v1/external-shares`
- [DELETE] `/api/v1/external-shares/{shareId}`
- [GET] `/public/share/{shareId}`

### ExternalUserController (11)
- [GET] `/api/admin/external-users`
- [GET] `/api/admin/external-users/{accountId}`
- [POST] `/api/admin/external-users`
- [PUT] `/api/admin/external-users/{accountId}`
- [POST] `/api/admin/external-users/removeUser`
- [GET] `/api/admin/external-users/institutions`
- [GET] `/api/admin/external-users/institutions/{institutionCode}/departments`
- [POST] `/api/admin/external-users/{accountId}/send-invitation`
- [GET] `/api/admin/external-users/generate-userId`
- [POST] `/api/admin/external-users/checkInitPassword`
- [POST] `/api/admin/external-users/modifyInitPassword`

### FileTransferController (3)
- [POST] `/api/v1/files/transfer-from-naver`
- [POST] `/api/v1/files/transfer-from-naver-stream`
- [GET] `/api/v1/files/transfer/status/{tusId}`

### HealthController (1)
- [GET] `/api/v1/health`

### InternalUserController (9)
- [GET] `/api/admin/internal-users`
- [GET] `/api/admin/internal-users/{accountId}`
- [POST] `/api/admin/internal-users`
- [PUT] `/api/admin/internal-users/{accountId}`
- [DELETE] `/api/admin/internal-users/{accountId}`
- [GET] `/api/admin/internal-users/institutions`
- [GET] `/api/admin/internal-users/institutions/{institutionCode}/departments`
- [PATCH] `/api/admin/internal-users/{accountId}/last-access`
- [PATCH] `/api/admin/internal-users/{accountId}/dn`

### IopSsoController (4)
- [POST] `/sso/handleToken`
- [GET] `/sso/enPass`
- [GET] `/sso/enNormal`
- [GET] `/sso/deNormal`

### KeycloakController (1)
- [POST] `/api/keycloak/userIdLogin`

### MemberController (2)
- [POST] `/api/member/iopJoin`
- [POST] `/api/member/iopModify`

### MypageController (3)
- [POST] `/api/dashboard/mypage/userImgUpload`
- [GET] `/api/dashboard/mypage/{accountId}`
- [POST] `/api/dashboard/mypage/receiveSettingRegist`

### NoticeController (8)
- [GET] `/api/admin/notices`
- [GET] `/api/admin/notices/{ntcMttrId}`
- [GET] `/api/admin/notices/byNo/{ntcMttrNo}`
- [DELETE] `/api/admin/notices/{ntcMttrId}`
- [POST] `/api/admin/notices`
- [PUT] `/api/admin/notices/{ntcMttrId}`
- [GET] `/api/admin/notices/recent`
- [GET] `/api/admin/notices/has-new`

### OrganizationController (7)
- [GET] `/api/v1/organizations`
- [GET] `/api/v1/organizations/{oucode}`
- [GET] `/api/v1/organizations/search`
- [GET] `/api/v1/organizations/parent/{parentOucode}`
- [GET] `/api/v1/organizations/level/{level}`
- [GET] `/api/v1/organizations/root`
- [GET] `/api/v1/organizations/tree/{rootOucode}`

### RagJobController (6)
- [POST] `/api/v1/assignments/{asmtId}/rag-jobs`
- [GET] `/api/v1/assignments/{asmtId}/rag-jobs/{id}`
- [GET] `/api/v1/assignments/{asmtId}/rag-jobs`
- [GET] `/api/v1/assignments/{asmtId}/rag-jobs:stats`
- [POST] `/api/v1/assignments/{asmtId}/rag-jobs/status`
- [POST] `/api/v1/external/rag-callbacks`

### RoleController (19)
- [GET] `/api/admin/roles`
- [GET] `/api/admin/roles/{roleId}`
- [POST] `/api/admin/roles`
- [POST] `/api/admin/roles/modify`
- [POST] `/api/admin/roles/delete`
- [GET] `/api/admin/roles/{roleId}/users`
- [POST] `/api/admin/roles/{roleId}/users`
- [POST] `/api/admin/roles/removeUser`
- [POST] `/api/admin/roles/modifyExp`
- [GET] `/api/admin/roles/getUserScopeList/{accountId}`
- [PUT] `/api/admin/roles/users/{userId}/change-role`
- [GET] `/api/admin/roles/users/{userId}`
- [GET] `/api/admin/roles/access-scopes`
- [GET] `/api/admin/roles/{roleId}/access-scopes`
- [PUT] `/api/admin/roles/{roleId}/access-scopes`
- [GET] `/api/admin/roles/check-name`
- [GET] `/api/admin/roles/by-type/{roleType}`
- [POST] `/api/admin/roles/{roleId}/users/bulk-assign`
- [GET] `/api/admin/roles/{roleId}/user-count`

### SaasAuthController (1)
- [GET] `/api/v1/saas/auth-token`

### SaasController (5)
- [GET] `/api/dashboard/saas/checkIopCount/{iopId}`
- [GET] `/api/dashboard/saas/checkIopIdByAccountId/{accountId}`
- [POST] `/api/dashboard/saas/updateCommonIopId`
- [POST] `/api/dashboard/saas/updateMainIopId`
- [GET] `/api/dashboard/saas/getViewData/{accountId}`

### ShareController (8)
- [GET] `/api/share/notices/has-new`
- [GET] `/api/share/notices`
- [GET] `/api/share/notices/{ntcMttrId}`
- [POST] `/api/share/external-users/checkInitPassword`
- [PATCH] `/api/share/internal-users/{accountId}/last-access`
- [GET] `/api/share/roles/getUserScopeList/{accountId}`
- [POST] `/api/share/external-users/modifyInitPassword`
- [GET] `/api/share/assignments/{asmtId}`

### TodoController (5)
- [GET] `/api/v1/assignments/{asmtId}/todos`
- [GET] `/api/v1/todos/{todoId}`
- [POST] `/api/v1/assignments/{asmtId}/todos`
- [PUT] `/api/v1/todos/{todoId}`
- [DELETE] `/api/v1/todos/{todoId}`

### TusdCallbackController (4)
- [POST] `/internal/tusd`
- [POST] `/internal/tusd/finish`
- [POST] `/internal/tusd/failed`
- [POST] `/internal/tusd/deleted`

### UnifiedHistoryController (14)
- [POST] `/api/v1/history/audit/events`
- [GET] `/api/v1/history/audit/events`
- [GET] `/api/v1/history/entity/{tableName}/{recordId}`
- [GET] `/api/v1/history/assignments/{asmtId}`
- [GET] `/api/v1/history/assignments/{asmtId}/pic`
- [GET] `/api/v1/history/user/{accountId}`
- [GET] `/api/v1/history/user/{accountId}/statistics`
- [POST] `/api/v1/history/complex-change`
- [GET] `/api/v1/history/complex-change/{entityType}/{entityId}`
- [GET] `/api/v1/history/recent`
- [GET] `/api/v1/history/statistics`
- [GET] `/api/v1/history/anomalies`
- [GET] `/api/v1/history/todo/{todoId}`
- [GET] `/api/v1/history/permission/{targetId}`

### UscmSearchController (2)
- [GET] `/api/v1/uscm/users/search`
- [GET] `/api/v1/uscm/orgs/search`

### UserActvController (12)
- [GET] `/api/admin/user-activities`
- [GET] `/api/admin/user-activities/{acntId}/{hstrySn}`
- [GET] `/api/admin/user-activities/user/{acntId}`
- [POST] `/api/admin/user-activities`
- [PUT] `/api/admin/user-activities/{acntId}/{hstrySn}`
- [DELETE] `/api/admin/user-activities/{acntId}/{hstrySn}`
- [DELETE] `/api/admin/user-activities/user/{acntId}`
- [GET] `/api/admin/user-activities/errors`
- [GET] `/api/admin/user-activities/period`
- [POST] `/api/admin/user-activities/record`
- [GET] `/api/admin/user-activities/statistics/{acntId}`
- [GET] `/api/admin/user-activities/excel`

### UserController (7)
- [GET] `/api/v1/users`
- [GET] `/api/v1/users/{accountId}`
- [GET] `/api/v1/users/search`
- [GET] `/api/v1/users/status`
- [PUT] `/api/v1/users/{accountId}/frst-crt-dt`
- [GET] `/api/v1/users/me/iop-dn`
- [PUT] `/api/v1/users/me/iop-dn`

### VocController (10)
- [GET] `/api/v1/voc`
- [GET] `/api/v1/voc/{vocId}`
- [POST] `/api/v1/voc`
- [PUT] `/api/v1/voc/{vocId}`
- [DELETE] `/api/v1/voc/{vocId}`
- [PATCH] `/api/v1/voc/{vocId}/status`
- [POST] `/api/v1/voc/{vocId}/comments`
- [PUT] `/api/v1/voc/{vocId}/comments/{cmntId}`
- [DELETE] `/api/v1/voc/{vocId}/comments/{cmntId}`
- [GET] `/api/v1/voc/types`

### WorksMailController (2)
- [POST] `/api/v1/works/mail-send`
- [POST] `/api/v1/works/message-send`

### WorkspaceController (18) - legacy/inactive 의심
- [GET] `/`
- [GET] `/{workspaceId}`
- [POST] `/`
- [PUT] `/{workspaceId}`
- [DELETE] `/{workspaceId}`
- [GET] `/handover/{handoverUserId}`
- [GET] `/user/{userId}`
- [GET] `/department/{deptId}`
- [GET] `/check-project-code`
- [GET] `/handovers`
- [GET] `/handovers/{handoverId}`
- [POST] `/handovers`
- [PUT] `/handovers/{handoverId}`
- [DELETE] `/handovers/{handoverId}`
- [PUT] `/handovers/{handoverId}/status`
- [POST] `/handovers/{handoverId}/complete`
- [GET] `/{workspaceId}/handover-history`
- [GET] `/handovers/pending-count/{receiveUserId}`
