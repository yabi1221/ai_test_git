package app.board;

import java.time.LocalDateTime;
import java.util.List;

class BoardService {
    private final BoardRepository repository;
    private int nextId = 1;

    BoardService(BoardRepository repository) {
        this.repository = repository;
        seedPosts();
    }

    List<BoardPost> getPosts() {
        return repository.findAll();
    }

    BoardPost getPost(int id) {
        return repository.findById(id);
    }

    BoardPost createPost(String title, String author, String content) {
        BoardPost post = new BoardPost(nextId++, title, author, content, LocalDateTime.now());
        repository.save(post);
        return post;
    }

    BoardPost updatePost(BoardPost post, String title, String author, String content) {
        post.setTitle(title);
        post.setAuthor(author);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
        repository.save(post);
        return post;
    }

    void deletePost(int id) {
        repository.deleteById(id);
    }

    private void seedPosts() {
        createPost("Welcome", "Admin", "This is a simple CRUD board example.");
        BoardPost tipsPost = createPost("Minesweeper Tips", "Operator", "Left click opens a cell. Right click toggles a flag.");
        tipsPost.setUpdatedAt(LocalDateTime.now().minusHours(6));
        BoardPost welcomePost = repository.findById(1);
        if (welcomePost != null) {
            welcomePost.setUpdatedAt(LocalDateTime.now().minusDays(1));
        }
    }
}
