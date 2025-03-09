package vn.iostar.tuan6.DatabaseHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.iostar.tuan6.R;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NotesModel> noteList;

    public NoteAdapter(Context context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.textViewNote = convertView.findViewById(R.id.editTextName);
            viewHolder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);
            viewHolder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final NotesModel notes = noteList.get(position);
        // Gán dữ liệu cho TextView
        viewHolder.textViewNote.setText(notes.getNameNote());

        // Bắt sự kiện khi bấm vào imageViewEdit
        viewHolder.imageViewEdit.setOnClickListener(v -> {
            // Ép kiểu context thành MainActivity
            if (context instanceof MainActivity) {
                ((MainActivity) context).DialogCapNhatNotes(notes.getNameNote(), notes.getIdNote());
            }
        });

        // Bắt sự kiện khi bấm vào imageViewDelete
        viewHolder.imageViewDelete.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).DialogDelete(notes.getNameNote(), notes.getIdNote());
            }
        });

        return convertView;
    }
}
