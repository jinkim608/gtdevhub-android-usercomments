package dev.jinkim.usercommentsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jin on 2/4/15.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private List<Comment> comments;

    static class ViewHolder {
        public TextView tvComment;
    }

    public CommentsAdapter(Context context, List<Comment> comments) {
        super(context, R.layout.row_comment, comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_comment, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvComment = (TextView) rowView.findViewById(R.id.tv_comment);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Comment co = comments.get(position);

        holder.tvComment.setText(co.getComment());

        return rowView;
    }
}
