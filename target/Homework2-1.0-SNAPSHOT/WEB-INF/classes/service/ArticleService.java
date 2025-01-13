package deim.urv.cat.homework2.service;

import java.util.List;
import deim.urv.cat.homework2.model.Article;


public interface ArticleService {

    public List<Article> getByTopicAndUser(String authorId, String... topicsId);
    public List<Article> getArticles();
    public Article getArticleById(int id);
    public int crearArticle(Article nou, String username, String encodedPassword);
}
