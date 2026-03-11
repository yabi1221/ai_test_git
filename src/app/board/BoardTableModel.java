package app.board;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class BoardTableModel extends AbstractTableModel {
    private static final String[] COLUMNS = {"ID", "Title", "Author", "Updated"};

    private List<BoardPost> posts = new ArrayList<>();

    void setPosts(List<BoardPost> posts) {
        this.posts = new ArrayList<>(posts);
        fireTableDataChanged();
    }

    BoardPost getPostAt(int row) {
        if (row < 0 || row >= posts.size()) {
            return null;
        }
        return posts.get(row);
    }

    boolean isEmpty() {
        return posts.isEmpty();
    }

    @Override
    public int getRowCount() {
        return posts.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BoardPost post = posts.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return post.getId();
            case 1:
                return post.getTitle();
            case 2:
                return post.getAuthor();
            case 3:
                return post.getFormattedDate();
            default:
                return "";
        }
    }
}
