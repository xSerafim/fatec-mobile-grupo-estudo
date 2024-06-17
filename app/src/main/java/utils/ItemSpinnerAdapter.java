package utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ItemSpinnerAdapter extends ArrayAdapter<ItemSpinner> {

    public ItemSpinnerAdapter(Context context, List<ItemSpinner> itens) {
        super(context, 0, itens);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return criarView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return criarView(position, convertView, parent);
    }

    private View criarView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        ItemSpinner item = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        if (item != null) {
            textView.setText(item.getNome());
        }

        return convertView;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemSpinnerAdapter{} " + super.toString();
    }
}
