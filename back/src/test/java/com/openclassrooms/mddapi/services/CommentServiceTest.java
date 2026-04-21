package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testCreateComment_WhenRequestIsValid_ThenSaveCommentAndReturnMessage() {
        User author = buildUser(1L);
        Article article = buildArticle(10L);
        CreateCommentRequestDto dto = new CreateCommentRequestDto("Very helpful article");
        given(userRepository.findById(1L)).willReturn(Optional.of(author));
        given(articleRepository.findById(10L)).willReturn(Optional.of(article));

        MessageResponse response = commentService.createComment(10L, dto);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(userRepository).findById(1L);
        verify(articleRepository).findById(10L);
        verify(commentRepository).save(commentCaptor.capture());

        Comment savedComment = commentCaptor.getValue();
        assertEquals("Very helpful article", savedComment.getContent());
        assertSame(author, savedComment.getAuthor());
        assertSame(article, savedComment.getArticle());
        assertEquals("Comment added successfully!", response.getMessage());
    }

    @Test
    void testCreateComment_WhenAuthorDoesNotExist_ThenThrowException() {
        CreateCommentRequestDto dto = new CreateCommentRequestDto("Very helpful article");
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> commentService.createComment(10L, dto));

        verify(userRepository).findById(1L);
        verify(articleRepository, never()).findById(any());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void testCreateComment_WhenArticleDoesNotExist_ThenThrowException() {
        User author = buildUser(1L);
        CreateCommentRequestDto dto = new CreateCommentRequestDto("Very helpful article");
        given(userRepository.findById(1L)).willReturn(Optional.of(author));
        given(articleRepository.findById(10L)).willReturn(Optional.empty());

        assertThrows(ArticleNotFoundWithIdException.class, () -> commentService.createComment(10L, dto));

        verify(userRepository).findById(1L);
        verify(articleRepository).findById(10L);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("user@mail.com");
        user.setUsername("user");
        user.setPassword("Password1!");
        return user;
    }

    private Article buildArticle(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setTitle("Title");
        article.setContent("Content");
        article.setAuthor(buildUser(2L));
        Subject subject = new Subject();
        subject.setId(3L);
        subject.setName("Java");
        subject.setDescription("description");
        article.setSubject(subject);
        return article;
    }
}
