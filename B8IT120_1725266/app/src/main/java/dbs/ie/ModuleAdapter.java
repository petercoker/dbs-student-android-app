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


//public class ViewHolder extends RecyclerView.ViewHolder {
//    public TextView module_name;
//    public TextView course_name;
//    public TextView lecturer;
//    public TextView start;
//    public TextView end;
//    public View layout;
//    public ImageView avatar;
//
//    ItemClickListener itemClickListener;
//
//    public ViewHolder(View v) {
//        super(v);
//        layout = v;
//        module_name = (TextView) v.findViewById(R.id.module_name);
//        course_name = (TextView) v.findViewById(R.id.course_name);
//        lecturer = (TextView) v.findViewById(R.id.lecturer);
//        start = (TextView) v.findViewById(R.id.start);
//        end = (TextView) v.findViewById(R.id.end);
//        avatar = (ImageView) v.findViewById(R.id.avatar);



public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private List<Module> modules;
    private ItemClickListener mItemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView module_name;
        public TextView course_name;
        public TextView lecturer;
        public TextView start;
        public TextView end;
        public View layout;
        public ImageView avatar;
        ItemClickListener itemClickListener;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            module_name = (TextView) v.findViewById(R.id.module_name);
            course_name = (TextView) v.findViewById(R.id.course_name);
            lecturer = (TextView) v.findViewById(R.id.lecturer);
            start = (TextView) v.findViewById(R.id.start);
            end = (TextView) v.findViewById(R.id.end);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION ){
                            mItemClickListener.onListClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }


    public ModuleAdapter(List<Module> dataset) {
        modules = dataset;


    }

    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View moduleView = inflater.inflate(R.layout.module, viewGroup, false);
        ViewHolder module = new ViewHolder(moduleView);
        return module;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        Module module = modules.get(i);

        viewHolder.module_name.setText(module.Module_Code + "-" + module.Module_Name);
        viewHolder.course_name.setText(module.Course + "-" + module.Course_Intake);
        viewHolder.lecturer.setText(module.Lecturer);
        viewHolder.start.setText(module.Start);
        viewHolder.end.setText(module.End);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public interface ItemClickListener {
        void onListClick(int position);
    }


    @Override
    public int getItemCount() {
        return modules.size();
    }

    public void setmItemClickListener(ItemClickListener listener) {

        mItemClickListener = listener;
    }
}