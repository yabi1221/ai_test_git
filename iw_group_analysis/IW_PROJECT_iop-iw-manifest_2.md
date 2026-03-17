# iop-iw-manifest 환경 차이 및 검증 분석

## 목적

이 문서는 manifest의 환경 차이, 검증 포인트, dev 범위 재해석을 하나로 묶은 통합 문서다.

## 1. dev 범위 해석

사용자 확인 기준으로 실제 개발용 `dev` 축은 `msit`, `mois`다.

판단:

- `api`, `tus`, `sch`는 `msit`, `mois`만 명확하다.
- `ui`에만 `mfds` 흔적이 남아 있다.
- 따라서 `dev ui mfds`는 본선 개발 라인보다는 잔존/미완 정리 대상으로 보는 것이 타당하다.

## 2. 환경 차이 핵심

기관별로 주로 바뀌는 값:

- host
- Keycloak realm
- 기관코드
- DB 접속지
- Kafka user
- 서비스명

즉 구조 자체는 거의 같고, 기관별 값만 치환되는 템플릿형 구조에 가깝다.

## 3. UI values 검증 포인트

정상 패턴:

- `/iw` -> UI 서비스
- `/iw/api` -> API 서비스
- `/iw/files` -> TUS 서비스

가장 강한 이상 징후:

- `dev/iop-iw-ui/values/iop-iw-ui-mfds-values.yaml`
- 파일명과 UI 서비스는 `mfds`인데, 내부 `api/tus` 목적지는 `msit`

## 4. manifest에서 바로 드러나는 위험

- 일부 `application`이 `path: tus-chart`를 가리킴
- 특히 `api`, `sch` 쪽에서도 반복됨
- 현재 워크스페이스에 없는 `iop-iw-sch`가 참조됨

## 5. 실무 체크리스트

1. `application.path`
2. 기관별 `values` self-reference
3. `dev/prd` 서비스명 일관성
4. 존재하지 않는 프로젝트 참조 여부
5. UI/API/TUS host/path 연결 일관성

## 결론

manifest는 단순 값 파일 저장소가 아니라, 실제 운영 동작 차이를 만드는 핵심 저장소다.
