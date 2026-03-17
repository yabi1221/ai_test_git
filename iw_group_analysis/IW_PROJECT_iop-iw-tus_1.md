# iop-iw-tus 분석

## 역할

`iop-iw-tus`는 대용량 파일 업로드 전용 서버다. TUS 프로토콜 기반으로 동작한다.

## 기술 스택

- `tusd` 컨테이너 기반
- Docker 중심
- 별도 애플리케이션 소스는 거의 없음

## 확인된 파일

- `Dockerfile`
- `DockerfileK8s`
- Jenkins 파일들
- README는 기본 템플릿 상태

## Dockerfile 기준 동작

- 포트: `1080`
- 기본 경로: `/files`
- 업로드 디렉터리: `/data`
- 허용 최대 크기: `5GB` 수준
- 이벤트 훅 활성화: `pre-create`, `post-create`, `post-finish`

## 핵심 연동

### API 서버로 훅 전달
- `TUSD_HOOKS_HTTP=iop-iw-api-sample-svc.../internal/tusd`
- 즉, 업로드 메타데이터와 완료 이벤트를 API 서버가 후처리한다.

### UI와의 연결
- UI 매니페스트에서 `/iw/files` 요청이 TUS 서비스로 라우팅된다.
- UI 환경변수에 업로드 관련 URL과 API 키가 존재한다.

## 해석

이 프로젝트는 독립 업무 서버가 아니라 `파일 업로드 전용 인프라 컴포넌트`다. 실제 업무 저장, 권한, 메타데이터 관리 책임은 API 서버에 있다.

## 추가 해석

이 프로젝트는 독립 서비스라기보다 `업로드 인프라 컴포넌트`로 보는 편이 맞다.

실제 장애 분석 시에는 아래 순서로 본다.

1. UI 업로드 요청
2. TUS 저장/훅 호출
3. API `/internal/tusd` 후처리
