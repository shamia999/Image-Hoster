package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class CommentRepository {

    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment newComment(Comment comment){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
        }
        return comment;

    }

    public Comment getComment(String text){
        EntityManager em = emf.createEntityManager();
       try {
           TypedQuery<Comment> commentTypedQuery = em.createQuery("SELECT c from Comment c where c.text=:text", Comment.class).setParameter("text",text);
           return commentTypedQuery.getSingleResult();
       }
       catch (NoResultException nre){
           return null;
       }
    }


}
