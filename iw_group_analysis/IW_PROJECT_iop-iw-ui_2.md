# iop-iw-ui 호출 매핑 분석

## 목적

이 문서는 UI가 실제로 어떤 API 축을 사용하는지, 어떤 화면이 어떤 서비스와 연결되는지, 실사용 범위가 어디까지인지 묶어서 정리한 통합 문서다.

## 1. UI가 기대는 주요 API 축

- 인증/세션: `/api/v1/auth/keycloak/*`
- 일반 업무: `/api/v1/*`
- 관리자: `/api/admin/*`
- 공유/공개: `/api/share/*`
- 조직/사용자 검색: `/api/v1/uscm/*`
- 업로드: `/iw/files`

## 2. 화면 기준 연결 축

### 로그인/진입

- `auth.service`
- `api.ts`
- 인증, callback, refresh와 직접 연결

### 일반 사용자 화면

- `assignment`
- `document`
- `share`
- `dashboard`
- `voc`

### 작업공간/에디터

가장 넓은 API 면적을 사용한다.

- `assignment`
- `document`
- `attachment`
- `share`
- `rag`

### 관리자

- `admin.api.ts` 중심
- 사용자, 권한, 공지, 로그, 작업공간 관리 축과 연결

## 3. 호출 방식 특성

UI 내부 호출 방식은 하나로 통일돼 있지 않다.

- `React Query` 훅
- `service` 계층
- `apiClient` 직접 호출

그래서 실제 추적은 아래 순서로 보는 게 빠르다.

`페이지 -> 훅/서비스 -> apiClient -> API 컨트롤러`

## 4. 실사용 범위 해석

정적 분석 기준으로 보면 UI가 실제로 쓰는 API 면은 백엔드 전체보다 훨씬 좁다.

핵심 실사용 축:

- `assignment`
- `document`
- `admin`
- `auth`

보조 축:

- `share`
- `dashboard`
- `uscm`
- `rag`
- `voc`

## 5. 실무 판단

UI 기능 수정 시에는 백엔드 전체를 보는 대신, 먼저 어떤 화면이 어떤 API 축을 건드리는지 좁히는 것이 효율적이다.

특히 `에디터`, `로그인`, `관리자` 3축이 우선순위가 높다.
