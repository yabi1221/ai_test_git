# AI_TEST

하나의 Java Swing 프로젝트 안에 `지뢰찾기`와 `게시판 CRUD`를 함께 구현한 예제입니다.

## 구성

- 지뢰찾기
- 게시판 CRUD

## 기능

### 지뢰찾기

- 난이도 선택
- 새 게임 시작
- 첫 클릭 안전 처리
- 우클릭 깃발 표시
- 주변 칸 자동 열기

### 게시판 CRUD

- 게시글 등록
- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제

## 실행 방법

프로젝트 루트에서 아래 명령으로 컴파일 후 실행합니다.

```powershell
javac -encoding UTF-8 -d out src\Main.java
java -cp out Main
```

## 파일 구조

```text
src/
  Main.java
```

`Main.java` 안에 메인 프레임, 지뢰찾기 패널, 게시판 패널, 게시글 모델이 함께 들어 있습니다.
