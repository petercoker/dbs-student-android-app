package dbs.ie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContentAdapter extends ContentActivity.Adapter<ContentAdapter.ViewHolder> {

    private List<Object> modules; //change
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView module_name;
        public TextView course_name;
        public TextView lecturer;
        public TextView start;
        public TextView end;
        public View layout;
        public ImageView avatar;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            module_name = (TextView) v.findViewById(R.id.module_name);
            course_name = (TextView) v.findViewById(R.id.course_name);
            lecturer = (TextView) v.findViewById(R.id.lecturer);
            start = (TextView) v.findViewById(R.id.start);
            end = (TextView) v.findViewById(R.id.end);
            avatar = (ImageView) v.findViewById(R.id.avatar);
        }
    }
}
