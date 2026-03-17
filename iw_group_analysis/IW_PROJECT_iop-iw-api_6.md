# iop-iw-api API 부록

## 목적

이 문서는 정적 추출 기준으로 `iop-iw-api`의 엔드포인트를 카테고리별로 다시 모아둔 부록이다.

## 주의

- 추출 기준: 컨트롤러 어노테이션 정적 파싱
- 일부 다중 경로, 배열형 매핑, 상수 조합은 축약 또는 누락 가능
- 목적은 전수 원본 복구와 카테고리별 탐색 시작점 제공이다.

## admin

### AdminAssignmentController
- `[GET] /api/admin/assignments` -> `getAllAssignments`
- `[GET] /api/admin/assignments/{asmtId}` -> `getAssignmentDetail`
- `[GET] /api/admin/assignments/{asmtId}/attachments` -> `getAssignmentAttachments`
- `[POST] /api/admin/assignments/{asmtId}/restore` -> `restoreAssignment`
- `[POST] /api/admin/assignments/transfer-owner` -> `transferOwnerAdmin`
- `[PUT] /api/admin/workspaces/{workspaceId}` -> `updateWorkspace`

### AdminUserController
- `[GET] /api/admin/users/{accountId}/iop-dn` -> `getUserIopDn`
- `[PUT] /api/admin/users/{accountId}/iop-dn` -> `updateUserIopDn`

## api

### UnifiedHistoryController
- `[GET] /api/v1/history/anomalies` -> `detectAnomalies`
- `[GET] /api/v1/history/assignments/{asmtId}` -> `getAssignmentHistory`
- `[GET] /api/v1/history/assignments/{asmtId}/pic` -> `getAssignmentPicHistory`
- `[GET] /api/v1/history/audit/events` -> `getAuditEvents`
- `[POST] /api/v1/history/audit/events` -> `createAuditEvent`
- `[POST] /api/v1/history/complex-change` -> `recordComplexChange`
- `[GET] /api/v1/history/complex-change/{entityType}/{entityId}` -> `getComplexChanges`
- `[GET] /api/v1/history/entity/{tableName}/{recordId}` -> `getEntityHistory`
- `[GET] /api/v1/history/permission/{targetId}` -> `getPermissionHistory`
- `[GET] /api/v1/history/recent` -> `getRecentChanges`
- `[GET] /api/v1/history/statistics` -> `getStatistics`
- `[GET] /api/v1/history/todo/{todoId}` -> `getTodoHistory`
- `[GET] /api/v1/history/user/{accountId}` -> `getUserActivity`
- `[GET] /api/v1/history/user/{accountId}/statistics` -> `getUserStatistics`

## assignment

### AssignmentController
- `[DELETE] /api/v1/assignments/{asmtId}` -> `deleteAssignment`
- `[GET] /api/v1/assignments/{asmtId}` -> `getAssignment`
- `[PUT] /api/v1/assignments/{asmtId}` -> `updateAssignment`
- `[POST] /api/v1/assignments/{asmtId}/approve` -> `approveAssignment`
- `[POST] /api/v1/assignments/{asmtId}/reject` -> `rejectAssignment`
- `[GET] /api/v1/assignments/my-participated` -> `getMyParticipatedAssignments`
- `[POST] /api/v1/assignments/transfer-owner` -> `transferOwner`

### ExternalAssignmentController
- `[GET] /api/v1/external/assignments/{accountId}` -> `getAssignmentsByAccountId`
- `[GET] /api/v1/external/assignments/{accountId}/{asmtId}` -> `getAssignmentDetail`

## attachment

### AttachmentController
- `[GET] /api/v1/assignments/{asmtId}/attachments` -> `getAssignmentAttachments`
- `[GET] /api/v1/assignments/{asmtId}/attachments/{fileId}` -> `getAttachment`
- `[GET] /api/v1/assignments/{asmtId}/attachments:filterByType` -> `filterAttachmentsByType`
- `[GET] /api/v1/assignments/{asmtId}/documents/{docId}/attachments` -> `getAssignmentDocumentAttachments`
- `[GET] /api/v1/attachments` -> `getAllAttachments`
- `[GET] /api/v1/attachments/{fileId}` -> `getAttachmentByFileId`
- `[PUT] /api/v1/attachments/{fileId}/asmt-id` -> `updateAsmtId`
- `[GET] /api/v1/attachments/search` -> `searchAttachmentsByFilename`
- `[GET] /api/v1/documents/{docId}/attachments` -> `getDocumentAttachments`
- `[GET] /api/v1/files/{fileId}` -> `downloadFile`

