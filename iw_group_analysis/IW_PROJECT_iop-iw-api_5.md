# iop-iw-api 심화 구조 분석

## 목적

이 문서는 패키지 책임도, 인증 구조, 업로드 후처리, 구조 복잡도를 묶은 API 심화 문서다.

## 1. 핵심 패키지 축

- `assignment`
- `document`
- `auth`
- `admin`
- `attachment`

## 2. 보조 축

- `rag`
- `uscm`
- `brm`
- `share`
- `voc`

## 3. 복잡도 축

- `common`
- `ihb`
- `schedule`

특히 `ihb`는 별도 하위 시스템처럼 느껴질 정도로 레거시성이 강하다.

## 4. 업로드 후처리

핵심 흐름:

- TUS 업로드 완료
- `/internal/tusd` 훅 호출
- 메타데이터 검증
- 문서/첨부 후처리

즉 업로드 문제는 API 단독도, TUS 단독도 아니라 결합 이슈로 봐야 한다.

## 5. 실무 판단

초반에는 아래 순서로 보는 것이 가장 효율적이다.

1. `assignment`
2. `document`
3. `auth`
4. `admin`
5. `attachment`

## 결론

이 API는 대형 모놀리식 백엔드에 가깝고, 기능 중심으로 진입점을 잡아야 효율적이다.
