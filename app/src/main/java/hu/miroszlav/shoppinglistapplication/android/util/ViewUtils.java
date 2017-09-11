package hu.miroszlav.shoppinglistapplication.android.util;


import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class ViewUtils {

    public static RecyclerView.ItemDecoration getItemDecorator(final int marginInDp) {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int pos = parent.getChildAdapterPosition(view);
                if (pos == 0) {
                    outRect.top += dpToPx(marginInDp);
                }
                outRect.bottom += dpToPx(marginInDp);
                outRect.left += dpToPx(marginInDp);
                outRect.right += dpToPx(marginInDp);
            }
        };
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
