package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;


   @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments",method = RequestMethod.POST)
    public String addComment(@PathVariable(value = "imageId") Integer imageId, @PathVariable(value = "imageTitle") String imageTitle, @RequestParam(name="comment") String newComment, HttpSession session, Model model){
       Comment comment = new Comment();
       User user = (User)session.getAttribute("loggeduser");
       comment.setUser(user);

       Image image = imageService.getImage(imageId);
       comment.setImage(image);

       comment.setCreatedDate(new Date());
       Comment co = commentService.getComment(newComment);
       comment.setText(newComment);

       commentService.newComment(comment);
       model.addAttribute("comments",image);
       model.addAttribute("comment",newComment);

       return "redirect:/images/"+imageId+"/"+imageTitle;

    }


}
