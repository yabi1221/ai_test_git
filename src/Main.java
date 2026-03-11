import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MultiAppFrame frame = new MultiAppFrame();
            frame.setVisible(true);
        });
    }
}

class MultiAppFrame extends JFrame {
    MultiAppFrame() {
        super("AI_TEST - 지뢰찾기 / 게시판");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        tabbedPane.addTab("지뢰찾기", new MinesweeperPanel());
        tabbedPane.addTab("게시판", new BoardPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setSize(1200, 760);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
    }
}

class MinesweeperPanel extends JPanel {
    private static final Difficulty[] DIFFICULTIES = {
        new Difficulty("쉬움", 9, 9, 10),
        new Difficulty("보통", 16, 16, 40),
        new Difficulty("어려움", 16, 30, 99)
    };

    private final JLabel statusLabel = new JLabel("", SwingConstants.LEFT);
    private final JLabel timerLabel = new JLabel("시간 0", SwingConstants.RIGHT);
    private final JButton resetButton = new JButton("새 게임");
    private final JComboBox<Difficulty> difficultyBox = new JComboBox<>(DIFFICULTIES);
    private final JPanel boardPanel = new JPanel();

    private CellButton[][] buttons;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int[][] adjacentCounts;

    private Difficulty difficulty = DIFFICULTIES[0];
    private final Timer timer;
    private final Random random = new Random();

    private boolean gameOver;
    private boolean firstClick;
    private int revealedSafeCells;
    private int elapsedSeconds;

    MinesweeperPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        timer = new Timer(1000, e -> {
            elapsedSeconds++;
            timerLabel.setText("시간 " + elapsedSeconds);
        });

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        statusLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        timerLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));

        difficultyBox.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        difficultyBox.setFocusable(false);
        difficultyBox.setRenderer(new DifficultyRenderer());
        difficultyBox.addActionListener(e -> {
            difficulty = (Difficulty) difficultyBox.getSelectedItem();
            rebuildBoard();
            resetGame();
        });

        resetButton.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controlPanel.add(difficultyBox);
        controlPanel.add(resetButton);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(statusLabel, BorderLayout.WEST);
        infoPanel.add(timerLabel, BorderLayout.EAST);

        boardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 160, 160)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        boardPanel.setBackground(new Color(194, 194, 194));

        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        rebuildBoard();
        resetGame();
    }

    private void resetGame() {
        timer.stop();
        elapsedSeconds = 0;
        timerLabel.setText("시간 0");
        gameOver = false;
        firstClick = true;
        revealedSafeCells = 0;

        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                mines[row][col] = false;
                revealed[row][col] = false;
                flagged[row][col] = false;
                adjacentCounts[row][col] = 0;

                JButton button = buttons[row][col];
                button.setEnabled(true);
                button.setText("");
                button.setBackground(null);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }

        updateStatus();
    }

    private void rebuildBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(difficulty.rows, difficulty.cols, 2, 2));

        buttons = new CellButton[difficulty.rows][difficulty.cols];
        mines = new boolean[difficulty.rows][difficulty.cols];
        revealed = new boolean[difficulty.rows][difficulty.cols];
        flagged = new boolean[difficulty.rows][difficulty.cols];
        adjacentCounts = new int[difficulty.rows][difficulty.cols];

        Font cellFont = new Font("Arial", Font.BOLD, difficulty.cols > 16 ? 13 : 18);
        int cellSize = difficulty.cols > 16 ? 28 : 42;
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                CellButton button = new CellButton(row, col);
                button.setFont(cellFont);
                button.setPreferredSize(new Dimension(cellSize, cellSize));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
                button.addActionListener(e -> revealCell(button.row, button.col));
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(button.row, button.col);
                        }
                    }
                });
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void placeMines(int safeRow, int safeCol) {
        int placed = 0;
        while (placed < difficulty.mines) {
            int row = random.nextInt(difficulty.rows);
            int col = random.nextInt(difficulty.cols);
            if (!mines[row][col] && !isProtectedZone(row, col, safeRow, safeCol)) {
                mines[row][col] = true;
                placed++;
            }
        }
    }

    private void calculateAdjacentCounts() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (mines[row][col]) {
                    continue;
                }

                int count = 0;
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        if (isInBounds(r, c) && mines[r][c]) {
                            count++;
                        }
                    }
                }
                adjacentCounts[row][col] = count;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (!isInBounds(row, col) || gameOver || flagged[row][col] || revealed[row][col]) {
            return;
        }

        if (firstClick) {
            placeMines(row, col);
            calculateAdjacentCounts();
            firstClick = false;
            timer.start();
        }

        if (mines[row][col]) {
            buttons[row][col].setText("*");
            buttons[row][col].setBackground(new Color(220, 80, 80));
            revealAllMines();
            gameOver = true;
            timer.stop();
            statusLabel.setText("게임 오버 | 지뢰를 밟았습니다.");
            JOptionPane.showMessageDialog(this, "지뢰를 밟았습니다.");
            return;
        }

        floodReveal(row, col);
        updateStatus();

        if (revealedSafeCells == difficulty.rows * difficulty.cols - difficulty.mines) {
            gameOver = true;
            timer.stop();
            statusLabel.setText("클리어 | 모든 안전 칸을 열었습니다.");
            disableAllCells();
            JOptionPane.showMessageDialog(this, "지뢰찾기 클리어");
        }
    }

    private void floodReveal(int startRow, int startCol) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] current = queue.removeFirst();
            int row = current[0];
            int col = current[1];

            if (!isInBounds(row, col) || revealed[row][col] || flagged[row][col] || mines[row][col]) {
                continue;
            }

            revealed[row][col] = true;
            revealedSafeCells++;

            JButton button = buttons[row][col];
            button.setBackground(new Color(235, 235, 235));
            button.setBorder(BorderFactory.createLoweredBevelBorder());

            int count = adjacentCounts[row][col];
            if (count > 0) {
                button.setText(String.valueOf(count));
                button.setForeground(colorForCount(count));
                continue;
            }

            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (!(r == row && c == col)) {
                        queue.addLast(new int[]{r, c});
                    }
                }
            }
        }
    }

    private void toggleFlag(int row, int col) {
        if (!isInBounds(row, col) || gameOver || revealed[row][col]) {
            return;
        }

        flagged[row][col] = !flagged[row][col];
        buttons[row][col].setText(flagged[row][col] ? "F" : "");
        buttons[row][col].setForeground(new Color(200, 120, 0));
        updateStatus();
    }

    private void revealAllMines() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (mines[row][col]) {
                    JButton button = buttons[row][col];
                    button.setText("*");
                    button.setEnabled(false);
                    if (!revealed[row][col]) {
                        button.setBackground(new Color(240, 170, 170));
                    }
                }
            }
        }
        disableAllCells();
    }

    private void disableAllCells() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void updateStatus() {
        int flagCount = 0;
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (flagged[row][col]) {
                    flagCount++;
                }
            }
        }

        int remaining = difficulty.mines - flagCount;
        String state = gameOver ? statusLabel.getText() : "열린 칸 " + revealedSafeCells;
        statusLabel.setText(state + " | 지뢰 " + difficulty.mines + " | 깃발 " + flagCount + " | 남음 " + remaining);
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < difficulty.rows && col >= 0 && col < difficulty.cols;
    }

    private boolean isProtectedZone(int row, int col, int safeRow, int safeCol) {
        return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
    }

    private Color colorForCount(int count) {
        switch (count) {
            case 1:
                return new Color(30, 90, 200);
            case 2:
                return new Color(40, 140, 60);
            case 3:
                return new Color(210, 50, 50);
            case 4:
                return new Color(95, 40, 160);
            default:
                return new Color(120, 70, 25);
        }
    }

    private static final class Difficulty {
        final String name;
        final int rows;
        final int cols;
        final int mines;

        private Difficulty(String name, int rows, int cols, int mines) {
            this.name = name;
            this.rows = rows;
            this.cols = cols;
            this.mines = mines;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final class DifficultyRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
        ) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Difficulty) {
                Difficulty difficulty = (Difficulty) value;
                label.setText(difficulty.name + " (" + difficulty.rows + "x" + difficulty.cols + ", " + difficulty.mines + ")");
            }
            return label;
        }
    }
}

