package com.testapp.androidapptestdb.helpers;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.testapp.androidapptestdb.R;
import com.testapp.androidapptestdb.viewholders.GroceryListItemVH;
import com.testapp.androidapptestdb.viewholders.GroceryListVH;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder != null) {
            View viewToSwipe = null;
            if (viewHolder instanceof GroceryListVH)
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            else if(viewHolder instanceof GroceryListItemVH){
                viewToSwipe = (viewHolder).itemView.findViewById(R.id.parent);
            }
            else
                viewToSwipe = null;

            if (viewToSwipe != null) {
                getDefaultUIUtil().onDraw(c, recyclerView, viewToSwipe, dX, dY,
                        actionState, isCurrentlyActive);
            }
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            View viewToSwipe = null;
            if (viewHolder instanceof GroceryListVH)
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            else if(viewHolder instanceof GroceryListItemVH){
                viewToSwipe = (viewHolder).itemView.findViewById(R.id.parent);
            }
            else
                viewToSwipe = null;

            if (viewToSwipe != null) {
                getDefaultUIUtil().clearView(viewToSwipe);
            }
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder != null) {
            View viewToSwipe = null;
            if (viewHolder instanceof GroceryListVH)
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            else if(viewHolder instanceof GroceryListItemVH){
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            }
            else
                viewToSwipe = null;

            if (viewToSwipe != null) {
                getDefaultUIUtil().onDrawOver(c, recyclerView, viewToSwipe, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View viewToSwipe = null;
            if (viewHolder instanceof GroceryListVH)
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            else if(viewHolder instanceof GroceryListItemVH){
                viewToSwipe = ( viewHolder).itemView.findViewById(R.id.parent);
            }
            else
                viewToSwipe = null;

            if (viewToSwipe != null) {
                getDefaultUIUtil().onSelected(viewToSwipe);
            }
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }


    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

}
