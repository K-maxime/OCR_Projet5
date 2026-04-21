package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.UnknowSortException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void testGetAllArticles_WhenSortIsNull_ThenReturnDescendingArticles() {
        User user = buildUser(1L);
        List<Article> expectedArticles = List.of(buildArticle(1L, "A"), buildArticle(2L, "B"));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(articleRepository.findArticlesByUserSubscriptionsDesc(1L)).willReturn(expectedArticles);

        List<Article> result = articleService.getAllArticles(null);

        assertEquals(expectedArticles, result);
        verify(userRepository).findById(1L);
        verify(articleRepository).findArticlesByUserSubscriptionsDesc(1L);
    }

    @Test
    void testGetAllArticles_WhenSortIsAsc_ThenReturnAscendingArticles() {
        User user = buildUser(1L);
        List<Article> expectedArticles = List.of(buildArticle(1L, "A"));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(articleRepository.findArticlesByUserSubscriptionsAsc(1L)).willReturn(expectedArticles);

        List<Article> result = articleService.getAllArticles("asc");

        assertEquals(expectedArticles, result);
        verify(userRepository).findById(1L);
        verify(articleRepository).findArticlesByUserSubscriptionsAsc(1L);
    }

    @Test
    void testGetAllArticles_WhenSortIsUnknown_ThenThrowException() {
        User user = buildUser(1L);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        assertThrows(UnknowSortException.class, () -> articleService.getAllArticles("invalid"));

        verify(userRepository).findById(1L);
        verify(articleRepository, never()).findArticlesByUserSubscriptionsAsc(any());
        verify(articleRepository, never()).findArticlesByUserSubscriptionsDesc(any());
    }

    @Test
    void testGetAllArticles_WhenUserDoesNotExist_ThenThrowException() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> articleService.getAllArticles("desc"));

        verify(userRepository).findById(1L);
        verify(articleRepository, never()).findArticlesByUserSubscriptionsDesc(any());
    }

    @Test
    void testGetArticleById_WhenArticleExists_ThenReturnArticle() {
        Article article = buildArticle(5L, "Mockito");
        given(articleRepository.findById(5L)).willReturn(Optional.of(article));

        Article result = articleService.getArticleById(5L);

        assertSame(article, result);
        verify(articleRepository).findById(5L);
    }

    @Test
    void testGetArticleById_WhenArticleDoesNotExist_ThenThrowException() {
        given(articleRepository.findById(5L)).willReturn(Optional.empty());

        assertThrows(ArticleNotFoundWithIdException.class, () -> articleService.getArticleById(5L));

        verify(articleRepository).findById(5L);
    }

    @Test
    void testGetArticleById_WhenIdIsNull_ThenThrowException() {
        given(articleRepository.findById(null)).willReturn(Optional.empty());

        assertThrows(ArticleNotFoundWithIdException.class, () -> articleService.getArticleById(null));

        verify(articleRepository).findById(null);
    }

    @Test
    void testCreateArticle_WhenRequestIsValid_ThenSaveArticleAndReturnMessage() {
        User author = buildUser(1L);
        Subject subject = buildSubject(2L, "Java");
        CreateArticleRequestDto dto = new CreateArticleRequestDto(2L, "JUnit 5", "Mockito and JUnit");
        given(userRepository.findById(1L)).willReturn(Optional.of(author));
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));

        MessageResponse response = articleService.createArticle(dto);

        ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
        verify(userRepository).findById(1L);
        verify(subjectRepository).findById(2L);
        verify(articleRepository).save(articleCaptor.capture());

        Article savedArticle = articleCaptor.getValue();
        assertEquals("JUnit 5", savedArticle.getTitle());
        assertEquals("Mockito and JUnit", savedArticle.getContent());
        assertSame(author, savedArticle.getAuthor());
        assertSame(subject, savedArticle.getSubject());
        assertEquals("Article created successfully!", response.getMessage());
    }

    @Test
    void testCreateArticle_WhenAuthorDoesNotExist_ThenThrowException() {
        CreateArticleRequestDto dto = new CreateArticleRequestDto(2L, "JUnit 5", "Mockito and JUnit");
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> articleService.createArticle(dto));

        verify(userRepository).findById(1L);
        verify(subjectRepository, never()).findById(any());
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testCreateArticle_WhenSubjectDoesNotExist_ThenThrowException() {
        User author = buildUser(1L);
        CreateArticleRequestDto dto = new CreateArticleRequestDto(2L, "JUnit 5", "Mockito and JUnit");
        given(userRepository.findById(1L)).willReturn(Optional.of(author));
        given(subjectRepository.findById(2L)).willReturn(Optional.empty());

        assertThrows(SubjectNotFoundWithIdException.class, () -> articleService.createArticle(dto));

        verify(userRepository).findById(1L);
        verify(subjectRepository).findById(2L);
        verify(articleRepository, never()).save(any(Article.class));
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("user@mail.com");
        user.setUsername("user");
        user.setPassword("Password1!");
        return user;
    }

    private Subject buildSubject(Long id, String name) {
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(name);
        subject.setDescription("description");
        return subject;
    }

    private Article buildArticle(Long id, String title) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setContent("content");
        article.setAuthor(buildUser(1L));
        article.setSubject(buildSubject(2L, "Java"));
        return article;
    }
}
