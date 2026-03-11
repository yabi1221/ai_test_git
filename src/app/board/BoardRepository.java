package app.board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class BoardRepository {
    private final List<BoardPost> posts = new ArrayList<>();

    List<BoardPost> findAll() {
        List<BoardPost> sortedPosts = new ArrayList<>(posts);
        sortedPosts.sort(Comparator.comparingInt(BoardPost::getId).reversed());
        return sortedPosts;
    }

    void save(BoardPost post) {
        posts.removeIf(item -> item.getId() == post.getId());
        posts.add(post);
    }

    void deleteById(int id) {
        posts.removeIf(post -> post.getId() == id);
    }

    BoardPost findById(int id) {
        for (BoardPost post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }
}
