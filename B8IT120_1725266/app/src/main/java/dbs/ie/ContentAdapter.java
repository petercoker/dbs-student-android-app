package dbs.ie;

import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.Viewholder> {

    private List<Object> modules;

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView topic_name;
        public TextView topic_description;
        public TextView subtopic_name;
        public TextView subtopic_description;
        public View layout;


        public Viewholder(View v) {
            super(v);
            layout = v;
            topic_name = (TextView) v.findViewById(R.id.topic_name);
            topic_description = (TextView) v.findViewById(R.id.topic_description_name);
            subtopic_name = (TextView) v.findViewById(R.id.subtopic_topic_name);
            subtopic_description = (TextView) v.findViewById(R.id.subtopic_topic_description_name);
        }
    }

    @Override
    public ContentAdapter.Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View moduleView = inflater.inflate(R.layout.module_content, viewGroup, false);
        Viewholder module = new Viewholder(moduleView);
        return module;
    }

    public ContentAdapter(List<Object> dataset) {
        modules = dataset;


    }

    public void onBindViewHolder(ContentAdapter.Viewholder viewholder, int i) {
        final Map module = (HashMap) modules.get(i);
        viewholder.topic_name.setText(module.get("Topic_Name").toString());
        viewholder.topic_description.setText(module.get("TopicDescription").toString());
        viewholder.subtopic_name.setText(module.get("Topic_Name").toString());
        viewholder.subtopic_description.setText(module.get("TopicDescription").toString());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    @Override
    public int getItemCount() {
        return modules.size();
    }
}