### ExternalFileShareController
- `[GET] /api/v1/external/files/download` -> `downloadSharedFile`
- `[POST] /api/v1/files/{fileId}/share` -> `createFileShare`

### FileTransferController
- `[GET] /api/v1/files/transfer/status/{tusId}` -> `getTransferStatus`
- `[POST] /api/v1/files/transfer-from-naver` -> `transferFileFromNaver`
- `[POST] /api/v1/files/transfer-from-naver-stream` -> `transferFileFromNaverStream`

### TusdCallbackController
- `[POST] /internal/tusd/` -> `handleHook`
- `[POST] /internal/tusd/deleted` -> `handleDeleted`
- `[POST] /internal/tusd/failed` -> `handleFailed`
- `[POST] /internal/tusd/finish` -> `handleFinish`

## auth

### ApiAuthTestController
- `[GET] /api/v1/test/api-auth/health` -> `checkApiAuthServiceBean`
- `[GET] /api/v1/test/api-auth/token` -> `testGetAccessToken`

### AuthController
- `[GET] /api/v1/auth/keycloak/authorize` -> `generateAuthorizationUrl`
- `[POST] /api/v1/auth/keycloak/callback` -> `handleOAuth2Callback`
- `[POST] /api/v1/auth/keycloak/login` -> `keycloakLogin`
- `[GET] /api/v1/auth/keycloak/logout` -> `generateLogoutUrl`
- `[POST] /api/v1/auth/keycloak/refresh` -> `refreshToken`
- `[POST] /api/v1/auth/keycloak/revoke` -> `revokeToken`
- `[POST] /api/v1/auth/keycloak/token` -> `exchangeToken`
- `[GET] /api/v1/auth/keycloak/userinfo` -> `getUserInfo`

### AuthorityManagementController
- `[POST] /api/v1/authorities/assign` -> `assignAuthority`
- `[DELETE] /api/v1/authorities/assign/{accountId}` -> `revokeAuthority`
- `[GET] /api/v1/authorities/groups` -> `getAuthorityGroups`
- `[POST] /api/v1/authorities/groups` -> `createAuthorityGroup`
- `[DELETE] /api/v1/authorities/groups/{authrtGroupId}` -> `deleteAuthorityGroup`
- `[GET] /api/v1/authorities/groups/{authrtGroupId}` -> `getAuthorityGroup`
- `[PUT] /api/v1/authorities/groups/{authrtGroupId}` -> `updateAuthorityGroup`
- `[GET] /api/v1/authorities/users/{accountId}` -> `getUserAuthorities`
- `[GET] /api/v1/authorities/validate/{accountId}/{authorityId}` -> `validateAuthority`

### BusinessAuthorityController
- `[DELETE] /api/v1/business-authority/assignments/{asmtId}/permissions` -> `revokePermission`
- `[GET] /api/v1/business-authority/assignments/{asmtId}/permissions`
- `[PUT] /api/v1/business-authority/assignments/{asmtId}/permissions`
- `[DELETE] /api/v1/business-authority/assignments/{asmtId}/permissions/department`
- `[POST] /api/v1/business-authority/assignments/{asmtId}/permissions/department`
- `[POST] /api/v1/business-authority/assignments/{asmtId}/permissions/departments`
- `[DELETE] /api/v1/business-authority/assignments/{asmtId}/permissions/user`
- `[POST] /api/v1/business-authority/assignments/{asmtId}/permissions/user`
- `[POST] /api/v1/business-authority/assignments/{asmtId}/permissions/users`
- `[GET] /api/v1/business-authority/department/{deptCode}/assignments`
- `[POST] /api/v1/business-authority/documents/{docId}/public` -> `createDocumentPublic`
- `[DELETE] /api/v1/business-authority/documents/public/{docPublicId}` -> `revokeDocumentPublic`
- `[GET] /api/v1/business-authority/my-assignments`
- `[GET] /api/v1/business-authority/my-public-documents` -> `getMyPublicDocuments`
- `[GET] /api/v1/business-authority/public/{docPublicId}` -> `accessPublicDocument`

