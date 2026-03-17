# iop-iw-api 데이터 구조 및 SQL 영향

## 목적

이 문서는 `assignment/document` 축의 DTO, 변경 컬럼, XML 매퍼, 핵심 테이블 영향을 한 번에 정리한 문서다.

## 1. 작업공간 축

핵심 경로:

- `AssignmentController`
- `AssignmentService`
- `AssignmentDao`
- `AssignmentHistoryDao`

주요 매퍼:

- `AssignmentMapper.xml`
- `AssignmentHistoryMapper.xml`
- `BusinessAuthorityMapper.xml`

핵심 테이블:

- `tbiw_asmt_m`
- `tbiw_biz_authrt_l`
- 작업공간 이력 테이블

DTO 관점:

- 생성/수정 DTO는 제목, 설명, 기간, 공개범위, 상태, BRM 태그, 상위 작업공간까지 함께 다룬다.
- 응답 DTO는 메타데이터뿐 아니라 권한/참여자/요약 정보까지 넓게 포함한다.

## 2. 문서 축

핵심 경로:

- `DocumentManagementController`
- `DocumentService`
- `DocumentDao`

주요 매퍼:

- `DocumentMapper.xml`
- `WorkspaceFileMapper.xml`
- `UnifiedHistoryMapper.xml`

핵심 테이블:

- `tbiw_doc_m`
- `tbiw_doc_d`
- 문서 댓글/버전/첨부 관련 테이블

DTO 관점:

- 문서 요청/응답 구조는 문서 메타, 본문, 위치, 잠금, 복원, 첨부 연결까지 폭넓다.
- UI 변경이 단순해 보여도 실제로는 문서 본문과 메타가 같이 흔들릴 수 있다.

## 3. SQL 영향 해석

실무적으로는 아래처럼 보면 된다.

- 작업공간 변경: `tbiw_asmt_m` + 권한/이력 테이블 동반
- 문서 변경: `tbiw_doc_m` + `tbiw_doc_d` + 첨부/이력 테이블 동반
- 권한 변경: `tbiw_biz_authrt_l` 중심

## 결론

이 API에서 `assignment/document`는 컨트롤러보다 DB 영향 범위가 더 넓다. 기능 수정 시 DTO와 XML 매퍼, 테이블까지 같이 보는 것이 안전하다.