class BoardPanel extends JPanel {
    private final List<BoardPost> posts = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(
        new Object[]{"번호", "제목", "작성자", "작성일"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable postTable = new JTable(tableModel);
    private final JTextField titleField = new JTextField();
    private final JTextField authorField = new JTextField();
    private final JTextArea contentArea = new JTextArea();
    private final JLabel modeLabel = new JLabel("등록 모드");
    private final JLabel detailTitleLabel = new JLabel("제목: -");
    private final JLabel detailAuthorLabel = new JLabel("작성자: -");
    private final JLabel detailDateLabel = new JLabel("작성일: -");
    private final JTextArea detailContentArea = new JTextArea();
    private final CardLayout editorCardLayout = new CardLayout();
    private final JPanel editorCardPanel = new JPanel(editorCardLayout);

    private int nextId = 1;
    private BoardPost editingPost;

    BoardPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel headerLabel = new JLabel("게시판 CRUD");
        headerLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerLabel, BorderLayout.WEST);

        postTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postTable.setRowHeight(28);
        postTable.getSelectionModel().addListSelectionListener(this::handleSelectionChange);

        JScrollPane tableScrollPane = new JScrollPane(postTable);
        tableScrollPane.setPreferredSize(new Dimension(540, 620));

        JPanel leftPanel = new JPanel(new BorderLayout(8, 8));
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);
        leftPanel.add(createTableButtonPanel(), BorderLayout.SOUTH);

