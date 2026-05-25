package com.booklovers.book_lovers_project.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.entity.LoanStatus;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.CategoryRepository;
import com.booklovers.book_lovers_project.repository.FavoriteRepository;
import com.booklovers.book_lovers_project.repository.LoanRepository;
import com.booklovers.book_lovers_project.repository.ReviewRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.response.BookStatResponse;
import com.booklovers.book_lovers_project.response.DashboardStatsResponse;
import com.booklovers.book_lovers_project.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final FavoriteRepository favoriteRepository;
	private final LoanRepository loanRepository;

	public DashboardServiceImpl(BookRepository bookRepository, UserRepository userRepository,
			CategoryRepository categoryRepository, ReviewRepository reviewRepository,
			FavoriteRepository favoriteRepository, LoanRepository loanRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.favoriteRepository = favoriteRepository;
		this.loanRepository = loanRepository;
	}

	@Override
	public DashboardStatsResponse getStats() {
		DashboardStatsResponse response = new DashboardStatsResponse();

		response.setTotalBooks(bookRepository.count());
		response.setTotalUsers(userRepository.count());
		response.setTotalCategories(categoryRepository.count());
		response.setTotalReviews(reviewRepository.count());
		response.setTotalFavorites(favoriteRepository.count());
		response.setActiveLoans(loanRepository.countByStatus(LoanStatus.BORROWED));

		response.setTopFavoritedBooks(getTopFavoritedBooks());
		response.setTopRatedBooks(getTopRatedBooks());

		return response;
	}

	private List<BookStatResponse> getTopFavoritedBooks() {
		List<BookEntity> books = bookRepository.findAll();

		List<BookStatResponse> list = new ArrayList<>();
		for (BookEntity book : books) {
			BookStatResponse stat = new BookStatResponse();
			stat.setBookId(book.getId());
			stat.setTitle(book.getTitle());
			stat.setAuthor(book.getAuthor());
			stat.setCount(favoriteRepository.countByBook_Id(book.getId()));
			list.add(stat);
		}

		return list.stream().sorted(Comparator.comparing(BookStatResponse::getCount).reversed()).limit(5).toList();
	}

	private List<BookStatResponse> getTopRatedBooks() {
		List<BookEntity> books = bookRepository.findAll();

		List<BookStatResponse> list = new ArrayList<>();
		for (BookEntity book : books) {
			Double avg = reviewRepository.findAll().stream()
					.filter(r -> r.getBook() != null && r.getBook().getId().equals(book.getId()))
					.mapToInt(r -> r.getRating() == null ? 0 : r.getRating()).average().orElse(0.0);

			BookStatResponse stat = new BookStatResponse();
			stat.setBookId(book.getId());
			stat.setTitle(book.getTitle());
			stat.setAuthor(book.getAuthor());
			stat.setAverageRating(avg);
			list.add(stat);
		}

		return list.stream().sorted(Comparator.comparing(BookStatResponse::getAverageRating).reversed()).limit(5)
				.toList();
	}
}