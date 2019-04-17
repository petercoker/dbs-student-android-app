package dbs.ie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentAdapter extends ContentActivity.Adapter<ContentAdapter.ViewHolder> {

    private List<Object> module_contents; //change
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topic_name;
        public TextView topic_description_name;
        public TextView subtopic_topic_name;
        public TextView subtopic_topic_description_name;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            topic_name = (TextView) v.findViewById(R.id.topic_name);
            topic_description_name = (TextView) v.findViewById(R.id.topic_description_name);
            subtopic_topic_name = (TextView) v.findViewById(R.id.subtopic_topic_name);
            subtopic_topic_description_name = (TextView) v.findViewById(R.id.subtopic_topic_description_name);
        }
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView = inflater.inflate(R.layout.module_content, parent, false);
        ContentAdapter.ViewHolder content = new ContentAdapter(contentView);
        return content;
    }

    public ContentAdapter (List<Object> dataset) {
        modules = dataset;
    }

    @Override
    public void onBindViewHolder(ModuleAdapter.ViewHolder holder, final int position) {
        final Map module = (HashMap) modules.get(position);
        holder.module_name.setText(module.get("Module_Code") + " - " + module.get("Module_Name"));
        holder.course_name.setText(module.get("Course") + " - " + module.get("Course_Intake"));
        holder.lecturer.setText(module.get("Lecturer").toString());
        holder.start.setText(module.get("From").toString());
        holder.end.setText(module.get("To").toString());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(RecyclerActivity.applicationContext.getResources().getString(R.string.api_url)
                    +module.get("Lecturer_Avatar").toString());
            Bitmap avatar = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.avatar.setImageBitmap(avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }
}