        editorCardPanel.add(createDetailPanel(), "DETAIL");
        editorCardPanel.add(createEditorPanel(), "EDITOR");
        editorCardLayout.show(editorCardPanel, "DETAIL");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, editorCardPanel);
        splitPane.setDividerLocation(560);
        splitPane.setResizeWeight(0.48);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        seedPosts();
        refreshTable();
        if (!posts.isEmpty()) {
            postTable.setRowSelectionInterval(0, 0);
        }
    }

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

        JButton newButton = new JButton("글쓰기");
        JButton editButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        JButton viewButton = new JButton("상세");

        newButton.addActionListener(e -> startCreateMode());
        editButton.addActionListener(e -> startEditMode());
        deleteButton.addActionListener(e -> deleteSelectedPost());
        viewButton.addActionListener(e -> showSelectedPost());

        panel.add(newButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel detailHeader = new JLabel("게시글 상세");
        detailHeader.setFont(new Font("Malgun Gothic", Font.BOLD, 20));

        detailTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        detailAuthorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        detailDateLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));

        JPanel metaPanel = new JPanel(new GridLayout(4, 1, 0, 8));
        metaPanel.add(detailHeader);
        metaPanel.add(detailTitleLabel);
        metaPanel.add(detailAuthorLabel);
        metaPanel.add(detailDateLabel);

        detailContentArea.setEditable(false);
        detailContentArea.setLineWrap(true);
        detailContentArea.setWrapStyleWord(true);
        detailContentArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));

        panel.add(metaPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(detailContentArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEditorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel editorHeader = new JLabel("게시글 편집");
        editorHeader.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        modeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 8));
        formPanel.add(editorHeader);
        formPanel.add(labeledField("제목", titleField));
        formPanel.add(labeledField("작성자", authorField));
        formPanel.add(modeLabel);

        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));

        JButton saveButton = new JButton("저장");
        JButton cancelButton = new JButton("취소");
        saveButton.addActionListener(e -> savePost());
        cancelButton.addActionListener(e -> cancelEditMode());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionPanel.add(cancelButton);
        actionPanel.add(saveButton);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel labeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        JLabel textLabel = new JLabel(label);
        textLabel.setPreferredSize(new Dimension(60, 28));
        panel.add(textLabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void seedPosts() {
        posts.add(new BoardPost(nextId++, "첫 번째 공지", "관리자", "게시판 CRUD 예제입니다.", LocalDateTime.now().minusDays(1)));
        posts.add(new BoardPost(nextId++, "지뢰찾기 안내", "운영자", "왼쪽 클릭은 열기, 오른쪽 클릭은 깃발 표시입니다.", LocalDateTime.now().minusHours(6)));
        posts.sort(Comparator.comparingInt(BoardPost::getId).reversed());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        posts.sort(Comparator.comparingInt(BoardPost::getId).reversed());
        for (BoardPost post : posts) {
            tableModel.addRow(new Object[]{
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getFormattedDate()
            });
        }
    }

    private void handleSelectionChange(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        BoardPost post = getSelectedPost();
        if (post != null) {
            showPostDetail(post);
        } else {
            clearDetail();
        }
    }

    private BoardPost getSelectedPost() {
        int selectedRow = postTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }

        int postId = (Integer) tableModel.getValueAt(selectedRow, 0);
        for (BoardPost post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }

    private void showSelectedPost() {
        BoardPost post = getSelectedPost();
        if (post == null) {
            JOptionPane.showMessageDialog(this, "상세 조회할 게시글을 선택하세요.");
            return;
        }
        showPostDetail(post);
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void showPostDetail(BoardPost post) {
        detailTitleLabel.setText("제목: " + post.getTitle());
        detailAuthorLabel.setText("작성자: " + post.getAuthor());
        detailDateLabel.setText("작성일: " + post.getFormattedDate());
        detailContentArea.setText(post.getContent());
        detailContentArea.setCaretPosition(0);
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void clearDetail() {
        detailTitleLabel.setText("제목: -");
        detailAuthorLabel.setText("작성자: -");
        detailDateLabel.setText("작성일: -");
        detailContentArea.setText("");
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void startCreateMode() {
        editingPost = null;
        modeLabel.setText("등록 모드");
        titleField.setText("");
        authorField.setText("");
        contentArea.setText("");
        editorCardLayout.show(editorCardPanel, "EDITOR");
    }

    private void startEditMode() {
        BoardPost post = getSelectedPost();
        if (post == null) {
            JOptionPane.showMessageDialog(this, "수정할 게시글을 선택하세요.");
            return;
        }

        editingPost = post;
        modeLabel.setText("수정 모드 - 게시글 #" + post.getId());
        titleField.setText(post.getTitle());
        authorField.setText(post.getAuthor());
        contentArea.setText(post.getContent());
        editorCardLayout.show(editorCardPanel, "EDITOR");
    }

    private void cancelEditMode() {
        editingPost = null;
        showSelectedPost();
    }

    private void savePost() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || author.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목, 작성자, 내용을 모두 입력하세요.");
            return;
        }

        if (editingPost == null) {
            BoardPost post = new BoardPost(nextId++, title, author, content, LocalDateTime.now());
            posts.add(post);
        } else {
            editingPost.setTitle(title);
            editingPost.setAuthor(author);
            editingPost.setContent(content);
            editingPost.setUpdatedAt(LocalDateTime.now());
        }

        refreshTable();
        selectLatestPost();
        editingPost = null;
        JOptionPane.showMessageDialog(this, "저장되었습니다.");
    }

    private void selectLatestPost() {
        if (tableModel.getRowCount() == 0) {
            clearDetail();
            return;
        }
        postTable.setRowSelectionInterval(0, 0);
        BoardPost post = getSelectedPost();
        if (post != null) {
            showPostDetail(post);
        }
    }

    private void deleteSelectedPost() {
        BoardPost post = getSelectedPost();
        if (post == null) {
            JOptionPane.showMessageDialog(this, "삭제할 게시글을 선택하세요.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
            this,
            "게시글 #" + post.getId() + "를 삭제하시겠습니까?",
            "삭제 확인",
            JOptionPane.YES_NO_OPTION
        );
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        posts.remove(post);
        refreshTable();
        editingPost = null;

        if (tableModel.getRowCount() > 0) {
            postTable.setRowSelectionInterval(0, 0);
        } else {
            clearDetail();
        }
    }
}

class BoardPost {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime updatedAt;

    BoardPost(int id, String title, String author, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    int getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    String getFormattedDate() {
        return updatedAt.format(FORMATTER);
    }
}

class CellButton extends JButton {
    final int row;
    final int col;

    CellButton(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