### CryptoTestController
- `[GET] /admin/crypto/basic` -> `basicTest`
- `[POST] /admin/crypto/decrypt` -> `decryptTest`
- `[POST] /admin/crypto/encrypt` -> `encryptTest`
- `[POST] /admin/crypto/verify-password` -> `verifyPasswordTest`

### IopSsoController
- `[GET] /sso/deNormal` -> `dePass`
- `[GET] /sso/enNormal` -> `enNormal`
- `[GET] /sso/enPass` -> `enPass`
- `[POST] /sso/handleToken` -> `handleToken`

### RequireAssignmentManagement
- `[GET] /api/v1/brm-relations/assignments/{asmtId}/functions` -> `getAssignmentFunctions`

### SaasAuthController
- `[GET] /api/v1/saas/auth-token` -> `getSaasAuthToken`

### SpNoneProfileWeb
- `[GET] /spnoneprofile/` -> `index`
- `[GET] /spnoneprofile/mobile` -> `mobile`

## brm

### BrmController
- `[GET] /api/v1/brm/function` -> `getFunctionList`
- `[GET] /api/v1/brm/function/{clsfSystId}` -> `getFunctionById`
- `[GET] /api/v1/brm/function/{parentId}/children` -> `getFunctionChildren`
- `[GET] /api/v1/brm/function/tree` -> `getFunctionTree`
- `[GET] /api/v1/brm/purpose` -> `getPurposeList`
- `[GET] /api/v1/brm/purpose/{clsfSystId}` -> `getPurposeById`
- `[GET] /api/v1/brm/purpose/{parentId}/children` -> `getPurposeChildren`
- `[GET] /api/v1/brm/purpose/tree` -> `getPurposeTree`
- `[GET] /api/v1/brm/stats` -> `getStats`

### BrmRelationController
- `[GET] /api/v1/brm-relations/assignments/{asmtId}/functions` -> `getAsmtBrmFunctions`
- `[GET] /api/v1/brm-relations/assignments/{asmtId}/organizations` -> `getAsmtBrmOrganizations`
- `[GET] /api/v1/brm-relations/assignments/{asmtId}/purposes` -> `getAsmtBrmPurposes`
- `[POST] /api/v1/brm-relations/assignments/bulk` -> `bulkCreateAsmtBrmRelations`
- `[POST] /api/v1/brm-relations/assignments/functions` -> `createAsmtBrmFunction`
- `[DELETE] /api/v1/brm-relations/assignments/functions/{asmtFunctionId}` -> `deleteAsmtBrmFunction`
- `[POST] /api/v1/brm-relations/assignments/organizations` -> `createAsmtBrmOrganization`
- `[DELETE] /api/v1/brm-relations/assignments/organizations/{asmtOgnzId}` -> `deleteAsmtBrmOrganization`
- `[POST] /api/v1/brm-relations/assignments/purposes` -> `createAsmtBrmPurpose`
- `[DELETE] /api/v1/brm-relations/assignments/purposes/{asmtPurposeId}` -> `deleteAsmtBrmPurpose`

## document

### DocumentCommentController
- `[GET] /api/v1/assignments/{asmtId}/comments` -> `getAssignmentComments`
- `[GET] /api/v1/assignments/{asmtId}/documents/{docId}/comments` -> `getComments`
- `[POST] /api/v1/assignments/{asmtId}/documents/comments` -> `createComment`
- `[DELETE] /api/v1/assignments/{asmtId}/documents/comments/{commentId}` -> `deleteComment`
- `[PUT] /api/v1/assignments/{asmtId}/documents/comments/{commentId}` -> `updateComment`
- `[POST] /api/v1/assignments/{asmtId}/documents/comments/{commentId}/like` -> `toggleLike`
- `[GET] /api/v1/assignments/{asmtId}/documents/comments/{commentId}/replies` -> `getReplies`

