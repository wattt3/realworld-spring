package wattt3.realworld.article.infra;

import static wattt3.realworld.article.domain.QArticle.article;
import static wattt3.realworld.article.domain.QTag.tag;
import static wattt3.realworld.user.domain.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.condition.ArticleSearchCondition;
import wattt3.realworld.article.domain.repository.ArticleRepository;

@Repository
public class ArticleRepositoryAdapter implements ArticleRepository {

    private final JpaArticleRepository jpaArticleRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public ArticleRepositoryAdapter(JpaArticleRepository jpaArticleRepository,
            JPAQueryFactory jpaQueryFactory) {
        this.jpaArticleRepository = jpaArticleRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Article save(Article article) {
        return jpaArticleRepository.save(article);
    }

    @Override
    public Article getBySlug(String slug) {
        return jpaArticleRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException(
                        "slug: %s 가 존재하지 않습니다.".formatted(slug)));
    }

    @Override
    public void delete(Article article) {
        jpaArticleRepository.delete(article);
    }

    @Override
    public Page<Article> findByAuthorIds(List<Long> authorIds, Pageable pageable) {
        return jpaArticleRepository.findByAuthorIdIn(authorIds, pageable);
    }

    @Override
    public Page<Article> search(ArticleSearchCondition condition, Pageable pageable) {
        List<Article> articles = jpaQueryFactory.selectFrom(article)
                .leftJoin(article.tags, tag)
                .leftJoin(user)
                .on(article.author.eq(user),
                        article.favoriteUserIds.isEmpty().or(article.favoriteUserIds.contains(
                                user.id)))
                .where(eqTagName(condition.tag()), eqAuthor(condition.author()),
                        eqFavorited(condition.favorited()))
                .orderBy(article.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(article.count())
                .from(article)
                .where(eqTagName(condition.tag()), eqAuthor(condition.author()),
                        eqFavorited(condition.favorited()));

        return PageableExecutionUtils.getPage(articles, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqTagName(String tagName) {
        if (StringUtils.isNullOrEmpty(tagName)) {
            return null;
        }
        return tag.name.eq(tagName);
    }

    private BooleanExpression eqAuthor(String authorName) {
        if (StringUtils.isNullOrEmpty(authorName)) {
            return null;
        }
        return user.username.eq(authorName);
    }

    private BooleanExpression eqFavorited(String favoritedUserName) {
        if (StringUtils.isNullOrEmpty(favoritedUserName)) {
            return null;
        }
        return user.username.eq(favoritedUserName);
    }

}
