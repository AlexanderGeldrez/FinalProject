package algonquin.cst2335.finalproject;
import java.util.List;

public interface SearchTermDAO {
    // Method to add a search term to the database
    void addSearchTerm(SearchTerm term);

    // Method to delete a search term from the database
    void deleteSearchTerm(String word);

    // Method to retrieve all search terms from the database
    List<SearchTerm> getAllSearchTerms();

    // Method to retrieve a search term by its word from the database
    SearchTerm getSearchTerm(String word);

    // Method to update a search term in the database
    void updateSearchTerm(SearchTerm term);
}