### DocumentManagementController
- `[GET] /api/v1/assignments/{asmtId}/documents` -> `getDocumentList`
- `[POST] /api/v1/assignments/{asmtId}/documents` -> `createDocument`
- `[PUT] /api/v1/assignments/{asmtId}/documents/{docId}/attachments` -> `linkDocumentAttachments`
- `[GET] /api/v1/assignments/{asmtId}/documents/recent` -> `getRecentDocumentList`
- `[GET] /api/v1/assignments/{asmtId}/documents/tree` -> `getDocumentTree`
- `[DELETE] /api/v1/assignments/{asmtId}/trash` -> `emptyTrash`
- `[GET] /api/v1/assignments/{asmtId}/trash` -> `getTrashDocuments`
- `[GET] /api/v1/assignments/{asmtId}/trash/count` -> `getTrashCount`
- `[DELETE] /api/v1/documents/{docId}` -> `moveDocumentToTrash`
- `[GET] /api/v1/documents/{docId}` -> `getDocument`
- `[GET] /api/v1/documents/{docId}` -> `getDocumentSnapshot`
- `[PUT] /api/v1/documents/{docId}` -> `updateDocument`
- `[POST] /api/v1/documents/{docId}/archive` -> `archiveDocumentAsReadOnly`
- `[GET] /api/v1/documents/{docId}/content` -> `getDocumentContent`
- `[POST] /api/v1/documents/{docId}/lock` -> `lockDocument`
- `[DELETE] /api/v1/documents/{docId}/permanent` -> `permanentDeleteDocument`
- `[PUT] /api/v1/documents/{docId}/position` -> `updateDocumentPosition`
- `[POST] /api/v1/documents/{docId}/restore` -> `restoreDocument`
- `[POST] /api/v1/documents/{docId}/unlock` -> `unlockDocument`

### DocumentShareController
- `[POST] /api/v1/documents/{docId}/share` -> `shareDocument`

### DocumentSnapshotController
- `[GET] /api/v1/documents/{docId}/snapshots` -> `getSnapshotList`
- `[POST] /api/v1/documents/{docId}/snapshots` -> `createSnapshot`
- `[GET] /api/v1/documents/{docId}/snapshots/{version}` -> `getSnapshot`
- `[GET] /api/v1/documents/{docId}/snapshots/{version}/data` -> `getSnapshotData`
- `[POST] /api/v1/documents/{docId}/snapshots/{version}:restore` -> `restoreSnapshot`
- `[DELETE] /api/v1/snapshots/{snapshotId}` -> `deleteSnapshot`

### DocumentTemplateController
- `[DELETE] /api/v1/document-templates/{templateId}` -> `deleteDocumentTemplate`
- `[GET] /api/v1/document-templates/{templateId}` -> `getDocumentTemplate`
- `[PUT] /api/v1/document-templates/{templateId}` -> `updateDocumentTemplate`

## ihb

### AsmtTkovController
- `[DELETE] /api/admin/asmt-takeovers/{asmtId}/{tkovSn}` -> `deleteAsmtTkov`
- `[GET] /api/admin/asmt-takeovers/{asmtId}/{tkovSn}` -> `getAsmtTkov`
- `[PUT] /api/admin/asmt-takeovers/{asmtId}/{tkovSn}` -> `updateAsmtTkov`
- `[GET] /api/admin/asmt-takeovers/by-acceptor/{acptnPicAcntId}` -> `getAsmtTkovListByAcptnPic`
- `[GET] /api/admin/asmt-takeovers/by-assessment/{asmtId}` -> `getAsmtTkovListByAsmtId`
- `[GET] /api/admin/asmt-takeovers/by-handover-person/{hnovPicAcntId}` -> `getAsmtTkovListByHnovPic`
- `[GET] /api/admin/asmt-takeovers/ongoing-count/{accountId}` -> `getOngoingAsmtTkovCount`

