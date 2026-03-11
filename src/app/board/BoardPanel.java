package app.board;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class BoardPanel extends JPanel {
    private final BoardService boardService = new BoardService(new BoardRepository());
    private final BoardTableModel tableModel = new BoardTableModel();
    private final JTable postTable = new JTable(tableModel);
    private final JTextField titleField = new JTextField();
    private final JTextField authorField = new JTextField();
    private final JTextArea contentArea = new JTextArea();
    private final JLabel modeLabel = new JLabel("Create mode");
    private final JLabel detailTitleLabel = new JLabel("Title: -");
    private final JLabel detailAuthorLabel = new JLabel("Author: -");
    private final JLabel detailDateLabel = new JLabel("Updated: -");
    private final JTextArea detailContentArea = new JTextArea();
    private final CardLayout editorCardLayout = new CardLayout();
    private final JPanel editorCardPanel = new JPanel(editorCardLayout);

    private BoardPost editingPost;

    public BoardPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel headerLabel = new JLabel("Board CRUD");
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

        refreshTable();
        if (!tableModel.isEmpty()) {
            postTable.setRowSelectionInterval(0, 0);
        }
    }

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

        JButton newButton = new JButton("Create");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View");

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

        JLabel detailHeader = new JLabel("Post Detail");
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

        JLabel editorHeader = new JLabel("Post Editor");
        editorHeader.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        modeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 8));
        formPanel.add(editorHeader);
        formPanel.add(labeledField("Title", titleField));
        formPanel.add(labeledField("Author", authorField));
        formPanel.add(modeLabel);

        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
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

    private void refreshTable() {
        tableModel.setPosts(boardService.getPosts());
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
        return tableModel.getPostAt(postTable.getSelectedRow());
    }

    private void showSelectedPost() {
        BoardPost post = getSelectedPost();
        if (post == null) {
            JOptionPane.showMessageDialog(this, "Select a post to view.");
            return;
        }
        showPostDetail(post);
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void showPostDetail(BoardPost post) {
        detailTitleLabel.setText("Title: " + post.getTitle());
        detailAuthorLabel.setText("Author: " + post.getAuthor());
        detailDateLabel.setText("Updated: " + post.getFormattedDate());
        detailContentArea.setText(post.getContent());
        detailContentArea.setCaretPosition(0);
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void clearDetail() {
        detailTitleLabel.setText("Title: -");
        detailAuthorLabel.setText("Author: -");
        detailDateLabel.setText("Updated: -");
        detailContentArea.setText("");
        editorCardLayout.show(editorCardPanel, "DETAIL");
    }

    private void startCreateMode() {
        editingPost = null;
        modeLabel.setText("Create mode");
        titleField.setText("");
        authorField.setText("");
        contentArea.setText("");
        editorCardLayout.show(editorCardPanel, "EDITOR");
    }

    private void startEditMode() {
        BoardPost post = getSelectedPost();
        if (post == null) {
            JOptionPane.showMessageDialog(this, "Select a post to edit.");
            return;
        }

        editingPost = post;
        modeLabel.setText("Edit mode - Post #" + post.getId());
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
            JOptionPane.showMessageDialog(this, "Enter title, author, and content.");
            return;
        }

        if (editingPost == null) {
            boardService.createPost(title, author, content);
        } else {
            boardService.updatePost(editingPost, title, author, content);
        }

        refreshTable();
        selectLatestPost();
        editingPost = null;
        JOptionPane.showMessageDialog(this, "Saved.");
    }

    private void selectLatestPost() {
        if (tableModel.isEmpty()) {
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
            JOptionPane.showMessageDialog(this, "Select a post to delete.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
            this,
            "Delete post #" + post.getId() + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        boardService.deletePost(post.getId());
        refreshTable();
        editingPost = null;

        if (!tableModel.isEmpty()) {
            postTable.setRowSelectionInterval(0, 0);
        } else {
            clearDetail();
        }
    }
}
