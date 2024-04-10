package algonquin.cst2335.finalproject;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeMainTest {

    @Rule
    public ActivityScenarioRule<RecipeMain> mActivityScenarioRule =
            new ActivityScenarioRule<>(RecipeMain.class);

    @Test
    public void testSearchOperationSuccessSnackbar() {
        // First, type the search query
        onView(withId(R.id.searchEditText)).perform(replaceText("chicken"), closeSoftKeyboard());

        // Perform the click on the search button
        onView(withId(R.id.searchButton)).perform(click());

        // Use a custom IdlingResource or a delay to wait for the network response
        // Here we're using a simple delay for demonstration, but in real tests, consider using IdlingResources
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now, verify the Snackbar with the success message is shown
        onView(withText("Recipes loaded successfully"))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testViewSavedRecipesDialog() {
        // Click the view saved recipes button
        onView(withId(R.id.viewSavedRecipesButton)).perform(click());
        // Verify the AlertDialog is displayed
        onView(withText("Are you sure you want to view saved recipes?")).check(matches(isDisplayed()));
    }

    @Test
    public void testUndoDeletionOfRecipes() {
        // Click the delete all recipes button
        onView(withId(R.id.deleteAllRecipesButton)).perform(click());
        // Click the UNDO action in Snackbar
        onView(withText("UNDO")).perform(click());
        // You would need a way to verify recipes have been restored, possibly by checking the count of items in the RecyclerView
    }

    @Test
    public void testPersistenceOfLastSearchQuery() {
        // Type a search query
        onView(withId(R.id.searchEditText)).perform(replaceText("pasta"), closeSoftKeyboard());

        // Force activity recreation simulating a configuration change like screen rotation
        mActivityScenarioRule.getScenario().recreate();

        // Verify the search query persists across activity restarts
        onView(withId(R.id.searchEditText)).check(matches(withText("pasta")));
    }

    @Test
    public void testSaveSearchQueryToSharedPreferences() {
        // Type a search query
        onView(withId(R.id.searchEditText)).perform(replaceText("vegan"), closeSoftKeyboard());
        mActivityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED);
        }




}