### AuditLogController
- `[GET] /api/admin/audit-logs/{logId}` -> `getAuditLog`
- `[GET] /api/admin/audit-logs/anomalous-access` -> `getAnomalousAccessLogs`
- `[POST] /api/admin/audit-logs/archive` -> `archiveAuditLogs`
- `[GET] /api/admin/audit-logs/category` -> `getAuditLogListByCategory`
- `[DELETE] /api/admin/audit-logs/cleanup` -> `deleteOldAuditLogs`
- `[GET] /api/admin/audit-logs/date-range` -> `getAuditLogListByDateRange`
- `[GET] /api/admin/audit-logs/errors` -> `getErrorLogList`
- `[GET] /api/admin/audit-logs/export/excel` -> `exportAuditLogToExcel`
- `[GET] /api/admin/audit-logs/files` -> `getFileLogList`
- `[GET] /api/admin/audit-logs/ip/{ipAddress}` -> `getAuditLogListByIpAddress`
- `[GET] /api/admin/audit-logs/login` -> `getLoginLogList`
- `[GET] /api/admin/audit-logs/recent` -> `getRecentAuditLogList`
- `[GET] /api/admin/audit-logs/resource` -> `getAuditLogListByResource`
- `[GET] /api/admin/audit-logs/resource-history` -> `getResourceAccessHistory`
- `[GET] /api/admin/audit-logs/roles` -> `getRoleLogList`
- `[GET] /api/admin/audit-logs/session/{sessionId}` -> `getAuditLogListBySession`
- `[GET] /api/admin/audit-logs/severity/{severity}` -> `getAuditLogListBySeverity`
- `[GET] /api/admin/audit-logs/stats/daily` -> `getDailyLogStats`
- `[GET] /api/admin/audit-logs/stats/log-types` -> `getLogTypeStats`
- `[GET] /api/admin/audit-logs/stats/severity` -> `getSeverityLogStats`
- `[GET] /api/admin/audit-logs/stats/users` -> `getUserLogStats`
- `[POST] /api/admin/audit-logs/test-log` -> `createTestLog`
- `[GET] /api/admin/audit-logs/time-range` -> `getAuditLogListByTimeRange`
- `[GET] /api/admin/audit-logs/type/{logType}` -> `getAuditLogListByType`
- `[GET] /api/admin/audit-logs/user` -> `getAuditLogListByUser`
- `[GET] /api/admin/audit-logs/workspaces` -> `getWorkspaceLogList`

### BannerController
- `[DELETE] /api/admin/banners/{bnrId}` -> `deleteBanner`
- `[GET] /api/admin/banners/{bnrId}` -> `getBanner`
- `[PUT] /api/admin/banners/{bnrId}` -> `updateBanner`
- `[PUT] /api/admin/banners/{bnrId}/activation` -> `updateBannerActivation`
- `[PATCH] /api/admin/banners/{bnrId}/links` -> `updateBannerLinks`
- `[PUT] /api/admin/banners/{bnrId}/order` -> `updateBannerOrder`
- `[GET] /api/admin/banners/active` -> `getActiveBannerList`
- `[GET] /api/admin/banners/author/{authorId}` -> `getBannerListByAuthor`
- `[DELETE] /api/admin/banners/batch` -> `deleteBannerBatch`
- `[PATCH] /api/admin/banners/batch/status` -> `updateBannerStatusBatch`
- `[GET] /api/admin/banners/daterange` -> `getBannerListByDateRange`
- `[POST] /api/admin/banners/register` -> `registerBanner`
- `[POST] /api/admin/banners/reorder` -> `reorderBanners`

### EmailController
- `[POST] /api/admin/email/extract-variables` -> `extractTemplateVariables`
- `[GET] /api/admin/email/logs` -> `getEmailLogList`
- `[GET] /api/admin/email/logs/{emailLogId}` -> `getEmailLog`
- `[GET] /api/admin/email/logs/pending` -> `getPendingEmailLogList`
- `[GET] /api/admin/email/logs/recent` -> `getRecentEmailLogList`
- `[POST] /api/admin/email/logs/retry` -> `retryFailedEmails`
- `[GET] /api/admin/email/logs/user/{recipientUserId}` -> `getEmailLogListByUser`
- `[POST] /api/admin/email/send/bulk` -> `sendBulkEmails`
- `[POST] /api/admin/email/send/direct` -> `sendDirectEmail`
- `[POST] /api/admin/email/send/template` -> `sendTemplateEmail`
- `[GET] /api/admin/email/statistics/period` -> `getEmailStatsByPeriod`
- `[GET] /api/admin/email/statistics/summary` -> `getEmailStatsSummary`
- `[GET] /api/admin/email/statistics/template` -> `getEmailStatsByTemplate`
- `[GET] /api/admin/email/statistics/type` -> `getEmailStatsByType`
- `[POST] /api/admin/email/substitute-variables` -> `substituteTemplateVariables`
- `[GET] /api/admin/email/templates` -> `getEmailTemplateList`
- `[POST] /api/admin/email/templates` -> `registerEmailTemplate`
- `[DELETE] /api/admin/email/templates/{templateId}` -> `deleteEmailTemplate`
- `[GET] /api/admin/email/templates/{templateId}` -> `getEmailTemplate`
- `[PUT] /api/admin/email/templates/{templateId}` -> `updateEmailTemplate`
- `[POST] /api/admin/email/templates/{templateId}/preview` -> `previewEmailTemplate`
- `[PUT] /api/admin/email/templates/{templateId}/status` -> `updateEmailTemplateStatus`
- `[GET] /api/admin/email/templates/active` -> `getActiveEmailTemplateList`
- `[GET] /api/admin/email/templates/type/{templateType}` -> `getEmailTemplateListByType`
- `[POST] /api/admin/email/validate-email` -> `validateEmailAddress`

