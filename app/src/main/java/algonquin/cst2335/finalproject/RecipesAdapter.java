package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> recipesList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Constructor
    public RecipesAdapter(Context context, List<Recipe> recipesList) {
        this.mInflater = LayoutInflater.from(context);
        this.recipesList = recipesList;
    }

    // Inflates the row layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    // Update recipes with a new list of Recipe objects
    public void updateRecipes(List<Recipe> recipes) {
        this.recipesList = recipes;
        notifyDataSetChanged();
    }

    // New method to update the adapter with RecipeEntity objects
    public void updateRecipesFromEntities(List<RecipeEntity> entities) {
        List<Recipe> recipes = new ArrayList<>();
        for (RecipeEntity entity : entities) {
            recipes.add(new Recipe(entity.id, entity.title, entity.image)); // Adjust according to your Recipe class constructor or setters
        }
        this.recipesList = recipes;
        notifyDataSetChanged();
    }





    // Binds data to each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.myTextView.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImage()).into(holder.myImageView);
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.recipeTitle);
            myImageView = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Recipe getItem(int id) {
        return recipesList.get(id);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void clearRecipes() {
        recipesList.clear();
        notifyDataSetChanged();
    }

}