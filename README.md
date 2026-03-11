# AI_TEST

하나의 Java Swing 애플리케이션 안에 `지뢰찾기`와 `게시판 CRUD` 기능을 함께 구현한 프로젝트입니다.

## 주요 기능

### 지뢰찾기

- 난이도 선택
- 새 게임 시작
- 첫 클릭 안전 처리
- 우클릭 깃발 표시
- 빈 칸 자동 열기

### 게시판 CRUD

- 게시글 등록
- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제

## 실행 방법

프로젝트 루트에서 아래 순서로 컴파일한 뒤 실행합니다.

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
java -cp out app.Main
```

## 프로젝트 구조

```text
src/
  app/
    Main.java
    MultiAppFrame.java
    board/
      BoardPanel.java
      BoardPost.java
      BoardRepository.java
      BoardService.java
      BoardTableModel.java
    minesweeper/
      CellButton.java
      CellPosition.java
      Difficulty.java
      MinesweeperGame.java
      MinesweeperPanel.java
      RevealResult.java
```

## 리팩토링 내용

### 게시판

- 화면 구성과 이벤트 처리는 `BoardPanel`
- 게시글 저장은 `BoardRepository`
- 게시글 등록, 수정, 삭제 흐름은 `BoardService`
- 표 데이터 표현은 `BoardTableModel`

### 지뢰찾기

- 화면 구성과 버튼 갱신은 `MinesweeperPanel`
- 게임 상태와 규칙 처리는 `MinesweeperGame`
- 칸 좌표 전달은 `CellPosition`
- 칸 열기 결과 전달은 `RevealResult`
