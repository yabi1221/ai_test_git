# iop-iw-ui 심화 분석

## 목적

이 문서는 UI의 진입점, 인증 흐름, 에디터 결합도, 호출 방식 혼재를 묶은 심화 문서다.

## 1. 가장 먼저 볼 파일

1. `src/main.tsx`
2. `src/App.tsx`
3. `src/routes/routes.tsx`
4. `src/config/api.ts`
5. `src/services/auth.service.ts`

## 2. 인증 흐름

핵심 파일:

- `src/services/auth.service.ts`
- `src/config/api.ts`

역할:

- Keycloak 로그인 URL 생성
- callback 처리
- 토큰 저장
- 사전 refresh
- 401 후 재시도

## 3. 화면별 중심축

### 작업공간/에디터

가장 결합도가 높다.

- 문서
- 첨부
- 공유
- 댓글
- 버전
- RAG

### 관리자

`admin.api.ts` 중심으로 넓은 API 표면적을 갖는다.

## 4. 구조적 주의점

- `React Query` 훅
- `service` 계층
- `apiClient` 직접 호출

이 세 방식이 공존한다.

## 결론

UI는 `포털 셸 + 인증 클라이언트 + 에디터 프런트`가 합쳐진 구조다.