### EventProduceController
- `[GET] /api/admin/kafka/send` -> `sendTopic`

### ExternalUserController
- `[GET] /api/admin/external-users/` -> `getExternalUserList`
- `[POST] /api/admin/external-users/` -> `registerExternalUser`
- `[GET] /api/admin/external-users/{accountId}` -> `getExternalUser`
- `[PUT] /api/admin/external-users/{accountId}` -> `updateExternalUser`
- `[POST] /api/admin/external-users/{accountId}/send-invitation` -> `sendInvitationEmail`
- `[POST] /api/admin/external-users/checkInitPassword` -> `checkInitPassword`
- `[GET] /api/admin/external-users/generate-userId` -> `createUserId`
- `[GET] /api/admin/external-users/institutions` -> `getInstitutionList`
- `[GET] /api/admin/external-users/institutions/{institutionCode}/departments` -> `getDeptList`
- `[POST] /api/admin/external-users/modifyInitPassword` -> `modifyInitPassword`
- `[POST] /api/admin/external-users/removeUser` -> `removeUser`

### InternalUserController
- `[GET] /api/admin/internal-users/` -> `getInternalUserList`
- `[POST] /api/admin/internal-users/` -> `registerInternalUser`
- `[DELETE] /api/admin/internal-users/{accountId}` -> `deleteInternalUser`
- `[GET] /api/admin/internal-users/{accountId}` -> `getInternalUser`
- `[PUT] /api/admin/internal-users/{accountId}` -> `updateInternalUser`
- `[PATCH] /api/admin/internal-users/{accountId}/dn` -> `updateUserDn`
- `[PATCH] /api/admin/internal-users/{accountId}/last-access` -> `updateUserLastAccessDate`
- `[GET] /api/admin/internal-users/institutions` -> `getInstitutionList`
- `[GET] /api/admin/internal-users/institutions/{institutionCode}/departments` -> `getDeptListByInstitution`

### KeycloakController
- `[POST] /api/keycloak/userIdLogin` -> `login`

### MemberController
- `[POST] /api/member/iopJoin` -> `iopJoin`
- `[POST] /api/member/iopModify` -> `iopModify`

### MypageController
- `[GET] /api/dashboard/mypage/{accountId}` -> `getUserInfo`
- `[POST] /api/dashboard/mypage/receiveSettingRegist` -> `receiveSettingRegist`
- `[POST] /api/dashboard/mypage/userImgUpload` -> `userImgUpload`

### NoticeController
- `[DELETE] /api/admin/notices/{ntcMttrId}` -> `deleteNotice`
- `[GET] /api/admin/notices/{ntcMttrId}` -> `getNotice`
- `[PUT] /api/admin/notices/{ntcMttrId}` -> `updateNoticeJson`
- `[GET] /api/admin/notices/byNo/{ntcMttrNo}` -> `getNoticeByNo`
- `[GET] /api/admin/notices/has-new` -> `checkHasNewNotice`
- `[GET] /api/admin/notices/recent` -> `getRecentNoticeList`

