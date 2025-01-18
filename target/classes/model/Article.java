package deim.urv.cat.homework2.model;

import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ElementCollection;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Entity
@XmlRootElement
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String title; 
    private String content; 
    private Boolean isPrivate; 
    private LocalDateTime publicationDate; 
    private int views; 
    private String image; 

    @ManyToOne
    private Usuari author; 

    @ElementCollection
    private List<String> topics; 

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Usuari getAuthor() {
        return author;
    }

    public void setAuthor(Usuari author) {
        this.author = author;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public static Article fromMap(Map<String, Object> data) {
    Article article = new Article();
    article.setId(((Number) data.get("id")).intValue());
    article.setTitle((String) data.get("titol"));
    article.setContent((String) data.get("descripcio"));
    article.setViews(((Number) data.get("nViews")).intValue());
    article.setPublicationDate(LocalDateTime.parse((String) data.get("dataPubli")));
    article.setTopics((List<String>) data.get("topics"));
    article.setImage((String) data.get("imatge"));
     article.setIsPrivate((boolean) data.get("isPrivate"));


    String authorUsername = (String) data.get("nomAut");
    if (authorUsername != null) {
        Usuari author = new Usuari();
        author.setUsername(authorUsername);
        article.setAuthor(author);
    }
    return article;
}

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + (author != null ? author.getUsername() : "unknown") +
                '}';
    }
}
