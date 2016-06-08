package kr.team12.hackathon.dear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.team12.hackathon.dear.R;
import kr.team12.hackathon.dear.item.MainListItem;

/**
 * Created by HunJin on 2016-05-29.
 */
public class MainListAdapter  extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<MainListItem> item;
    private int layout;

    public MainListAdapter(Context context, int layout, ArrayList<MainListItem> item) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = item;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder.txtListDate = (TextView) convertView.findViewById(R.id.txtItemContext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainListItem listITem = item.get(position);
        viewHolder.txtListDate.setText(listITem.getContext());

        return convertView;
    }

    class ViewHolder {
        private TextView txtListDate;
    }
}