### RoleController
- `[GET] /api/admin/roles/{roleId}` -> `getRole`
- `[GET] /api/admin/roles/{roleId}/access-scopes` -> `getRoleAccessScopes`
- `[PUT] /api/admin/roles/{roleId}/access-scopes` -> `setRoleAccessScopes`
- `[GET] /api/admin/roles/{roleId}/user-count` -> `getUserCountByRole`
- `[GET] /api/admin/roles/{roleId}/users` -> `getUserListByRole`
- `[POST] /api/admin/roles/{roleId}/users` -> `assignRoleToUser`
- `[POST] /api/admin/roles/{roleId}/users/bulk-assign` -> `assignRoleToMultipleUsers`
- `[GET] /api/admin/roles/access-scopes` -> `getAccessScope`
- `[GET] /api/admin/roles/by-type/{roleType}` -> `getRoleListByType`
- `[GET] /api/admin/roles/check-name` -> `checkRoleNameDuplicate`
- `[POST] /api/admin/roles/delete` -> `deleteRole`
- `[GET] /api/admin/roles/getUserScopeList/{accountId}` -> `getUserScopeList`
- `[POST] /api/admin/roles/modify` -> `updateRole`
- `[POST] /api/admin/roles/modifyExp` -> `modifyExp`
- `[POST] /api/admin/roles/removeUser` -> `removeRoleFromUser`
- `[GET] /api/admin/roles/users/{userId}` -> `getUserRoles`
- `[PUT] /api/admin/roles/users/{userId}/change-role` -> `changeUserRole`

### SaasController
- `[GET] /api/dashboard/saas/checkIopCount/{iopId}` -> `checkIopCount`
- `[GET] /api/dashboard/saas/checkIopIdByAccountId/{accountId}` -> `checkIopIdByAccountId`
- `[GET] /api/dashboard/saas/getViewData/{accountId}` -> `getViewData`
- `[POST] /api/dashboard/saas/updateCommonIopId` -> `updateCommonIopId`
- `[POST] /api/dashboard/saas/updateMainIopId` -> `updateMainIopId`

### ShareController
- `[GET] /api/share/assignments/{asmtId}` -> `getAssignmentDetail`
- `[POST] /api/share/external-users/checkInitPassword` -> `checkInitPassword`
- `[POST] /api/share/external-users/modifyInitPassword` -> `modifyInitPassword`
- `[PATCH] /api/share/internal-users/{accountId}/last-access` -> `updateUserLastAccessDate`
- `[GET] /api/share/notices` -> `getNoticeList`
- `[GET] /api/share/notices/{ntcMttrId}` -> `getNotice`
- `[GET] /api/share/notices/has-new` -> `checkHasNewNotice`
- `[GET] /api/share/roles/getUserScopeList/{accountId}` -> `getUserScopeList`

### UserActvController
- `[GET] /api/admin/user-activities/` -> `getUserActvList`
- `[POST] /api/admin/user-activities/` -> `registerUserActv`
- `[DELETE] /api/admin/user-activities/{acntId}/{hstrySn}` -> `deleteUserActv`
- `[GET] /api/admin/user-activities/{acntId}/{hstrySn}` -> `getUserActv`
- `[PUT] /api/admin/user-activities/{acntId}/{hstrySn}` -> `updateUserActv`
- `[GET] /api/admin/user-activities/errors` -> `getErrorActvList`
- `[GET] /api/admin/user-activities/excel` -> `downloadUserActvExcel`
- `[GET] /api/admin/user-activities/period` -> `getUserActvByPeriod`
- `[POST] /api/admin/user-activities/record` -> `recordUserActivity`
- `[GET] /api/admin/user-activities/statistics/{acntId}` -> `getUserActvStatistics`
- `[DELETE] /api/admin/user-activities/user/{acntId}` -> `deleteUserActvByAcntId`
- `[GET] /api/admin/user-activities/user/{acntId}` -> `getUserActvByAcntId`

### WorkspaceController
- `[DELETE] /api/admin/workspaces/{workspaceId}` -> `deleteWorkspace`
- `[GET] /api/admin/workspaces/{workspaceId}` -> `getWorkspace`
- `[PUT] /api/admin/workspaces/{workspaceId}` -> `updateWorkspace`
- `[GET] /api/admin/workspaces/{workspaceId}/handover-history` -> `getHandoverHistoryByWorkspace`
- `[GET] /api/admin/workspaces/check-project-code` -> `checkProjectCodeDuplicate`
- `[GET] /api/admin/workspaces/department/{deptId}` -> `getWorkspaceListByDept`
- `[GET] /api/admin/workspaces/handover/{handoverUserId}` -> `getHandoverWorkspaceList`
- `[GET] /api/admin/workspaces/handovers` -> `getHandoverList`
- `[POST] /api/admin/workspaces/handovers` -> `createHandover`
- `[DELETE] /api/admin/workspaces/handovers/{handoverId}` -> `deleteHandover`
- `[GET] /api/admin/workspaces/handovers/{handoverId}` -> `getHandover`
- `[PUT] /api/admin/workspaces/handovers/{handoverId}` -> `updateHandover`
- `[POST] /api/admin/workspaces/handovers/{handoverId}/complete` -> `completeHandover`
- `[PUT] /api/admin/workspaces/handovers/{handoverId}/status` -> `updateHandoverStatus`
- `[GET] /api/admin/workspaces/handovers/pending-count/{receiveUserId}` -> `getPendingHandoverCount`
- `[GET] /api/admin/workspaces/user/{userId}` -> `getWorkspaceListByUser`

## mail

### WorksMailController
- `[POST] /api/v1/works/mail-send` -> `sendMail`
- `[POST] /api/v1/works/message-send` -> `sendMessage`

## rag

### RagJobController
- `[GET] /api/v1/assignments/{asmtId}/rag-jobs` -> `getRagJobList`
- `[POST] /api/v1/assignments/{asmtId}/rag-jobs`
- `[GET] /api/v1/assignments/{asmtId}/rag-jobs/{id}` -> `getRagJob`
- `[POST] /api/v1/assignments/{asmtId}/rag-jobs/status` -> `getRagJobStatus`
- `[GET] /api/v1/assignments/{asmtId}/rag-jobs:stats` -> `getRagJobStats`
- `[POST] /api/v1/external/rag-callbacks` -> `handleRagCallback`

## schedule

### BatchController
- `[GET] /api/batch/memberSync` -> `runMemberSync`

## share

### ExternalShareController
- `[POST] /api/v1/external-shares`
- `[DELETE] /api/v1/external-shares/{shareId}`
- `[GET] /public/share/{shareId}` -> `viewExternalShare`

## todo

### TodoController
- `[GET] /api/v1/assignments/{asmtId}/todos` -> `getTodoList`
- `[POST] /api/v1/assignments/{asmtId}/todos` -> `createTodo`
- `[DELETE] /api/v1/todos/{todoId}` -> `deleteTodo`
- `[GET] /api/v1/todos/{todoId}` -> `getTodoById`
- `[PUT] /api/v1/todos/{todoId}` -> `updateTodo`

## uscm

### UscmSearchController
- `[GET] /api/v1/uscm/orgs/search` -> `searchInstitutions`
- `[GET] /api/v1/uscm/users/search` -> `searchUsers`

## user

### OrganizationController
- `[GET] /api/v1/organizations/{oucode}` -> `getOrganizationByOucode`
- `[GET] /api/v1/organizations/level/{level}` -> `getOrganizationsByLevel`
- `[GET] /api/v1/organizations/parent/{parentOucode}` -> `getOrganizationsByParent`
- `[GET] /api/v1/organizations/root` -> `getRootOrganizations`
- `[GET] /api/v1/organizations/search` -> `searchOrganizations`
- `[GET] /api/v1/organizations/tree/{rootOucode}` -> `getOrganizationTree`

### UserController
- `[GET] /api/v1/users/{accountId}` -> `getUserById`
- `[PUT] /api/v1/users/{accountId}/frst-crt-dt` -> `updateUserFrstCrtDt`
- `[GET] /api/v1/users/me/iop-dn` -> `getMyIopDn`
- `[PUT] /api/v1/users/me/iop-dn` -> `updateMyIopDn`
- `[GET] /api/v1/users/search` -> `searchUsers`
- `[GET] /api/v1/users/status` -> `getUsersByStatus`

## voc

### VocController
- `[DELETE] /api/v1/voc/{vocId}` -> `deleteVoc`
- `[GET] /api/v1/voc/{vocId}` -> `getVoc`
- `[PUT] /api/v1/voc/{vocId}` -> `updateVoc`
- `[POST] /api/v1/voc/{vocId}/comments` -> `registerVocComment`
- `[DELETE] /api/v1/voc/{vocId}/comments/{cmntId}` -> `deleteVocComment`
- `[PUT] /api/v1/voc/{vocId}/comments/{cmntId}` -> `updateVocComment`
- `[PATCH] /api/v1/voc/{vocId}/status` -> `updateVocStatus`
- `[GET] /api/v1/voc/types` -> `getVocTypes`

